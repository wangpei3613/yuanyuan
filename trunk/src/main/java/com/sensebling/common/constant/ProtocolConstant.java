package com.sensebling.common.constant;

import java.math.BigDecimal;
import java.util.Map;

public class ProtocolConstant {
	/**
	 * 使用数据库schema
	 */
	public final static String SCHMEA = "DB2INST1.";
	/**一次性令牌有效期，单位秒*/
	public final static long disposableTokenTime = 2l*60*60;
	/**银行卡号开户行*/
	public final static String BankName = "扬州农村商业银行";
	public final static String BankFullName = "江苏扬州农村商业银行股份有限公司";
	
	/**
	 * 审批流程编码
	 * 
	 *
	 */
	public static interface activitiProcessCode{
		public final static String TEST = "test";
		public final static String credit = "credit";
		public final static String jjnj = "jjnj";
		public final static String zxapply = "zxapply";
		public final static String BORROW = "borrow";
	}
	
	/**
	 * 审批流状态：与数据字典同步
	 * 
	 *
	 */
	public static interface  activitiApplyState{
		/**
		 * 待提交
		 */
		public final static String state1 = "1";//待提交
		/**
		 * 审核中
		 */
		public final static String state2 = "2";//审核中
		/**
		 * 审核通过
		 */
		public final static String state3 = "3";//审核通过
		/**
		 * 复议
		 */
		public final static String state4 = "4";//复议
		/**
		 * 否决
		 */
		public final static String state5 = "5";//否决
		/**
		 * 取消
		 */
		public final static String state6 = "6";//取消
	}
	/**
	 * 审批记录状态
	 */
	public static interface  activitiRecordState{
		/**
		 * 待审
		 */
		public final static String state1 = "1";//待审
		/**
		 * 通过
		 */
		public final static String state2 = "2";//通过
		/**
		 * 复议
		 */
		public final static String state3 = "3";//复议
		/**
		 * 否决
		 */
		public final static String state4 = "4";//否决
		/**
		 * 取消
		 */
		public final static String state5 = "5";//取消
	}
	/**
	 * 审批结果
	 */
	public static interface  activitiOptionResult{
		/**
		 * 同意
		 */
		public final static String state1 = "1";//同意
		/**
		 * 复议
		 */
		public final static String state2 = "2";//复议
		/**
		 * 否决
		 */
		public final static String state3 = "3";//否决
		/**
		 * 取消
		 */
		public final static String state4 = "4";//取消
	}
	
	/**
	 * 流程环节编码
	 * 
	 */
	public static interface Tache{
		/**
		 * 申请 apply = "10"
		 * 主查 zc = "20"
		 * 协查 xc = "30"
		 */
		public final static String apply = "10";
		public final static String zc = "20";
		public final static String xc = "30";
		
		/**一级审批**/
		public final static String audit1 = "40";
		/**二级审批**/
		public final static String audit2 = "50";
		/**三级审批**/
		public final static String audit3 = "60";
		
		/**信息采集**/
		public final static String dataCollect = "10";
		/**非现场检验**/
		public final static String offsiteInspect = "30";
		/**现场检验**/
		public final static String siteInspect = "40";
	}
	
	
	/**
	 * 数据字典
	 *
	 */
	public static interface dictData{
		/**
		 * 家庭角色{"1":"户主","2":"配偶","3":"父母","4":"子女"
		 */
		public static interface familyRole{
			public final static String hz = "1";
			public final static String po = "2";
			public final static String fm = "3";
			public final static String zn = "4";
		}
		/**
		 * 婚姻状况{"10":"未婚","20":"已婚","30":"离异","40":"丧偶"
		 */
		public static interface marriage{
			public final static String wh = "10";
			public final static String yh = "20";
			public final static String ly = "30";
			public final static String so = "40";
		}
		
