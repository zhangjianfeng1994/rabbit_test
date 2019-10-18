package com.sltas.example.spring.rabbit.unofficial_1.basics;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;


/**
 * <p>
 * Title: MQConfig.java
 * </p>
 * <p>
 * Description: MQ配置文件
 * 
 * @ComponentScan("包路径") 会自动扫描包路径下面的所有@Controller、@Service、@Repository、@Component 的类
 * 	includeFilters 指定包含扫描的内容
 * 	excludeFilters 指定不包含的内容
 * 	@Filter 指定过滤规则，type指定扫描的规则（注解，正则，自定义，ASPECTJ表达式），classes指定的扫描的规则类@
 * 
 * 如果我们在使用自定义（includeFilters = @Filter(type = FilterType.CUSTOM, classes = {自己定义的类})）过滤规则的时候，我们自己定义的类要实现TypeFilter接口，例如：
 * 
 * 参考资料：https://blog.csdn.net/u010285684/article/details/79621548
 * 
 * @Configuation + @Bean
 * 
 * (1)、@Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同； 
 * (2)、@Bean注解默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域； 
 * (3)、既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Ripository等注解注册bean，当然需要配置@ComponentScan注解进行自动扫描。
 * 
 * @Configuation总结
 * 
 * @Configuation等价于<Beans></Beans>
 * @Bean等价于<Bean></Bean>
 * @ComponentScan等价于<context:component-scan base-package="com.dxz.demo"/>
 * 
 * 参考资料：https://www.cnblogs.com/duanxz/p/7493276.html
 * 
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月27日 上午9:46:30  
 */
@Configuration
public class MQConfig {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Bean
	public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        return factory;
    }

	/**
	 * spring-amqp二个核心类RabbitAdmin和RabbitTemplate类
	 * 1.RabbitAdmin类完成对Exchange，Queue，Binging的操作，在容器中管理了RabbitAdmin类的时候，可以对Exchange，Queue，Binging进行自动声明。
	 * 2.RabbitTemplate类是发送和接收消息的工具类。
	 */
	@Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }
	
	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
       //设置Exchange默认操作的exchange和routingkey
        rabbitTemplate.setExchange("sltas.direct.exchange");
        rabbitTemplate.setRoutingKey("sltas.debug");
        return rabbitTemplate;
    }
	
	/*
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //监听队列zhihao.user.queue，监听队列可以多个，参数类型是String[]
        container.setQueueNames("zhihao.user.queue");
        container.setMessageListener(new MessageListener() {
            //具体的消费逻辑
            @Override
            public void onMessage(Message message) {
                System.out.println("====接收到消息=====");
                System.out.println(message.getMessageProperties());
                System.out.println(new String(message.getBody()));
            }
        });
        return container;
    }
    */

	
	/**
	 * 消息。服务器和应用程序之间传送的数据，本质上就是一段数据，由Properties和Payload(body)组成。
	 * 
	 * Delivery mode：是否持久化，如果未设置持久化，转发到queue中并未消费则重启服务或者服务宕机则消息丢失。
	 * Headers：头信息，是由一个或多个健值对组成的，当固定的Properties不满足我们需要的时候，可以自己扩展。
	 * 
	 * Properties（属性）
	 * content_type：传输协议
	 * content_encoding：编码方式
	 * priority：优先级
	 * correlation_id：rpc属性，请求的唯一标识。
	 * reply_to：rpc属性，
	 * expiration：消息的过期时间
	 * message_id：消息的id
	 * timestamp：消息的时间戳
	 * 
	 * 如何保证消息的不丢失，三个地方做到持久化。
	 * 
	 * Exchange需要持久化。
	 * Queue需要持久化。
	 * Message需要持久化。
	 */
    
	/**
	 * <p>
	 * Title: messageListenerContainer
	 * </p>
	 * <p>
	 * Description: 消息的消费
	 * 
	 * 
	 * </p>
	 * @param @param connectionFactory
	 * @param @return 
	 * @return SimpleMessageListenerContainer
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月27日 下午4:29:44 
	 */
	@Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
		
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
		
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //队列可以是多个，参数是String的数组
        container.setQueueNames("sltas.info");
        //设置autoStartUp为false表示SimpleMessageListenerContainer没有启动
