package com.sltas.flow.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Title: BusinessLock.java
 * </p>
 * <p>
 * Description: 业务锁
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2018年11月5日 上午10:26:50  
 */
public class BusinessLock implements Lock{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * 本地化线程
	 */
	private final static ThreadLocal<BusinessLock> lock = new ThreadLocal<BusinessLock>();
	
	/**
	 * 默认等待时间
	 */
	private final static long defaultWaitTime = 30;
	
	/**
	 * 闭锁(CountDownLatch)是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
	 */
	private volatile CountDownLatch latch;
	
	/**
	 * 日志统一规则打印
	 */
	private final String logMark;
	
	/**
	 * <p>
	 * Title: BusinessLock.java
	 * </p>
	 * <p>
	 * Description: 业务锁
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @param logMark 日志统一规则
	 * @param count 初始化闭锁数量
	 * 
	 */
	private BusinessLock(String logMark,int count){
		this.logMark = logMark;
		this.latch = new CountDownLatch(count);
	}

	/**
	 * 获取锁。 
	 */
	@Override
	public void lock() {
		try {
			if(this.tryLock()){
				return;
			}else{
				logger.info("{} [lock] Thread [{}] Didn't get the lock ！sleep {} ",logMark,Thread.currentThread().getId(),defaultWaitTime);
				this.tryLock(defaultWaitTime, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			logger.error("{} [lock] Lock failure : {}",logMark,e,e);
		}
		
	}

	/**
	 * 获取该锁除非当前线程 interrupted。 
	 */
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 只有在调用时释放该锁，才能获取锁。 
	 */
	@Override
	public boolean tryLock() {
		if(this.latch.getCount() == 0){
			
		}
		return false;
	}

	/**
	 * 获取锁，如果它是免费的在给定的等待时间和当前线程没有被 interrupted。 
	 */
	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 释放锁。 
	 */
	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 返回一个新的 Condition实例绑定到该 Lock实例。 
	 */
	@Override
	public Condition newCondition() {
		return null;
	}
	
	
}
