package com.sensebling.core.common.service.impl;

import javax.annotation.Resource;

import org.apache.axis.client.Call;
import org.springframework.stereotype.Service;

import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.DebugOut;
import com.sensebling.core.common.entity.CoreMessage;
import com.sensebling.core.common.service.CoreMessageSvc;
import com.sensebling.ope.sync.util.HtmlUtil;
import com.sensebling.system.finals.BasicsFinal;

@Service
public class CoreMessageUtil {
	@Resource
	private CoreMessageSvc coreMessageSvc;
	protected static DebugOut logger=new DebugOut(CoreMessageUtil.class);
	/**
	 * 发送短信
	 * 
	 * @param mbNo 短信编号
	 * @param tel 接收手机号
	 * @param str 短信替换参数 多个参数用@|@分隔
	 * @throws Exception
	 */
	public String sendMesWeb2Service(String mbNo, String tel, String str){
		return sendMesWeb2Service(mbNo, tel, str, null);
	}
	/**
	 * 发送短信
	 * 
	 * @param mbNo 短信编号
	 * @param tel 接收手机号
	 * @param str 短信替换参数 多个参数用@|@分隔
	 * @param applyid 关联id
	 * @throws Exception
	 */
	public String sendMesWeb2Service(String mbNo, String tel, String str, String applyid) {
		String result = "-99";
		if("true".equalsIgnoreCase(BasicsFinal.getParamVal(mbNo))) {//发送该模板短信
			result = "-1";
			try {
				if("true".equalsIgnoreCase(BasicsFinal.getParamVal("environment.isformal"))) {//正式环境
					String endpoint = "http://32.239.32.67:8080/WebServiceServer/services/MessageService";
					org.apache.axis.client.Service service = new org.apache.axis.client.Service(); // new 一个服务
					Call call = (Call) service.createCall(); // 创建一个call对象
					call.setTargetEndpointAddress(endpoint);
					call.setOperationName("sendMbMessage"); // 设置要调用的接口方法
					call.addParameter("mbNo", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 设置参数名
					call.addParameter("tel", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.addParameter("params", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 返回参数类型
					result = (String)call.invoke(new Object[] { mbNo, tel, str });
				}
			} catch (Exception e) {
				logger.errPrint("短信发送失败,【mbNo="+mbNo+"，tel="+tel+"，str="+str+"】", e);  
				result = HtmlUtil.getExceptionInfo(e);
			} finally {
				CoreMessage message = new CoreMessage();
				message.setAddtime(DateUtil.getStringDate());  
				message.setCode(mbNo);
				message.setParams(str);
				message.setResult(result);
				message.setTel(tel);
				message.setApplyid(applyid);  
				coreMessageSvc.save(message);
			}
		}
		return result;
	}
	/**
	 * 发送短信(异步)
	 * 
	 * @param mbNo 短信编号
	 * @param tel 接收手机号
	 * @param str 短信替换参数 多个参数用@|@分隔
	 * @param applyid 关联id
	 * @throws Exception
	 */
	public void sendMesWeb2ServiceAsync(final String mbNo, final String tel, final String str, final String applyid) {
		new Thread() {
            public void run() {
            		sendMesWeb2Service(mbNo, tel, str, applyid);  
            }
        }.start();
	}
	/**
	 * 发送短信(异步)
	 * 
	 * @param mbNo 短信编号
	 * @param tel 接收手机号
	 * @param str 短信替换参数 多个参数用@|@分隔
	 * @throws Exception
	 */
	public void sendMesWeb2ServiceAsync(final String mbNo, final String tel, final String str) {
		new Thread() {
            public void run() {
            		sendMesWeb2Service(mbNo, tel, str, null);  
            }
        }.start();
	}
}
