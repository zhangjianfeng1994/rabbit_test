package com.sltas.flow.redis.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.sltas.flow.redis.service.RedisScriptLua;
import com.sltas.flow.redis.service.RedisService;

/**
 * <p>
 * Title: RedisService
 * </p>
 * <p>
 * Description: redis基础操作类
 * </p>
 * <p>
 * 
 * @Qualifier的参数名称必须为我们之前定义@Service注解的名称之一！
 * 											</p>
 * @author 周顺宇
 * @date 2017年11月2日下午4:41:27
 * 
 */
@Component("redisService")
public class RedisServiceImpl implements RedisService{

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private Gson gson = new Gson();
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<?, ?> redisTemplate;

	@Autowired
	@Qualifier("stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;
	
	
	/* ----------- lua methods --------- */
	
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
	@Override
	public Boolean executeTpsHandler(String key,Integer time,Integer tps){
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(time, "Time must not be null!");
		Assert.notNull(tps, "Tps must not be null!");
		logger.info("[redis] request -> key : {}, time : {}",key,time);
		@SuppressWarnings("unchecked")
		RedisScript<Boolean> rs = RedisScript.of(RedisScriptLua.REDIS_LUA_TPS, Boolean.class);
		Boolean flag = stringRedisTemplate.execute(rs, Collections.singletonList(key), String.valueOf(time) , String.valueOf(tps) );
		logger.info("[redis] response -> key : {}, result : {}",key,flag);
		return flag;
	}
	
	
	/* ----------- basics KEY methods --------- */
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
	@Override
	public Long delete(Collection<String> keys){
		Assert.notEmpty(keys, "keys must not be null!");
		logger.info("[redis] request -> keys : {} ",keys);
		Long count = stringRedisTemplate.delete(keys);
		logger.info("[redis] response -> keys : {}, result : {}  ",keys,count);
		return count;
	}
	
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
	@Override
	public Boolean delete(String key){
		Assert.notNull(key, "key must not be null!");
		logger.info("[redis] request -> key : {} ",key);
		Boolean flag = stringRedisTemplate.delete(key);
		logger.info("[redis] response -> key : {}, result : {} ",key,flag);
		return flag;
	}
	
	
	/* ----------- basics STRING methods --------- */
	
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
	@Override
	public  <T> void set(String key, T obj) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(obj, "Value must not be null!");
		logger.info("[redis] request -> key : {}, obj : {}",key,obj);
		stringRedisTemplate.opsForValue().set(key, toJson(obj));
	}


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
	@Override
	public <T> Boolean set(String key, T obj, Long ttl, TimeUnit unit, SetOption option) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(obj, "Value must not be null!");
		Assert.notNull(ttl,"TTL must not be null!");
		Assert.notNull(unit, "TimeUnit must not be null!");
		Assert.notNull(option, "Option must not be null!");
		
		return stringRedisTemplate.execute((RedisCallback<Boolean>)connection -> {
			RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
			byte[] _key = serializer.serialize(key);
			byte[] _value = serializer.serialize(toJson(obj));
			logger.info("[redis] request -> key : {}, obj : {}, ttl : {}",key,obj,ttl);
			Boolean flag = connection.set(_key, _value, Expiration.from(ttl, unit), option);
			logger.info("[redis] response -> key : {}, result : {}",key,flag);
			return flag;
		});
	}
	
	
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
	@Override
	public  <T> T get(String key, Class<T> clazz) {
		
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(clazz, "Clazz must not be null!");
		
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null){
        	return null;
        }
        logger.info("[redis] request -> key : {}, clazz : {}",key,clazz);
        return parseJson(value, clazz);
    }
	
	
	
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
	@Override
	public Long incr(String key){
		return stringRedisTemplate.execute((RedisCallback<Long>)connection -> {
			RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
			byte[] _key = serializer.serialize(key);
			logger.info("[redis] request -> key : {}",key);
			Long count = connection.incr(_key);
			logger.info("[redis] response -> key : {}, result : {}",key,count);
			return count;
		});
	}
	
	
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
	@Override
	public Long decr(String key){
		return stringRedisTemplate.execute((RedisCallback<Long>)connection -> {
			RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
			byte[] _key = serializer.serialize(key);
			logger.info("[redis] request -> key : {}",key);
			Long count = connection.decr(_key);
			logger.info("[redis] response -> key : {}, result : {}",key,count);
			return count;
		});
	}
	
    /* ----------- tool methods --------- */
	
    private String toJson(Object obj) {
    	return gson.toJson(obj);
    }
 
    private <T> T parseJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

	
    
}
