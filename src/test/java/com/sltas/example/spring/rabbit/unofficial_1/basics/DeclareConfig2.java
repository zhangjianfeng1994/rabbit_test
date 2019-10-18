//package com.sltas.example.spring.rabbit.unofficial_1.basics;
//
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.Argument;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.messaging.handler.annotation.Header;
//
//import com.lmax.disruptor.Foo;
//
//
//@Configuration
//public class DeclareConfig2 {
//	
//	  @RabbitListener(bindings = @QueueBinding(
//	        value = @Queue(value = "myQueue", durable = "true"),
//	        exchange = @Exchange(value = "auto.exch", ignoreDeclarationExceptions = "true"),
//	        key = "orderRoutingKey")
//	  )
//	  public void processOrder(String foo) {
//	  }
//
//	  @RabbitListener(bindings = @QueueBinding(
//	        value = @Queue,
//	        exchange = @Exchange(value = "auto.exch"),
//	        key = "invoiceRoutingKey")
//	  )
//	  public void processInvoice(String foo) {
//	  }
//
//	  @RabbitListener(queuesToDeclare = @Queue(name = "${my.queue}", durable = "true"))
//	  public String handleWithSimpleDeclare(String data) {
//	      return null;
//	  }
//	  
//	  
//	  /**
//	   * 
//	   * 在第一个示例中，myQueue如果需要，队列将与交换一起自动声明（持久），并使用路由键绑定到交换机。
//	   * 在第二个示例中，将声明和绑定匿名（独占，自动删除）队列。QueueBinding可以提供多个条目，允许侦听器侦听多个队列。
//	   * 在第三个示例中，my.queue如果需要，将声明具有从属性检索到的名称的队列，使用队列名称作为路由键，使用默认绑定到默认交换。
//	   * 
//	   * 从2.0版开始，@Exchange注释支持任何交换类型，包括自定义。请参阅AMQP Concepts文档中的更多信息。
//	   * Bean需要更高级配置时，请使用常规定义。
//	   * 请注意ignoreDeclarationExceptions第一个例子中的交换。例如，这允许绑定到可能具有不同设置（例如internal）的现有交换。默认情况下，现有交换的属性必须匹配。
//	   */
//
//
//	@RabbitListener(bindings = @QueueBinding(
//	        value = @Queue(value = "auto.headers", autoDelete = "true",
//	                        arguments = @Argument(name = "x-message-ttl", value = "10000",
//	                                                type = "java.lang.Integer")),
//	        exchange = @Exchange(value = "auto.headers", type = ExchangeTypes.HEADERS, autoDelete = "true"),
//	        arguments = {
//	                @Argument(name = "x-match", value = "all"),
//	                @Argument(name = "foo", value = "bar"),
//	                @Argument(name = "baz")
//	        })
//	)
//	public String handleWithHeadersExchange(String foo) {
//		return null;
//	}
//	
//	/**
//	 * 请注意，x-message-ttl队列的参数设置为10秒。由于参数类型不是String，我们必须指定它的类型; 在这种情况下Integer。与所有此类声明一样，如果队列已存在，则参数必须与队列上的参数匹配。对于标头交换，我们设置绑定参数以匹配标头foo设置为的消息，bar并且标头baz必须与任何值一起出现。该x-match参数是指两个条件必须得到满足。
//	 * 
//	 * 参数名称，值和类型可以是属性占位符（${...}）或SpEL表达式（#{...}）。在name必须解决的String; 表达式type必须解析为a Class或类的完全限定名称。的value必须解决的东西，可以通过转换DefaultConversionService的类型（如x-message-ttl在上面的例子）。
//	 * 
//	 * 如果名称解析为null或为空String，@Argument则忽略该名称。
//	 */
//	
//	@RabbitListener(queues = "#{'${property.with.comma.delimited.queue.names}'.split(',')}" )
//    public void processOrder(String data, @Header(AmqpHeaders.CONSUMER_QUEUE) String queue) {
//    }
//
//	
//	/**
//	 * 
//	 * SpEL表达式的运行时性质用!{...}分隔符表示。#root表达式的评估上下文对象有三个属性：
//	 * 
//	 * request- o.s.amqp.core.Message请求对象。
//	 * source- o.s.messaging.Message<?>转换后。
//	 * result - 方法结果。
//	 * 上下文具有map属性访问器，标准类型转换器和bean解析器，允许引用上下文中的其他bean（例如@someBeanName.determineReplyQ(request, result)）。
//	 * 
//	 * 总之，#{...}在初始化期间评估一次，#root对象是应用程序上下文; bean由其名称引用。 !{...}在运行时为每个消息评估具有上述属性的根对象，并使用其名称引用bean，前缀为@。
//	 * 
//	 */
////	@RabbitListener(queues = "test.sendTo.spel")
////	@SendTo("!{'some.reply.queue.with.' + result.queueName}")
////	public Bar capitalizeWithSendToSpel(Foo foo) {
////	    return processTheFooAndReturnABar(foo);
////	}
//	
//	/**
//	 * 
//	 * 使用RabbitTemplate从消息转换
//	 * 
//	 * 如上所述，类型信息在消息头中传送，以在从消息转换时帮助转换器。这在大多数情况下都可以正常工作，但是当使用泛型类型时，
//	 * 它只能转换简单对象和已知的“容器”对象（列表，数组，映射）。从2.0版开始，
//	 * 这些Jackson2JsonMessageConverter工具SmartMessageConverter允许它与带有参数的新RabbitTemplate方法一起使用ParameterizedTypeReference; 
//	 * 这允许转换复杂的泛型类型。例如：
//	 * 
//	 */
////	Foo<Bar<Baz, Qux>> foo =
////		    rabbitTemplate.receiveAndConvert(new ParameterizedTypeReference<Foo<Bar<Baz, Qux>>>() { });
//    
//}
