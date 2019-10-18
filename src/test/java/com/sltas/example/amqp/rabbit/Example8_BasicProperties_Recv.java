package com.sltas.example.amqp.rabbit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * <p>
 * Title: Example8_BasicProperties_Recv
 * </p>
 * <p>
 * Description: 优先级队列
 * </p>
 * <p>
 * </p>
 * 
 * 队列queue的最大优先级
 * 
 * Map<String,Object> args = new HashMap<String,Object>();
 * args.put("x-max-priority", 10);
 * channel.queueDeclare("queue_priority", true, false, false, args);
 * 
 * 发送的消息中设置消息本身的优先级
 * 
 * AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
 * builder.priority(5);
 * AMQP.BasicProperties properties = builder.build();
 * channel.basicPublish("exchange_priority","rk_priority",properties,("messages").getBytes());
 * 
 * PS：如果队列queue不设置成优先级队列，那么在消息中设置消息的优先级没有意义
 * 
 * 参考资料：https://www.jianshu.com/p/fc97159f31d1
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example8_BasicProperties_Recv {

	private static Logger logger = LoggerFactory.getLogger(Example8_BasicProperties_Recv.class);

	private static final String EXCHANGE_NAME = "exchange_priority";
	
	private static final String bindingKey = "*.priority";
	
	private static final String QUEUE_NAME = "queue_priority";

	public static void main(String[] args) throws Exception {
		
		String uuid = UUID.randomUUID().toString();
		MDC.put("destination", uuid);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		
		
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

		Map<String,Object> values = new HashMap<String,Object>();
        values.put("x-max-priority", 10);//设置队列的最大优先级
		channel.queueDeclare(QUEUE_NAME, false, false, false, values);
		
		/**
		 * default Exchange不能进行Binding,也不需要进行绑定。
		 * 除default Exchange之外，其他任何Exchange都需要和Queue进行Binding，否则无法进行消息路由（转发）
		 * Binding的时候，可以设置一个或多个参数，其中参数要特别注意参数类型，如果Routing key中指定的参数类型和消息中指定的参数类型不一致（header Exchange）也不能进行消息转发。
		 * Direct Exchange，Topic Exchange进行Binding的时候，需要指定Routing key
		 * Fanout Exchange，Headers Exchange进行Binding的时候，不需要指定Routing key。
		 */
		
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
		
		logger.info(" [*] Waiting for messages. bindingKey : '{}' ",bindingKey);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				MDC.put("destination", uuid);
				String message = new String(body, "UTF-8");
				logger.info(" [x] Received routingKey : '{}' message : '{}' properties ： '{}'",envelope.getRoutingKey(), message,properties);
				
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);

	}

}
