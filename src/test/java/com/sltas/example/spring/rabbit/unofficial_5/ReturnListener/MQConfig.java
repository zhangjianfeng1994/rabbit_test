package com.sltas.example.spring.rabbit.unofficial_5.ReturnListener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Title: MQConfig.java
 * </p>
 * <p>
 * Description: ReturnListener
 * 
 * 
 * mandatory
 * 
 * This flag tells the server how to react if the message cannot be routed to a queue.if this flag is set,the server will return an unroutable message with a return method.if this flag is zero,the server silently drops the message
 * 如果mandatory有设置，则当消息不能路由到队列中去的时候，会触发return method。如果mandatory没有设置，则当消息不能路由到队列的时候，server会删除该消息。
 * 
 * immediate
 * 
 * This flag tell the server how to react if the message cannot be routed to a queue consumer immediately.if the flag is set ,the server will return an undeliverable message with a return method.if this flag is zero,the server will queue the message ,but with no guarantee that it will ever be consumed.
 * 如果有设置immediate，则当消息路由到队列的时候，没有消费者的时候，会触发return method。如果immediate标识是0，则服务器就会将消息加入队列，但是不能保证这个消息能被消费。
 * 
 * 总结
 * 
 * 设置factory.setPublisherReturns(true);
 * rabbitTemplate.setMandatory(true);或rabbitTemplate.setMandatoryExpression(new SpelExpressionParser().parseExpression("(1+2) > 2")); //表达式的值为true
 * 才会触发returnCallback回调方法的执行。
 * 
 * 
 * 通过将CachingConnectionFactory's publisherConfirms和publisherReturns属性分别设置为'true' 来支持确认和返回的消息。
 * 
 * 设置这些选项后，Channel工厂创建的s包装在一个PublisherCallbackChannel，用于方便回调。当获得这样的信道时，客户端可以注册PublisherCallbackChannel.Listener与所述Channel。
 * 该PublisherCallbackChannel实现包含将确认/返回路由到适当的侦听器的逻辑。以下各节将进一步解释这些功能。
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月10日 下午4:26:55  
 */
@Configuration
public class MQConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPublisherReturns(true);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //rabbitTemplate.setMandatoryExpression(new SpelExpressionParser().parseExpression("(1+2) > 3")); //表达式的值为false
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback(){
            @Override
            public void returnedMessage(Message message,
                                        int replyCode,
                                        String replyText,
                                        String exchange,
                                        String routingKey){
                System.out.println("============returnedMessage==method=========");
                System.out.println("replyCode: "+replyCode);
                System.out.println("replyText: "+replyText);
                System.out.println("exchange: "+exchange);
                System.out.println("routingKey: "+routingKey);
            }
        });
        return rabbitTemplate;
    }
}
