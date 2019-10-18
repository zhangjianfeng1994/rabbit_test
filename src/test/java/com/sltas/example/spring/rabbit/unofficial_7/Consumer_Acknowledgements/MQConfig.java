package com.sltas.example.spring.rabbit.unofficial_7.Consumer_Acknowledgements;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

//    @Bean
//    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        //默认的确认模式是AcknowledgeMode.AUTO
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        return factory;
//    }
    
    /**
     */
    /**
     * <p>
     * Title: messageListenerContainer
     * </p>
     * <p>
     * Description: 
     * AcknowledgeMode.NONE：自动确认，等效于autoAck=true
     * AcknowledgeMode.MANUAL：手动确认，等效于autoAck=false，此时如果要实现ack和nack回执的话，使用ChannelAwareMessageListener监听器处理
     * </p>
     * @param @param connectionFactory
     * @param @return 
     * @return SimpleMessageListenerContainer
     * @throws
     * @author 周顺宇 
     * @date 2018年10月11日 上午10:17:11 
     */
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("sltas.info");
        /**
         * 自动确认涉及到一个问题就是如果在消息消息的时候抛出异常，消息处理失败，但是因为自动确认而server将该消息删除了。
         * NONE表示自动确认
         */
        /**
         *  AcknowledgeMode.NONE：自动确认，等效于autoAck=true
         */
//        container.setAcknowledgeMode(AcknowledgeMode.NONE);
//        container.setMessageListener((MessageListener) message -> {
//            System.out.println("====接收到消息=====");
//            System.out.println(new String(message.getBody()));
//
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            //相当于自己的一些消费逻辑抛错误
//            throw new NullPointerException("consumer fail");
//
//        });
//        return container;
        /**
         * AcknowledgeMode.MANUAL：手动确认，等效于autoAck=false，此时如果要实现ack和nack回执的话，使用ChannelAwareMessageListener监听器处理
         */
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
//            System.out.println("====接收到消息=====");
//            System.out.println(new String(message.getBody()));
//            TimeUnit.SECONDS.sleep(10);
//            if(message.getMessageProperties().getHeaders().get("error") == null){
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//                System.out.println("消息已经确认");
//            }else {
//                //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
//                System.out.println("消息拒绝");
//            }
//
//        });
//        return container;
        /**
         * AcknowledgeMode.AUTO 根据方法的执行情况来决定是否确认还是拒绝（是否重新入queue）
         * 
         * 如果消息成功被消费（成功的意思就是在消费的过程中没有抛出异常），则自动确认。
         * 1）当抛出AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false（不重新入队列）
         * 2）当抛出ImmediateAcknowledgeAmqpException异常，则消费者会被确认
         * 3）其他的异常，则消息会被拒绝，且requeue=true（如果此时只有一个消费者监听该队列，则有发生死循环的风险，多消费端也会造成资源的极大浪费，这个在开发过程中一定要避免的）。可以通过setDefaultRequeueRejected（默认是true）去设置，
         */
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener((MessageListener) (message) -> {
            System.out.println("====接收到消息=====");
            System.out.println(new String(message.getBody()));
            //抛出NullPointerException异常则重新入队列
            throw new NullPointerException("消息消费失败");
            //当抛出的异常是AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false
            //throw new AmqpRejectAndDontRequeueException("消息消费失败");
            //当抛出ImmediateAcknowledgeAmqpException异常，则消费者会被确认
//            throw new ImmediateAcknowledgeAmqpException("消息消费失败");

        });
        return container;
    }
    
    
    /**
     * 可靠消息总结
     * 
     * 实际使用mq的实例，每段时间定期的给经常订早餐的推送短信（上新品）。
     * 登录短信（也是使用消息中间件）
     * 下单的时候，使用消息中间件发送到配送系统(消息不能丢失）。
     * 
     * 做到消息不能丢失，我们就要实现可靠消息，做到这一点，我们要做到下面二点：
     * 
     * 一：持久化
     * 1: exchange要持久化
     * 2: queue要持久化
     * 3: message要持久化
     * 二：消息确认
     * 1: 启动消费返回（@ReturnList注解，生产者就可以知道哪些消息没有发出去）
     * 2：生产者和Server（broker）之间的消息确认。
     * 3: 消费者和Server（broker）之间的消息确认。
     * 
     */
}
