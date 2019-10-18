package com.sltas.example.spring.rabbit;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.BindingBuilder.GenericArgumentsConfigurer;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.rabbitmq.client.ShutdownSignalException;

/**
 * <p>
 * Title: Example1_Amqp_Emit.java
 * </p>
 * <p>
 * Description: AMQP抽象
 * </p>
 * <p>
 * 
 * spring-amqp二个核心类RabbitAdmin和RabbitTemplate类
 * 1.RabbitAdmin类完成对Exchange，Queue，Binging的操作，在容器中管理了RabbitAdmin类的时候，可以对Exchange，Queue，Binging进行自动声明。
 * 2.RabbitTemplate类是发送和接收消息的工具类。(下一篇博客具体讲解）
 * 
 * https://docs.spring.io/spring-amqp/reference/html/_reference.html 
 * 
 * https://www.jianshu.com/p/e8de480e3598
 * 
 * 
 * 资料文档地址
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月19日 上午10:49:29  
 */
public class Example1_Amqp_Emit {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
//	private static final String EXCHANGE_NAME = "spring_amqp_exchange";
//	
//	private static final String QUEUE_NAME = "spring_amqp_queue";
//	
//	private static final String routingKey = "spring_amqp_routingKey";
	
	private static final String EXCHANGE_NAME = "direct_logs";
	
	private static final String QUEUE_NAME = "direct_queue";
	
	private static final String routingKey = "info";
	
	/**
	 * <p>
	 * Title: message
	 * </p>
	 * <p>
	 * Description: 0-9-1 AMQP规范未定义Message类或接口。相反，当执行诸如的操作时basicPublish()，内容作为字节数组参数传递，
	 * 而其他属性作为单独的参数传递。Spring AMQP将Message类定义为更通用的AMQP域模型表示的一部分。
	 * Message类的目的是简单地将主体和属性封装在单个实例中，以便API可以更简单。Message类定义非常简单。 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月19日 上午10:49:01 
	 */
	public void message(){
		/**
		 * void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;
		 * channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		 * 
		 * 与版本开始1.5.7，1.6.11，1.7.4，2.0.0，如果消息体是序列化Serializable的Java对象，所以不再执行时反序列化（默认）toString()的操作（如在日志消息）。
		 * 这是为了防止不安全的反序列化。默认情况下，仅对类java.util和java.lang类进行反序列化。
		 * 要恢复到以前的行为，可以通过调用添加允许的类/包模式Message.addWhiteListPatterns(...)。
		 * *例如，支持简单的通配符com.foo.*, *.MyClass。无法反序列化的实体将byte[<size>]在日志消息中表示。
		 */
		
		
		/***********************方法 1***************************/
		
//		byte[] body = {};
//		/**
//		 * 该MessageProperties接口定义了几个常见属性，例如messageId，timestamp，contentType等等。通过调用方法，
//		 * 还可以使用用户定义的标头扩展这些属性setHeader(String key, Object value)。
//		 */
//		MessageProperties messageProperties = new MessageProperties();
//		messageProperties.setHeader(key, value);
		
//		Message message = new Message(body, messageProperties);
		
		/***********************方法 2***************************/
		
//		Message message =  MessageBuilder.withBody("foo".getBytes())
//			.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
//			.setMessageId("123")
//			.setHeader("bar", "baz")
//			.build();
		
		/***********************方法 3***************************/
		
		/**
		 * 可以设置MessageProperties上定义的每个属性。其他方法包括setHeader(String key, String value)，removeHeader(String key)，removeHeaders()，
		 * 和copyProperties(MessageProperties properties)。每种属性设置方法都有一个set*IfAbsent()变体。
		 * 在存在默认初始值的情况下，该方法被命名set*IfAbsentOrDefault()。
		 */
		
		MessageProperties props = MessagePropertiesBuilder.newInstance()
			    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
			    .setMessageId("123")
			    .setHeader("bar", "baz")
			    .build();
		Message message = MessageBuilder.withBody("foo".getBytes())
		    .andProperties(props)
		    .build();
		
		
		/**
		 * 
		 * public  static MessageBuilder withBody（byte [] body） 构建器创建的消息将具有一个直接引用该参数的主体。
		 * 
		 * public  static MessageBuilder withClonedBody（byte [] body）构建器创建的消息将具有一个主体，该主体是包含参数中字节副本的新数组。
		 * 
		 * public  static MessageBuilder withBody（byte [] body，int from，int to）构建器创建的消息将具有一个主体，该主体是包含参数中字节范围的新数组。有关Arrays.copyOfRange()详细信息，请参阅
		 * 
		 * public  static MessageBuilder fromMessage（Message message）构建器创建的消息将具有一个主体，该主体直接引用参数主体。参数的属性将复制到新MessageProperties对象。
		 * 
		 * public  static MessageBuilder fromClonedMessage（Message message）构建器创建的消息将具有一个主体，该主体是包含参数主体副本的新数组。参数的属性将复制到新MessageProperties对象。
		 * 
		 */
		
		/**
		 * 
		 * public  static MessagePropertiesBuilder newInstance（）使用默认值初始化新的消息属性对象。
		 * 
		 * public  static MessagePropertiesBuilder fromProperties（MessageProperties properties）构建器初始化为，build()并将返回提供的属性对象。
		 * 
		 * public  static MessagePropertiesBuilder fromClonedProperties（MessageProperties properties）参数的属性将复制到新MessageProperties对象。
		 * 
		 */
		
	}
	
