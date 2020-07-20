package com.sensebling.index.service.impl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexHistoryConversion;
import com.sensebling.index.entity.IndexHistoryDetail;
import com.sensebling.index.entity.IndexHistoryParameter;
import com.sensebling.index.entity.IndexHistoryVersion;
import com.sensebling.index.entity.IndexValueDetail;
import com.sensebling.index.entity.IndexValueParameter;
import com.sensebling.index.entity.IndexValueVersion;
import com.sensebling.index.entity.IndexVersion;
import com.sensebling.index.service.IndexHistoryConversionSvc;
import com.sensebling.index.service.IndexHistoryVersionSvc;
import com.sensebling.index.service.IndexSvc;
import com.sensebling.index.service.IndexValueDetailSvc;
import com.sensebling.index.service.IndexValueParameterSvc;
import com.sensebling.index.service.IndexValueVersionSvc;
import com.sensebling.index.service.IndexVersionSvc;
import com.sensebling.system.entity.User;
@Service
public class IndexSvcImpl extends BasicsSvcImpl<Serializable> implements IndexSvc{
	
	protected static DebugOut logger=new DebugOut(IndexSvcImpl.class);
	@Resource
	private IndexVersionSvc indexVersionSvc;
	@Resource
	private IndexHistoryVersionSvc indexHistoryVersionSvc;
	@Resource
	private IndexValueVersionSvc indexValueVersionSvc;
	@Resource
	private IndexValueParameterSvc indexValueParameterSvc;
	@Resource
	private IndexValueDetailSvc indexValueDetailSvc;
	@Resource
	private IndexHistoryConversionSvc indexHistoryConversionSvc;
	
