package com.sensebling.common.util;

import org.apache.log4j.Logger;
/**
 * 日志输出工具类,使用log4j
 * @author 
 */
public class DebugOut 
{
	private static DebugOut log=new DebugOut(DebugOut.class);
	private Logger logger;
	private boolean isDebug=false;
	/**
	 * 构造方法
	 * @param cl 持有此对象的Class
	 */
	public DebugOut(@SuppressWarnings("rawtypes") Class cl)
	{
		logger=Logger.getLogger(cl);
	}
	/**
	 * 消息信息打印
	 * @param message 消息内容
	 */
	public void msgPrint(String message)
	{
		if(isDebug)
			System.out.println(message);
		logger.info(message);
	}
	/**
	 * 错误信息打印
	 * @param message 消息内容
	 * @param t 异常对象
	 */
	public void errPrint(String message,Throwable t)
	{
		if(isDebug)
		{
			System.err.println(message+"\n");
			t.printStackTrace();
		}
		if(t!=null)
			logger.error(message, t);
		else
			logger.error(message);
	}
	/**
	 * 静态方法,日志信息打印
	 * @param message 消息内容
	 * @param t 为空时打印消息日志,不为空时打印错误信息
	 */
	public static void logInfo(String message,Throwable t)
	{
		if(t==null)
			log.msgPrint(message);
		else
			log.errPrint(message, t);
	}
	
	/**
	 * 静态方法,日志信息打印
	 * @param message 消息内容
	 * @param t 为空时打印消息日志,不为空时打印错误信息
	 */
	public static void errInfo(String message,Throwable t)
	{
		log.errPrint(message, t);
	}
}
