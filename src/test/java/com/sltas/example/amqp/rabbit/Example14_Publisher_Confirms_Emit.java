package com.sltas.example.amqp.rabbit;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



/**
 * <p>
 * Title: Example14_Publisher_Confirms_Emit.java
 * </p>
 * <p>
 * Description: 流媒体轻量级发布者确认
 * 
 * 在这种情况下使用事务有两个问题。第一个是它们阻塞：发布者必须等待代理处理每个消息。Â知道所有可能除了最后一个消息被成功处理的消息通常都是过于强大的保证; 
 * 如果发布者知道当经纪人去世时尚未处理哪些消息就足够了。第二个问题是事务处理不必要：每次提交都需要fsync（），这需要花费大量时间才能完成。
 * 输入确认：一旦通道进入确认模式，代理将在处理消息时确认消息。Â由于这是异步完成的，生产者可以流式发布而不是等待代理，代理可以有效地批量磁盘写入。 
 * 
 * 完整代码可在此处获得。在继续之前，值得一提的是，运行此操作大约需要2秒钟。它比事务代码快100多倍。
 * 代码做了什么？它首先声明一个将保存迄今未经证实的消息的ID的集合。然后，它将通道设置为确认模式并将AckListener附加到通道。Â在发布消息时，会将它们添加到集合中; 
 * 同时，AckListener在接收确认时从集合中删除消息。最后，生产者等待确认所有消息。Â该组始终保存在发生故障时需要重新传输的消息。
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年9月19日 下午5:05:14  
 */
public class Example14_Publisher_Confirms_Emit {

	private static Logger logger = LoggerFactory.getLogger(Example14_Publisher_Confirms_Emit.class);

	private static final String EXCHANGE_NAME = "direct_logs";
	
	private static final String routingKey = "info";
	
	private volatile static SortedSet<Long> unconfirmedSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		
		channel.addConfirmListener(new ConfirmListener() {
		    public void handleAck(long seqNo, boolean multiple) {
		    	logger.info(" [x] handleAck seqNo '{}' multiple '{}'", seqNo,multiple);
		        if (multiple) {
		            unconfirmedSet.headSet(seqNo+1).clear();
		        } else {
		            unconfirmedSet.remove(seqNo);
		        }
		    }
		    public void handleNack(long seqNo, boolean multiple) {
		        // handle the lost messages somehow
		    	logger.info(" [x] handleNack seqNo '{}' multiple '{}'", seqNo,multiple);
		    }
		});
		
		//开启消息确认
		channel.confirmSelect();
		
		for(int i = 0 ; i < 10 ; i++){
			String message = "Hello World!"+i;
			unconfirmedSet.add(channel.getNextPublishSeqNo());
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
			logger.info(" [x] Sent '{}' routingKey '{}'", message,routingKey);
		}
		while (unconfirmedSet.size() > 0)
		     Thread.sleep(10);

		channel.close();
		connection.close();

	}

}
