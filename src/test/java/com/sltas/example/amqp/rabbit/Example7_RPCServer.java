package com.sltas.example.amqp.rabbit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Title: Example7_RPCServer
 * </p>
 * <p>
 * Description: 回调队列
 * </p>
 * <p>
 * </p>
 * 
 * 一般来说，通过RabbitMQ进行RPC很容易。客户端发送请求消息，服务器回复响应消息。
 * 为了接收响应，我们需要发送带有请求的“回调”队列地址。我们可以使用默认队列（在Java客户端中是独占的）。我们来试试吧：
 * 
 * 
 * 关于RPC的说明
 *
 * 尽管RPC在计算中是一种非常常见的模式，但它经常受到批评。当程序员不知道函数调用是本地的还是慢的RPC时，会出现问题。这样的混淆导致系统不可预测，
 * 并增加了调试的不必要的复杂性。错误使用RPC可以导致不可维护的意大利面条代码，而不是简化软件。
 *
 * 考虑到这一点，请考虑以下建议：
 * 
 * 确保明显哪个函数调用是本地的，哪个是远程的。 记录您的系统。使组件之间的依赖关系变得清晰。 处理错误案例。当RPC服务器长时间停机时，客户端应该如何反应？
 * 如有疑问，请避免使用RPC。如果可以，您应该使用异步管道 - 而不是类似RPC的阻塞，将结果异步推送到下一个计算阶段。
 * 
 * 
 * 远程过程调用协议 编辑 同义词 RPC一般指远程过程调用协议 RPC（Remote ProcedureCall）—远程过程调用
 * 
 * 它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的协议。RPC协议假定某些传输协议的存在，如TCP或UDP，
 * 为通信程序之间携带信息数据。在OSI网络通信模型中，RPC跨越了传输层和应用层。RPC使得开发包括网络分布式多程序在内的应用程序更加容易。
 * RPC采用客户机/服务器模式。请求程序就是一个客户机，而服务提供程序就是一个服务器。首先，客户机调用进程发送一个有进程参数的调用信息到服务进程，
 * 然后等待应答信息。在服务器端，进程保持睡眠状态直到调用信息到达为止。当一个调用信息到达，服务器获得进程参数，计算结果，发送答复信息，然后等待下一个调用信息
 * ，最后，客户端调用进程接收答复信息，获得进程结果，然后调用执行继续进行。 有多种 RPC模式和执行。最初由 Sun 公司提出。IETF ONC
 * 宪章重新修订了 Sun 版本，使得 ONC RPC 协议成为 IETF 标准协议。现在使用最普遍的模式和执行是开放式软件基础的分布式计算环境（DCE）。
 * 
 * 
 * @author 周顺宇
 * @date 2018年7月27日下午4:24:20
 * 
 */
public class Example7_RPCServer {

	private static Logger logger = LoggerFactory.getLogger(Example7_RPCServer.class);

	private static final String RPC_QUEUE_NAME = "rpc_queue";

	private static int fib(int n) {
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		return fib(n - 1) + fib(n - 2);
	}

	public static void main(String[] argv) {
		
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("127.0.0.1");

	    Connection connection = null;
	    try {
	      connection      = factory.newConnection();
	      final Channel channel = connection.createChannel();

	      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

	      channel.basicQos(1);

	      logger.info(" [x] Awaiting RPC requests");

	      Consumer consumer = new DefaultConsumer(channel) {
	        @Override
	        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	          AMQP.BasicProperties replyProps = new AMQP.BasicProperties
	                  .Builder()
	                  .correlationId(properties.getCorrelationId())
	                  .build();

	          String response = "";

	          try {
					Thread.sleep(10000l);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          
	          try {
	            String message = new String(body,"UTF-8");
	            int n = Integer.parseInt(message);
	            
	            logger.info(" [.] fib({})",message);
	            response += fib(n);
	          }
	          catch (RuntimeException e){
	            logger.info(" [.] ({})",e.toString());
	          }
	          finally {
	            channel.basicPublish( "", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
	            channel.basicAck(envelope.getDeliveryTag(), false);
	            // RabbitMq consumer worker thread notifies the RPC server owner thread 
	            synchronized(this) {
	            	this.notify();
	            }
	          }
	        }
	      };

	      channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
	      // Wait and be prepared to consume the message from RPC client.
	      while (true) {
	      	synchronized(consumer) {
	      		try {
	      			//导致当前线程等待另一个线程调用
	      			consumer.wait();
	      	    } catch (InterruptedException e) {
	      	    	e.printStackTrace();	    	
	      	    }
	      	}
	      }
	    } catch (IOException | TimeoutException e) {
	      e.printStackTrace();
	    }
	    finally {
	      if (connection != null)
	        try {
	          connection.close();
	        } catch (IOException _ignore) {}
	    }
	  }

}
