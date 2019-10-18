package com.sltas.example.spring.rabbit.unofficial_7.Consumer_Acknowledgements;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Automatic_Confirmation_SimpleConsumer extends DefaultConsumer {

    public Automatic_Confirmation_SimpleConsumer(Channel channel){
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println(consumerTag);
        System.out.println("-----收到消息了---------------");
        System.out.println("消息属性为："+properties);
        System.out.println("消息内容为："+new String(body));
        try
        {
            int i = 1/0;
            System.out.println(i);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    
    /**
     * 此时可以看到消费端抛出了异常，但是我们发现这条消息也已经消费掉了，此时如果消费端消费逻辑使用spring进行管理的话消费端业务逻辑会进行回滚，
     * 这也就造成了实际意义的消息丢失。
     */
}
