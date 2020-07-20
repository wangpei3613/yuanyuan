package com.sensebling.system.context;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;


@Component
public class RedisTokenManager implements TokenManager{
	
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.redisTemplate.setKeySerializer(new StringRedisSerializer());
	}
	@Override
	public String getToken(User user) {
		String token = UUID.randomUUID().toString().replace("-", "");
		redisTemplate.opsForValue().set(ProtocolConstant.RedisPrefix.token+":"+token, user, Long.parseLong(BasicsFinal.getParamVal("sys_token_times")), TimeUnit.SECONDS);
		redisTemplate.opsForValue().set(ProtocolConstant.RedisPrefix.recentlytime+":"+user.getId()+"@"+user.getLogintype(), user.getLogintime(), 365*10, TimeUnit.DAYS);//记录每个登录类别最近一次用户登录时间
        return token;
	}

	@Override
	public void refreshUserToken(String token) {
		if(StringUtil.notBlank(token) && redisTemplate.hasKey(ProtocolConstant.RedisPrefix.token+":"+token)) {
			redisTemplate.expire(ProtocolConstant.RedisPrefix.token+":"+token, Long.parseLong(BasicsFinal.getParamVal("sys_token_times")), TimeUnit.SECONDS); 
		}
	}

	@Override
	public void loginOff(String token) {
		if(StringUtil.notBlank(token)) {
			redisTemplate.delete(ProtocolConstant.RedisPrefix.token+":"+token);  
		}
	}

	@Override
	public User getUserInfoByToken(String token) {
		if(StringUtil.notBlank(token)) {
			Object obj = redisTemplate.opsForValue().get(ProtocolConstant.RedisPrefix.token+":"+token);
			if(obj != null) {
				User info = (User) obj;
				Object o1 = redisTemplate.opsForValue().get(ProtocolConstant.RedisPrefix.validitytime+":"+info.getId());//用户token有效期
				if(o1!=null && Long.parseLong(o1+"") > info.getLogintime()) {//小于该有效期的token失效
					return null;
				}
				if("true".equals(BasicsFinal.getParamVal("system.login.single"))) {//开启单点登录
					Object o2 = redisTemplate.opsForValue().get(ProtocolConstant.RedisPrefix.recentlytime+":"+info.getId()+"@"+info.getLogintype());//用户某登录类别token有效期
					if(o2!=null && Long.parseLong(o2+"") > info.getLogintime()) {//小于该有效期的token失效
						return null;
					}
				}
				return info;
			}
		}
		return null;
	}
	@Override
	public void setUserValidity(String userid, Long time) {
		redisTemplate.opsForValue().set(ProtocolConstant.RedisPrefix.validitytime+":"+userid, time, 30, TimeUnit.DAYS);
	}

}
