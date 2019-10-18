package com.sltas.example.spring.rabbit.unofficial_1.basics;

import java.util.HashMap;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Title: DeclareConfig.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * 
 * 总结
 * 
 * 自动声明的一些条件
 * 
 * 要有连接（对rabbitmq的连接）
 * 容器中要有org.springframework.amqp.rabbit.core.RabbitAdmin的实例
 * RabbitAdmin的autoStartup属性必须为true（默认就是true）。
 * 如果ConnectionFactory使用的是CachingConnectionFactory，则cacheMode必须是CachingConnectionFactory.CacheMode.CHANNEL（默认）。
 * 所要声明的组件(Queue，Exchange和Binding)的shouldDeclare必须是true（默认就是true）
 * Queue队列的名字不能以amq.开头。
 * 注意：Queue，Exchange和Binding都直接或者间接的继承Declarable，而Declarable中定义了shouldDeclare的方法。
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月27日 下午2:48:29  
 */
@Configuration
public class DeclareConfig {
	

    //声明direct类型的Exchange
    @Bean
    public Exchange directExchange(){
        return new DirectExchange("sltas.direct.exchange",true,false);
    }

    //声明topic类型的Exchange
    @Bean
    public Exchange topicExchange(){
        return new TopicExchange("sltas.topic.exchange",true,false);
    }

    //声明fanout类型的Exchange
    @Bean
    public Exchange fanoutExchange(){
        return new FanoutExchange("sltas.fanout.exchange",true,false);
    }

    //声明headers类型的Exchange
    @Bean
    public Exchange headersExchange(){
        return new HeadersExchange("sltas.header.exchange",true,false);
    }
    
    /**
	 * default Exchange不能进行Binding,也不需要进行绑定。
	 * 除default Exchange之外，其他任何Exchange都需要和Queue进行Binding，否则无法进行消息路由（转发）
	 * Binding的时候，可以设置一个或多个参数，其中参数要特别注意参数类型，如果Routing key中指定的参数类型和消息中指定的参数类型不一致（header Exchange）也不能进行消息转发。
	 * Direct Exchange，Topic Exchange进行Binding的时候，需要指定Routing key
	 * Fanout Exchange，Headers Exchange进行Binding的时候，不需要指定Routing key。
	 */
    
    @Bean
    public Queue debugQueue(){
        return new Queue("sltas.debug",true);
    }

    @Bean
    public Queue infoQueue(){
        return new Queue("sltas.info",true);
    }

    @Bean
    public Queue errorQueue(){
        return new Queue("sltas.error",true);
    }
    
    @Bean
    public Binding binding(){
        return new Binding("sltas.debug",Binding.DestinationType.QUEUE,
                "sltas.direct.exchange","sltas.debug",new HashMap<>());
    }

    @Bean
    public Binding binding2(){
        return new Binding("sltas.info",Binding.DestinationType.QUEUE,
                "sltas.direct.exchange","sltas.info",new HashMap<>());
    }

    @Bean
    public Binding binding3(){
        return new Binding("sltas.error",Binding.DestinationType.QUEUE,
                "sltas.direct.exchange","sltas.error",new HashMap<>());
    }
    
    
//	@Bean
//  public List<Queue> queues(){
//      List<Queue> queueList = new ArrayList<>();
//      queueList.add(new Queue("chao.wang.debug",true));
//      queueList.add(new Queue("chao.wang.info",true));
//      queueList.add(new Queue("chao.wang.error",true));
//      return queueList;
//  }
//
//  @Bean
//  public List<Exchange> exchanges(){
//      List<Exchange> exchangeList = new ArrayList<>();
//      exchangeList.add(new TopicExchange("chao.wang.debug.topic.exchange",true,false));
//      exchangeList.add(new TopicExchange("chao.wang.info.topic.exchange",true,false));
//      exchangeList.add(new TopicExchange("chao.wang.error.topic.exchange",true,false));
//      return exchangeList;
//  }
//
//  @Bean
//  public List<Binding> bindings(){
//      List<Binding> bindingList = new ArrayList<>();
//      bindingList.add(BindingBuilder.bind(new Queue("chao.wang.debug")).
//              to(new TopicExchange("chao.wang.debug.topic.exchange")).with("chao.wang.#"));
//      bindingList.add(BindingBuilder.bind(new Queue("chao.wang.info")).
//              to(new TopicExchange("chao.wang.debug.topic.exchange")).with("chao.wang.*"));
//      bindingList.add(BindingBuilder.bind(new Queue("chao.wang.error")).
//              to(new TopicExchange("chao.wang.debug.topic.exchange")).with("chao.wang.error.*"));
//      return bindingList;
//  }
    
    /**
     * 注意
     * 当声明队列是以amp开头的时候，队列是不能创建声明的。
     * 
     * @Bean
     * public Queue amqQueue(){
     *    return new Queue("amp.log",true);
     * }
     */
    
    
//    @Bean
//    public List<Declarable> ds() {
//    	return Arrays.<Declarable>asList(
//    			new DirectExchange("e4", false, true),
//    			new Queue("q4", false, false, true),
//    			new Binding("q4", DestinationType.QUEUE, "e4", "k4", null)
//    	);
//    }

    
    
}
