package com.sltas.example.spring.rabbit.unofficial_1.basics;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * Title: Application_RabbitAdmin.java
 * </p>
 * <p>
 * Description: 
 * 
 * @ComponentScan：会自动扫描包路径下面的所有@Controller、@Service、@Repository、@Component 的类它里面的属性： value指定扫描的包，includeFilters包含那些过滤，
 * excludeFilters不包含那些过滤，useDefaultFilters默认的过滤规则是开启的，如果我们要自定义的话是要关闭的。其中@Filters是一个过滤器的接口。
 * 
 * 参考资料：https://www.jianshu.com/p/e8de480e3598
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月26日 下午5:34:07  
 */
@ComponentScan
public class Application_RabbitAdmin {
	
	private static Logger logger = LoggerFactory.getLogger(Application_RabbitAdmin.class);

	/**
	 * spring-amqp二个核心类RabbitAdmin和RabbitTemplate类
	 * 1.RabbitAdmin类完成对Exchange，Queue，Binging的操作，在容器中管理了RabbitAdmin类的时候，可以对Exchange，Queue，Binging进行自动声明。
	 * 2.RabbitTemplate类是发送和接收消息的工具类。
	 */
	
	/**
	 * 简介
	 * 
	 * Spring AMQP项目将核心Spring概念应用于基于AMQP的消息传递解决方案的开发。 它提供了一个“模板”（template）作为发送和接收消息的高级抽象。 
	 * 它还通过“侦听器容器（listener container）”为消息驱动的POJO提供支持。 这些库促进AMQP资源的管理，同时促进使用依赖注入和声明式配置。 
	 * 在所有这些情况下，您将看到与Spring框架中的JMS支持的相似之处。
	 * 
	 * Spring AMQP包括两个部分；spring-amqp是对amqp的一些概念的一些抽象。spring-rabbit是对AMQP的实现RabbitMQ的实现。
	 */
	
	/**
	 * 特征
	 * 
	 * 异步处理消费消息的一个监听容器（Listener container）
	 * 使用RabbitTemplate类的实例来发送和接收消息。
	 * 使用RabbitAdmin去自动声明队列（queues），交换机（exchanges），绑定（bindings）
	 * spring-amqp模块是对AMQP协议的一个抽象和封装。所以说对所有的AMQP的实现都进行的抽象和封装，比如
	 * org.springframework.amqp.core.Binding：绑定的封装，类型有QUEUE和EXCHANGE。
	 * org.springframework.amqp.core.Exchange：其有基本的四种实现
	 * org.springframework.amqp.core.Message：消息是由属性和body构成，将属性也封装成一个对象MessageProperties。
	 * org.springframework.amqp.core.MessageProperties：对消息属性进行了抽象。
	 * org.springframework.amqp.core.Queue：队列的封装。
	 * 
	 * 还有对消息的转换进行了封装，相关的类在org.springframework.amqp.support.converter包下面。（下面的博客会专门讲解消息转换converter的一些实现）。
	 * 
	 * spring-rabbit模块是建立在spring，spring-amqp，amqp-client（rabbitmq java client）之上的，是具体操作RabbitMQ的，底层对Rabbitmq的操作是使用amqp-client的。
	 * 
	 * 二个核心类，一个是org.springframework.amqp.rabbit.core.RabbitAdmin和org.springframework.amqp.rabbit.core.RabbitTemplate
	 * 
	 * spring-rabbit对日志进行了扩展，可以将日志发送到mq中。
	 * 
	 * 总结：
	 * spring-amqp二个核心类RabbitAdmin和RabbitTemplate类
	 * 1.RabbitAdmin类完成对Exchange，Queue，Binging的操作，在容器中管理了RabbitAdmin类的时候，可以对Exchange，Queue，Binging进行自动声明。
	 * 2.RabbitTemplate类是发送和接收消息的工具类。
	 * 
	 * =============================================================================================================================================
	 * 
	 * RabbitTemplate类是简化RabbitMQ访问的工具类（发送和接收消息）
	 * 
	 * 总结：
	 * 1.使用RabbitTemplate进行消息的发送。
	 * 2.使用SimpleMessageListenerContainer类监听队列，进行消息的消费。
	 * 
	 */
	
