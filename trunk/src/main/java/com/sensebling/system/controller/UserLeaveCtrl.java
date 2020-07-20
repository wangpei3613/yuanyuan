package com.sensebling.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
import com.sensebling.system.entity.User;
import com.sensebling.system.entity.UserLeave;
/**
 * 用户请假
 *
 */
import com.sensebling.system.service.UserLeaveSvc;
import com.sensebling.system.service.UserSvc;
@Controller
@RequestMapping("/system/userLeave")
public class UserLeaveCtrl extends BasicsCtrl{
	@Resource
	private UserLeaveSvc userLeaveSvc;
	@Resource
	private UserSvc userSvc;
	/**
	 * 分页查询
	 * @return
	 */
	@RequestMapping("/select")
	@ResponseBody
	public Result select() {
		Result r = new Result();
		r.setData(userLeaveSvc.getPager(getQueryParameter()));
		r.success();
		return r;
	}
	/**
	 * 保存请假记录
	 * @param v
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@ModuleAuth({"userLeave:openAdd","userLeave:openEdit"})
	@DisposableToken
	public Result save(UserLeave v) {
		Result r = new Result();
		if(StringUtil.notBlank(v.getUserid(), v.getStartdate(), v.getEnddate())) { 
			if(DateUtil.getTime(v.getStartdate()).getTime() <= DateUtil.getTime(v.getEnddate()).getTime()) {
				if(StringUtil.isBlank(v.getId())) {
					v.setAddtime(DateUtil.getStringDate());
					v.setAdduser(getUser().getId());
					userLeaveSvc.save(v);
				}else {
					v.setUpdatetime(DateUtil.getStringDate());
					v.setUpdateuser(getUser().getId());
					userLeaveSvc.update(v);
				}
				r.success();
			}else {
				r.setError("结束时间不能小于开始时间");
			}
		}
		return crudError(r);  
	}
	/**
	 * 删除请假记录
	 * @param data
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@ModuleAuth("userLeave:remove")
	@DisposableToken
	public Result delete(String data) {
		Result r = new Result();
		List<UserLeave> list = new Gson().fromJson(data, new TypeToken<List<UserLeave>>(){}.getType());
		for(UserLeave v:list) {
			userLeaveSvc.delete(v.getId());
		}
		r.success(); 
		return crudError(r); 
	}
	/**
	 * 进入用户请假列表页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/sys/userLeave/index";
	}
	/**
	 * 进入用户请假编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEdit(String id) {
		UserLeave v = new UserLeave();  
		if(StringUtil.notBlank(id)) {
			v = userLeaveSvc.get(id);
			User u = userSvc.get(v.getUserid());
			if(u != null) {
				v.setNickname(u.getNickName()); 
			}
		}
		getRequest().setAttribute("v", v);  
		return "/sys/userLeave/edit";
	}
}
