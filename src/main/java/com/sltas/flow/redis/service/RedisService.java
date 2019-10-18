package com.sltas.flow.redis.service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisStringCommands.SetOption;

public interface RedisService {

	/**
	 * <p>
	 * Title: executeTpsHandler
	 * </p>
	 * <p>
	 * Description: 执行tps控制
	 * </p>
	 * <p>
	 * 
 	 * 	local current
	 *	current = redis.call("incr",KEYS[1])
	 *	if tonumber(current) == 1 then
	 *	    redis.call("expire",KEYS[1],ARGV[2])
	 *	end
	 *	
	 *	if tonumber(current) > ARGV[1] then
	 *		return 1
	 *	else
	 *		return -1
	 *	end
	 *
	 * redis 命令
	 * eval "local current = redis.call("incr",KEYS[1]) if tonumber(current) == 1 then redis.call("expire",KEYS[1],ARGV[2]) end if tonumber(current) > tonumber(ARGV[1]) then return -1 else return 1 end" 1 test_127.0.0.1 1 1800
	 *
	 * </p>
	 * 使用场景多节点
	 * 
	 * @tags @param token	锁
	 * @tags @param time	过期时间
	 * @tags @param tps		TPS
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月27日上午10:13:19
	 */
	Boolean executeTpsHandler(String token, Integer time, Integer tps);

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: DEL key [key ...]
	 *  https://redis.io/commands/del
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param keys
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月26日下午3:51:57
	 */
	Long delete(Collection<String> keys);

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: DEL key [key ...]
	 *  https://redis.io/commands/del
	 *  
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param key
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月26日下午3:54:38
	 */
	Boolean delete(String key);

	/**
	 * <p>
	 * Title: set
	 * </p>
	 * <p>
	 * Description: SET key value [expiration EX seconds|PX milliseconds] [NX|XX]
	 * 	https://redis.io/commands/set
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param key
	 * @tags @param obj 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月26日下午3:40:57
	 */
	<T> void set(String key, T obj);

	/**
	 * <p>
	 * Title: set
	 * </p>
	 * <p>
	 * Description: SET key value [expiration EX seconds|PX milliseconds] [NX|XX]
	 * 	https://redis.io/commands/set
	 * 
	 * redis锁 纯原子化操作 由于SET命令加上选项已经可以完全取代SETNX, SETEX,
	 * PSETEX的功能，所以在将来的版本中，redis可能会不推荐使用并且最终抛弃这几个命令。
	 * </p>
	 * <p>
	 * 
	 * redis 127.0.0.1:6379> SET KEY VALUE [EX seconds] [PX milliseconds] [NX|XX] 
	 * Shell EX seconds − 设置指定的到期时间(以秒为单位)。 PX milliseconds -
	 * 设置指定的到期时间(以毫秒为单位)。 NX - 仅在键不存在时设置键。 XX - 只有在键已存在时才设置。 redis
	 * 127.0.0.1:6379> SET mykey "redis" EX 60 NX OK Shell
	 * 以上示例将在键“mykey”不存在时，设置键的值，到期时间为60秒。
	 * 
	 * </p>
	 * 
	 * @tags @param key
	 * @tags @param obj
	 * @tags @param ttl
	 * @tags @param unit 	TimeUnit.SECONDS
	 * @tags @param option	SetOption.ifAbsent() {@code NX} SetOption.fPresent() {@code XX}
	 * @tags @return
	 * 
	 * @author 周顺宇
	 * @date 2017年11月6日上午11:32:54
	 */
	<T> Boolean set(String key, T obj, Long ttl, TimeUnit unit, SetOption option);

	/**
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description: GET key
	 * 	https://redis.io/commands/get
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param key
	 * @tags @param clazz
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月26日下午3:46:43
	 */
	<T> T get(String key, Class<T> clazz);

	/**
	 * <p>
	 * Title: incr
	 * </p>
	 * <p>
	 * Description: 递增
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param key
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月27日上午10:53:54
	 */
	Long incr(String key);

	/**
	 * <p>
	 * Title: decr
	 * </p>
	 * <p>
	 * Description: 递减
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param key
	 * @tags @return 
	 * 
	 * @author 周顺宇
	 * @date 2018年7月27日上午10:57:12
	 */
	Long decr(String key);
	
	

}
