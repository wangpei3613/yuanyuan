package com.sensebling.common.exception;

/**
 * 系统自定义异常类
 * @author 
 * @date 2014-9-17
 */
public class UserException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误类型标识
	 */
	private ExcCode excCode = null;

	public enum ExcCode {
		AppError, InvalidRights, IllegalData, DataProcessing, Unlogined
	}

	public static final String[] excMessage = {
			"内部异常",
			"您没有执行本操作的权限",
			"提供的数据为空或不合法",
			"数据处理异常",
			"您可能还没有登录本系统，或者已经超时，您必须先登录本系统后才能使用该功能<p><a href='../' target='_parent'>重新登录</a></p>" };

	public UserException() {
		super(getExcMessage(ExcCode.AppError));
		excCode = ExcCode.AppError;
	}
	
	public UserException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param arg0
	 *            错误类型标识
	 */
	public UserException(ExcCode excCode) {
		super(getExcMessage(excCode));
		this.excCode = excCode;
	}

	/**
	 * 根据错误类型标识获取错误信息
	 * 
	 * @param emFlag
	 *            错误类型标识
	 * 
	 * @return 错误信息
	 */
	public static String getExcMessage(ExcCode excCode) {
		return excMessage[excCode.ordinal()];
	}

	public String getExcMessage() {
		return excMessage[excCode.ordinal()];
	}

	/**
	 * 构造函数
	 * 
	 * @param arg0
	 *            错误类型标识
	 *            
	 * @param arg1
	 *            被包含的异常对象
	 */
	public UserException(ExcCode excCode, Throwable throwable) {
		super(getExcMessage(excCode), throwable);
		setStackTrace(throwable.getStackTrace());
		this.excCode = excCode;
	}

	/**
	 * 构造函数
	 * 
	 * @param arg0
	 *            被包含的异常对象
	 */
	@SuppressWarnings("static-access")
	public UserException(Throwable throwable) {
		super(
				getExcMessage(throwable.getClass() == UserException.class ? ((UserException) throwable).excCode
						: ExcCode.AppError), throwable);
		setStackTrace(throwable.getStackTrace());
		if (throwable.getClass() == UserException.class)
			excCode = ((UserException) throwable).excCode;
		else
			excCode = excCode.AppError;
	}

}
