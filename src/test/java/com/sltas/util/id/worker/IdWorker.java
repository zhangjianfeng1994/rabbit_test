package com.sltas.util.id.worker;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdWorker {
	
	private static Logger logger = LoggerFactory.getLogger(IdWorker.class);

    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
 
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
 
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
 
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
 
        lastTimestamp = timestamp;
 
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }
 
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
 
    protected long timeGen() {
        return System.currentTimeMillis();
    }
 
//    public static void main(String[] args) {
//       
//        for (int i = 0; i < 10; i++) {
//            long id = idWorker.nextId();
//            System.out.println(id);
//        }
//    }
    
    public static void main(String[] args) {
		
    	Set<Long> set = new HashSet<Long>();
    	
    	
    	
    	CountDownLatch c = new CountDownLatch(3);
    	
    	new Thread(()->{
    		IdWorker idWorker = new IdWorker(21, 10);
    		for(int i = 0 ;  i < 500000 ; i ++){
        		set.add(idWorker.nextId());
        	}
    		c.countDown();
    	}).start();
    	
    	new Thread(()->{
    		IdWorker idWorker = new IdWorker(22, 10);
    		for(int i = 0 ;  i < 500000 ; i ++){
        		set.add(idWorker.nextId());
        	}
    		c.countDown();
    	}).start();
    	
    	new Thread(()->{
    		IdWorker idWorker = new IdWorker(23, 10);
    		for(int i = 0 ;  i < 500000 ; i ++){
        		set.add(idWorker.nextId());
        	}
    		c.countDown();
    	}).start();
    	
    	try {
    		c.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	logger.info("{}",set.size());
    	
//    	try {
//			TimeUnit.SECONDS.sleep(100000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
    
}
