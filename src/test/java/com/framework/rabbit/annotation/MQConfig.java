package com.framework.rabbit.annotation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value={"classpath:rabbit.properties"})
public class MQConfig {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	
	@Value("${rabbit.addresses}")
	private String addresses;
	@Value("${rabbit.host}")
	private String host;
	@Value("${rabbit.port}")
	private String port;
	@Value("${rabbit.username}")
	private String username;
	@Value("${rabbit.password}")
	private String password;

	
	@Bean
	public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(addresses);
        factory.setUsername(username);
        factory.setPassword(password);
        logger.info("ConnectionFactory [ addresses : {}, username : {}, password : {} ]",addresses,username,password);
        logger.info("ConnectionFactory : {}",factory);
        return factory;
    }
	
	/**
	 * spring-amqp二个核心类RabbitAdmin和RabbitTemplate类
	 * 1.RabbitAdmin类完成对Exchange，Queue，Binging的操作，在容器中管理了RabbitAdmin类的时候，可以对Exchange，Queue，Binging进行自动声明。
	 * 2.RabbitTemplate类是发送和接收消息的工具类。
	 */
	@Bean
	public RabbitAdmin rabbitAdmin(){
        return new RabbitAdmin(connectionFactory());
    }
	
	@Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }
	
	/**
	 * SimpleMessageListenerContainer详解
	 * 
	 * 使用容器的方式进行消费
	 * 认识一个接口org.springframework.amqp.rabbit.listener.MessageListenerContainer，
	 * 其默认实现类org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer。
	 * MessageListenerContainer#setMessageListener方法，接收的参数类型
	 * org.springframework.amqp.core.MessageListener或者org.springframework.amqp.rabbit.core.ChannelAwareMessageListener接口
	 * 
	 * 同一个queue上有多个消费者的时候，只会有一个消费者收到消息，一般是多个消费者轮流收到消息。
	 */
	int count=0;
	@Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
		
		
		
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("sltas.info");
        /**
         * 压缩
         * 
         * 这些扩展点用于压缩等功能，为此，MessagePostProcessor提供了几个扩展点：
         * 
         * GZipPostProcessor
         * ZipPostProcessor
         * 用于在发送之前压缩消息，以及
         * 
         * GUnzipPostProcessor
         * UnzipPostProcessor
         * 用于解压缩收到的消息。
         * 
         * 类似地，SimpleMessageListenerContainer还有一种setAfterReceivePostProcessors()方法，允许在容器接收消息之后执行解压缩。
         */
        container.setAfterReceivePostProcessors(message -> {
        	//后置处理器 接收到消息进行数据解析
            message.getMessageProperties().getHeaders().put("desc",10);
            return message;
        });
        
        /**
         * container.setConsumerTagStrategy可以设置消费者的 Consumer_tag
         * container.setConsumerArguments可以设置消费者的 Arguments
         */
        //设置消费者的consumerTag_tag
        container.setConsumerTagStrategy(queue -> "order_queue_"+(++count));
        //设置消费者的Arguments
        Map<String, Object> args = new HashMap<>();
        args.put("module","订单模块");
        args.put("fun","发送消息");
        container.setConsumerArguments(args);
        //设置消费者PrefetchCount
//        container.setPrefetchCount(1);
        //设置并发消费者setConcurrentConsumers
        /**
         * setConcurrentConsumers设置多个并发消费者一起消费，并支持运行时动态修改。
         * setMaxConcurrentConsumers设置最多的并发消费者。
         * 
         * 如果maxConcurrentConsumers尚未达到并且现有消费者已连续10个周期处于活动状态并且自上次消费者启动以来已经过了至少10秒，则启动新的消费者。如果消费者在txSize* receiveTimeout毫秒内收到至少一条消息，则认为该消费者是活动的。
         * 使用默认设置，减少消费者的算法的工作方式如下：
         * 如果有多个concurrentConsumers运行并且消费者检测到10个连续超时（空闲）并且最后一个消费者在至少60秒前停止，则将停止消费者。超时取决于receiveTimeout和txSize属性。如果消费者在txSize* receiveTimeout毫秒内没有收到消息，则认为该消费者处于空闲状态。因此，在默认超时（1秒）和txSize4的情况下，将在40秒的空闲时间后停止消费者（4次超时对应1次空闲检测）。
         * 
         */
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(10);
        
//        container.setMessageListener((ChannelAwareMessageListener)(message, channel) -> {
//            logger.info("====接收到消息=====");
//            logger.info("MessageProperties : {}",message.getMessageProperties());
//            logger.info("ConsumerQueue : {}",message.getMessageProperties().getConsumerQueue());
//            logger.info("Body : {}",new String(message.getBody()));
//        });
    
//        container.setMessageListener(new MessageListenerAdapter(new MessageHandler()));
        
        /*************************************MessageListenerAdapter*******************************************/
        
        /**
         * MessageListenerAdapter详解
         * 消息监听适配器（adapter），通过反射将消息处理委托给目标监听器的处理方法，并进行灵活的消息类型转换。允许监听器方法对消息内容类型进行操作，完全独立于Rabbit API。
         * 
         * 
         * 默认情况下，传入Rabbit消息的内容在被传递到目标监听器方法之前被提取，以使目标方法对消息内容类型进行操作以String或者byte类型进行操作，而不是原始Message类型。 （消息转换器）
         * 消息类型转换委托给MessageConverter接口的实现类。 默认情况下，将使用SimpleMessageConverter。 （如果您不希望进行这样的自动消息转换，
         * 那么请自己通过#setMessageConverter MessageConverter设置为null）
         * 
         * 使用MessageListenerAdapter处理器进行消息队列监听处理，如果容器没有设置setDefaultListenerMethod，则处理器中默认的处理方法名是handleMessage，
         * 如果设置了setDefaultListenerMethod，则处理器中处理消息的方法名就是setDefaultListenerMethod方法参数设置的值。
         * 也可以通过setQueueOrTagToMethodName方法为不同的队列设置不同的消息处理方法。
         * 
         */
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageHandler());
        //设置处理器的消费消息的默认方法,如果没有设置，那么默认的处理器中的默认方式是handleMessage方法
//        adapter.setDefaultListenerMethod("onMessage");
        
        /**
         * 1.MessageConverter可以把java对象转换成Message对象，也可以把Message对象转换成java对象
         * 2.MessageListenerAdapter内部通过MessageConverter把Message转换成java对象，然后找到相应的处理方法，参数为转换成的java对象。
         * 3.SimpleMessageConverter处理逻辑：
         * 如果content_type是以text开头，则把消息转换成String类型
         * 如果content_type的值是application/x-java-serialized-object则把消息序列化为java对象，否则，把消息转换成字节数组。
         */
        //指定Json转换器
        adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        
        container.setMessageListener(adapter);
        
        /**
         * 总结：
         * MessageListenerAdapter
         * 1.可以把一个没有实现MessageListener和ChannelAwareMessageListener接口的类适配成一个可以处理消息的处理器
         * 2.默认的方法名称为：handleMessage，可以通过setDefaultListenerMethod设置新的消息处理方法
         * 3.MessageListenerAdapter支持不同的队列交给不同的方法去执行。使用setQueueOrTagToMethodName方法设置，当根据queue名称没有找到匹配的方法的时候，就会交给默认的方法去处理。
         */
        
        return container;
    }
	
}
