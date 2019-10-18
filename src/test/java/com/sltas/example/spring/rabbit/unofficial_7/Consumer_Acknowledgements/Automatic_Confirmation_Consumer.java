package com.sltas.example.spring.rabbit.unofficial_7.Consumer_Acknowledgements;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeUnit;

public class Automatic_Confirmation_Consumer {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setUri("amqp://zhihao.miao:123456@192.168.1.131:5672");
        connectionFactory.setHost("127.0.0.1");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        /**
         * basicConsume方法的第二个参数是boolean类型，true表示消息一旦投递出去就自动确认，而false表示需要自己手动去确认
         * 自动确认有丢消息的可能，因为如果消费端消费逻辑抛出异常，也就是消费端没有处理成功这条消息，那么就相当于丢失了消息
         * 设置了false，表示需要人为手动的去确定消息，只有消费者将消息消费成功之后给与broker人为确定才进行消息确认
         * 这边也有个问题就是如果由于程序员自己的代码的原因造成人为的抛出异常，人工确认那么消息就会一直重新入队列，一直重发？
         */

        String consumerTag = channel.basicConsume("sltas.debug",true,new Automatic_Confirmation_SimpleConsumer(channel));
        System.out.println(consumerTag);

        TimeUnit.SECONDS.sleep(30);

        channel.close();
        connection.close();
    }
}
