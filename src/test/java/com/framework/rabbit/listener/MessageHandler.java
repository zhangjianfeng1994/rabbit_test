package com.framework.rabbit.listener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class MessageHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
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
	 * @throws IOException 
	 */
	
	 /**
	  * 
	  * Rabbitmq自己扩展的功能，不是AMQP协议定义的。
	  * 
	  * Alternate Exchange
	  * 
	  * 属性的作用，创建Exchange指定该Exchange的Alternate Exchange属性，发送消息的时候根据route key并没有把消息路由到队列中去，这就会将此消息路由到Alternate Exchange属性指定的Exchange上了。
	  * alternate-exchange
	  * 如果对该交易所的消息不能被路由，请将它们发送到此处指定的交换交换。（设置“alternate-exchange”的论点。）
	  * 
	  * Dead Letter Exchange
	  * 
	  * 在队列上指定一个Exchange，则在该队列上发生如下情况，
	  * 1.消息被拒绝（basic.reject or basic.nack)，且requeue=false
	  * 2.消息过期而被删除（TTL）
	  * 3.消息数量超过队列最大限制而被删除
	  * 4.消息总大小超过队列最大限制而被删除
	  * 就会把该消息转发到指定的这个exchange
	  * 同时也可以指定一个可选的x-dead-letter-routing-key，表示默认的routing-key，如果没有指定，则使用消息的routeing-key(也跟指定的exchange有关，
	  * 如果是Fanout类型的exchange，则会转发到所有绑定到该exchange的所有队列）。
	  * 
	  * 
	  */
	@RabbitListener(
		containerFactory="rabbitListenerContainerFactory2",
		bindings = @QueueBinding(
				value = @Queue(
						value = "sltas.info",
						arguments = @Argument(name = "x-max-priority",type="java.lang.Integer",value="10")
						), 
				exchange = @Exchange(
						value = "sltas.direct.exchange",
						type = ExchangeTypes.DIRECT,
						arguments = @Argument(name = "alternate-exchange",value="sltas.alternate.exchange")
						),
				key = "sltas.info.routingKey"
				)
	)
	public void handleMessage(Message message, Channel channel) throws IOException {
		
		logger.info("=====消费消息======");
		logger.info("消息的优先级是：{} 消息内容是：{}",message.getMessageProperties().getPriority(),new String(message.getBody()));
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//        Integer.valueOf("abc");
//        if(message.getMessageProperties().getHeaders().get("error") == null){
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//            logger.info("消息已经确认");
//        }else {
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
//        	logger.info("消息拒绝");
//        }
        
	}
	

}
