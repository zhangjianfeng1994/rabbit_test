package com.sltas.example.spring.rabbit.unofficial_6.Publisher_Confirms;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUri("amqp://zhihao.miao:123456@192.168.1.131:5672");
        factory.setHost("127.0.0.1");
        factory.setPublisherConfirms(true);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    /**
     * 
     * <p>
     * Title: rabbitTemplate
     * </p>
     * <p>
     * Description: 原理其实和java client是一样的，
     * 我们在发送消息的时候落地本地的消息表（有表示confirm字段），然后进行回调确认的方法中进行状态的更新，最后轮询表中状态不正确的消息进行轮询重发。
     * </p>
     * @param @param connectionFactory
     * @param @return 
     * @return RabbitTemplate
     * @throws
     * @author 周顺宇 
     * @date 2018年10月10日 下午5:49:50
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
             * @param ack
             * @param cause
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("=====消息进行消费了======");
                if(ack){
                    System.out.println("消息id为: "+correlationData+"的消息，已经被ack成功");
                }else{
                    System.out.println("消息id为: "+correlationData+"的消息，消息nack，失败原因是："+cause);
                }
            }
        });
        return rabbitTemplate;
    }
    
    /**
     * 步骤
     * 
     * 在容器中的ConnectionFactory实例中加上setPublisherConfirms属性
     * factory.setPublisherConfirms(true);
     * 在RabbitTemplate实例中增加setConfirmCallback回调方法。
     * 发送消息的时候，需要指定CorrelationData，用于标识该发送的唯一id。
     * 对比与java client的publisher confirm：
     * 1.spring amqp不支持批量确认，底层的rabbitmq java client方式支持批量确认。
     * 2.spring amqp提供的方式更加的简单明了。
     * 
     */

}
