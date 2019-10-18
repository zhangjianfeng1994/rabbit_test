package com.sltas.example.spring.rabbit.unofficial_8.Alternate_Exchange.B;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    public Exchange fanoutExchange(){
    	/**
    	 * 一般alternate-exchange属性的值最好是fanout类型的exchange，否则还会根据route key与alternate-exchange属性的exchange进行匹配再去路由。
    	 * 而如果指定了fanout类型的exchange，不需要去匹配routekey。
    	 */
        return new FanoutExchange("sltas.alternate.exchange",true,false,null);
    }
    
    @Bean
    public Binding binding1(){
        return new Binding("sltas.error",Binding.DestinationType.QUEUE,"sltas.alternate.exchange","abc",new HashMap<>());
    }
    
    @Bean
    public Exchange exchange(){
        Map<String,Object> argsMap = new HashMap<>();
        /**
         * alternate-exchange
         * 如果对该交易所的消息不能被路由，请将它们发送到此处指定的交换交换。（设置“alternate-exchange”的论点。）
         */
        argsMap.put("alternate-exchange","sltas.alternate.exchange");
        return new TopicExchange("sltas.hehe.exchange",true,false,argsMap);
    }


    @Bean
    public Binding binding(){
        return new Binding("sltas.info",Binding.DestinationType.QUEUE,"sltas.hehe.exchange","sltas.*",new HashMap<>());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}