	@Transactional
	private void calcIndex(String caseid, String versionid, boolean unique, User user) {
		//long x1 = new Date().getTime();
		//logger.error("开始时间："+DateUtil.getStringDate()); 
		if(StringUtil.notBlank(caseid) && StringUtil.notBlank(versionid)) {
			synchronized (caseid + versionid) {
				IndexVersion version = indexVersionSvc.get(versionid);
				if(version != null) {
					IndexHistoryVersion historyVersion = null;//当前计算使用版本数据
					if("1".equals(version.getChanged())) {//当前版本指标配置有变动  生成一个新版本
						historyVersion = indexHistoryVersionSvc.addCalcData(version);
					}else {
						historyVersion = indexHistoryVersionSvc.getCalcData(version.getId());
					}
					if(historyVersion != null) {
						/********************排除掉指标大类的指标*******************/
						List<IndexHistoryDetail> temp1 = new ArrayList<IndexHistoryDetail>();
						for(IndexHistoryDetail index:historyVersion.getIndexs()) {
							if("2".equals(index.getLevel())) {
								temp1.add(index);
							}
						}
						historyVersion.setIndexs(temp1);  
						/***********************end******************************/
						//记录参数计算得到的值，key为参数id，value为值
						Map<String, String> parameterValues = new HashMap<String, String>();
						//记录指标计算得到的值，key为指标编码，value为指标
						Map<String, IndexHistoryDetail> indexValues = new HashMap<String, IndexHistoryDetail>();
						/******优先计算参数类型为sql和函数的的，拼接为一条sql统一执行*******/
						StringBuffer sql = new StringBuffer("");
						for(IndexHistoryDetail index:historyVersion.getIndexs()) {
							indexValues.put(index.getCode(), index);  
							if(index.getParameters()!=null && index.getParameters().size()>0) {
								for(IndexHistoryParameter parameter:index.getParameters()) {
									if("2".equals(parameter.getType()) && !parameter.getValue().contains("@INDEX_")) {
										if(StringUtil.notBlank(sql)) {  
											sql.append(" union all ");
										}
										sql.append(" select * from (select '"+parameter.getId()+"' id,t.* from SYSIBM.sysdummy1 left join ("+parameter.getValue()+") t on 1=1 fetch first rows only) ");
									}else if("3".equals(parameter.getType())) {
										if(StringUtil.notBlank(sql)) {  
											sql.append(" union all ");
										}
										sql.append(" select * from (select '"+parameter.getId()+"' id,"+parameter.getValue()+"(?) from SYSIBM.sysdummy1 fetch first rows only) ");
									}
								}
							}
						}
						if(StringUtil.notBlank(sql)) {
							logger.msgPrint(sql.toString().replace("?", "'"+caseid+"'"));  
							List<Object[]> temps = indexVersionSvc.queryBySql(sql.toString().replace("?", "'"+caseid+"'"));
							if(temps!=null && temps.size()>0){
								for(Object[] temp:temps){
									parameterValues.put(String.valueOf(temp[0]).trim(), String.valueOf(temp[1]).trim());
								}
							}
						}
						/***********************end******************************/
						
						/******************循环计算指标值**************************/
						for(IndexHistoryDetail index:historyVersion.getIndexs()) {
							calcIndexValue(index, indexValues, parameterValues, caseid);
						}
						/***********************end******************************/
						
						//删除已计算数据
						if(unique) {
							indexValueVersionSvc.delData(caseid, versionid);
						}else {
							indexValueVersionSvc.delData(caseid);
						}
						
						
						/********************计算结果入库**************************/
						IndexValueVersion valueVersion = new IndexValueVersion();
						valueVersion.setCaseid(caseid);
						valueVersion.setCreatetime(DateUtil.getStringDate());
						valueVersion.setCreateuser(user.getId());
						valueVersion.setHisid(historyVersion.getId());
						valueVersion.setVersionid(versionid);
						indexValueVersionSvc.save(valueVersion);
						List<IndexValueDetail> valueDetails = new ArrayList<IndexValueDetail>();
						for(IndexHistoryDetail index:historyVersion.getIndexs()) {
							IndexValueDetail valueDetail = new IndexValueDetail();
							valueDetail.setIndexid(index.getIndexid());
							valueDetail.setValue(index.getCalcValue());
							valueDetail.setValueid(valueVersion.getId());
							valueDetails.add(valueDetail);
						}
						indexValueDetailSvc.save(valueDetails);
						Map<String, IndexValueDetail> map = new HashMap<String, IndexValueDetail>();
						for(IndexValueDetail temp:valueDetails) {
							map.put(temp.getIndexid(), temp);
						}
						List<IndexValueParameter> valueParameters = new ArrayList<IndexValueParameter>();
						for(IndexHistoryDetail index:historyVersion.getIndexs()) {
							if(index.getParameters()!=null && index.getParameters().size()>0) {
								for(IndexHistoryParameter parameter:index.getParameters()) {
									IndexValueParameter valueParameter = new IndexValueParameter();
									valueParameter.setParameterid(parameter.getId());
									valueParameter.setValue(parameter.getCalcValue());
									valueParameter.setValueindexid(map.get(index.getIndexid()).getId());
									valueParameters.add(valueParameter);
								}
							}
						}
						indexValueParameterSvc.save(valueParameters); 
						/***********************end******************************/
					}else {
						logger.msgPrint("版本【"+version.getCode()+"】配置有问题，无法计算");  
					}
				}else {
					logger.msgPrint("版本不存在");  
				}
			}
		}else {
			logger.msgPrint("关联id或版本id为空");  
		}
		//logger.error("结束时间："+DateUtil.getStringDate()); 
		//logger.error("消耗时间："+(new Date().getTime()-x1)); 
	}
	@Override
	public void calcIndex(String caseid, String versionid, boolean unique) {
		  calcIndex(caseid, versionid, unique, HttpReqtRespContext.getUser());   
	}
	
	@Override
	public void calcIndex(String caseid, String versionid,User user, boolean unique) {
		  calcIndex(caseid, versionid, unique, user);   
	}

	@Override
	public void calcIndexSync(final String caseid, final String versionid, final boolean unique) {
		final User user = HttpReqtRespContext.getUser();
		new Thread() {
            public void run() {
            		calcIndex(caseid, versionid, unique, user);    
            }
        }.start();
	}
	
