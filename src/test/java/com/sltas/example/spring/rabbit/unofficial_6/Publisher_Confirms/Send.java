package com.sltas.example.spring.rabbit.unofficial_6.Publisher_Confirms;

import java.io.IOException;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * <p>
 * Title: Send.java
 * </p>
 * <p>
 * Description: 消息可靠性的二种方式
 * 
 * 1.事务，利用AMQP协议的一部分，发送消息前设置channel为tx模式（channel.txSelect();），
 * 如果txCommit提交成功了，则消息一定到达了broker了，如果在txCommit执行之前broker异常崩溃或者由于其他原因抛出异常，这个时候我们便可以捕获异常通过txRollback回滚事务了。（大大得削弱消息中间件的性能）
 * 
 * 2.消息确认（publish confirms），设置管道为confirmSelect模式（channel.confirmSelect();）
 * 生产者与broker之间的消息确认称为public confirms，public confirms机制用于解决生产者与Rabbitmq服务器之间消息可靠传输，它在消息服务器持久化消息后通知消息生产者发送成功。
 * 
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年10月10日 下午5:34:20  
 */
public class Send {

	static Long id = 0L;

    static TreeSet<Long> tags = new TreeSet<>();

    public static Long send(Channel channel,byte[] bytes) throws Exception{
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().deliveryMode(2).
                contentEncoding("UTF-8").build();
        channel.basicPublish("sltas.direct.exchange","sltas.info",properties,bytes);
        return ++id;
    }

	/**
	 * channel.waitForConfirms():表示等待已经发送给broker的消息act或者nack之后才会继续执行。
	 * channel.waitForConfirmsOrDie():表示等待已经发送给broker的消息act或者nack之后才会继续执行，如果有任何一个消息触发了nack则抛出IOException。
	 */
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
//        connectionFactory.setUri("amqp://zhihao.miao:123456@192.168.1.131:5672");
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //是当前的channel处于确认模式
        channel.confirmSelect();

        //使当前的channel处于事务模式，与上面的使channel处于确认模式使互斥的
        //channel.txSelect();

        /**
         * deliveryTag 消息id
         * multiple 是否批量
         *      如果是true，就意味着，小于等于deliveryTag的消息都处理成功了
         *      如果是false，只是成功了deliveryTag这一条消息
         */
        channel.addConfirmListener(new ConfirmListener() {
            //消息发送成功并且在broker落地，deliveryTag是唯一标志符，在channek上发布的消息的deliveryTag都会比之前加1
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("=========deliveryTag==========");
                System.out.println("deliveryTag: "+deliveryTag);
                System.out.println("multiple: "+multiple);
                //处理成功发送的消息
                if(multiple){
                    //批量操作
                    for(Long _id:new TreeSet<>(tags.headSet(deliveryTag+1))){
                        tags.remove(_id);
                    }
                }else{
                    //单个确认
                    tags.remove(deliveryTag);
                }

                System.out.println("未处理的消息: "+tags);
            }

            /**
             * deliveryTag 消息id
             * multiple 是否批量
             *      如果是true，就意味着，小于等于deliveryTag的消息都处理失败了
             *      如果是false，只是失败了deliveryTag这一条消息
             */
            //消息发送失败或者落地失败
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("===========handleNack===========");
                System.out.println("deliveryTag: "+deliveryTag);
                System.out.println("multiple: "+multiple);
            }
        });

        /**
         * 当Channel设置成confirm模式时，发布的每一条消息都会获得一个唯一的deliveryTag
         * deliveryTag在basicPublish执行的时候加1
         */


        Long id = send(channel,"你的外卖已经送达".getBytes());
        tags.add(id);
        //channel.waitForConfirms();

        id =send(channel,"你的外卖已经送达".getBytes());
        tags.add(id);
        //channel.waitForConfirms();

        id = send(channel,"呵呵，不接电话".getBytes());
        tags.add(id);
        //channel.waitForConfirms();  

        TimeUnit.SECONDS.sleep(1000);

        channel.close();
        connection.close();
    }
	
    /**
     * 总结
     * 生产者与broker之间的消息可靠性保证的基本思路就是
     * 
     * 当消息发送到broker的时候，会执行监听的回调函数，其中deliveryTag是消息id（在同一个channel中这个数值是递增的，而multiple表示是否批量确认消息。
     * 在生产端要维护一个消息发送的表，消息发送的时候记录消息id，在消息成功落地broker磁盘并且进行回调确认（ack）的时候，根据本地消息表和回调确认的消息id进行对比，这样可以确保生产端的消息表中的没有进行回调确认（或者回调确认时网络问题）的消息进行补救式的重发，当然不可避免的就会在消息端可能会造成消息的重复消息。针对消费端重复消息，在消费端进行幂等处理。（丢消息和重复消息是不可避免的二个极端，比起丢消息，重复消息还有补救措施，而消息丢失就真的丢失了。
     * 
     */
}
