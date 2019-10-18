//package com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.recv;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@ComponentScan
//public class Application {
//	
//	private static Logger logger = LoggerFactory.getLogger(Application.class);
//	
//	public static void sendOrderList(RabbitTemplate rabbitTemplate) throws Exception{
//	    Order order = new Order();
//	    order.setId(1);
//	    order.setUserId(1000);
//	    order.setAmout(88d);
//	    order.setTime(LocalDateTime.now().toString());
//
//	    Order order2 = new Order();
//	    order2.setId(2);
//	    order2.setUserId(2000);
//	    order2.setAmout(99d);
//	    order2.setTime(LocalDateTime.now().toString());
//
//	    List<Order> orderList = Arrays.asList(order,order2);
//
//	    ObjectMapper mapper = new ObjectMapper();
//	    String json = mapper.writeValueAsString(orderList);
//
//	    MessageProperties messageProperties = new MessageProperties();
//	    messageProperties.setContentType("application/json");
//	    messageProperties.getHeaders().put("__TypeId__","java.util.List");
//	    messageProperties.getHeaders().put("__ContentTypeId__","order");
//
//
//	    Message message = new Message(json.getBytes(),messageProperties);
//	    rabbitTemplate.send("sltas.direct.exchange","sltas.info",message);
//	}
//
//
//	public static void sendOrderMap(RabbitTemplate rabbitTemplate) throws Exception{
//	    Order order = new Order();
//	    order.setId(1);
//	    order.setUserId(1000);
//	    order.setAmout(88d);
//	    order.setTime(LocalDateTime.now().toString());
//
//	    Order order2 = new Order();
//	    order2.setId(2);
//	    order2.setUserId(2000);
//	    order2.setAmout(99d);
//	    order2.setTime(LocalDateTime.now().toString());
//
//	    Map<String,Object> orderMaps = new HashMap<>();
//	    orderMaps.put("10",order);
//	    orderMaps.put("20",order2);
//
//	    ObjectMapper mapper = new ObjectMapper();
//	    String json = mapper.writeValueAsString(orderMaps);
//
//	    MessageProperties messageProperties = new MessageProperties();
//	    messageProperties.setContentType("application/json");
//	    messageProperties.getHeaders().put("__TypeId__","java.util.Map");
//	    messageProperties.getHeaders().put("__KeyTypeId__","java.lang.String");
//	    messageProperties.getHeaders().put("__ContentTypeId__","order");
//
//
//	    Message message = new Message(json.getBytes(),messageProperties);
//	    rabbitTemplate.send("sltas.direct.exchange","sltas.info",message);
//	}
//	
//	public static void sendJepg(RabbitTemplate rabbitTemplate) throws Exception{
//        byte[] body = Files.readAllBytes(Paths.get("D:/","timg3.jpg"));
//
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("image/jepg");
//
//        Message message = new Message(body,messageProperties);
//
//        rabbitTemplate.send("sltas.direct.exchange","sltas.info",message);
//    }
//
//    public static void sendJson( RabbitTemplate rabbitTemplate) throws Exception{
//
//
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("text/plain");
//        Message message = new Message("hello".getBytes(),messageProperties);
//        rabbitTemplate.send("sltas.direct.exchange","sltas.info",message);
//    }
//
//	
//	public static void sendOrder(RabbitTemplate rabbitTemplate) throws Exception{
//		
//        Order order = new Order();
//        order.setId(1);
//        order.setUserId(1000);
//        order.setAmout(88d);
//        order.setTime(LocalDateTime.now().toString());
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(order);
//        logger.info("send message json : {}",json);
//
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("application/json");
//        //指定的__TypeId__属性值必须是消费端的Order的全类名，如果不匹配则会报错。
////        messageProperties.getHeaders().put(DefaultJackson2JavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME,Order.class.getName());
//        messageProperties.getHeaders().put(DefaultJackson2JavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME,"order");
//        Message message = new Message(json.getBytes(),messageProperties);
//
//        rabbitTemplate.send("sltas.direct.exchange","sltas.info",message);
//    }
//	
//	@SuppressWarnings("resource")
//	public static void main(String[] args) throws Exception {
//		
//		/**
//		 * 使用Jackson2JsonMessageConverter处理器，客户端发送JSON类型数据，但是没有指定消息的contentType类型，
//		 * 那么Jackson2JsonMessageConverter就会将消息转换成byte[]类型的消息进行消费。
//		 * 
//		 * 如果指定了contentType为application/json，那么消费端就会将消息转换成Map类型的消息进行消费。
//		 * 如果指定了contentType为application/json，并且生产端是List类型的JSON格式，那么消费端就会将消息转换成List类型的消息进行消费。
//		 */
//		
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
//
//        RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
//        logger.info("RabbitTemplate : {}",rabbitTemplate);
//        
////        sendOrder(rabbitTemplate);
//        
//        
//        /**
//         * 如果生产者发送的是list的json数据，则还需要增加一个__ContentTypeId__的header，用于指明List里面的具体对象。
//         * 
//         * 如果生产者发送的是map的json数据，则需要指定__KeyTypeId__，__ContentTypeId__的header，用于指明map里面的key，value的具体对象。
//         */
////        sendOrderMap(rabbitTemplate);
////        sendOrderList(rabbitTemplate);
//        		
////        sendJson(rabbitTemplate);
//        
//        sendJepg(rabbitTemplate);
//        
//        
////        Order order = new Order();
////        order.setId(1);
////        order.setUserId(1000);
////        order.setAmout(88d);
////        order.setTime(LocalDateTime.now().toString());
////
//////        String json = new Gson().toJson(order);
//////        logger.info("send message json : {}",json);
//////        rabbitTemplate.convertAndSend("sltas.direct.exchange","sltas.info",json);
////        
////        //等同于new Gson().toJson(order)
////        ObjectMapper mapper = new ObjectMapper();
////        String json = mapper.writeValueAsString(order);
////        logger.info("send message json : {}",json);
////        
////        MessageProperties messageProperties = new MessageProperties();
////        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
////        Message message = new Message(json.getBytes(),messageProperties);
////        
////        rabbitTemplate.send("sltas.direct.exchange","sltas.info",message);
////        
////        Order order2 = new Order();
////        order2.setId(1);
////        order2.setUserId(1000);
////        order2.setAmout(99d);
////        order2.setTime(LocalDateTime.now().toString());
////        
////        List<Order> orderList = new ArrayList<>();
////        orderList.add(order);
////        orderList.add(order2);
////        
////        String jsonlist = mapper.writeValueAsString(orderList);
////        Message message2 = new Message(jsonlist.getBytes(),messageProperties);
////        
////        rabbitTemplate.send("sltas.direct.exchange","sltas.info",message2);
//        
//        context.close();
//
//    }
//
//}
