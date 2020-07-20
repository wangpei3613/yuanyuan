package com.sensebling.activiti.entity;
/**
 * 流程bean  用于审批
 *
 */
public class ActAudit {
	private String result;//审批结果  1同意 2复议 3否决
	private String options;//审批意见
	private String applytacheid;//申请环节id  审批主键
	private String aduittype = "1";//1审批 2提交
	private String sign_data;//签名数据
	private String nextTache;//下环节id   用户选择值
	private String prevTache;//复议环节id  用户选择值
	private String nextUserid;//下环节用户id  系统计算值（实际使用）
	private String nextTacheid;//下环节id   系统计算值（实际使用）
	private String handlerResult;//审批完成返回值  0.会签审批中 1.审批前进 2.审批后退 3.审批通过 4.审批复议 5.审批否决
	private ActConfigTache tache;//当前环节
	private ActApplyInfo apply;//申请信息
	private ActApplyRecord applyRecord;//申请记录
	private ActApplyTache applyTache;//申请代办
	private ActApplyTache nextApplyTache;//下个申请代办
	private String calcResult;//系统审批结果  若不为空且为有效值时将覆盖用户审批结果
	
	/****移交字段****/
	private String transferUserid;//移交用户id
	private String transferReceiveid;//接收用户id
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getApplytacheid() {
		return applytacheid;
	}
	public void setApplytacheid(String applytacheid) {
		this.applytacheid = applytacheid;
	}
	public String getAduittype() {
		return aduittype;
	}
	public ActApplyTache getNextApplyTache() {
		return nextApplyTache;
	}
	public void setNextApplyTache(ActApplyTache nextApplyTache) {
		this.nextApplyTache = nextApplyTache;
	}
	public void setAduittype(String aduittype) {
		this.aduittype = aduittype;
	}
	public String getSign_data() {
		return sign_data;
	}
	public void setSign_data(String sign_data) {
		this.sign_data = sign_data;
	}
	public String getNextTache() {
		return nextTache;
	}
	public void setNextTache(String nextTache) {
		this.nextTache = nextTache;
	}
	public String getPrevTache() {
		return prevTache;
	}
	public void setPrevTache(String prevTache) {
		this.prevTache = prevTache;
	}
	public String getNextUserid() {
		return nextUserid;
	}
	public void setNextUserid(String nextUserid) {
		this.nextUserid = nextUserid;
	}
	public String getNextTacheid() {
		return nextTacheid;
	}
	public void setNextTacheid(String nextTacheid) {
		this.nextTacheid = nextTacheid;
	}
	public String getHandlerResult() {
		return handlerResult;
	}
	public void setHandlerResult(String handlerResult) {
		this.handlerResult = handlerResult;
	}
	public ActConfigTache getTache() {
		return tache;
	}
	public void setTache(ActConfigTache tache) {
		this.tache = tache;
	}
	public String getCalcResult() {
		return calcResult;
	}
	public void setCalcResult(String calcResult) {
		this.calcResult = calcResult;
	}
	public ActApplyInfo getApply() {
		return apply;
	}
	public void setApply(ActApplyInfo apply) {
		this.apply = apply;
	}
	public ActApplyRecord getApplyRecord() {
		return applyRecord;
	}
	public void setApplyRecord(ActApplyRecord applyRecord) {
		this.applyRecord = applyRecord;
	}
	public ActApplyTache getApplyTache() {
		return applyTache;
	}
	public void setApplyTache(ActApplyTache applyTache) {
		this.applyTache = applyTache;
	}
	public String getTransferUserid() {
		return transferUserid;
	}
	public void setTransferUserid(String transferUserid) {
		this.transferUserid = transferUserid;
	}
	public String getTransferReceiveid() {
		return transferReceiveid;
	}
	public void setTransferReceiveid(String transferReceiveid) {
		this.transferReceiveid = transferReceiveid;
	}
}
