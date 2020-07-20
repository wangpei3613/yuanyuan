package com.sensebling.common.util;

import java.math.BigDecimal;

public class IntegerUtil {
	
	/**
	 * 获取int，为null=0
	 * @param obj
	 * @return
	 */
	public static int getInt(Object obj){
        int tmpret =0;
        if(obj!=null){
               try{
                   tmpret = Integer.parseInt(obj.toString().trim());
               }catch(Exception ex){
                   
               }
        }
       return tmpret;
    }
	public static int intNull(Object obj) {
		if(obj == null) return new Integer(0);
		if(obj instanceof BigDecimal) return (Integer)obj;
		String str = obj.toString().trim();
		if(str.equals(""))return new  Integer(0);
		return Integer.valueOf(obj.toString());
	}
	
	/**
	 * 转换为非 null 的int,null "" -> 0
	 * @param obj
	 * @return
	 */
	public static BigDecimal bNull(Object obj) {
		if(obj == null) return new BigDecimal(0);
		if(obj instanceof BigDecimal) return (BigDecimal)obj;
		String str = obj.toString().trim();
		if(str.equals(""))return new BigDecimal(0);
		return BigDecimal.valueOf(Double.parseDouble(str));
	}
	/**
	 * 是否中文
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
	public static Double getDouble(Object obj){
		Double tmpret = null;
        if(obj!=null){
               try{
                   tmpret = Double.parseDouble(obj.toString().trim());
               }catch(Exception ex){
                   
               }
        }
       return tmpret;
    }
}