	/**
	 * <p>
	 * Title: exchange
	 * </p>
	 * <p>
	 * 
	 * Description: 该Exchange接口代表AMQP Exchange，它是Message Producer发送给的。代理虚拟主机中的每个Exchange都具有唯一的名称以及一些其他属性：
	 * 
	 * 如您所见，Exchange也有一个由定义的常量表示的类型ExchangeTypes。基本类型有：Direct，Topic，Fanout，和Headers。在核心包中，
	 * 您将找到Exchange每种类型的接口实现。在这些Exchange类型处理绑定到队列的方式方面，行为会有所不同。
	 * 例如，直接交换允许队列由固定路由密钥（通常是队列的名称）绑定。主题交换支持带有路由模式的绑定，这些路由模式可能包含*和＃通配符，
	 * 用于完全一个和零或多个， 分别。扇出交换发布到绑定到它的所有队列，而不考虑任何路由密钥。有关这些和其他Exchange类型的更多信息，请参阅第5章，其他资源。
	 * 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月19日 上午10:57:19 
	 */
	public void exchange(){
		
		/**
		 * channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		 */
		Exchange exchange = null;
		exchange = new DirectExchange("DirectExchange");
		exchange = new TopicExchange("TopicExchange");
		exchange = new FanoutExchange("FanoutExchange");
		exchange = new HeadersExchange("HeadersExchange");
		
	}
	
	
	/**
	 * <p>
	 * Title: queue
	 * </p>
	 * <p>
	 * Description: 所述Queue类表示从其中一个消息用户接收消息的组件。与各种Exchange类一样，我们的实现旨在成为此核心AMQP类型的抽象表示。
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月19日 上午11:11:19 
	 */
	public void queue(){
		
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
		 * channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		 */
		Queue queue = new Queue("Queue");
		
	}
	
	/**
	 * 
	 * <p>
	 * Title: binding
	 * </p>
	 * <p>
	 * 
	 * Description: 鉴于生产者发送到Exchange并且消费者从队列接收，将队列连接到Exchange的绑定对于通过消息传递连接这些生产者和消费者是至关重要的。
	 * 在Spring AMQP中，我们定义了一个Binding表示这些连接的类。让我们回顾一下将队列绑定到Exchange的基本选项。
	 * 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月19日 上午11:19:33
	 */
	public void binding(){
		
		Queue someQueue = new Queue("someQueue");
		Exchange someTopicExchange =  new TopicExchange("someTopicExchange");
		GenericArgumentsConfigurer b = BindingBuilder.bind(someQueue).to(someTopicExchange).with("foo.*");
		/**
		 * channel.queueBind(queueName, EXCHANGE_NAME, bindingKey,null);
		 */
		new Binding("queueName", DestinationType.QUEUE, "exchangeName", "routingKey", null);
		
		/**
		 * 就其本身而言，Binding类的一个实例只是保存有关连接的数据。换句话说，它不是“活跃”组件。
		 * 但是，正如您将在后面的第3.1.10节“配置代理”中看到的那样，AmqpAdmin类可以使用绑定实例来实际触发代理上的绑定操作。
		 * 此外，正如您将在同一部分中看到的，可以使用类中的Spring- @Beanstyle 定义Binding实例@Configuration。
		 * 还有一个方便的基类，它进一步简化了生成与AMQP相关的bean定义并识别队列，交换和绑定的方法，以便在应用程序启动时它们都将在AMQP代理上声明。
		 * 
		 * 的AmqpTemplate也被芯包中定义。作为实际AMQP消息传递中涉及的主要组件之一，它将在其自己的部分中详细讨论（请参见第3.1.4节“AmqpTemplate”）
		 */
	}
	
