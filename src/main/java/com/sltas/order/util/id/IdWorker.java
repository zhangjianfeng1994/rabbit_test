package com.sltas.order.util.id;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

import com.sltas.order.util.DateTimeUtils;

/**
 * 高效GUID产生算法(sequence),基于Snowflake实现64位自增ID算法。
 * <p>优化开源项目 https://gitee.com/yu120/sequence</p>
 *
 * @author hubin
 * @since 2016-08-01
 */
public class IdWorker {

    /**
     * 主机和进程的机器码
     */
    private static Sequence WORKER = new Sequence();

    /**
     * 有参构造器
     *
     * @param workerId     工作机器 ID
     * @param datacenterId 序列号
     */
    public static void initSequence(long workerId, long datacenterId) {
        WORKER = new Sequence(workerId, datacenterId);
    }
    
    /**
     * 有参构造器
     *
     * @param workerId     工作机器 ID
     */
    public static void initSequence(long workerId) {
        WORKER = new Sequence(workerId);
    }
    
    public static long getId() {
        return WORKER.nextId();
    }
    
    
    /**
     * <p>
     * Title: parseId
     * </p>
     * <p>
     * Description: 通过ID值解析生成时间戳
     * </p>
     * @param @param id
     * @param @return 
     * @return LocalDateTime
     * @throws
     * @author 周顺宇 
     * @date 2019年1月7日 下午5:47:14 
     */
    public static LocalDateTime parseId(long id) {
    	
    	Assert.notNull(id, "id can not be null");
    	
    	return DateTimeUtils.getDateTimeOfTimestamp(WORKER.getTimestamp(id));
    }
    
    /**
     * <p>
     * Title: getNo
     * </p>
     * <p>
     * Description: 通过  标识+ ID 
     * </p>
     * @param @param mark	1位标识
     * @param @param id		主键
     * @param @return 
     * @return String
     * @throws
     * @author 周顺宇 
     * @date 2019年1月7日 下午5:49:36 
     */
    public static String getNo(String mark,Long id) {
    	
    	Assert.notNull(mark, "mark can not be null");
		Assert.notNull(id, "id can not be null");
		
		return new StringBuffer(mark)
				.append(id)
				.toString();
	}
    
    /**
     * <p>
     * Title: parseNo
     * </p>
     * <p>
     * Description:  通过 No 获得操作时间
     * </p>
     * @param @param no
     * @param @return 
     * @return LocalDateTime
     * @throws
     * @author 周顺宇 
     * @date 2019年1月7日 下午5:49:22 
     */
    public static LocalDateTime parseNo(String no) {
    	
    	Assert.notNull(no, "no can not be null");
    	
    	long id = Long.valueOf(no.substring(OrderWorker.TRANS_NO.length(), no.length()));
    	return DateTimeUtils.getDateTimeOfTimestamp(WORKER.getTimestamp(id));
    	
    }

}