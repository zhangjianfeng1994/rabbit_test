package com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.emit;

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
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.recv.Order;

@Configuration
public class MQConfig {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Bean
	public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        return factory;
    }

	@Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }
	
	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }
	
	@Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
		
		
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("sltas.info","sltas.debug","sltas.error");
//        container.setConsumerTagStrategy(queue -> "order_queue_"+(++count));
//        Map<String, Object> args = new HashMap<>();
//        args.put("module","订单模块");
//        args.put("fun","发送消息");
//        container.setConsumerArguments(args);
//        container.setPrefetchCount(1);
//        container.setConcurrentConsumers(5);
//        container.setMaxConcurrentConsumers(5);
        
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageHandler());
        
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        
        //消费端配置映射
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("order",Order.class);
        
        DefaultJackson2JavaTypeMapper jackson2JavaTypeMapper = new DefaultJackson2JavaTypeMapper();
        jackson2JavaTypeMapper.setIdClassMapping(idClassMapping);
        
        logger.info("在jackson2JsonMessageConverter转换器中指定映射配置");
        
//        adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        jackson2JsonMessageConverter.setJavaTypeMapper(jackson2JavaTypeMapper);
        adapter.setMessageConverter(jackson2JsonMessageConverter);
        
        //ContentTypeDelegatingMessageConverter
        TextMessageConverter textMessageConverter = new TextMessageConverter();
        /**
         * ContentTypeDelegatingMessageConverter是一个代理的MessageConverter。
         * ContentTypeDelegatingMessageConverter本身不做消息转换的具体动作，而是将消息转换委托给具体的MessageConverter。我们可以设置COntentType和MessageConverter的映射关系。
         * ContentTypeDelegatingMessageConverter还有一个默认的MessageConverter，也就是说当根据ContentType没有找到映射的MessageConverter的时候，就会使用默认的MessageConverter。
         */
        ContentTypeDelegatingMessageConverter contentTypeDelegatingMessageConverter = new ContentTypeDelegatingMessageConverter();
        contentTypeDelegatingMessageConverter.addDelegate("text",textMessageConverter);
        contentTypeDelegatingMessageConverter.addDelegate("html/text",textMessageConverter);
        contentTypeDelegatingMessageConverter.addDelegate("xml/text",textMessageConverter);
        contentTypeDelegatingMessageConverter.addDelegate("text/plain",textMessageConverter);

        contentTypeDelegatingMessageConverter.addDelegate("json",jackson2JsonMessageConverter);
        contentTypeDelegatingMessageConverter.addDelegate("application/json",jackson2JsonMessageConverter);

        contentTypeDelegatingMessageConverter.addDelegate("image/jpg",new JPGMessageConverter());
        contentTypeDelegatingMessageConverter.addDelegate("image/jepg",new JPGMessageConverter());
        contentTypeDelegatingMessageConverter.addDelegate("image/png",new JPGMessageConverter());

        
        adapter.setMessageConverter(contentTypeDelegatingMessageConverter);
        
        //设置处理器的消费消息的默认方法,如果没有设置，那么默认的处理器中的默认方式是handleMessage方法
        adapter.setDefaultListenerMethod("onMessage");
        container.setMessageListener(adapter);
        return container;
    }
	
    int count=0;
    
    
    
}
