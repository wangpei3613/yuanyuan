package com.sensebling.system.finals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sensebling.common.util.JsonUtil;

public enum ErrorInfoFinal {
	
		$E000("系统维护中","$E000"),
        $E001("用户未登录","$E001"), 
        $E002("权限不足","$E002"),
        $E003("系统繁忙","$E003"); 
        private final String remark;//备注说明
        private final String handle;//处理方式
        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        ErrorInfoFinal(String remark,String handle) {
            this.remark = remark;
            this.handle=handle;
        }
        
        public String getRemark() {
            return remark;
        }
        public String getHandle() {
            return handle;
        }
        public String toJson()
        {
        	Map<String, String> map=new HashMap<String, String>();
        	map.put("$SYSTEM_ERROR", new Date().getTime()+"");
        	map.put("code", this.toString());
        	map.put("remark", remark);
        	map.put("handle", handle);
        	return JsonUtil.entityToJSON(map);
        }

}
