package com.sensebling.ope.sync.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;

/**
 * 同步信贷系统html解析帮助类
 *
 */
public class HtmlUtil {
	protected static DebugOut log=new DebugOut(HtmlUtil.class);
	 /**
     * 获取html表单数据
     * @param html 页面html
     * @param formid 表单id
     * @return
     */
    public static Map<String, Object> getFormData(String html, String formid) {
    		Map<String, Object> map = new HashMap<String, Object>();
        Document doc = Jsoup.parse(html);
        Element form = doc.getElementById(formid);
        if(form == null) {
        		Elements eles = doc.getElementsByAttributeValue("name", formid);
        		if(eles!=null && eles.size()>0) {
        			form = eles.get(0);
        		}else {
        			return map;
        		}
        }
        Elements child = form.getAllElements();
        for (int i = 0; i < child.size(); i++) {
            Element e = child.get(i);
            if (StringUtil.notBlank(e.attr("name"))) {
                if (e.nodeName().equalsIgnoreCase("input")) {
                    if(map.containsKey(e.attr("name"))) {
                    		map.put(e.attr("name"), map.get(e.attr("name"))+","+e.attr("value"));
                    }else {
                    		map.put(e.attr("name"), e.attr("value"));
                    }
                }
                else if (e.nodeName().equalsIgnoreCase("textarea")) {
                    map.put(e.attr("name"), e.text());
                }
                else if (e.nodeName().equalsIgnoreCase("select")) {
                		if(e.attributes().hasKey("initvalue")) {
                			map.put(e.attr("name"), e.attr("initvalue"));
                		}else {
                			Element __ = e.select("option[selected]").first();
                			if(__ != null) {
                				map.put(e.attr("name"), __.val());
                			}
                		}
                }
            }
        }
        return map;
    }
    /**
     * 获取下环节名称
     * @param html 页面html
     * @return
     */
    public static String getNextPhaseAttr(String html){
    		Document doc = Jsoup.parse(html);
        Elements eles = doc.select("input[name=phaseopinion]");
        List<String> list = new ArrayList<String>();
        for(Element ele : eles){
	        	String s = ele.val();
	        	if(s.equals("通过") || s.equals("批准")) {
	        		return s;
	        	}
	        	if(s.equals("通过") || (s.contains("提交") && !s.equals("否决")&& !s.contains("复议"))){  
	        		list.add(s);
	        	}
        }
        if(list.size() > 0){
        		return list.get(0);
        }
    		return null;
    }
    /**
     * 获取下环节审批人员
     * @param str 审批人员汇总字符串
     * @return
     */
    public static String getNextAuditMan(String str, int bz){
    		String[] arr = str.substring(8).split("@");
    		log.errPrint("获取下环节审批人员:"+str+" js ", null);
    		String uanme = null;
    		if(bz == 0) {
    			uanme = getPriorityUname(arr, "sync.credit.audit.user1");
    		}else if(bz == 1){
    			uanme = getPriorityUname(arr, "sync.credit.audit.user2");
    		}
    		if(StringUtil.notBlank(uanme)) {
    			return uanme;
    		}
    		return arr[new Random().nextInt(arr.length)];
    }
    /**
     * 获取下环节审批人员
     * @param str 审批人员汇总字符串
     * @param user 优先用户
     * @return
     */
    public static String getNextAuditMan(String str, Integer bz, List<User> users) {
		if(bz == 0 && users!=null && users.size()>0) {
			String[] arr = str.substring(8).split("@");
			for(String a:arr) {
				for(User u:users) {
					if(a.contains(u.getUserName())) {
						return a;
					}
				}
			}
		}
		return getNextAuditMan(str,bz);
	}
    private static String getPriorityUname(String[] arr, String string) {
    		String uname = BasicsFinal.getParamVal(string);
		if(StringUtil.notBlank(uname)) {
			 String[] s = uname.split("@");
			 for(String a:arr) {
				 for(String b:s) {
					 if(a.contains(b)) {
						 return a;
					 }
				 }
			 }
		}
		return null;
	}
	public static String getExceptionInfo(Exception e){
    		StringWriter sw = new StringWriter();   
        e.printStackTrace(new PrintWriter(sw, true));   
        return sw.toString();   
    }
    public static String getStrByExp(String str,String pattern) {
    		Pattern r = Pattern.compile(pattern);
    		Matcher m = r.matcher(str);
    		if (m.find( )) {
    			return m.group(1);
    		}
    		return "";
    }
}
