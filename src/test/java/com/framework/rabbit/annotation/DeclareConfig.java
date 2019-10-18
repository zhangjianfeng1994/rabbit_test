package com.framework.rabbit.annotation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeclareConfig {
	

    
    /**
	 * 声明队列
	 * 
	 * @see com.rabbitmq.client.AMQP.Queue.Declare
	 * @see com.rabbitmq.client.AMQP.Queue.DeclareOk
	 * @param queue			排队等待队列的名称
	 * @param durable		如果我们声明一个持久的队列（队列将在服务器重启时存活），持久true。
	 * @param exclusive		如果我们声明一个独占队列（仅限于此连接），独占true。
	 * @param autoDelete	如果我们声明一个自动删除队列，autoDelete true（服务器将在不再使用时删除它,不会关注里面是否仍然存在数据）
	 * @param arguments		参数为队列的其他属性（构造参数）
	 * @return 声明确认方法，以表明已成功申报队列
	 * @throws . io .如果遇到错误，IOException
	 * 
	 * Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) throws IOException;
	 */
	
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
    
    @Bean
    public Queue queue(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority",10);
        return new Queue("sltas.info",true,false,false,arguments);
    }
    
	//声明direct类型的Exchange
    @Bean
    public Exchange directExchange(){
        return new DirectExchange("sltas.direct.exchange",true,false);
    }
    
    /**
	 * default Exchange不能进行Binding,也不需要进行绑定。
	 * 除default Exchange之外，其他任何Exchange都需要和Queue进行Binding，否则无法进行消息路由（转发）
	 * Binding的时候，可以设置一个或多个参数，其中参数要特别注意参数类型，如果Routing key中指定的参数类型和消息中指定的参数类型不一致（header Exchange）也不能进行消息转发。
	 * Direct Exchange，Topic Exchange进行Binding的时候，需要指定Routing key
	 * Fanout Exchange，Headers Exchange进行Binding的时候，不需要指定Routing key。
	 */
    
    @Bean
    public Binding binding(){
        return new Binding("sltas.info",Binding.DestinationType.QUEUE,
                "sltas.direct.exchange","sltas.info.routingKey",new HashMap<>());
    }

    
}
