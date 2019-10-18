package com.sltas.example.framework.api.server;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sltas.flow.api.server.OrderRefundHandler;
import com.sltas.flow.dto.RefundOrder;
import com.sltas.flow.util.api.CommonResponseHeader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RefundOrderTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("orderRefundHandler")
	private OrderRefundHandler orderRefundHandler;
	
	
	@Test
	public void refundOrder() throws InterruptedException{
		
		Date startDate = new Date();
		
		for(int i = 0; i < 10000 ; i ++){
			
			String uuid = UUID.randomUUID().toString();
			
			RefundOrder refundOrder = new RefundOrder();
			refundOrder.setTransNo(uuid);
			refundOrder.setDescribe("发起退款");
			refundOrder.setRefundAmount(new BigDecimal("100"));
			refundOrder.setStartInvoice(false);
			refundOrder.setCreatedTime(new Date());
			
			CommonResponseHeader<?> result = orderRefundHandler.refundExecute(refundOrder);
			logger.info("请求响应结果： {}",result);
			
			TimeUnit.MILLISECONDS.sleep(1000/100);
//			TimeUnit.SECONDS.sleep(10);
			
		}
		
		Date endDate = new Date();
		
		Long times = endDate.getTime() - startDate.getTime();
		
		System.out.println("=========================================="+times);
		System.out.println("=========================================="+times);
		System.out.println("=========================================="+times);
		System.out.println("=========================================="+times);
		System.out.println("=========================================="+times);
		
		TimeUnit.SECONDS.sleep(10000);
		
		
	}
	
	
}
