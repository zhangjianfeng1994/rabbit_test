package com.framework.rabbit.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        /**
         * 设置factory.setPublisherReturns(true);rabbitTemplate.setMandatory(true);
		 * 或rabbitTemplate.setMandatoryExpression(new SpelExpressionParser().parseExpression("(1+2) > 2")); //表达式的值为true
		 * 才会触发returnCallback回调方法的执行。
		 * 
		 * 发送端将出现两种异常情况，
		 * 1.消息无法到达 （ReturnListener）,其中exchange找不到将触发Channel shutdown, routingKey找不到才会触发下面的方法,
		 * 将在路由不到指定的队列时触发rabbitTemplate.setReturnCallback()的方法
		 * 
		 * 2.发送确认（publisher confirms）,其中exchange找不到将触发Channel shutdown, 
		 * 将在路由找不到时报出  reply-code=404, reply-text=NOT_FOUND - no exchange,
		 * 但是会通过rabbitTemplate.setConfirmCallback()报出错误
		 * 
         */
        factory.setPublisherReturns(true);
        /**
         * 消息可靠性的二种方式
		 * 
		 * 1.事务，利用AMQP协议的一部分，发送消息前设置channel为tx模式（channel.txSelect();），
		 * 如果txCommit提交成功了，则消息一定到达了broker了，如果在txCommit执行之前broker异常崩溃或者由于其他原因抛出异常，这个时候我们便可以捕获异常通过txRollback回滚事务了。（大大得削弱消息中间件的性能）
		 * 
		 * 2.消息确认（publish confirms），设置管道为confirmSelect模式（channel.confirmSelect();）
		 * 生产者与broker之间的消息确认称为public confirms，public confirms机制用于解决生产者与Rabbitmq服务器之间消息可靠传输，它在消息服务器持久化消息后通知消息生产者发送成功。
		 * 
		 * 在容器中的ConnectionFactory实例中加上setPublisherConfirms属性
	     * factory.setPublisherConfirms(true);
	     * 在RabbitTemplate实例中增加setConfirmCallback回调方法。
	     * 发送消息的时候，需要指定CorrelationData，用于标识该发送的唯一id。
	     * 对比与java client的publisher confirm：
	     * 1.spring amqp不支持批量确认，底层的rabbitmq java client方式支持批量确认。
	     * 2.spring amqp提供的方式更加的简单明了。
		 * 
		 * 官方描述
		 * https://docs.spring.io/spring-amqp/reference/html/_reference.html#containerAttributes
		 * 
		 * 对于Publisher Confirms（也称为Publisher Acknowledgements），模板需要将CachingConnectionFactory其publisherConfirms属性设置为true。确认通过它注册发送到到客户端RabbitTemplate.ConfirmCallback通过调用setConfirmCallback(ConfirmCallback callback)。回调必须实现此方法：
		 * 
		 * void confirm（CorrelationData correlationData，boolean ack，String cause）;
		 * 这CorrelationData是客户端在发送原始邮件时提供的对象。的ack是一个真实ack的和虚假的nack。对于nacks，原因可能包含nack的原因，如果它在nack生成时可用。一个例子是向不存在的交换机发送消息。在这种情况下，经纪人关闭渠道; 关闭的原因包括在cause。 cause在版本1.4中添加。
		 * 
		 * a只ConfirmCallback支持一个RabbitTemplate。
		 * 
         */
        factory.setPublisherConfirms(true);
        return factory;
    }
    
	/**
	 * spring-amqp二个核心类RabbitAdmin和RabbitTemplate类
	 * 1.RabbitAdmin类完成对Exchange，Queue，Binging的操作，在容器中管理了RabbitAdmin类的时候，可以对Exchange，Queue，Binging进行自动声明。
	 * 2.RabbitTemplate类是发送和接收消息的工具类。
	 */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		
		/**
		 * 如果mandatory有设置，则当消息不能路由到队列中去的时候，会触发return method。
		 * 如果mandatory没有设置，则当消息不能路由到队列的时候，server会删除该消息。
		 */
		rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback(){
            @Override
            public void returnedMessage(Message message,
                                        int replyCode,
                                        String replyText,
                                        String exchange,
                                        String routingKey){
            	logger.info("rabbitTemplate.setReturnCallback() returnedMessage() ");
            	logger.info("replyCode: {}",replyCode);
            	logger.info("replyText: {}",replyText);
            	logger.info("exchange: {}",exchange);
            	logger.info("routingKey: {}",routingKey);
            	//此处进行异常处理
            }
        });
        
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
             * @param ack
             * @param cause
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            	logger.info("rabbitTemplate.setConfirmCallback() confirm()");
                if(ack){
                	logger.info("消息id为: {} 的消息，已经被ack成功",correlationData);
                }else{
                	logger.info("消息id为: {} 的消息，消息nack，失败原因是：{}",correlationData,cause);
                }
            }
        });
        return rabbitTemplate;
    }
	

    @Bean("rabbitListenerContainerFactory2")
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(10);
        factory.setConcurrentConsumers(5);
        
        /**
         * AcknowledgeMode 默认 AcknowledgeMode.AUTO
         * 
         * 
         * 自动确认涉及到一个问题就是如果在消息消息的时候抛出异常，消息处理失败，但是因为自动确认而server将该消息删除了。
         * NONE表示自动确认 
         * 
         * AcknowledgeMode.NONE：自动确认，等效于autoAck=true
         * 
         * AcknowledgeMode.MANUAL：手动确认，等效于autoAck=false，此时如果要实现ack和nack回执的话，使用ChannelAwareMessageListener监听器处理
         * 
         * AcknowledgeMode.AUTO 根据方法的执行情况来决定是否确认还是拒绝（是否重新入queue）
         * 	如果消息成功被消费（成功的意思就是在消费的过程中没有抛出异常），则自动确认。
         * 	1）当抛出AmqpRejectAndDontRequeueException异常的时候，则消息会被拒绝，且requeue=false（不重新入队列）
         * 	2）当抛出ImmediateAcknowledgeAmqpException异常，则消费者会被确认
         * 	3）其他的异常，则消息会被拒绝，且requeue=true（如果此时只有一个消费者监听该队列，则有发生死循环的风险，多消费端也会造成资源的极大浪费，这个在开发过程中一定要避免的）。可以通过setDefaultRequeueRejected（默认是true）去设置，
         * 
         */
//        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        /**
         * The number of unacknowledged messages that can be outstanding at each consumer. The higher this is the faster the messages can be delivered, 
         * but the higher the risk of non-sequential processing. Ignored if the acknowledgeMode is NONE. This will be increased, if necessary, 
         * to match the txSize or messagePerAck. Defaults to 250 since 2.0; set to 1 to revert to previous behavior.
         * 
         * 在每个消费者中，未被认可的消息的数量。越高的消息传递的速度越快，但非顺序处理的风险就越高。
         * 如果acknowledgeMode是NONE，就会被忽略。如果有必要，这将会增加，以匹配txSize或messagePerAck。自2.0以来默认为250;设置为1，恢复到以前的行为。
         */
        factory.setPrefetchCount(1);
//        factory.setTxSize(1);
//      int count = 0;
//        factory.setConsumerTagStrategy(queue -> "order_queue_"+(++count));
//        factory.setMessageConverter(new MessageConverter() {
//            @Override
//            public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
//                return null;
//            }
//            @Override
//            public Object fromMessage(Message message) throws MessageConversionException {
//                return new User(1,new String(message.getBody()));
//            }
//        });
        return factory;
    }

}