		/**当地人均收入**/
		public static final BigDecimal perIncome = new BigDecimal("2.2");
	}
	/**风险模型编码**/
	public static interface RiskCode{
		/**授信申请风险准入模型**/
		public final static String creditApply = "creditApply";
		/**授信申请提交准入模型**/
		public final static String creditSubmit = "creditSubmit";
		/**授信调查提交准入模型**/
		public final static String investSubmit = "investSubmit";
		/**季检年检信息采集提交准入模型**/
		public final static String jjnjDataCollect = "jjnjDataCollect";
		/**季检年检非现场检验提交准入模型**/
		public final static String jjnjOffsiteInspect = "jjnjOffsiteInspect";
		/**季检年检现场检验提交准入模型**/
		public final static String jjnjSiteInspect = "jjnjSiteInspect";
		/**季检年检判断是否需要现场检验**/
		public final static String jjnjInspectResult = "jjnjInspectResult";
		/**征信查询申请提交准入模型**/
		public final static String zxapplySubmit = "zxapplySubmit";
		/**供应链金融授信申请执行前校验**/
		public final static String scfApplyRunBefore = "scfApplyRunBefore";
		/**供应链金融同步信贷授信前校验**/
		public final static String scfSyncCreditApplyBefore = "scfSyncCreditApplyBefore";
		/**供应链金融用信申请执行前校验**/
		public final static String scfLoanRunBefore = "scfLoanRunBefore";
		/**供应链金融同步信贷用信前校验**/
		public final static String scfSyncCreditLoanBefore = "scfSyncCreditLoanBefore";
	}
	/**分配模型编码**/
	public static interface AlloCode{
		/**调查人员分配模型**/
		public final static String investMan = "investMan";
		/**季检年检采集人员分配模型**/
		public final static String jjnjCollectManAllo = "jjnjCollectManAllo";
	}
	/**
	 * 角色编码
	 */
	public static interface RoleCode{
		/**审批部门负责人**/
		public final static String auditHeader = "auditHeader";
		/**支行行长**/
		public final static String branchPresident = "branchPresident";
		/**团队主管**/
		public final static String teamLeader = "teamLeader";
	}
	
	/**
	 * 过期时间15分钟
	 */
	public static final long EXPIRE_TIME= 15* 60 * 1000;

	/**
	 * token 私钥
	 */
	public static final String TOKEN_SECRET = "4028b8e669ffbaf60169ffbaf6330000";
	
	//这个对象以后需要放到缓存中 
	public static Map<String,Object> tokenMap ;
	
	/**
	 * 这个对象以后需要放到缓存中  这个是请求 token
	 */
	public static Map<String,Object> requestTokenMap ;
	
	/**
	 * 登录类别
	 * @author llmke
	 *
	 */
	public interface LoginType {
		/**pc端登录*/
		public static String pc = "1";
		/**app登录*/
		public static String app = "2";
		/**二维码登陆*/
		public static String qrcode = "3";
	}
	/**redis缓存前缀*/
	public interface RedisPrefix {
		/**token前缀*/
		public static String token = "token";
		/**用户最近登陆时间前缀*/
		public static String recentlytime = "recentlytime";
		/**用户有效期前缀*/
		public static String validitytime = "validitytime";
		/**系统参数前缀*/
		public static String param = "param";
		/**字典前缀*/
		public static String dict = "dict";
		/**一次性令牌前缀*/
		public static String disposableToken = "disposabletoken";
		/**一次性令牌锁前缀*/
		public static String disposableTokenLock = "disposabletokenlock";
		/**qrcode前缀*/
		public static String qrcode = "qrcode";
		/**自动化流程任务前缀*/
		public static String amp = "amp";
	}
	
	public final static String[] ampTask = new String[] {"处理中","成功","失败","异常","取消"};
	
	/**供应链金融反馈结果*/
	public interface ScfResult {
		/**通过*/
		public static String success = "00";
		/**拒绝*/
		public static String refuse = "01";
	}
	
	/**自动任务环节编码*/
	public interface AmpTachecode {
		/**授信人工审批*/
		public static String maudit = "90";
	}
	
	/**供应链金融归属平台*/
	public interface PlatformCode {
		/**文沥*/
		public static String welinkdata = "HRWLSC";
	}
}
