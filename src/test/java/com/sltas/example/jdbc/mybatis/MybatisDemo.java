package com.sltas.example.jdbc.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sltas.order.domain.OrderHeader;
import com.sltas.order.handler.MybatisHandler;
import com.sltas.order.util.id.OrderWorker;

/**
 * <p>
 * Title: MybatisDemo.java
 * </p>
 * <p>
 * Description: MyBatis的主要成员

		Configuration        MyBatis所有的配置信息都保存在Configuration对象之中，配置文件中的大部分配置都会存储到该类中
		SqlSession            作为MyBatis工作的主要顶层API，表示和数据库交互时的会话，完成必要数据库增删改查功能
		Executor               MyBatis执行器，是MyBatis 调度的核心，负责SQL语句的生成和查询缓存的维护
		StatementHandler 封装了JDBC Statement操作，负责对JDBC statement 的操作，如设置参数等
		ParameterHandler  负责对用户传递的参数转换成JDBC Statement 所对应的数据类型
		ResultSetHandler   负责将JDBC返回的ResultSet结果集对象转换成List类型的集合
		TypeHandler          负责java数据类型和jdbc数据类型(也可以说是数据表列类型)之间的映射和转换
		MappedStatement  MappedStatement维护一条<select|update|delete|insert>节点的封装
		SqlSource              负责根据用户传递的parameterObject，动态地生成SQL语句，将信息封装到BoundSql对象中，并返回
		BoundSql              表示动态生成的SQL语句以及相应的参数信息
		以上主要成员在一次数据库操作中基本都会涉及，在SQL操作中重点需要关注的是SQL参数什么时候被设置和结果集怎么转换为JavaBean对象的，这两个过程正好对应StatementHandler和ResultSetHandler类中的处理逻辑。
		
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2019年5月30日 下午3:12:33  
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class MybatisDemo extends AbstractJUnit4SpringContextTests{

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	@Qualifier("mybatisHandler")
	private MybatisHandler mybatisHandler;
	
	@Test
	public void mybatisAopSession() {
		
		logger.info("mybatis aop session");
		
		OrderWorker ow = OrderWorker.initNo(OrderWorker.TRANS_NO);
		OrderHeader oh = new OrderHeader();
		oh.setId(ow.getId());
		oh.setTransNo(ow.getNo());
		oh.setGmtDate(ow.getGmtDate());
		
		mybatisHandler.save(oh);
		
	}
	
}
