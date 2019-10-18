package com.sltas.example.spring.rabbit.unofficial_7.Consumer_Acknowledgements;

import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Manual_Confirmation_Consumer {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //手动确认
        String consumerTag = channel.basicConsume("sltas.info",false,new Manual_Confirmation_SimpleConsumer(channel));
        System.out.println(consumerTag);

        TimeUnit.SECONDS.sleep(10000);

        channel.close();
        connection.close();
    }
}
