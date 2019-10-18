package com.framework.rabbit.xml;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class ChannelAwareMessage implements ChannelAwareMessageListener {

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		System.out.println("=====消费消息======");
        System.out.println("消息的优先级是："+message.getMessageProperties().getPriority()+
                " 消息内容是："+new String(message.getBody()));
	}

}
