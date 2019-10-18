package com.sltas.flow.redis.consts;

public class RedisScriptLua {

	/**
	 * REDIS_LUA_TPS
	 * @author 周顺宇
	 * @date 2018年7月27日上午9:48:25
	 * 
	 * 1. key [1] {KEYS[1] 检索的key}
	 * 2. args [2] {ARGV[1] 间隔时间 , ARGV[2] tps}
	 * 
	 */
	public final static String REDIS_LUA_TPS = "local current = redis.call('incr',KEYS[1]) if tonumber(current) == 1 then redis.call('expire',KEYS[1],tonumber(ARGV[1])) end if tonumber(current) > tonumber(ARGV[2]) then return -1 else return 1 end";
	
}
