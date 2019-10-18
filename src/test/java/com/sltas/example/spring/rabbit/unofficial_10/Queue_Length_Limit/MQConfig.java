package com.sltas.example.spring.rabbit.unofficial_10.Queue_Length_Limit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
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
        return rabbitTemplate;
    }

    /**
     * Max length(x-max-length) 用来控制队列中消息的数量。
     * 如果超出数量，则先到达的消息将会被删除掉。
     * 
     * Max length bytes(x-max-length-bytes) 用来控制队列中消息总的大小。
     * 如果超过总大小，则最先到达的消息将会被删除，直到总大小不超过x-max-length-byte为止。
     */
    
    @Bean
    public Queue queue1(){
       Map<String, Object> arguments = new HashMap<>();
       //表示队列中最多存放三条消息
       arguments.put("x-max-length",3);
        return new Queue("weixin",true,false,false,arguments);
    }

    @Bean
    public Queue queue2(){
        Map<String, Object> arguments = new HashMap<>();
        //表示队列中最多存放三条消息
        arguments.put("x-max-length-bytes",10);
        return new Queue("eamil",true,false,false,arguments);
    }
}