	/**
	 * 单步计算指标值
	 * @param index 指标
	 * @param indexValues 指标map
	 * @param parameterValues 指标参数值map
	 * @param caseid 关联id
	 */
	private void calcIndexValue(IndexHistoryDetail index, Map<String, IndexHistoryDetail> indexValues,
			Map<String, String> parameterValues, String caseid) {
		/******************循环计算指标参数值************************/
		if(index.getParameters()!=null && index.getParameters().size()>0) {
			for(IndexHistoryParameter parameter:index.getParameters()) {
				String value = "";
				if(parameterValues.containsKey(parameter.getId())) {
					value = parameterValues.get(parameter.getId());
				}else {
					if("1".equals(parameter.getType())) {
						value = parameter.getValue().trim();
					}else if("2".equals(parameter.getType())) {
						String sql = parameter.getValue().trim();
						String[] temps = sql.split("@");
						for(int i=0;i<temps.length;i++) {
							if(i%2 == 1){
								String temp = "";
								if(indexValues.containsKey(temps[i])) { 
									if(StringUtil.isBlank(indexValues.get(temps[i]).getCalcValue())) { 
										calcIndexValue(indexValues.get(temps[i]), indexValues, parameterValues, caseid);
									}
									temp = indexValues.get(temps[i]).getCalcValue();
								}else {
									logger.msgPrint("指标【"+index.getCode()+"】的参数【"+parameter.getCode()+"】使用了版本中不存在的指标【"+temps[i]+"】");  
								}
								sql = sql.replace("@"+temps[i]+"@", temp);
							}
						}
						List<Object[]> temp2 = indexVersionSvc.queryBySql(sql.toString().replace("?", "'"+caseid+"'"));
						if(temp2!=null && temp2.size()>0){
							value = String.valueOf(temp2.get(0)).trim();
						}
					}else if("4".equals(parameter.getType())) {
						if(indexValues.containsKey(parameter.getValue().trim())) {  
							if(StringUtil.isBlank(indexValues.get(parameter.getValue().trim()).getCalcValue())) { 
								calcIndexValue(indexValues.get(parameter.getValue().trim()), indexValues, parameterValues, caseid);
							}
							value = indexValues.get(parameter.getValue().trim()).getCalcValue();
						}else {
							logger.msgPrint("指标【"+index.getCode()+"】的参数【"+parameter.getCode()+"】使用了版本中不存在的指标【"+parameter.getValue().trim()+"】");  
						}
					}else if("5".equals(parameter.getType())) {
						value = indexVersionSvc.callProcedure("{call "+parameter.getValue()+"(?,?)}", caseid);
					}
				}
				if(StringUtil.isBlank(value)) {
					value = "";
				}
				parameterValues.put(parameter.getId(), value);
				parameter.setCalcValue(value);  
			}
		}
		/***********************end******************************/
		
		/***********************计算指标值************************/
		if("1".equals(index.getCategory())) {//定性
			String conversionCode = "", calcValue = "0";
			if(StringUtil.notBlank(index.getArgument())) {//若取值参数不为空，使用其计算值，否则使用第一个参数值
				FelEngine engine = new FelEngineImpl();
				FelContext context = engine.getContext();
				if(index.getParameters()!=null && index.getParameters().size()>0) {
					for(IndexHistoryParameter parameter:index.getParameters()) {
						if("1".equals(parameter.getValuetype())) {//数字 
							try {
								context.set(parameter.getCode(), Double.parseDouble(parameter.getCalcValue().trim()));
							} catch (NumberFormatException e) {
								context.set(parameter.getCode(), 0);
								logger.msgPrint("指标【"+index.getCode()+"】的参数【"+parameter.getCode()+"】值为："+parameter.getCalcValue()+"，无法转为数字类型");
							}  
						}else {//字符串
							context.set(parameter.getCode(), parameter.getCalcValue());  
						}
					}
				}
				conversionCode = String.valueOf(engine.eval(index.getArgument()));  
			}else {
				if(index.getParameters()!=null && index.getParameters().size()>0) {
					conversionCode = index.getParameters().get(0).getCalcValue();
				}
			}
			if(index.getConversions()!=null && index.getConversions().size()>0) {
				for(IndexHistoryConversion conversion:index.getConversions()) {
					if(conversion.getCode().equals(conversionCode)) {
						calcValue = new DecimalFormat("###################.###########").format(conversion.getValue());
					}
				}
			}
			index.setCalcValue(calcValue);  
		}else {//定量
			FelEngine engine = new FelEngineImpl();
			FelContext context = engine.getContext();
			if(index.getParameters()!=null && index.getParameters().size()>0) {
				for(IndexHistoryParameter parameter:index.getParameters()) {
					if("1".equals(parameter.getValuetype())) {//数字 
						try {
							context.set(parameter.getCode(), Double.parseDouble(parameter.getCalcValue().trim()));
						} catch (NumberFormatException e) {
							context.set(parameter.getCode(), 0);
							logger.msgPrint("指标【"+index.getCode()+"】的参数【"+parameter.getCode()+"】值为："+parameter.getCalcValue()+"，无法转为数字类型");
						}  
					}else {//字符串
						context.set(parameter.getCode(), parameter.getCalcValue());  
					}
				}
			}
			index.setCalcValue(String.valueOf(engine.eval(index.getFormula())));  
		}
		/***********************end******************************/
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IndexHistoryVersion getCalcData(String caseid, String versionid) {
		IndexValueVersion valueVersion = indexValueVersionSvc.getByParams(caseid, versionid);
		if(valueVersion != null) {
			IndexHistoryVersion historyVersion = indexHistoryVersionSvc.get(valueVersion.getHisid());
			if(historyVersion != null) {
				String sql = "SELECT a.*, b.value calcValue FROM model_index_history_detail a LEFT JOIN model_index_value_detail b ON b.indexid=a.indexid AND b.valueid=? WHERE a.hisid=? ORDER BY a.sort";
				List historyDetailList = baseDao.findBySQLEntity(sql, IndexHistoryDetail.class.getName(), valueVersion.getId(), valueVersion.getHisid());
				sql = "SELECT a.*, c.value calcValue FROM model_index_history_parameter a LEFT JOIN( SELECT * FROM model_index_value_parameter WHERE valueindexid IN ( SELECT d.id FROM model_index_value_detail d WHERE d.valueid=?)) c ON c.parameterid=a.id WHERE a.indexid IN ( SELECT b.id FROM model_index_history_detail b WHERE b.hisid=?) ORDER BY a.indexid, a.sort";
				List historyParameterList = baseDao.findBySQLEntity(sql, IndexHistoryParameter.class.getName(), valueVersion.getId(), valueVersion.getHisid());
				if(historyDetailList!=null && historyDetailList.size()>0 && historyParameterList!=null && historyParameterList.size()>0) { 
					List<IndexHistoryConversion> historyConversionList = indexHistoryConversionSvc.getListByHisid(valueVersion.getHisid());
					return indexHistoryVersionSvc.initData(historyVersion, historyDetailList, historyParameterList, historyConversionList);
				}
			}
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getCalcDataTree(String caseid, String versionid) {
		IndexHistoryVersion historyVersion = getCalcData(caseid, versionid);
		if(historyVersion != null) {
			List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			if(historyVersion.getIndexs()!=null && historyVersion.getIndexs().size()>0) {
				for(IndexHistoryDetail m:historyVersion.getIndexs()) {
					map = JsonUtil.beanToMap(m);
					temp.add(map);
				}
			}
			return EasyTreeUtil.toTreeGrid(temp, "indexid", "pid");
		}
		return null;
	}

	@Override
	public IndexHistoryVersion getCalcData(String caseid) {
		return getCalcData(caseid, getVersionid(caseid));
	}

	@Override
	public List<Map<String, Object>> getCalcDataTree(String caseid) {
		return getCalcDataTree(caseid, getVersionid(caseid));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getVersionid(String caseid) {
		if(StringUtil.notBlank(caseid)) {
			String sql = "select * from model_index_value_version where caseid=? order by createtime desc";
			List list = baseDao.findBySQLEntity(sql, IndexValueVersion.class.getName(), caseid);
			if(list != null && list.size() >= 1) {
				return ((IndexValueVersion)list.get(0)).getVersionid();
			}
		}
		return null;
	}

}