	/**
	 * <p>
	 * Title: connectionFactory
	 * </p>
	 * <p>
	 * Description: 建立连接
	 * 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月19日 下午3:11:02 
	 */
	@Test
	public void connectionFactory(){
		
		/**
		 * 使用XML时，配置可能如下所示
		 * 
		 * <bean  id = “connectionFactory” 
		 *       class = “org.springframework.amqp.rabbit.connection.CachingConnectionFactory” > 
		 *     <constructor-arg  value = “somehost” /> 
		 *     <property  name = “username”  value = “guest” /> 
		 *     <property  name = “password”  value = “guest” /> 
		 * </bean>
		 */
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
		
		//查看连接时，这些属性显示在RabbitMQ Admin UI中。
//		connectionFactory.getRabbitConnectionFactory().getClientProperties().put("foo", "bar");

		Connection connection = connectionFactory.createConnection();
		
		logger.info("构建 CachingConnectionFactory");
		
		connection.close();
		
		logger.info("关闭 CachingConnectionFactory");
		
		try {
			Thread.sleep(60000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * <p>
	 * Title: connectionListener
	 * </p>
	 * <p>
	 * 
	 * Description: 与开始2.0版本，
	 * 所述org.springframework.amqp.rabbit.connection.Connection对象可以与被供给com.rabbitmq.client.BlockedListeners到被通知用于连接阻挡和畅通的事件。
	 * 
	 * </p>
	 * @param  
	 * @return void
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月19日 下午5:46:13 
	 */
	public void connectionListener(){
		
		ConnectionListener cl = new ConnectionListener() {
			@Override
			public void onCreate(Connection connection) {
				// TODO Auto-generated method stub
				
			}
		};
		
	}
	
	/**
	 * <p>
	 * Title: rabbitTemplate
	 * </p>
	 * <p>
	 * Description: 与Spring Framework和相关项目提供的许多其他高级抽象一样，Spring AMQP提供了一个起着核心作用的“模板”。
	 * 调用定义主要操作的接口AmqpTemplate。这些操作涵盖了发送和接收消息的一般行为。
	 * 换句话说，它们并不是任何实现的唯一，因此名称中的“AMQP”。另一方面，该接口的实现与AMQP协议的实现相关联。
	 * 与JMS不同，JMS是一种接口级API本身，AMQP是一种线级协议。该协议的实现提供了自己的客户端库，因此模板接口的每个实现都将依赖于特定的客户端库。
	 * 目前，只有一个实现：RabbitTemplate。在下面的示例中，您将经常看到“AmqpTemplate”的用法，但是当您查看配置示例或任何实例化模板和/或调用setter的代码摘录时，
	 * 您将看到实现类型（例如“RabbitTemplate”）。
	 * 
	 * 如上所述，AmqpTemplate接口定义了发送和接收消息的所有基本操作。我们将分别在以下两个部分中探讨消息发送和接收。
	 * 
	 * 另请参阅“AsyncRabbitTemplate”一节。
	 * </p>
	 * @param  
	 * @return void
	 * @throws Exception 
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年9月20日 上午9:45:33 
	 */
	@Test
	public void rabbitTemplate() throws Exception{
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.0.119");
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		
		/**
	     * 添加重试功能
	     * 
	     * 从版本1.3开始，您现在可以配置RabbitTemplate使用a RetryTemplate来帮助处理代理连接问题。
	     * 有关完整信息，请参阅spring-retry项目; 以下只是一个使用指数退避策略的示例，默认情况下SimpleRetryPolicy会在将异常抛出给调用者之前进行三次尝试。
	     * 
	     * 从版本1.4开始，除了retryTemplate属性之外，该recoveryCallback选项也受支持RabbitTemplate。
	     * 它被用作第二个参数RetryTemplate.execute(RetryCallback<T, E> retryCallback, RecoveryCallback<T>recoveryCallback)。
	     */
	    RetryTemplate retryTemplate = new RetryTemplate();
	    retryTemplate.execute(new RetryCallback<Object,Exception>() {

	    	@Override
			public Object doWithRetry(RetryContext context) throws Exception {
				String message = "2333";
				context.setAttribute("message", message);
	            rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, message);
	            return null;
			}
	    	
		}, new RecoveryCallback<Object>(){

			@Override
			public Object recover(RetryContext context) throws Exception {
				Object message = context.getAttribute("message");
		        Throwable t = context.getLastThrowable();
				return null;
			}
		});
	    
	 
	    
	    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
	    backOffPolicy.setInitialInterval(500);
	    backOffPolicy.setMultiplier(10.0);
	    backOffPolicy.setMaxInterval(10000);
	    retryTemplate.setBackOffPolicy(backOffPolicy);
	    rabbitTemplate.setRetryTemplate(retryTemplate);
//	    return template;
		
	    try {
			Thread.sleep(60000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  
	}
	
//	@Test
//	public void amqp_server(){
//		
//		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
//		connectionFactory.setUsername("guest");
//		connectionFactory.setPassword("guest");
//		connectionFactory.setPublisherConfirms(true);
//		connectionFactory.setPublisherReturns(true);
//		
//		Exchange exchange = new DirectExchange(EXCHANGE_NAME);
//		Queue queue = new Queue(QUEUE_NAME);
//		GenericArgumentsConfigurer b = BindingBuilder.bind(queue).to(exchange).with(routingKey);
//		logger.info("{}",b);
//		
//		try {
//			Thread.sleep(60000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
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
		
		//查看连接时，这些属性显示在RabbitMQ Admin UI中。
//		connectionFactory.getRabbitConnectionFactory().getClientProperties().put("foo", "bar");

		logger.info("构建 CachingConnectionFactory");
		
//		Connection connection = connectionFactory.createConnection();
//		
//		logger.info("构建 Connection");
		
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
		
		try {
			Thread.sleep(60000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void invoke(){
		
		/**
		 * 范围运营
		 * 
		 * 通常，在使用模板时，Channel会从缓存（或已创建）中检出a ，用于操作，并返回到缓存以供重用。
		 * 在多线程环境中，无法保证下一个操作将使用相同的通道。但是，有时您可能希望更多地控制通道的使用，并确保在同一通道上执行大量操作。
		 * 
		 * 从版本2.0开始，invoke提供了一种新方法，其中包含OperationsCallback。
		 * 在回调范围内和提供的RabbitOperations参数范围内执行的任何操作都将使用相同的专用Channel，最后将关闭（不返回缓存）。
		 */
		
//		@FunctionalInterface
//		public interface OperationsCallback<T> {
//		    T doInRabbit(RabbitOperations operations);
//		}
		
		/**
		 * 您可能需要这个的一个例子是，如果您希望waitForConfirms()在底层使用该方法Channel。
		 * 之前未使用Spring API公开此方法，因为通常如上所述缓存和共享通道。
		 * 在RabbitTemplate现在提供waitForConfirms(long timeout)和waitForConfirmsOrDie(long timeout)其委托的范围之内所使用的专用信道OperationsCallback。
		 * 出于显而易见的原因，这些方法不能在该范围之外使用。
		 * 
		 * 请注意，允许您将确认与请求相关联的更高级抽象在其他地方提供（请参阅“发布者确认和返回”一节）。
		 * 您仍然需要将连接工厂的publisherConfirms属性设置true为该部分中所讨论的，但对于您只想等到收到所有确认的简单用例，您可以在此处使用此技术：
		 * 
		 */
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("127.0.0.1");
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		Collection<?> messages = Stream.of("a","b","c").collect(Collectors.toList());
		Boolean result = template.invoke(t -> {
		    messages.forEach(m -> t.convertAndSend(routingKey, m));
		    t.waitForConfirmsOrDie(10_000);
		    return true;
		});
		//如果您希望RabbitAdmin在同一个通道上调用操作，则在该范围内，OperationsCallback必须使用与操作相同RabbitTemplate的invoke操作构造admin 。
	}
	
	
	
}
