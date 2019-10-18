package com.sltas.order.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sltas.order.domain.OrderHeader;
import com.sltas.order.handler.MybatisHandler;
import com.sltas.order.service.IOrderHeaderService;

@Component("mybatisHandler")
public class MybatisHandlerImpl implements MybatisHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("orderHeaderService")
	private IOrderHeaderService orderHeaderService;
	
	@Override
	public void save(OrderHeader oh) {
		
		logger.info("{}",oh);
		
		orderHeaderService.save(oh);
		
	}

	
	
}
