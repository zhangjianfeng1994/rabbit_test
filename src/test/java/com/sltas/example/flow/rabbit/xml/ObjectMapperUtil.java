package com.sltas.example.flow.rabbit.xml;
//package com.framework.rabbit.test;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sltas.flow.rabbit.domian.RefundPojo;
//
//public class ObjectMapperUtil {
//
//	public static void main(String[] args) {
//		
//		try {
//			
//        	String uuid = UUID.randomUUID().toString();
//        	
//        	RefundPojo pojo = new RefundPojo();
//        	pojo.setId(uuid);
//        	pojo.setCreateTime(new Date());
//	
//		    ObjectMapper mapper = new ObjectMapper();
//	    
//			String json = mapper.writeValueAsString(pojo);
//			
//			System.out.println(json);
//			System.out.println(json.getBytes(AbstractJsonMessageConverter.DEFAULT_CHARSET));
//			
//			pojo = mapper.readValue(json.getBytes(AbstractJsonMessageConverter.DEFAULT_CHARSET), RefundPojo.class);
//			System.out.println(pojo);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//}
