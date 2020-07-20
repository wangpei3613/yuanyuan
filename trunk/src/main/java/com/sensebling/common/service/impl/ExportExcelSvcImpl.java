package com.sensebling.common.service.impl;

import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.sensebling.common.service.ExportExcelSvc;
@Service("exportExcelSvc")
public class ExportExcelSvcImpl extends BasicsSvcImpl<Serializable> implements ExportExcelSvc,ApplicationContextAware {
	private ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
	
	public Object getBean(String serviceName) {
		return applicationContext.getBean(serviceName);
	}
}
