package com.sltas.example.spring.rabbit.unofficial_3.RabbitListenerConfigurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.AbstractRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        return factory;
    }
    
	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

	int count=0;
	@Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        //public class SimpleRabbitListenerContainerFactory extends AbstractRabbitListenerContainerFactory<SimpleMessageListenerContainer> {
		//SimpleMessageListenerContainer extends AbstractMessageListenerContainer 
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(5);
        factory.setMaxConcurrentConsumers(5);
        factory.setPrefetchCount(1);
        factory.setConsumerTagStrategy(queue -> "order_queue_"+(++count));
        return factory;
    }

    /**
     * <p>
     * Title: rabbitListenerConfigurer
     * </p>
     * <p>
     * Description: 使用总结
     * 
     * 实现RabbitListenerConfigurer接口，并把实现类托管到spring容器中
     * 在spring容器中，托管一个RabbitListenerContainerFactory的bean（SimpleRabbitListenerContainerFactory）
     * 在启动类上加上@EnableRabbit注解
     * </p>
     * @param @return 
     * @return RabbitListenerConfigurer
     * @throws
     * @author 周顺宇 
     * @date 2018年9月29日 下午2:17:19 
     */
    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(){

        return new RabbitListenerConfigurer() {
            @Override
            public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {

                //endpoint设置zhihao.miao.order队列的消息处理逻辑
                SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
                endpoint.setId("10");
                endpoint.setQueueNames("sltas.info");
                endpoint.setMessageListener(message -> {
                	logger.info("endpoint1处理消息的逻辑");
                	logger.info("{}",new String(message.getBody()));
                });


                //使用适配器来处理消息，设置了order，pay队列的消息处理逻辑
                SimpleRabbitListenerEndpoint endpoint2 = new SimpleRabbitListenerEndpoint();
                endpoint2.setId("11");
                endpoint2.setQueueNames("sltas.debug","sltas.error");
                logger.info("endpoint2处理消息的逻辑");
                endpoint2.setMessageListener(new MessageListenerAdapter(new MessageHandler()));
                //注册二个endpoint
                registrar.registerEndpoint(endpoint);
                registrar.registerEndpoint(endpoint2);
                
            }
        };
    }
    
}
