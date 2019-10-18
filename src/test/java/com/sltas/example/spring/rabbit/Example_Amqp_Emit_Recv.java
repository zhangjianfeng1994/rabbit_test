package com.sltas.example.spring.rabbit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.rabbitmq.client.ShutdownSignalException;

public class Example_Amqp_Emit_Recv {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private static final String EXCHANGE_NAME = "direct_logs";
	
	private static final String QUEUE_NAME = "direct_queue";
	
	private static final String routingKey = "info";
	
	
	@Test
	public void emit() throws Exception{
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
//		connectionFactory.setCacheMode(CacheMode.CONNECTION);
		
		connectionFactory.addConnectionListener(new ConnectionListener() {
			@Override
			public void onCreate(Connection connection) {
				logger.info(" [x] *************onCreate************* connection : {}  ", connection);
			}
			@Override
			public void onClose(Connection connection) {
				logger.info(" [x] **************onClose************ connection : {}  ", connection);
			}
			@Override
			public void onShutDown(ShutdownSignalException signal) {
				logger.error(" [x] *************onShutDown************* signal : {}  ", signal, signal);
			}
		});
		
		/**
		 * 出版商确认并退货
		 * 
		 * 通过将CachingConnectionFactory's publisherConfirms和publisherReturns属性分别设置为'true' 来支持确认和返回的消息。
		 * 
		 * 设置这些选项后，Channel工厂创建的s包装在一个PublisherCallbackChannel，用于方便回调。
		 * 当获得这样的信道时，客户端可以注册PublisherCallbackChannel.Listener与所述Channel。
		 * 该PublisherCallbackChannel实现包含将确认/返回路由到适当的侦听器的逻辑。以下各节将进一步解释这些功能。
		 * 
		 */
		connectionFactory.setPublisherConfirms(true);
		connectionFactory.setPublisherReturns(true);
		

		logger.info("构建 CachingConnectionFactory");
		
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
	    RetryTemplate retryTemplate = new RetryTemplate();
	    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
	    backOffPolicy.setInitialInterval(500);
	    backOffPolicy.setMultiplier(10.0);
	    backOffPolicy.setMaxInterval(10000);
	    retryTemplate.setBackOffPolicy(backOffPolicy);
	    template.setRetryTemplate(retryTemplate);
	    
	    /**
	     * 该RabbitTemplate实施AmqpTemplate支持出版商确认并返回。
	     * 
	     * 对于返回的消息，mandatory必须将模板的属性设置为true，或者mandatory-expression 必须true为特定消息评估。
	     * 此功能要求将CachingConnectionFactory其publisherReturns属性设置为true（请参阅“发布者确认和返回”一节）。
	     * 返回RabbitTemplate.ReturnCallback通过调用注册来发送给客户端setReturnCallback(ReturnCallback callback)。回调必须实现此方法：
	     * 
	     * ReturnCallback每个只支持一个RabbitTemplate。另请参阅“回复超时”一节。
	     */
	    
	    template.setReturnCallback(new ReturnCallback() {
			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
				logger.error(" [x] message : '{}' ", message);
				logger.error(" [x] replyCode : '{}' ", replyCode);
				logger.error(" [x] replyText : '{}' ", replyText);
				logger.error(" [x] exchange : '{}' ", exchange);
				logger.error(" [x] routingKey : '{}' ", routingKey);
//				logger.error(" [x] body : '{}' ", new String(body, "UTF-8"));
			}
		});
	    
	    /**
	     * 对于Publisher Confirms（也称为Publisher Acknowledgements），模板需要将CachingConnectionFactory其publisherConfirms属性设置为true。
	     * 确认通过它注册发送到到客户端RabbitTemplate.ConfirmCallback通过调用setConfirmCallback(ConfirmCallback callback)。回调必须实现此方法：
	     * 
	     * void confirm（CorrelationData correlationData，boolean ack，String cause）;
	     * 这CorrelationData是客户端在发送原始邮件时提供的对象。的ack是一个真实ack的和虚假的nack。对于nacks，原因可能包含nack的原因，如果它在nack生成时可用。
	     * 一个例子是向不存在的交换机发送消息。在这种情况下，经纪人关闭渠道; 关闭的原因包括在cause。 cause在版本1.4中添加。
	     * 
	     * a只ConfirmCallback支持一个RabbitTemplate。
	     */
	    template.setConfirmCallback(new ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				logger.error(" [x] correlationData : '{}' ", correlationData);
				logger.error(" [x] ack : '{}' ", ack);
				logger.error(" [x] cause : '{}' ", cause);
			}
		});
	    
//	    template.waitForConfirms(timeout);
//	    template.waitForConfirmsOrDie(timeout);
	    
//	    Exchange exchange = new DirectExchange(EXCHANGE_NAME,true,false);
	    
		template.setExchange(EXCHANGE_NAME);
		template.setRoutingKey(routingKey);
		
//	    byte[] body = "Hello word!".getBytes("UTF-8");
//		/**
//		 * 该MessageProperties接口定义了几个常见属性，例如messageId，timestamp，contentType等等。通过调用方法，
//		 * 还可以使用用户定义的标头扩展这些属性setHeader(String key, Object value)。
//		 */
//		MessageProperties messageProperties = new MessageProperties();
////		messageProperties.setHeader(key, value);
//		
//		Message message = new Message(body, messageProperties);
		
		Message message =  MessageBuilder.withBody("Hello word!".getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
				.setMessageId("123")
				.setHeader("bar", "baz")
				.build(); 
		
		template.convertAndSend(message);
		
//		try {
//			Thread.sleep(60000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
}
