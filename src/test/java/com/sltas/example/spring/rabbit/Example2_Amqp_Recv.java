package com.sltas.example.spring.rabbit;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * <p>
 * Title: Example2_Amqp_Recv.java
 * </p>
 * <p>
 * Description: AMQP抽象
 * </p>
 * <p>
 * 
 * https://docs.spring.io/spring-amqp/reference/html/_reference.html
 * 
 * 
 * 资料文档地址
 * 
 * </p>
 * 
 * @author 周顺宇
 * @date 2018年9月19日 上午10:49:29
 */
public class Example2_Amqp_Recv {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final String EXCHANGE_NAME = "direct_logs";

	private static final String QUEUE_NAME = "direct_queue";

	private static final String routingKey = "info";
	
	private static final String RECV_EXCHANGE_NAME = "recv_exchange_name";

	private static final String RECV_QUEUE_NAME = "recv_queue_name";
	
	private static final String recv_routingKey = "recv_info";
	


	/**
	 * 介绍
	 * 
	 * 消息接收总是比发送复杂一点。有两种方法可以获得Message。
	 * 更简单的选项是Message使用轮询方法调用一次轮询一个。更复杂但更常见的方法是注册将Messages按需异步接收的侦听器。
	 * 我们将在接下来的两个小节中查看每种方法的示例。
	 */

	/**
	 * 投票消费者
	 * 
	 * 它AmqpTemplate本身可用于轮询消息接收。默认情况下，如果没有可用消息，null则立即返回;
	 * 没有阻挡。从版本1.5开始，您现在可以设置一个receiveTimeout（以毫秒为单位），
	 * 接收方法将阻止长达这么长时间，等待消息。小于零的值意味着无限期地阻塞（或者至少在与代理的连接丢失之前）。
	 * 版本1.6引入了receive方法的变体，允许在每次调用时传递超时。
	 * 
	 * 有四种简单的接收方法可供使用。与发送方的Exchange一样，有一种方法需要在模板本身上直接设置默认队列属性，并且有一种方法在运行时接受队列参数。
	 * 版本1.6引入了变体以接受基于每个请求的timeoutMillis覆盖receiveTimeout。
	 * 
	 */

	/**
	 * 警告 由于接收操作QueueingConsumer为每条消息创建一个新的，因此这种技术并不适用于大容量环境;
	 * 考虑使用异步使用者，或者receiveTimeout对于这些用例使用零。
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void receive() throws UnsupportedEncodingException {

		/**
		 * Message receive() throws AmqpException;
		 * 
		 * Message receive(String queueName) throws AmqpException;
		 * 
		 * Message receive(long timeoutMillis) throws AmqpException;
		 * 
		 * Message receive(String queueName, long timeoutMillis) throws
		 * AmqpException;
		 */

		/**
		 * 就像发送消息的情况一样，AmqpTemplate有一些方便的方法来接收POJO而不是Message实例，
		 * 实现将提供一种方法来定制MessageConverter用于创建Object返回的用于：
		 * 
		 * Object receiveAndConvert() throws AmqpException;
		 * 
		 * Object receiveAndConvert(String queueName) throws AmqpException;
		 * 
		 * Message receiveAndConvert(long timeoutMillis) throws AmqpException;
		 * 
		 * Message receiveAndConvert(String queueName, long timeoutMillis)
		 * throws AmqpException;
		 */

		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
//		template.setExchange(RECV_EXCHANGE_NAME);
//		template.setRoutingKey(recv_routingKey);
//		template.setQueue(RECV_QUEUE_NAME);

		Message message = template.receive(RECV_QUEUE_NAME,-1);// 小于零的值意味着无限期地阻塞
		logger.info(" [x] receive message : '{}' ", message);
		logger.error(" [x] body : '{}' ", new String(message.getBody(), "UTF-8"));

		/**
		 * 从版本2.0开始，这些方法的变体采用额外的ParameterizedTypeReference参数来转换复杂类型。
		 * 模板必须配置为SmartMessageConverter; 有关详细信息，请参阅“使用RabbitTemplate从消息转换”一节。
		 */

	}
	
	@Test
	public void receiveAndReply(){
		
		//暂时留有一个疑点 queue方式报错
		/**
		 * <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback) throws AmqpException;
		 * 
		 * <R, S> boolean receiveAndReply(String queueName,ReceiveAndReplyCallback<R, S> callback) throws AmqpException;
		 * 
		 * <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback, String replyExchange, String replyRoutingKey) throws AmqpException;
		 * 
		 * <R, S> boolean receiveAndReply(String queueName,ReceiveAndReplyCallback<R, S> callback, String replyExchange, String replyRoutingKey) throws AmqpException;
		 * 
		 * <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> callback, ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException;
		 * 
		 * <R, S> boolean receiveAndReply(String queueName, ReceiveAndReplyCallback<R, S> callback, ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException;
		 */
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setExchange(EXCHANGE_NAME);
		template.setRoutingKey(routingKey);
//		template.setQueue(QUEUE_NAME);
		
		/**
		 * @param queueName队列名称来接收一条消息。  重点注意 下面例子中的 QUEUE_NAME 等同于template.setQueue(QUEUE_NAME); 是获取数据的队列名称
		 * @param回调，用户提供的@link ReceiveAndReplyCallback实现
		 * 进程接收到消息并返回一条应答消息。
		 * @param回复交换名称以发送回复消息。
		 * @param replyRoutingKey路由键发送回复消息。
		 * @param从@link消息转换后的请求类型。
		 * @param是响应的类型。
		 * @@code true，如果接收到消息
		 * 如果出现问题，就会抛出AmqpException
		 */
		boolean flag = template.receiveAndReply(QUEUE_NAME,new ReceiveAndReplyCallback<String, String>() {

			@Override
			public String handle(String payload) {
				logger.info(" [x] receive body : '{}' ", payload);
				return "233";
			}
			
		},RECV_EXCHANGE_NAME,recv_routingKey);
		
//		
//		boolean flag = template.receiveAndReply(new ReceiveAndReplyCallback<String, String>() {
//
//			@Override
//			public String handle(String payload) {
//				logger.info(" [x] receive body : '{}' ", payload);
//				return "233";
//			}
//			
//		},RECV_EXCHANGE_NAME,recv_routingKey);
		
		logger.info(" [x] flag : '{}' ", flag);
		
	}
	
}
