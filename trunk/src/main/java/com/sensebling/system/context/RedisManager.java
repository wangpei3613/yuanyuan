package com.sensebling.system.context;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
/**
 * reids普通存储
 */
@Component
public class RedisManager {
	
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisTemplate.setKeySerializer(new StringRedisSerializer());
	}
	
	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	/**
	 * set数据
	 * @param prefix key前缀
	 * @param code key编码
	 * @param value 值
	 */
	public void setRedisStorage(String prefix, String code, Object value) {
		redisTemplate.opsForValue().set(prefix+":"+code, value, 365*10, TimeUnit.DAYS);
	}
	/**
	 * get数据
	 * @param prefix key前缀
	 * @param code key编码
	 */
	public Object getRedisStorage(String prefix, String code) {
		return redisTemplate.opsForValue().get(prefix+":"+code);
	}
	/**
	 * remove数据
	 * @param prefix key前缀
	 * @param code key编码
	 */
	public void removeRedisStorage(String prefix, String code) {
		redisTemplate.delete(prefix+":"+code);
	}
	/**
	 * 存在key
	 * @param prefix key前缀
	 * @param code key编码
	 */
	public Boolean hasKey(String prefix, String code) {
		return redisTemplate.hasKey(prefix+":"+code);
	}
	/**
	 * set数据
	 * @param prefix key前缀
	 * @param code key编码
	 * @param value 值
	 * @param time 有效期，单位秒
	 */
	public void setRedisStorage(String prefix, String code, Object value, Long time) {
		redisTemplate.opsForValue().set(prefix+":"+code, value, time, TimeUnit.SECONDS);
	}
	
	private static final String LOCK_SUCCESS = "OK";
	private static final Long RELEASE_SUCCESS = 1L;
	/**
	 * 尝试获取分布式锁
	 * @param lockKey 锁
	 * @param requestId 请求标识
	 * @param expireTime 超期时间  单位秒
	 * @return 是否获取成功
	 */
	public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
	    String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				JedisCommands commands = (JedisCommands) connection.getNativeConnection();
				return commands.set(lockKey, requestId, "NX", "EX", expireTime);
			}
		});
	    if (LOCK_SUCCESS.equals(result)) {
	        return true;
	    }
	    return false;
	}
	/**
	 * 释放分布式锁
	 * @param lockKey 锁
	 * @param requestId 请求标识
	 * @return 是否释放成功
	 */
	public boolean releaseDistributedLock(String lockKey, String requestId) {
	    Object result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				Jedis commands = (Jedis) connection.getNativeConnection();
				String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
				return (Long) commands.eval(script, Collections.singletonList(lockKey),Collections.singletonList(requestId));  
			}
		});;
	    if (RELEASE_SUCCESS.equals(result)) {
	        return true;
	    }
	    return false;

	}
}
