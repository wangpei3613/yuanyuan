package com.sensebling.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/***
 * easyui tree数据格式转换类
 * @author 
 *
 */
public class EasyTreeUtil {

	/**
	 * 将Map格式的数据集合转换为适合easyui tree控件使用的数据结构,
	 * 我们系统中保存子父级的关系数据时采用一般是 id,pid 关联关系,这中数据格式可以
	 * 使用zTree树控件,此控件支持的格式为{id:'',name:'',pid:''},zTree会自动解析子父级的关系.
	 * 但是zTree的下拉框Tree没有很好的支持所以需要使用easyui的combotree控件,combotree支持的json格式为
	 * {id:'',text:'',children:[{id:'',text:''},...]},此方法会根据id和pid的关系将数据进行处理满足easyui tree的格式
	 * 注意:若原始数据出现子父级互相嵌套的时候(如:[{id:'1',text:'a',pid:'2'},{id:'2',text:'b',pid:'1'}])会将这两个对象作为根节点
	 * @param treeData 原始的数据集合
	 * @param idName id列的key名称
	 * @param pidName pid列的key名称
	 * @return 处理后的List<Map<String, Object>> ,可直接转为json字符串
	 */
	public static List<Map<String, Object>> ToEasyTree(List<Map<String,Object>> treeData,String idName,String pidName)
	{
		if(StringUtil.isBlank(idName))
			idName="id";
		if(StringUtil.isBlank(pidName))
			pidName="pid";
		List<Map<String, Object>> treeList=new ArrayList<Map<String,Object>>();//根节点
		for(Map<String, Object> m:treeData)
		{
			String id=String.valueOf(m.get(idName));
			String pid=String.valueOf(m.get(pidName));
			m.put("iconCls", m.get("icon"));
			m.remove("icon");
			boolean isFind=false;
			for(Map<String, Object> t_m:treeData)
			{
				if(StringUtil.notBlank(t_m.get(pidName))&&t_m.get(pidName).equals(id)&&!t_m.get(idName).equals(pid))
				{
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> children=(List<Map<String, Object>>)m.get("children");
					if(children==null)
					{
						children=new ArrayList<Map<String,Object>>();
						m.put("children", children);
					}
					children.add(t_m);
					m.put("state", "closed");
				}
				if(t_m.get(idName).equals(pid)&&!id.equals(t_m.get(pidName)))
					isFind=true;
			}
			if(!isFind)
			{
				m.remove("state");
				treeList.add(m);
			}
		}
		return treeList;
	}

	public static List<Map<String, Object>> toTreeGrid(List<Map<String,Object>> treeData,String idName,String pidName)
	{
		if(StringUtil.isBlank(idName))
			idName="id";
		if(StringUtil.isBlank(pidName))
			pidName="pid";
		List<Map<String, Object>> treeList=new ArrayList<Map<String,Object>>();//根节点
		for(Map<String, Object> m:treeData){
			String id=String.valueOf(m.get(idName));
			String pid=String.valueOf(m.get(pidName));
			boolean isFind=false;
			m.put("leaf", true);
			for(Map<String, Object> t_m:treeData)
			{
				if(StringUtil.notBlank(t_m.get(pidName))&&t_m.get(pidName).equals(id)&&!t_m.get(idName).equals(pid))
				{
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> children=(List<Map<String, Object>>)m.get("children");
					if(children==null)
					{
						children=new ArrayList<Map<String,Object>>();
						m.put("children", children);
					}
					children.add(t_m);
					m.put("leaf", false);
					if(StringUtil.notBlank(t_m.get("expanded")) && !(boolean)(t_m.get("expanded"))) {
						m.put("expanded", false);
					}else {
						m.put("expanded", true);
					}
				}
				if(t_m.get(idName).equals(pid)&&!id.equals(t_m.get(pidName)))
					isFind=true;
			}
			if(!isFind){
				treeList.add(m);
			}
		}
		return treeList;
	}
}