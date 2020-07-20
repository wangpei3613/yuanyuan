package com.sensebling.common.util;

import java.math.BigInteger;

/**
 * 权限控制工具类
 * @author 
 * @date 2014-9-23
 */
public class CRUDUtil {

	
	
	/**
	 * 十进制转换成二进制
	 * @param crud int型 十进制
	 * @return String 二进制
	 */
	public static String toBinaryString(int crud){
		return Integer.toBinaryString(crud);
	}
	/**
	 * 二进制转十进制
	 * @param strNum String型 二进制
	 * @return int 十进制
	 */
	public static int toInt(String strNum){
		return new BigInteger(strNum,2).intValue();
	}
	/**
	 * 新增权限
	 * 用二进制运算操作
	 * @param crud int型，原有的操作权限
	 * @param i int型，新赋予的权限
	 * @return int
	 */
	public static int addCRUD(int crud,int i){
		return (crud | i);
	}
	/**
	 * 删除权限
	 * 用二进制运算操作
	 * @param crud int型， 原有的操作权限
	 * @param i int型， 需要删除的权限
	 * @return 返回新权限
	 */
	public static int deleteCRUD(int crud,int i){
		return crud & (~i);
	}
	/**
	 * 当多个角色权限重叠时，需要取得所有权限
	 * 取得角色重叠的权限进行汇总，
	 * @param cruds int[] 用户所有角色的权限集合
	 * @return
	 */
	public static int getCRUD(Integer[] cruds){
		int returnCrud = 0;
		for(int i =0;i<cruds.length;i++){
			returnCrud = returnCrud | cruds[i];
		}
		return returnCrud;
	}
	/**
	 * 当多个角色权限重叠时，需要取得所有权限
	 * 取得角色重叠的权限进行汇总，
	 * @param cruds int[] 用户所有角色的权限集合
	 * @return
	 */
	public static int getCRUD(int[] cruds){
		int returnCrud = 0;
		for(int i =0;i<cruds.length;i++){
			returnCrud = returnCrud | cruds[i];
		}
		return returnCrud;
	}
	
	/**
	 * 验证权限
	 * 对用户权限进行校验
	 * @param crud int型 用户现有权限
	 * @param i int型 需要验证的权限
	 * @return boolean false表示没有该权限
	 */
	public static boolean checkCRUD(int crud,int i){
		return (crud & i)==i ;
	}
	
}
