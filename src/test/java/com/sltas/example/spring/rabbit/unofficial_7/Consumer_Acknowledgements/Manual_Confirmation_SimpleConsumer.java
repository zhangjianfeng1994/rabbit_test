package com.sltas.example.spring.rabbit.unofficial_7.Consumer_Acknowledgements;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Manual_Confirmation_SimpleConsumer extends DefaultConsumer {

    public Manual_Confirmation_SimpleConsumer(Channel channel){
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException{
        System.out.println(consumerTag);
        System.out.println("-----收到消息了--------------");

        if(properties.getHeaders().get("error") != null){
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
          
            System.out.println("nack");
            this.getChannel().basicNack(envelope.getDeliveryTag(),false,true);

            
          //这个api也支持拒绝消息消费，第二个参数表示是否重新入队列
//            this.getChannel().basicReject(envelope.getDeliveryTag(),false);
            
            return;
        }
        System.out.println("消息属性为："+properties);
        System.out.println("消息内容为："+new String(body));

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.getChannel().basicAck(envelope.getDeliveryTag(),false);
        System.out.println("消息消费成功");
    }
}
