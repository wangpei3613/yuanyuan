package com.sensebling.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexHistoryConversion;
import com.sensebling.index.entity.IndexHistoryDetail;
import com.sensebling.index.entity.IndexHistoryParameter;
import com.sensebling.index.entity.IndexHistoryVersion;
import com.sensebling.index.entity.IndexLibrary;
import com.sensebling.index.entity.IndexVersion;
import com.sensebling.index.entity.IndexVersionConversion;
import com.sensebling.index.entity.IndexVersionParameter;
import com.sensebling.index.service.IndexHistoryConversionSvc;
import com.sensebling.index.service.IndexHistoryDetailSvc;
import com.sensebling.index.service.IndexHistoryParameterSvc;
import com.sensebling.index.service.IndexHistoryVersionSvc;
import com.sensebling.index.service.IndexLibrarySvc;
import com.sensebling.index.service.IndexVersionConversionSvc;
import com.sensebling.index.service.IndexVersionParameterSvc;
import com.sensebling.index.service.IndexVersionSvc;
@Service
public class IndexHistoryVersionSvcImpl extends BasicsSvcImpl<IndexHistoryVersion> implements IndexHistoryVersionSvc{
	@Resource
	private IndexHistoryDetailSvc indexHistoryDetailSvc;
	@Resource
	private IndexHistoryParameterSvc indexHistoryParameterSvc;
	@Resource
	private IndexHistoryConversionSvc indexHistoryConversionSvc;
	@Resource
	private IndexLibrarySvc indexLibrarySvc;
	@Resource
	private IndexVersionParameterSvc indexVersionParameterSvc;
	@Resource
	private IndexVersionConversionSvc indexVersionConversionSvc;
	@Resource
	private IndexVersionSvc indexVersionSvc;

	@Override
	public IndexHistoryVersion getCalcData(String versionid) {
		String sql = "select * from model_index_history_version where versionid=? order by createdate desc fetch first 1 rows only";
		List<IndexHistoryVersion> historyVersionList = baseDao.findBySQLEntity(sql, new Object[] {versionid}, IndexHistoryVersion.class.getName());
		if(historyVersionList!=null && historyVersionList.size()>0) {
			IndexHistoryVersion historyVersion = historyVersionList.get(0);
			List<IndexHistoryDetail> historyDetailList = indexHistoryDetailSvc.getListByHisid(historyVersion.getId());
			List<IndexHistoryParameter> historyParameterList = indexHistoryParameterSvc.getListByHisid(historyVersion.getId());
			List<IndexHistoryConversion> historyConversionList = indexHistoryConversionSvc.getListByHisid(historyVersion.getId());
			if(historyDetailList!=null && historyDetailList.size()>0
					&& historyParameterList!=null && historyParameterList.size()>0) {
				return initData(historyVersion, historyDetailList, historyParameterList, historyConversionList);
			}
		}
		return null;
	}


