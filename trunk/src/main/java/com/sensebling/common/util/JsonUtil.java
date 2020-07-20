package com.sensebling.common.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * json操作工具类
 * @date 2014-9-17
 */
public class JsonUtil {
	private static Logger logger = Logger.getLogger(JsonUtil.class);
	/**
	 * 得到JsonUtil实例化对象
	 * @return
	 */
	public static JsonUtil getInstance(){
		return JsonHolder.JSON_BUILDER;
	}
	/**
	 * json乱码转换
	 * @param json
	 * @return
	 */
	public static JSONArray jsonEncoding( String json){
		try {
			return new JSONArray(new String(json));
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 静态内部类
	 * @author 
	 */
	private static class JsonHolder{
		private static final JsonUtil JSON_BUILDER=new JsonUtil();
		private static ObjectMapper mapper=new ObjectMapper();
	}
	/**
	 * 将一个数据实体解析成Json数据格式
	 * 例如：对象User 有属性 id,name等，解析以后为{"id":1,"name":"aaa"}
	 * @param obj 实体对象 
	 * @return String  {"属性名称":属性值,"属性名称":属性值} ；发生异常，返回 空字符串
	 */
	public static String entityToJSON(Object obj){
		try {
			JsonHolder.mapper.setSerializationInclusion(Include.NON_NULL);
			return JsonHolder.mapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("实体对象"+obj.getClass().getCanonicalName()+"转换成json字符串异常："+e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 将一个Json字符串封装为指定类型对象,
	 * 方法返回的类型为调用方法接受的参数类型,若不能转为接受参数的类型则抛异常
	 * @param json 需要转换的json字符串
	 * @param Class 需要转换成的类
	 * @return 泛型类型对象
	 */
	public static <T> T jsonToEntity(String json, Class<?> c){
		json = formatJson(json);
		try {
			@SuppressWarnings("unchecked")
			T obj = (T) JsonHolder.mapper.readValue(json, c);
			return obj;
		} catch (Exception e) {
			logger.error("json格式字符串转换成指定的实体类异常：json="+json+"；指定的类="+c.getCanonicalName());
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据节点获取该节点的json值，转换成字符串
	 * @param json 
	 * @param key 节点键
	 * @return String
	 */
	public static String jsonToString(JSONObject json, String key) {
		String jsonToString = "";
		try {
			if (StringUtil.notBlank(key)) {
				jsonToString = json.get(key).toString();
			} else {
				jsonToString = json.toString();
			}

		} catch (JSONException e) {
			logger.error("json转换异常：json=" + json + "；需要获取的节点 key=" + key);
			logger.error(e.getMessage());
		}

		return jsonToString;
	}

	/**
	 * json转换成字符串
	 * @param json
	 * @return
	 */
	public static String jsonToString(JSONObject json) {
		return jsonToString(json, null);
	}

	/**
	 * 根据key把字符串转换成相应的json，key是需要取得的键
	 * @param str
	 * @param key
	 * @return json
	 */
	public static JSONObject stringToJSON(String str, String key) {
		JSONObject json;
		try {
			json = new JSONObject(str);
			if (StringUtil.notBlank(key)) {
				json = (JSONObject) json.get(key);
			}
			return json;
		} catch (JSONException e) {
			logger.error("字符串转换json异常：str=" + str + "；需要获取的节点 key=" + key);
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 字符串转换成json
	 * @param str
	 * @return json
	 */
	public static JSONObject stringToJSON(String str) {
		return stringToJSON(str, null);
	}

	/**
	 * map转换成json，key默认是data
	 * @param key 生产json时对应的key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JSONObject mapToJSON(Map map, String key) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtil.notBlank(key)) {
				json.put(key, map);
			} else {
				key = "data";
				json.put(key, map);
			}

		} catch (JSONException e) {
			logger.error("map转换json异常：map=" + map + "；需要设置的节点 key=" + key);
			logger.error(e.getMessage());
		}
		return json;
	}

	/**
	 * map转换成json
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JSONObject mapToJSON(Map map) {
		return mapToJSON(map);
	}

	/**
	 * json转换成map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<Object, Object> jsonToMap(JSONObject json, String key) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if (StringUtil.isBlank(key)) {
				key = "data";
			}
			JSONObject list = (JSONObject) json.get(key);
			Iterator it = list.keys();
			while (it.hasNext()) {
				String mapKey = it.next().toString();
				if (mapKey != null) {
					map.put(mapKey, list.get(mapKey));
				}
			}
		} catch (JSONException e) {
			logger.error("json转换map异常：json=" + json + "；需要读取的节点 key=" + key);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * json转换成map
	 * @param json
	 * @return
	 */
	public static Map<Object, Object> jsonToMap(JSONObject json) {
		return jsonToMap(json, null);
	}

	/**
	 * list转成json，根据传入的key生产json的键 ，key默认值是result
	 * @param list
	 * @param key
	 * @return
	 */
	public static JSONObject listToJSON(List<?> list, String key) {

		JSONObject json = new JSONObject();
		try {
			if (StringUtil.isBlank(key)) {
				key = "result";
			}
			json.put(key, list);
		} catch (JSONException e) {
			logger.error("json转换map异常：json=" + json + "；需要读取的节点 key=" + key);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return json;

	}

	/**
	 * list转换成json
	 * @param list
	 * @return
	 */
	public JSONObject listToJSON(List<?> list) {
		return listToJSON(list, null);
	}

	/**
	 * json转换成list，key需要读取的json节点，默认是data
	 * @return
	 */
	public static List<?> jsonToList(JSONObject json, String key) {
		List<Object> returnList = new ArrayList<Object>();
		try {
			if (StringUtil.isBlank(key)) {
				key = "data";
			}
			JSONArray list = (JSONArray) json.get(key);

			for (int i = 0; i < list.length(); i++) {
				returnList.add(list.get(i));
			}
		} catch (JSONException e) {
			logger.error("json转换map异常：json=" + json + "；需要读取的节点 key=" + key);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return returnList;

	}

	public static List<?> jsonToList(JSONObject json) {
		return jsonToList(json, null);
	}
	/**
	 * 格式化Json 去掉空格
	 * @param json
	 * @return String 
	 */
	public static String formatJson(String json){
		if(StringUtil.notBlank(json)){
			return json.replaceAll("\n", "");
		}else{
			return "";
		}
	}
	
	
 // 将Json数据解析成相应的映射对象
     public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
         Gson gson = new Gson();
         T result = gson.fromJson(jsonData, type);
         return result;
     }
 
     // 将Json数组解析成相应的映射对象列表
     public static <T> List<T> parseJsonArrayWithGson(String jsonData,
             Class<T> type) {
         Gson gson = new Gson();
         List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
         }.getType());
         return result;
     }
     /**
      * javabean实体类对象转为Map类型对象的方法
      * @param obj
      * @return
      */
     public static Map<String, Object> beanToMap(Object obj) { 
         Map<String, Object> params = new HashMap<String, Object>(0); 
         try { 
             PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
             PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
             for (int i = 0; i < descriptors.length; i++) { 
                 String name = descriptors[i].getName(); 
                 if (!"class".equals(name)) { 
                     params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                 } 
             } 
         } catch (Exception e) { 
             e.printStackTrace(); 
         } 
         return params; 
     }
     public static String formatJsonHttp(Object json) throws UnsupportedEncodingException{
 		if(!StringUtil.notBlank(json)){
 			return "";
 		}
 		String str = "";
 		if(json instanceof String){
 			str = (String)json;
 		}else{
 			str = entityToJSON(json);
 		}
 		JSONObject j = stringToJSON(str);
 		str = "";
 		@SuppressWarnings("rawtypes")
 		Iterator it = j.keys();
 		try {
 			while (it.hasNext()) {
 					String key = (String)it.next();
 					String value = URLEncoder.encode(String.valueOf(j.get(key)),"utf-8");
 					//String value = String.valueOf(j.get(key));
 					str += "&"+key+"="+value;
 			}
 		} catch (JSONException e) {
 			str = "";
 			e.printStackTrace();
 		}
 		return str.length()>0?str.substring(1):str;
 	}
     
     public static String toString(Object o) {
    	 if(o == null) {
    		 return "";
    	 }else if(o instanceof String) {
    		 return (String) o;
    	 }
    	 return JSON.toJSONString(o);
     }

}
