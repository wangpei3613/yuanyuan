package com.sensebling.archive.service.impl;

import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.archive.entity.ArchReelFileMaterial;
import com.sensebling.archive.service.ArchReelFileMaterialSvc;

import java.lang.reflect.Method;

@Service
public class ArchReelFileMaterialSvcImpl extends BasicsSvcImpl<ArchReelFileMaterial> implements ArchReelFileMaterialSvc,ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Override
    public Integer getSerial(String applyid) {
        Integer newSerial = null;
        StringBuffer sb = new StringBuffer("select max(meta_serial) as meta_serial from arch_reel_file_material where arch_reel_files_id =?");
        ArchReelFileMaterial material = baseDao.getBySQLEntity(sb.toString(),ArchReelFileMaterial.class,applyid);
        if(StringUtil.notBlank(material) && StringUtil.notBlank(material.getMeta_serial())){
            newSerial = material.getMeta_serial()+1;
        }else{
            newSerial = 1;
        }
        return newSerial;
    }
    private Result checkAction(String checkAction, String applyid) {
        if(StringUtil.notBlank(checkAction) && checkAction.trim().split("\\.").length==2) {
            String beanName = checkAction.trim().split("\\.")[0].trim();
            String method = checkAction.trim().split("\\.")[1].trim();
            Object obj = applicationContext.getBean(beanName);
            try {
                Method m = obj.getClass().getMethod(method, String.class);
                return (Result)m.invoke(obj, applyid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}