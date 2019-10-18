package com.framework.rabbit.listener;

import java.util.HashMap;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeclareConfig {
	
   
    @Bean
    public Queue queue(){
        return new Queue("sltas.error",true);
    }
    
    /**
	 * 一般alternate-exchange属性的值最好是fanout类型的exchange，否则还会根据route key与alternate-exchange属性的exchange进行匹配再去路由。
	 * 而如果指定了fanout类型的exchange，不需要去匹配routekey。
	 */
    @Bean
    public Exchange fanoutExchange(){
        return new FanoutExchange("sltas.alternate.exchange",true,false,null);
    }
    
    @Bean
    public Binding binding(){
        return new Binding("sltas.error",Binding.DestinationType.QUEUE,"sltas.alternate.exchange","",new HashMap<>());
    }

    
//    @Bean
//    public Exchange directExchange(){
//        return new DirectExchange("sltas.alternate.exchange.direct",true,false,null);
//    }
//    
//    @Bean
//    public Binding bindingDirect(){
//        return new Binding("sltas.error",Binding.DestinationType.QUEUE,"sltas.alternate.exchange.direct","sltas.info.routingKey.1",new HashMap<>());
//    }
    
}