//        container.setAutoStartup(false);
        
        
        //SimpleMessageListenerContainer运行时动态的添加监听队列
        container.addQueueNames("sltas.debug");
        container.addQueueNames("sltas.error");
        //SimpleMessageListenerContainer运行时后动态的移除监听队列
//        container.removeQueueNames("sltas.error");
        //SimpleMessageListenerContainer增加后置处理
        //后置处理器，接收到的消息都添加了Header请求头
        
        /**
         * 3.1.8修改消息 - 压缩等
         * 
         * 存在许多扩展点，您可以在将消息发送到RabbitMQ之前或者在接收到消息之后立即对消息执行某些处理。
         * 
         * 从第3.1.7节“消息转换器”中可以看到，操作中有一个这样的扩展点AmqpTemplate convertAndReceive，您可以在其中提供MessagePostProcessor。例如，在转换POJO之后，您MessagePostProcessor可以在其上设置自定义标题或属性Message。
         * 
         * 从版本1.4.2开始，添加了额外的扩展点RabbitTemplate- setBeforePublishPostProcessors()和setAfterReceivePostProcessors()。第一个使后处理器能够在发送到RabbitMQ之前立即运行。使用批处理时（参见 “批处理”一节），在批处理组装之后和批处理发送之前调用此方法。收到消息后立即调用第二个。
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
        container.setPrefetchCount(1);
        //设置并发消费者setConcurrentConsumers
        /**
         * setConcurrentConsumers设置多个并发消费者一起消费，并支持运行时动态修改。
         * setMaxConcurrentConsumers设置最多的并发消费者。
         */
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(5);
        
        //设置消费监听器
        container.setMessageListener(new ChannelAwareMessageListener(){
            @Override
            //得到了Channel参数，具体使用会在下面的博客详细讲解
            public void onMessage(Message message, Channel channel) throws Exception {
                logger.info("====接收到消息=====");
                logger.info("MessageProperties : {}",message.getMessageProperties());
                logger.info("ConsumerQueue : {}",message.getMessageProperties().getConsumerQueue());
                logger.info("Body : {}",new String(message.getBody()));
            }
        });
        
        
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
        adapter.setDefaultListenerMethod("onMessage");
//        Map<String, String> queueOrTagToMethodName = new HashMap<>();
//        queueOrTagToMethodName.put("sltas.info","onorder");
//        queueOrTagToMethodName.put("sltas.debug","onpay");
//        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        
        
        /**
         * 1.MessageConverter可以把java对象转换成Message对象，也可以把Message对象转换成java对象
         * 2.MessageListenerAdapter内部通过MessageConverter把Message转换成java对象，然后找到相应的处理方法，参数为转换成的java对象。
         * 3.SimpleMessageConverter处理逻辑：
         * 如果content_type是以text开头，则把消息转换成String类型
         * 如果content_type的值是application/x-java-serialized-object则把消息序列化为java对象，否则，把消息转换成字节数组。
         */
        //指定消息转换器(自定义)
//        adapter.setMessageConverter(new TestMessageConverter());
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
	
    int count=0;
	/**
	 * 原理分析
	 * 
	 * 稍微分析一下原理
	 * org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer接口，它继承AbstractMessageListenerContainer类，
	 * 实现SmartLifecycle接口然后继承Lifecycle接口，意味着一旦SimpleMessageListenerContainer实例被spring容器管理，其生命周期就托管与spring容器来管理了，
	 * 意味着当spring容器运行起来的时候，SimpleMessageListenerContainer容器启动，spring容器关闭的时候，SimpleMessageListenerContainer容器也关闭了。
	 * 
	 * 设置在spring容器初始化的时候设置SimpleMessageListenerContainer不启动，（container.setAutoStartup(false);）
	 * 
	 * 在spring容器中启动SimpleMessageListenerContainer
	 * context.getBean(SimpleMessageListenerContainer.class).start();
	 * 
	 * SimpleMessageListenerContainer可以托管到spring容器中，由spring容器进行SimpleMessageListenerContainer的生命周期管理，
	 * 默认情况下spring容器启动的时候，启动SimpleMessageListenerContainer，spring容器关闭，会stop掉SimpleMessageListenerContainer，
	 * 也可以设置SimpleMessageListenerContainer手动启动(context.getBean(SimpleMessageListenerContainer.class).start();)。
	 */
    
    
    
}
