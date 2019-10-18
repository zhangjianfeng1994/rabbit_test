package com.sltas.example.spring.rabbit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Title: Example3_Amqp_Listener.java
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
@Configuration
public class Example3_Amqp_Listener {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final String EXCHANGE_NAME = "direct_logs";

	private static final String QUEUE_NAME = "direct_queue";

	private static final String routingKey = "info";
	
	
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(rabbitConnectionFactory());
//        container.setQueueNames("some.queue");
//        container.setMessageListener(exampleListener());
//        return container;
//    }
//
//    @Bean
//    public ConnectionFactory rabbitConnectionFactory() {
//        CachingConnectionFactory connectionFactory =
//            new CachingConnectionFactory("localhost");
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        return connectionFactory;
//    }
//
//    @Bean
//    public MessageListener exampleListener() {
//        return new MessageListener() {
//            public void onMessage(Message message) {
//                System.out.println("received: " + message);
//            }
//        };
//    }
	
	/**
	 * 消息监听器
	 * 
	 * 对于异步消息接收，涉及专用组件（不是AmqpTemplate）。该组件是消息回调的容器。
	 * 我们将在稍后讨论容器及其属性，但首先我们应该查看回调，因为这是您的应用程序代码将与消息传递系统集成的地方。从MessageListener接口的实现开始，回调有几个选项：
	 * 
	 * MessageListener
	 * 
	 * 如果您的回调逻辑出于任何原因依赖于AMQP Channel实例，您可以改为使用ChannelAwareMessageListener。它看起来很相似，但有一个额外的参数：
	 * 
	 * ChannelAwareMessageListener 
	 */
	
	public void messageListenerAdapter(){
	
		Object object = new Object();
		MessageListenerAdapter listener = new MessageListenerAdapter(object);
		listener.setDefaultListenerMethod("myMethod");
		
//		@FunctionalInterface
//		public interface ReplyingMessageListener<T, R> {
//
//			R handleMessage(T t);
//
//		}
		
//		new MessageListenerAdapter((ReplyingMessageListener<String, String>) data -> {
//		    ...
//		    return result;
//		}));
		
	}
	
	/**
	 * 
	 * Container 容器
	 * 
	 * 现在您已经看到了Message-listening回调的各种选项，我们可以将注意力转向容器。基本上，容器处理“活动”职责，以便监听器回调可以保持被动。
	 * 容器是“生命周期”组件的示例。它提供了启动和停止的方法。配置容器时，您实际上弥合了AMQP队列和MessageListener实例之间的差距。
	 * 您必须提供ConnectionFactory对该侦听器应使用消息的队列名称或队列名称或队列实例的引用。
	 * 
	 * 对于版本2.0之前的版本，有一个侦听器容器 - SimpleMessageListenerContainer; 现在有第二个容器 - DirectMessageListenerContainer。
	 * 选择使用哪个容器和标准之间的差异在“选择容器”一节中描述。
	 * 
	 */
	
	public void simpleMessageListenerContainer(){
		
		CachingConnectionFactory rabbitConnectionFactory = new CachingConnectionFactory("127.0.0.1");
		Object somePojo = new Object();
		
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(rabbitConnectionFactory);
		container.setQueueNames("some.queue");
		container.setMessageListener(new MessageListenerAdapter(somePojo));
		
		DirectMessageListenerContainer directMessageListenerContainer = new DirectMessageListenerContainer();
		
	}
	
	/**
	 * <p>
	 * Title: listener
	 * </p>
	 * <p>
	 * Description: 简单的使用listener
	 * </p>
	 * @param @throws Exception 
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月26日 下午4:34:49 
	 */
	@Test
	public void listener() throws Exception{
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_NAME);
		container.setMessageListener(message -> {
			logger.info(" [x] ListenerContainer Queue : '{}' ", message.getMessageProperties().getConsumerQueue());
			logger.info(" [x] ListenerContainer MessageProperties : '{}' ", message.getMessageProperties());
			logger.info(" [x] ListenerContainer Message : '{}' ", new String(message.getBody()));
			String nullStr = null;
	        nullStr.toString();
		});
		
		
	}
	
}