	public static void main(String[] args) {
		
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application_RabbitAdmin.class);

        RabbitAdmin rabbitAdmin = context.getBean(RabbitAdmin.class);
        logger.info("RabbitAdmin : {}",rabbitAdmin);
        

        //创建四种类型的Exchange，可重复执行
//        rabbitAdmin.declareExchange(new DirectExchange("sltas.direct.exchange",true,false));
//        rabbitAdmin.declareExchange(new TopicExchange("sltas.topic.exchange",true,false));
//        rabbitAdmin.declareExchange(new FanoutExchange("sltas.fanout.exchange",true,false));
//        rabbitAdmin.declareExchange(new HeadersExchange("sltas.header.exchange",true,false));

        //删除Exchange 删除一个不存在的Exchange不会报错
//        rabbitAdmin.deleteExchange("sltas.header.exchange");
//        rabbitAdmin.deleteExchange("sltas.direct.exchange");
//        rabbitAdmin.deleteExchange("sltas.topic.exchange");
//        rabbitAdmin.deleteExchange("sltas.fanout.exchange");

        //定义队列
//        rabbitAdmin.declareQueue(new Queue("sltas.debug",true));
//        rabbitAdmin.declareQueue(new Queue("sltas.info",true));
//        rabbitAdmin.declareQueue(new Queue("sltas.error",true));

        //删除队列
//        rabbitAdmin.deleteQueue("sltas.debug");
//        rabbitAdmin.deleteQueue("sltas.info");
//        rabbitAdmin.deleteQueue("sltas.error");
        

        //将队列中的消息全消费掉
        rabbitAdmin.purgeQueue("sltas.info",false); //清空一个不存在的 Queue 会报错

//        //绑定,指定要绑定的Exchange和Route key
//        rabbitAdmin.declareBinding(new Binding("sltas.debug",Binding.DestinationType.QUEUE,
//                "sltas.direct.exchange","sltas.hehe",new HashMap()));
//
//        rabbitAdmin.declareBinding(new Binding("sltas.info",Binding.DestinationType.QUEUE,
//                "sltas.direct.exchange","sltas.haha",new HashMap()));
//
//        rabbitAdmin.declareBinding(new Binding("sltas.error",Binding.DestinationType.QUEUE,
//                "sltas.direct.exchange","sltas.welcome",new HashMap()));


        //绑定header exchange
        Map<String,Object> headerValues = new HashMap<>();
        headerValues.put("type",1);
        headerValues.put("size",10);

        //whereAll指定了x-match:   all参数
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("sltas.debug")).
                to(new HeadersExchange("sltas.header.exchange")).whereAll(headerValues).match());

        //whereAll指定了x-match:   any参数
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("sltas.info")).
                to(new HeadersExchange("sltas.header.exchange")).whereAny(headerValues).match());


        //进行解绑 -> 解绑不存在的时候不会报错
        rabbitAdmin.removeBinding(BindingBuilder.bind(new Queue("sltas.info")).
              to(new TopicExchange("sltas.direct.exchange")).with("sltas.info"));
        
//        rabbitAdmin.removeBinding(BindingBuilder.bind(new Queue("sltas.info")).
//                to(new TopicExchange("sltas.direct.exchange")).with("sltas.haha"));
        

        //声明topic类型的exchange
        rabbitAdmin.declareExchange(new TopicExchange("sltas.hehe.exchange",true,false));
        rabbitAdmin.declareExchange(new TopicExchange("sltas.miao.exchange",true,false));

        //exchange与exchange绑定
        rabbitAdmin.declareBinding(new Binding("sltas.hehe.exchange",Binding.DestinationType.EXCHANGE,
                "sltas.miao.exchange","sltas",new HashMap()));

        //使用BindingBuilder进行绑定
        rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("sltas.debug")).
                to(new TopicExchange("sltas.topic.exchange")).with("sltas.miao"));

        //rabbitAdmin.declareBinding(new Binding("amq.rabbitmq.trace",Binding.DestinationType.EXCHANGE,
                //"amq.rabbitmq.log","sltas",new HashMap()));

        context.close();

    }

}
