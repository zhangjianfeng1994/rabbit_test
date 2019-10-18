package com.sltas.example.spring.rabbit.unofficial_8.Alternate_Exchange.C;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
        factory.setPublisherReturns(true);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    
    @Bean
    public Exchange topicExchange(){
    	/**
    	 * 一般alternate-exchange属性的值最好是fanout类型的exchange，否则还会根据route key与alternate-exchange属性的exchange进行匹配再去路由。
    	 * 而如果指定了fanout类型的exchange，不需要去匹配routekey。
    	 */
        return new TopicExchange("head.error.exchange",true,false,null);
    }
    
    @Bean
    public Queue queue(){
    	return new Queue("head.error.queue", true);
    }
    
    @Bean
    public Binding bindingTopicExchange(){
        return new Binding("head.error.queue",Binding.DestinationType.QUEUE,"head.error.exchange","head.error.*",new HashMap<>());
    }
    
    @Bean
    public Exchange exchange(){
        Map<String,Object> argsMap = new HashMap<>();
        /**
         * alternate-exchange
         * 如果对该交易所的消息不能被路由，请将它们发送到此处指定的交换交换。（设置“alternate-exchange”的论点。）
         */
        argsMap.put("alternate-exchange","head.error.exchange");
        return new TopicExchange("head.info.exchange",true,false,argsMap);
    }


    @Bean
    public Binding binding(){
        return new Binding("sltas.info",Binding.DestinationType.QUEUE,"head.info.exchange","sltas.*",new HashMap<>());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
    	RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("===========消息无法被路由=========");
            System.out.println("replyCode: "+replyCode);
            System.out.println("replyText: "+replyText);
            System.out.println("exchange: "+exchange);
            System.out.println("routingKey: "+routingKey);
        });
        return rabbitTemplate;
    }
    
	/**
	 * Durability：是否持久化，Durable是，Transient是否。如果不持久化，那么在服务器宕机或重启之后Queue就会丢失。
	 * Auto delete：如果选择yes，当最后一个消费者不在监听Queue的时候，该Queue就会自动删除，一般选择false。
	 * Arguments：AMQP协议留给AMQP实现者扩展使用的。
	 * 		x-message-ttl：一个消息推送到队列中的存活时间。设置的值之后还没消费就会被删除。
	 * 		x-expires：在自动删除该队列的时候，可以使用该队列的时间。
	 * 		x-max-length：在队列头部删除元素之前，队列可以包含多少个（就绪）消息，如果再次向队列中发送消息，会删除最早的那条消息，用来控制队列中消息的数量。
	 * 		x-max-length-bytes：在队列头部删除元素之前，队列的总消息体的大小，用来控制队列中消息的总大小。
	 * 		x-dead-letter-exchange：当消息被拒绝或者消息过期，消息重新发送到的交换机（Exchange）的可选名称。
	 * 		x-dead-letter-routing-key：当消息被拒绝或者消息过期，消息重新发送到的交换机绑定的Route key的名称，如果没有设置则使用之前的Route key。
	 * 		x-max-priority：队列支持的最大优先级数，如果没有设置则不支持消息优先级
	 * 		x-queue-mode：将队列设置为延迟模式，在磁盘上保留尽可能多的消息以减少RAM使用; 如果未设置，队列将保持在内存中的缓存，以尽可能快地传递消息。
	 * 		x-queue-master-locator：将队列设置为主位置模式，确定在节点集群上声明队列主节点所在的规则。
	 */
}
