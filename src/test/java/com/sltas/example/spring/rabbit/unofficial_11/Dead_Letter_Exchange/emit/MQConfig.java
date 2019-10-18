package com.sltas.example.spring.rabbit.unofficial_11.Dead_Letter_Exchange.emit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

	/**
	 * 
	 * Dead Letter Exchange
	 * 
	 * 在队列上指定一个Exchange，则在该队列上发生如下情况，
	 * 1.消息被拒绝（basic.reject or basic.nack)，且requeue=false
	 * 2.消息过期而被删除（TTL）
	 * 3.消息数量超过队列最大限制而被删除
	 * 4.消息总大小超过队列最大限制而被删除
	 * 就会把该消息转发到指定的这个exchange
	 * 同时也可以指定一个可选的x-dead-letter-routing-key，表示默认的routing-key，如果没有指定，则使用消息的routeing-key(也跟指定的exchange有关，
	 * 如果是Fanout类型的exchange，则会转发到所有绑定到该exchange的所有队列）。
	 * 
	 */
	
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUri("amqp://zhihao.miao:123456@192.168.1.131:5672");
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
    public Exchange directExchange(){
        return new DirectExchange("sltas.direct.exchange",true,false);
    }
    
    @Bean
    public Exchange directExchange1(){
        return new DirectExchange("sltas.dead.letter.exchange",true,false);
    }
    
    @Bean
    public Queue infoQueue(){
//        return new Queue("sltas.info",true);
        Map<String, Object> arguments = new HashMap<>();
        /**
         * 因为其指定了x-dead-letter-exchange是sltas.dead.letter.exchange，所以会将消息转发到sltas.dead.letter.exchange，
         * 而因为没有指定x-dead-letter-routing-key，所以会使用默认的发送的消息的route key（sltas.info）进行路由，
         * 而我们sltas.dead.letter.exchange的路由信息如下，所以会将消息转发到sltas.auto队列中去。
         */
        arguments.put("x-dead-letter-exchange","sltas.dead.letter.exchange");
        /**
         * 
         */
        arguments.put("x-dead-letter-routing-key","sltas.error");
        return new Queue("sltas.info",true,false,false,arguments);
        
        /**
         * 总结
         * 
         * 上面的示列展示了当定义队列时指定了x-dead-letter-exchange（x-dead-letter-routing-key视情况而定），并且消费端执行拒绝策略的时候将消息路由到指定的Exchange中去。我们知道还有二种情况会造成消息转发到死信队列。
         * 一种是消息过期而被删除，可以使用这个方式使的rabbitmq实现延迟队列的作用。还有一种就是消息数量超过队列最大限制而被删除或者消息总大小超过队列最大限制而被删除
         * 
         */
    }
    
    @Bean
    public Queue errorQueue(){
        return new Queue("sltas.error",true);
    }
    
    @Bean
    public Queue autoQueue(){
        return new Queue("sltas.auto",true);
    }
    
    @Bean
    public Binding binding(){
        return new Binding("sltas.info",Binding.DestinationType.QUEUE,
                "sltas.direct.exchange","sltas.info",new HashMap<>());
    }
    
    @Bean
    public Binding binding1(){
        return new Binding("sltas.error",Binding.DestinationType.QUEUE,
                "sltas.dead.letter.exchange","sltas.error",new HashMap<>());
    }
    
    @Bean
    public Binding binding2(){
        return new Binding("sltas.auto",Binding.DestinationType.QUEUE,
                "sltas.dead.letter.exchange","sltas.info",new HashMap<>());
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
