package com.sensebling.system.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.util.ConfigFileUtil;
import com.sensebling.system.bean.EnvironmentType;
import com.sensebling.system.context.RedisManager;
import com.sensebling.system.entity.DictionaryContent;
import com.sensebling.system.entity.DictionaryType;
import com.sensebling.system.entity.Params;
import com.sensebling.system.service.DicTypeSvc;
import com.sensebling.system.service.ParamsSvc;

/**
 * 初始化系统基础数据
 * 在容器加载完bean以后执行
 * @author 
 * @date 2011-9-24
 */
public class CacheBeanPostProcessor implements BeanPostProcessor{
	@Autowired
	private RedisManager redisManager;
	@Autowired
	private EnvironmentType environmentType;
	/**
	 * 在spring中定义的bean初始化后调用这个方法
	 */
	public Object postProcessAfterInitialization(Object obj, String arg1)
			throws BeansException {
		if(obj instanceof DicTypeSvc){
			//加载数据字典信息
			List<DictionaryType> dictionaryTypes =((DicTypeSvc)obj).findAll();
			List<DictionaryContent> list = null;
			List<DictionaryContent> ll = null;
			for(DictionaryType dt:dictionaryTypes){
				list = dt.getContents();
				ll = new ArrayList<DictionaryContent>();
				if(list!=null && list.size()>0){
					for(DictionaryContent dc:list){
						if("1".equals(dc.getStatus())){
							ll.add(dc);
						}
					}
				}
				redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.dict, dt.getCode(), ll);
			}
		}else if(obj instanceof ParamsSvc) {
			//加载系统参数
			List<Params> params = ((ParamsSvc)obj).findAll();
			if(params!=null && params.size()>0) {
				for(Params param:params) {
					redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.param, param.getId(), param.getValue());
				}
			}
			Map<String, String> map = ConfigFileUtil.loadProperties("prop/"+environmentType.getType()+"/sysconfig.properties");
			if(map != null) {
				for(String key:map.keySet()) {
					redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.param, key, map.get(key));  
				}
			}
		}
			
		return obj;
	}
	/**
	 * 在spring中定义的bean初始化前调用这个方法
	 */
	public Object postProcessBeforeInitialization(Object obj, String arg1)
			throws BeansException {
		return obj;
	}

}
