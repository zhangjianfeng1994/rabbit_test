package com.sltas.example.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import com.google.gson.Gson;

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
public class RedisServiceImpl {

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
	private Boolean executeTpsHandler(String token,Integer time,Integer tps){
		Assert.notNull(token, "Key must not be null!");
		Assert.notNull(time, "Time must not be null!");
		Assert.notNull(tps, "Tps must not be null!");
		@SuppressWarnings("unchecked")
		RedisScript<Boolean> rs = RedisScript.of(RedisScriptLua.REDIS_LUA_TPS, Boolean.class);
		return stringRedisTemplate.execute(rs, Collections.singletonList(token), String.valueOf(time) , String.valueOf(tps) );
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
	public Long delete(Collection<String> keys){
		Assert.notEmpty(keys, "keys must not be null!");
		return stringRedisTemplate.delete(keys);
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
	public Boolean delete(String key){
		Assert.notNull(key, "key must not be null!");
		return stringRedisTemplate.delete(key);
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
	public  <T> void set(String key, T obj) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(obj, "Value must not be null!");
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
	public <T> Boolean set(String key, T obj, Long ttl, TimeUnit unit, SetOption option) {
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(obj, "Value must not be null!");
		Assert.notNull(ttl,"TTL must not be null!");
		Assert.notNull(unit, "TimeUnit must not be null!");
		Assert.notNull(option, "Option must not be null!");
		
		return stringRedisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
				byte[] _key = serializer.serialize(key);
				byte[] _value = serializer.serialize(toJson(obj));
				return connection.set(_key, _value, Expiration.from(ttl, unit), option);
			}
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
	public  <T> T get(String key, Class<T> clazz) {
		
		Assert.notNull(key, "Key must not be null!");
		Assert.notNull(clazz, "Clazz must not be null!");
		
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null){
        	return null;
        }
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
	public Long incr(String key){
		return stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
				byte[] _key = serializer.serialize(key);
				return connection.incr(_key);
			}
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
	public Long decr(String key){
		return stringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = stringRedisTemplate.getStringSerializer();
				byte[] _key = serializer.serialize(key);
				return connection.decr(_key);
			}
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
