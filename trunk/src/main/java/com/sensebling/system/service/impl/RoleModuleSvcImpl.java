package com.sensebling.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.system.entity.RoleModule;
import com.sensebling.system.service.RoleModuleSvc;

/**
 * "角色——模块"关系业务层实现类，
 * @author 
 * @date 2011-9-17
 */
@Service("roleModuleSvc")
@Transactional
public class RoleModuleSvcImpl extends BasicsSvcImpl<RoleModule> implements RoleModuleSvc{

}