	@Override
	public IndexHistoryVersion addCalcData(IndexVersion version) {
		if(version != null) {
			List<IndexLibrary> indexList = indexLibrarySvc.getVersionIndexEnable(version.getId());
			List<IndexVersionParameter> parameters = indexVersionParameterSvc.getListByVersionid(version.getId());
			List<IndexVersionConversion> conversions = indexVersionConversionSvc.getListByVersionid(version.getId());
			if(indexList!=null && indexList.size()>0 && parameters!=null && parameters.size()>0) {
				IndexHistoryVersion historyVersion = new IndexHistoryVersion();
				historyVersion.setCode(version.getCode());
				historyVersion.setCreatedate(DateUtil.getStringDate());
				historyVersion.setName(version.getName());
				historyVersion.setRemark(version.getRemark());
				historyVersion.setVersionid(version.getId());
				save(historyVersion);
				List<IndexHistoryDetail> historyIndexList = new ArrayList<IndexHistoryDetail>();
				for(IndexLibrary index:indexList) {
					IndexHistoryDetail historyIndex = new IndexHistoryDetail();
					historyIndex.setArgument(index.getArgument());
					historyIndex.setCategory(index.getCategory());
					historyIndex.setCode(index.getCode());
					historyIndex.setContent(index.getContent());
					historyIndex.setFormula(index.getFormula());
					historyIndex.setHisid(historyVersion.getId());
					historyIndex.setLevel(index.getLevel());
					historyIndex.setMaxscore(index.getMaxscore());
					historyIndex.setName(index.getName());
					historyIndex.setRemark(index.getRemark());
					historyIndex.setSort(index.getSort());
					historyIndex.setPid(index.getPid());
					historyIndex.setIndexid(index.getId());
					historyIndexList.add(historyIndex);
				}
				indexHistoryDetailSvc.save(historyIndexList); 
				Map<String, IndexHistoryDetail> map = new HashMap<String, IndexHistoryDetail>();
				for(IndexHistoryDetail historyIndex:historyIndexList) {
					map.put(historyIndex.getIndexid(), historyIndex);
				}
				List<IndexHistoryParameter> historyParameters = new ArrayList<IndexHistoryParameter>();
				for(IndexVersionParameter parameter:parameters) {
					if(map.containsKey(parameter.getIndexid())) {
						IndexHistoryDetail temp = map.get(parameter.getIndexid());
						if("2".equals(temp.getCategory()) && !StringUtil.ValueOf(temp.getArgument()).contains(parameter.getCode())
								&& !StringUtil.ValueOf(temp.getFormula()).contains(parameter.getCode())) {
							continue;
						}
						IndexHistoryParameter historyParameter = new IndexHistoryParameter();
						historyParameter.setCode(parameter.getCode());
						historyParameter.setIndexid(temp.getId());
						historyParameter.setName(parameter.getName());
						historyParameter.setRemark(parameter.getRemark());
						historyParameter.setSort(parameter.getSort());
						historyParameter.setType(parameter.getType());
						historyParameter.setValuetype(parameter.getValuetype());
						historyParameter.setValue(parameter.getValue()); 
						historyParameters.add(historyParameter);
					}
				}
				indexHistoryParameterSvc.save(historyParameters); 
				List<IndexHistoryConversion> historyConversions = new ArrayList<IndexHistoryConversion>();
				if(conversions!=null && conversions.size()>0) {
					for(IndexVersionConversion conversion:conversions) {
						if(map.containsKey(conversion.getIndexid())) {
							IndexHistoryConversion historyConversion = new IndexHistoryConversion();
							historyConversion.setCode(conversion.getCode());
							historyConversion.setIndexid(map.get(conversion.getIndexid()).getId());
							historyConversion.setName(conversion.getName());
							historyConversion.setRemark(conversion.getRemark());
							historyConversion.setSort(conversion.getSort());
							historyConversion.setValue(conversion.getValue());
							historyConversions.add(historyConversion);
						}
					}
					indexHistoryConversionSvc.save(historyConversions);
				}
				indexVersionSvc.removeChanged(version.getId());  
				return initData(historyVersion, historyIndexList, historyParameters, historyConversions);
			}
		}
		return null;
	}
	
	
	@Override
	public IndexHistoryVersion initData(IndexHistoryVersion historyVersion, List<IndexHistoryDetail> historyDetailList,
			List<IndexHistoryParameter> historyParameterList, List<IndexHistoryConversion> historyConversionList) {
		Map<String, IndexHistoryDetail> map = new HashMap<String, IndexHistoryDetail>();
		for(IndexHistoryDetail historyDetail:historyDetailList) {
			map.put(historyDetail.getId(), historyDetail);
		}
		for(IndexHistoryParameter historyParameter:historyParameterList) {
			if(map.containsKey(historyParameter.getIndexid())) {
				if(map.get(historyParameter.getIndexid()).getParameters() == null) {
					map.get(historyParameter.getIndexid()).setParameters(new ArrayList<IndexHistoryParameter>());
				}
				map.get(historyParameter.getIndexid()).getParameters().add(historyParameter);
			}
		}
		if(historyConversionList!=null && historyConversionList.size()>0) {
			for(IndexHistoryConversion historyConversion:historyConversionList) {
				if(map.containsKey(historyConversion.getIndexid())) {
					if(map.get(historyConversion.getIndexid()).getConversions() == null) {
						map.get(historyConversion.getIndexid()).setConversions(new ArrayList<IndexHistoryConversion>());
					}
					map.get(historyConversion.getIndexid()).getConversions().add(historyConversion);
				}
			}
		}
		historyVersion.setIndexs(historyDetailList); 
		return historyVersion;
	}

}
