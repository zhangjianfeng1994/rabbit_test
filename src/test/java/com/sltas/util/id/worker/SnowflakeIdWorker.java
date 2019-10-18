package com.sltas.util.id.worker;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * ProjectName: sltas_customer_client
 * ClassName: SnowflakeIdWorker
 * Date: 2018/9/11 11:28
 * Content: 雪花算法单例模式
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
public class SnowflakeIdWorker {

    /*--------------------static_filed--------------------*/

    /**
     * 开始时间戳(2015-01-01)
     */
    private static final long START_TIMESTAMP = 1288834974657L;
    /**
     * 机器id的占位数
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 数据标识id所占的位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;
    /**
     * 序列在id中占的位数12位
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 支持的最大机器id，结果是31
     * (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long  MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    /**
     * 支持的最大数据标识id，结果是31
     * (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据标识id向左移17位(12+5)
     * sequenceBits是12位,workerIdBits是5位
     */
    private static final long DATA_CENTER_ID_SHIFT = 17L;
    /**
     * 时间戳向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = 22L;
    /**
     * 生成序列的掩码，这里最大为4095
     */
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
    /**
     * 毫秒内序列(0~4095)
     * 上一次生产id的时间戳
     */
    private Long sequence = 0L;
    private Long lastTimestamp = -1L;
    /**
     * 单例的对象
     */
    private static SnowflakeIdWorker snowflakeIdWorker;


    /*--------------------static_code--------------------*/

    static {
        snowflakeIdWorker = new SnowflakeIdWorker();
    }

    /*--------------------private_filed--------------------*/

    /**
     * 工作机器id(0~31)
     * 数据中心id(0~31)
     */
    private Long workerId;
    private Long datacenterId;
    private void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }
    private void setDatacenterId(Long datacenterId) {
        this.datacenterId = datacenterId;
    }


    /*--------------------constructor--------------------*/

    /**
     * 私有无参构造方法
     */
    private SnowflakeIdWorker(){}

    /**
     * 对外提供获得实体的公共方法
     */
    static SnowflakeIdWorker getinstance(Long workerId,Long datacenterId){
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATA_CENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        snowflakeIdWorker.setWorkerId(workerId);
        snowflakeIdWorker.setDatacenterId(datacenterId);
        return snowflakeIdWorker;
    }
    static SnowflakeIdWorker getinstance(Long workerId){
        Long datacenterId = 0L;
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATA_CENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        snowflakeIdWorker.setWorkerId(workerId);
        snowflakeIdWorker.setDatacenterId(datacenterId);
        return snowflakeIdWorker;
    }

    /*--------------------tools_method--------------------*/

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp		旧时间戳
     * @return					新时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }
    /**
     * 返回以毫秒为单位的当前时间
     * @return  返回当前的时间毫秒单位
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
    /**
     * 生成线程安全的id
     * @return 生成的id
     */
    synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT) | (datacenterId << DATA_CENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
    }
    
    public static void main(String[] args) {
		
    	Set<Long> set = new HashSet<Long>();
    	
    	CountDownLatch c = new CountDownLatch(1);
    	
    	new Thread(()->{
    		SnowflakeIdWorker s = SnowflakeIdWorker.getinstance(10l);
    		for(int i = 0 ;  i < 500000 ; i ++){
        		set.add(s.nextId());
        	}
    		c.countDown();
    	}).start();
    	
    	new Thread(()->{
    		SnowflakeIdWorker s = SnowflakeIdWorker.getinstance(11l);
    		for(int i = 0 ;  i < 500000 ; i ++){
        		set.add(s.nextId());
        	}
    		c.countDown();
    	}).start();
    	
//    	new Thread(()->{
//    		SnowflakeIdWorker s = SnowflakeIdWorker.getinstance(12l);
//    		for(int i = 0 ;  i < 500000 ; i ++){
//        		set.add(s.nextId());
//        	}
//    		c.countDown();
//    	}).start();
    	
    	try {
    		c.await();
    		
//    		TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	System.out.println(set.size());
    	
	}

}
