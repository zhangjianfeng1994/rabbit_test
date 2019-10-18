package com.sltas.example.amqp.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * <p>
 * Title: Example1
 * </p>
 * <p>
 * Description: “Hello World！”
 * </p>
 * <p>
 * RabbitMQ说多种协议。本教程使用AMQP 0-9-1，它是一种开放的，通用的消息传递协议。RabbitMQ有许多不同语言的客户端。我们将使用RabbitMQ提供的Java客户端。
 * 
 * RabbitMQ Java客户端使用com.rabbitmq.client作为其顶级包。关键类和接口是：
 * 
 * Channel：表示AMQP 0-9-1通道，并提供大部分操作（协议方法）。
 * Connection：表示AMQP 0-9-1连接
 * ConnectionFactory：构造Connection实例
 * Consumer：代表消息消费者
 * DefaultConsumer：消费者常用的基类
 * BasicProperties：消息属性（元数据）
 * BasicProperties.Builder：建设者BasicProperties
 * 
 * 协议操作可通过  Channel接口获得。连接用于打开通道，注册连接生命周期事件处理程序以及关闭不再需要的连接。 
 * Connection通过ConnectionFactory实例化，这是您配置各种连接设置的方式，例如vhost或用户名。
 * 
 * </p>
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 *  
 */
public class Example1_Emit {
	
	private static Logger logger = LoggerFactory.getLogger(Example1_Emit.class);
	
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory factory = new ConnectionFactory();
		// "guest"/"guest" by default, limited to localhost connections
//		factory.setUsername("guest");// 用户名
//		factory.setPassword("guest");// 密码
//		factory.setVirtualHost("");// 虚拟主机
		factory.setHost("127.0.0.1");// 主机名
//		factory.setPort(portNumber);//端口  5672用于常规连接， 5671用于使用TLS的连接

		Connection connection = factory.newConnection();
		//通道
		Channel channel = connection.createChannel();
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
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		/**
		 * 发布消息
		 * 
		 * 发布到一个不存在的交换将会导致一个通道级别 协议异常，它关闭通道。
		 * 如果a/code的调用最终会被调用资源驱动的警报系统正在发挥作用，这是一种有效的方法。
		 *
		 * @see com.rabbitmq.client.AMQP.Basic.Publish 资源驱动的警报/信息：资源驱动的警报。
		 * @param exchange		交换交换来发布消息
		 * @param routingKey	路由键路由键
		 * @param props			支持消息-路由标头等的其他属性
		 * @param body			消息体
		 * @throws . io .如果遇到错误，IOException
		 * 
		 * void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;
		 */
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		logger.info(" [x] Sent '{}'",message);
		
		//请注意，关闭频道可能被认为是很好的做法，但在这里并不是必须的 - 当底层连接关闭时，它将自动完成。
	    channel.close();
	    connection.close();
		
	}
	
}
