package com.sensebling.system.listener;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.sensebling.system.service.LoginLogSvc;

/**
 * redis 过期监听事件
 */
@Component
public class KeyExpiredListener extends KeyExpirationEventMessageListener{
	@Resource
	private LoginLogSvc loginLogSvc;
	
	@Autowired
	public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
        //String key = new String(message.getBody(),StandardCharsets.UTF_8);
		super.onMessage(message, pattern);
	}

}
