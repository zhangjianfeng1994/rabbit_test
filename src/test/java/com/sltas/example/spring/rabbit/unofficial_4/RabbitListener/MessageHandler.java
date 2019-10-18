//package com.sltas.example.spring.rabbit.unofficial_4.RabbitListener;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageHandler {
//	
//	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
////	@RabbitListener(queues ="zhihao.miao.order")
////    public void handleMessage(byte[] bytes, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
////        System.out.println("====消费消息===handleMessage");
////        System.out.println(new String(bytes));
////        channel.basicAck(tag,false);
////    }
//	
//	
////	//支持自动声明绑定，声明之后自动监听队列的队列，此时@RabbitListener注解的queue和bindings不能同时指定，否则报错
////    @RabbitListener(bindings ={@QueueBinding(value = @Queue(value = "q5",durable = "true"),
////            exchange =@Exchange(value = "zhihao.miao.exchange",durable = "true"),key = "welcome")})
////    public void handleMessage(Message message){
////        System.out.println("====消费消息"+message.getMessageProperties().getConsumerQueue()+"===handleMessage");
////        System.out.println(message.getMessageProperties());
////        System.out.println(new String(message.getBody()));
////    }
//	
//    @RabbitListener(queues = "sltas.info")
//    public void handleMessageSltasInfo(byte[] message){
//        logger.info("sltas.info 消费消息 {}",new String(message));
//    }
//    
//    @RabbitListener(queues = "sltas.debug")
//    public void handleMessageSltasDebug(byte[] message){
//        logger.info("sltas.debug 消费消息 {}",new String(message));
//    }
//
////    @RabbitListener(queues = "sltas.info")
////    public void handleMessageSltasInfo(byte[] message){
////        logger.info("sltas.info 消费消息 {}",new String(message));
////    }
////    
////    @RabbitListener(queues = "sltas.debug")
////    public void handleMessageSltasDebug(byte[] message){
////        logger.info("sltas.debug 消费消息 {}",new String(message));
////    }
//    
//    
////    //此时如果去掉content_type为text，那么会将消息转换成其每个字符的int类型
////    //@RabbitListener(queues = "zhihao.miao.order")
////    public void handleMessage(String message){
////        System.out.println("消费消息");
////        System.out.println(new String(message));
////    }
////
////    //此时不管属性中有没有content_type属性都能接收到数据
////    @RabbitListener(queues = "zhihao.miao.order")
////    public void handleMessage(Message message){
////        System.out.println("====消费消息===handleMessage(message)");
////        System.out.println(message.getMessageProperties());
////        System.out.println(new String(message.getBody()));
////    }
//    
//    /**
//     * 
//     * 总结
//     * 
//     * 如果消息属性中没有指定content_type，则接收消息的处理方法接收类型是byte[],如果消息属性中指定content_type为text，
//     * 则接收消息的处理方法的参数类型是String类型。不管有没有指定content_type，处理消息方法的参数类型是Message都不会报错。
//     * 
//     */
//}
