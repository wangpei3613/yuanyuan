package com.sensebling.system.finals;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.util.EncrypDES;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.context.RedisManager;
import com.sensebling.system.entity.DictionaryContent;



/**
 * 静态参数设置类
 * 
 * @author 
 * @date 2013-9-17
 */
@Component
public final class BasicsFinal implements ApplicationContextAware{
	
	private static RedisManager redisManager;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BasicsFinal.redisManager = applicationContext.getBean(RedisManager.class);
	}
	//加解密工具
	public static EncrypDES EDES=new EncrypDES("sensebling");
	// 请求处理成功
	public static final String SUCCESS = "success";
	// 请求处理失败
	public static final String FAILED = "failed";
	// 请求处理异常
	public static final String EXCEPTION = "error";
	// 登出处理
	public static final String LOGINOUT = "loginOut";
	// 登陆处理
	public static final String LOGINING = "logining";
	// session超时
	public static final String SESSIONOUTTIME = "sessionOutTime";
	
	/**=====权限控制CRUD=====**/
	public static final int C = 1;// 添加    0001
	public static final int R = 2;// 查询  0010 
	public static final int U = 4;// 修改    0100
	public static final int D = 8;// 删除  1000
	/**数据库查询需要的连接符*/
	//排序标示
	public static final String ORDER_BY="orderstr";
	//倒序
	public static final String DESC="desc";
	//默认排序
	public static final String ASC="asc";
	//等于
	public static final String EQ="=";
	//不等于
	public static final String NOT_EQ="!=";
	//大于
	public static final String GT=">";
	//大于等于
	public static final String GE=">=";
	//小于
	public static final String LT="<";
	//小于等于
	public static final String LE="<=";
	//等于空值
	public static final String IS_NULL="is null";
	//非空
	public static final String IS_NOT_NULL="is not null";
	//like
	public static final String LIKE="like";
	//like  开始于
	public static final String BEGIN_LIKE="begin like";
	//like 结束于
	public static final String END_LIKE="end like";
	//in
	public static final String IN="in";
	//not in
	public static final String NOT_IN="not in";
	//between x and y
	public static final String BETWEEN="between";
	//not between x and y
	public static final String NOT_BETWEEN="not between";
	/**
	 * 数据字典数据保存对象
	 * 通过BaseFinal.dictionaryMap.get("字典类型编码")获取此类型下的字典值集合
	 */
	//public static HashMap<String, List<DictionaryContent>> dictionaryMap = new HashMap<String, List<DictionaryContent>>();
	/**
	 * 系统参数配置,保存的值为sysconfig.properties文件中的内容和表sen_params中的内容
	 * 通过BaseFinal.systemConfig.get("xxx.xx.x")的方式获取系统配置参数值
	 */
	//public static HashMap<String, String> systemConfig=new HashMap<String, String>();
	/**
	 * 根据参数编码获取参数值
	 * @param paramcode
	 * @return
	 */
	public static String getParamVal(String paramcode) {
		return StringUtil.ValueOf(redisManager.getRedisStorage(ProtocolConstant.RedisPrefix.param, paramcode)); 
	}
	/**
	 * 根据字典编码获取字典列表
	 * @param dictcode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<DictionaryContent> getDictContent(String dictcode){
		return (List<DictionaryContent>)redisManager.getRedisStorage(ProtocolConstant.RedisPrefix.dict, dictcode);
	}
}
