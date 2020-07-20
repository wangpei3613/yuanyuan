package com.sensebling.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体处理类，采用Java反射，
 * 如果能直接使用具体类，建议使用该类提供的方法
 * @author 
 * @date 2014-9-17
 */
public class ModelUtil {
	public static Map<String,List<Field>> modelFields=new HashMap<String,List<Field>>();
	public static Map<String,String> modelJson=new HashMap<String,String>();
	/**
	 * 判断实体不为空
	 * @param obj
	 * @return
	 */
	public static Boolean isNotNull(Object obj){
		if(obj!=null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获取class类的属性集合
	 * @param c 类的class
	 * @param includeSupper 是否包含父类的属性
	 */
	public static List<Field> getFields(Class<?> c,boolean includeSupper){
		List<Field> temp=new ArrayList<Field>();
		List<Field> list=modelFields.get(c.getName());
		if(list==null&&c!=Object.class)
		{
			list=Arrays.asList(c.getDeclaredFields());
			modelFields.put(c.getName(), list);
			for(Field f:list)
				f.setAccessible(true);
			if(includeSupper)
				temp.addAll(getFields(c.getSuperclass(), true));
		}
		else if(includeSupper&&c!=Object.class)
		{
			temp.addAll(getFields(c.getSuperclass(), true));
		}
		if(list!=null)
			temp.addAll(list);
		return temp;
	}
	
	/**
	 * 根据实体名称获取Class对象，Java反射
	 * @param className
	 * @return
	 */
	public static Class<?> getClassByName(String className){
		Class<?> clazz=null;
		try {
			clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	/**
	 * 执行指定类的方法
	 * @param owner 类
	 * @param methodName 需要执行的方法
	 * @param args 方法传递参数
	 * @return 该类所需要执行的方法的返回值
	 */
	@SuppressWarnings("unlikely-arg-type")
	public static Object invokeMethod(Object owner, String methodName,Object[] args){
		Class<?> ownerClass=owner.getClass();
		Class<?>[] argsClass=null;
		if(args!=null){
			argsClass=new Class<?>[args.length];
			for(int i=0;i<args.length;i++){
				if(args[i]!=null){
					if("java.lang.Bollean".equals(args[i].getClass())){
						argsClass[i]=boolean.class;
					}else{
						argsClass[i]=args[i].getClass();
					}
				}else{
					argsClass[i]=Object.class;
				}
			}
		}
		Method method=null;
		try {
			method=ownerClass.getMethod(methodName, argsClass);
		} catch (Exception e) {
			System.out.println("获取类函数失败");
		}
		Object obj=null;
		try {
			obj=method.invoke(owner, args);
		} catch (Exception e) {
			System.out.println("执行"+ownerClass.getName()+"类中的"+method.getName()+"函数失败");
		}
		return obj;
	}
	/**
	 * 
	 * 执行属性的set方法
	 * @param model
	 * @param keyName
	 * @param args
	 */
	public static void invokeSetMethod(Object model,String keyName,Object[] args){
		String setMethodName="set"+keyName.substring(0,1).toUpperCase()+keyName.substring(1);
		invokeMethod(model, setMethodName, args);
	}
	/**
	 * 执行属性的get方法
	 * @param model
	 * @param proName
	 * @return
	 */
	public static Object getPropertyValue(Object model, String proName){
		String getMethodName="get"+proName.substring(0,1).toUpperCase()+proName.substring(1);
		Object obj=invokeMethod(model, getMethodName, null);
		return obj;
	}
}
