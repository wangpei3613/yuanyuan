
package com.sensebling.common.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.nfunk.jep.JEP;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;

/**
 * StringUtil
 */
public class StringUtil {
	static SimpleDateFormat df_sdf1=new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat df_sdf2=new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat df_sdf3=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static DecimalFormat nb_ft=new DecimalFormat("0.00");
	
	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toLowerCase(firstChar) + tail;
		return str;
	}
	
	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		Character firstChar = str.charAt(0);
		String tail = str.substring(1);
		str = Character.toUpperCase(firstChar) + tail;
		return str;
	}
	/**
	 * 判断字符串是否是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		boolean isNumber = false;
		if (notBlank(str)) {
			str.matches("^([1-9]\\d*)|(0)$");
			isNumber = true;
		}
		return isNumber;
	}
	/**
	 * 判断字符串为 null 或者为  "" 时返回 true
	 */
	public static boolean isBlank(Object str) {
		return str == null || "".equals(str.toString().trim())||"null".equalsIgnoreCase(str.toString().trim()) ? true : false;
	}
	
	/**
	 * 判断字符串不为 null 而且不为  "" 时返回 true
	 */
	public static boolean notBlank(Object str) {
		return str == null || "".equals(str.toString().trim())|| "null".equalsIgnoreCase(str.toString().trim()) ? false : true;
	}
	/**判断字符串数组是否存在空字符串*/
	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim())|| "null".equalsIgnoreCase(str.trim()))
				return false;
		return true;
	}
	/**判断对象数组是否存在空对象*/
	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}
	/**
	 * 把数组转换成字符串，并且以分隔符（decollator） 分割开,
	 * @param strs 对象数组
	 * @param decollator 分割符
	 * @return 分割后的字符串
	 */
	public static String arrayToString(Object[] strs,String decollator) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			str.append(strs[i] + decollator);
		}
		if (str.length() > 0) {
			str.deleteCharAt(str.length() - decollator.length());
		}
		return str.toString();
	}
	/**
	 * 把object数组转换成String，如 id[]：1,2,3 转换为'1','2','3',
	 * 数组中的空对象或空字符串将被剔除
	 * @param objs
	 * @return 分割后的字符串
	 */
	public static String arrayIDToString(Object[] objs) {
		int num=0;
		StringBuffer string=new StringBuffer();
		for(Object obj:objs)
		{
			if(isBlank(obj))
				continue;
			String temp=obj.toString();
			temp=temp.trim().replace("'", "").replace("\"", "");
			if(num==0)
			{
				string.append("'"+temp+"'");
				num++;
			}
			else
				string.append(",'"+temp+"'");
		}
		return string.toString();

	}
	/**
	 * 将集合转换为字符串表示,集合中的对象默认采用toString()方法转换为字符串
	 * 采用,号进行分割
	 * @param collection 集合对象
	 * @return
	 */
	public static String arrayIDToString(@SuppressWarnings("rawtypes") Collection collection) 
	{
		if(collection==null||collection.size()==0)
			return "";
		else
			return arrayIDToString(collection.toArray());

	}
	/**
	 * 将字符串处理成适合in查询的条件(如:aa,bb,cc 处理成'aa','bb','cc')
	 * @param stringByComma
	 * @return
	 */
	public static String change_in(String stringByComma)
	{
		return change_in(stringByComma,",");
	}
	/**
	 * 将字符串处理成适合in查询的条件(如:aa;bb;cc 处理成'aa','bb','cc')
	 * @param stringByComma 处理的字符串
	 * @param decollator 分割字符串
	 * @return
	 */
	public static String change_in(String stringByComma,String decollator)
	{
		if(stringByComma==null||stringByComma.equals(""))
			return "''";
		String[] strings=stringByComma.trim().split(decollator);
		return arrayIDToString(strings);
	}
	/**
	 * 将数值转换为中文单位表示的字符串,一般用于报表数据转换
	 * 当数值大于1亿则转换到亿单位级,当数值小于1亿大于1万将转换到万单位级
	 * 如:8934343232 转换后为89.3亿;4343232转换后未434.3万
	 * @param num
	 * @return
	 */
	public static String transitionNum(double num)
	{
		DecimalFormat dFormat = new DecimalFormat("0.0");
		if(num<10000&&num>-10000)
			return dFormat.format(num);
		num=num/10000;
//转亿表示
		if (num / 10000d > 1||num / 10000d < -1)
			return dFormat.format(num / 10000d) + "亿";
//转万表示
		return dFormat.format(num) + "万";
	}
	/**
	 * 去除字符串中的空格、回车、换行符、制表符 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str)
	{
		String dest = "";
		if (str != null)
		{
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	/**
	 * 将字符串(true或false 转为boolean类型的true或false)
	 * @param boolean_str
	 * @return
	 */
	public static boolean changeToBoolean(String boolean_str)
	{
		if(StringUtil.notBlank(boolean_str))
		{
			if(boolean_str.trim().equals("true"))
				return true;
			else
				return false;
		}
		else
			return false;
	}
	/**
	 * 转成大写
	 * @param str
	 * @return
	 */
	public static String toUpperStr(String str)
	{
		if(isBlank(str))
			return "";
		else
			return str.toUpperCase();
	}
	/**
	 * 转成小写
	 * @param str
	 * @return
	 */
	public static String toLowerStr(String str)
	{
		if(isBlank(str))
			return "";
		else
			return str.toLowerCase();
	}
	/**
	 * 将字符串转换为int类型
	 * @param str
	 * @return
	 * @throws NumberFormatException
	 */
	public static int parseToInt(String str) throws NumberFormatException
	{
		if(isBlank(str))
			return 0;
		else
			return Integer.parseInt(str);
	}
	/**
	 * 将字符串转换为double类型
	 * @param str
	 * @return
	 * @throws NumberFormatException
	 */
	public static double parseToDouble(String str) throws NumberFormatException
	{
		if(isBlank(str))
			return 0;
		else
			return Double.parseDouble(str);
	}
	/**
	 * 将对象转换为double,如果对象为空则转换为0
	 * @param num
	 * @return
	 */
	public static Double parseToDouble(Object num)
	{
		if(isBlank(num))
			return 0d;
		else if(num instanceof Double)
			return (Double)num;
		else if(num instanceof BigDecimal)
			return ((BigDecimal)num).doubleValue();
		else if(num instanceof Number)
			return ((Number)num).doubleValue();
		else
			return new Double(parseToDouble(num.toString()));
	}
	/**
	 * 将对象转换为字符串表示,若对象为null则返回空字符串
	 * @param obj 转换的对象
	 * @return
	 */
	public static String ValueOf(Object obj) {
		if(obj == null) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}
	/**
	 * 格式化小数,精确到小数点后2位
	 * @param value
	 * @return 格式化后的小数字符串表示
	 */
	public static String formatDouble_str(double value)
	{
		return nb_ft.format(value);
	}
	/**
	 * 格式化小数,精确到小数点后2位
	 * @param value
	 * @return 格式化后的小数
	 */
	public static double formatDouble_num(double value)
	{
		return Double.parseDouble(nb_ft.format(value));
	}
	/**
	 * 字符串数学运算
	 * @param str
	 * @return
	 */
	public static double evalStr(String str){
		JEP myPrase=new JEP(); 
		myPrase.parseExpression(str); 
		return myPrase.getValue(); 
	}
	/**
	 * 字符串中 去掉重复的数据
	 * 例如：1,2,3,4,5,4,3,2,1   得到 1,2,3,4,5
	 * @param str
	 * @return
	 */
	public static String delRepeatByStrNodyh(String str){
		String result = "";
		if(notBlank(str)){
			String[] list = str.split(",");
			for(int i=0; i<list.length; i++){
				if(result.indexOf(list[i]) == -1 ){
					result = result + list[i] + ",";
				}
			}
		}
		if(notBlank(str)){
			result=result.substring(0, result.length()-1);
		}
		return result;
	}
	/**
	 * table高级查询转换工具
	 * @param s
	 * @return
	 */
	public static String searchKey(String s){
		
		if("eq".equals(s)){
			return BasicsFinal.EQ;
		}else if("ne".equals(s)){
			return BasicsFinal.NOT_EQ;
		}else if("gt".equals(s)){
			return BasicsFinal.GT;
		}else if("ge".equals(s)){
			return BasicsFinal.GE;
		}else if("lt".equals(s)){
			return BasicsFinal.LT;
		}else if("le".equals(s)){
			return BasicsFinal.LE;
		}else if("cn".equals(s)){
			return BasicsFinal.LIKE;
		}else if("bw".equals(s)){
			return BasicsFinal.BEGIN_LIKE;
		}else if("ew".equals(s)){
			return BasicsFinal.END_LIKE;
		}else if("in".equals(s)){
			return BasicsFinal.IN;
		}else if("ni".equals(s)){
			return BasicsFinal.NOT_IN;
		}
		return BasicsFinal.EQ;
	}
	
	/**
	 * 字符串加密
	 * @param s
	 * @return
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte strTemp[] = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte md[] = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}

			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static int indexOfArr(String[] arr,String value2){
		if(arr == null || arr.length == 0){
			return -1;
		}
		for(int i=0;i<arr.length;i++){
			if(arr[i].equals(value2)){
				return i;
			}
		}
		return -1;
	}
	public static String replaceSqlPlaceholder(String sql, List<String> params) {
		char c = '?';
		char[] chars = sql.toCharArray();
		List<Integer> indexList = new ArrayList<Integer>();
		for (int i = 0; i < chars.length; i++) {
			if (c == chars[i]) {
				indexList.add(i);
			}
		}
		if (indexList != null && indexList.size() > 0) {
			for (int i = 0; i < indexList.size(); i++) {
				sql = sql.replaceFirst("\\?", "'"+params.get(i)+"'");
			}
		}
		return sql;
	}
	
	/**
	 * 转换为非 null 的字符串 null -> ""
	 * @param obj
	 * @return
	 */
	public static String sNull(Object obj) {
		if(obj == null) return "";
		return obj.toString().trim().replace(" ", "");
//		if(obj instanceof String) {}
	}
	
	/**
	 * 获取指定字符串出现的次数
	 * 
	 * @param srcText 源字符串
	 * @param findText 要查找的字符串
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {
	    int count = 0;
	    Pattern p = Pattern.compile(findText);
	    Matcher m = p.matcher(srcText);
	    while (m.find()) {
	        count++;
	    }
	    return count;
	}
	/**
	 * 获得两个list中的相同元素
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<Object> getTheSameSection(List<Object> list1,List<Object> list2){
		List<Object> list = new ArrayList<Object>();
		if(list1!=null && list1.size()>0 && list2!=null && list2.size()>0){
			for(Object o:list1){
				if(list2.contains(o)){
					list.add(o);
				}
			}
		}
		return list;
	}
	
	/**
	 * 转换为非 null 的int,null "" -> 0
	 * @param obj
	 * @return
	 */
	public static int iNull(Object obj) {
		if(obj == null) return 0;
		if(obj instanceof Integer) return ((Integer)obj).intValue();//把integer对象转换为int类型了
		String str = obj.toString().trim();
		if(str.equals(""))return 0;
		else return Integer.parseInt(str.split("\\.")[0]);
	}
	/**
	 * 判断被查找的数组的所有元素是否在要查找的数组中
	 * @param sources 要查找的数组
	 * @param finds 被查找的数组
	 * @return
	 */
	public static boolean arrInArr(String[] sources, String[] finds) {
		int num = 0;
		for(String find:finds) {
			for(String source:sources) {
				if(source.equals(find)) {
					num++;
					break;
				}
			}
		}
		return num == finds.length;
	}
	/**
	 * 替换字符串
	 * @param str
	 * @param map key为被替换的字符串，value为替换为的字符串
	 * @return
	 */
	public static String replaceStr(String str, Map<String, String> map) {
		if(map != null) {
			for(String key:map.keySet()) {
				str = str.replace(key, map.get(key));  
			}
		}
		return str;
	}
	/**
	 * 拼接部门视野权限
	 * @param userid_column 用户id所在字段
	 * @param departid_column 部门id所在字段
	 * @return
	 */
	public static String initViewAuth(String userid_column, String departid_column) {
		StringBuffer sql = new StringBuffer();
		if(notBlank(userid_column)){
			User user = HttpReqtRespContext.getUser();
			sql.append(" 1=1 and ( "+userid_column+" = '"+user.getId()+"' ");
			if(notBlank(departid_column)){
				sql.append(" or exists (select 1 from sen_user_department where userid='"+user.getId()+"' and departid="+departid_column+") ");
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}
	/**
	 * html代码编码
	 * @param str
	 * @return
	 */
	public static String htmlEncode(String str){
		if(notBlank(str)){
			str = str.replaceAll("&", "&amp;");  
			str = str.replaceAll("<", "&lt;");  
			str = str.replaceAll(">", "&gt;");  
			str = str.replaceAll("\"", "&quot;");  
		}
		return str;
	}
	
	
	/**
	 * 删除所有的文件 YF
	 * 
	 * @param file
	 *            2017-3-5上午10:45:33
	 */
	public static void deleteFile(File file) {
		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
			} else if (file.isDirectory()) {// 否则如果它是一个目录
				File[] files = file.listFiles();// 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
					deleteFile(files[i]);// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
			}
		} else {
			// System.out.println("所删除的文件不存在");
		}
	}
	
	/**
	 * 获取当前日期的前一年，前两年时间
	 * @return
	 */
	public static String getYearDate(int y) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
        c.add(Calendar.YEAR, -y);
        Date d = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(d);
	}
	
	public static String getCurrentTime(String bz){
		Date date = new Date();
		if("1".equals(bz)){
			return df_sdf1.format(date);
		}else if("2".equals(bz)){
			return df_sdf2.format(date);
		}else {
			return df_sdf3.format(date);
		}
	}
	
	/**
	 * 通过类获得对应数据库的表名
	 * @param info
	 * @return
	 */
	public static String getTableName(Object info){
		if(null==info)return "";
		Annotation[] a =  info.getClass().getAnnotations();
		for (int i = 0; i < a.length; i++) {
			Annotation ann = a[i];
			if(ann instanceof javax.persistence.Table){
				String name = ((javax.persistence.Table) ann).name().trim();
				return name;
			}
		}
		
		return "";
	}
	

	
	/**
	 * 转换为非 null 的int,null "" -> 0
	 * 
	 * @param obj
	 * @return
	 */
	public static long lNull(Object obj) {
		if (obj == null)
			return 0;
		if (obj instanceof Long)
			return ((Long) obj).longValue();
		String str = obj.toString().trim();
		if (str.equals(""))
			return 0;
		return Long.parseLong(str);
	}

	/**
	 * 转换为非 null 的int,null "" -> 0
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal bNull(Object obj) {
		if (obj == null)
			return new BigDecimal(0);
		if (obj instanceof BigDecimal)
			return (BigDecimal) obj;
		String str = obj.toString().trim();
		if (str.equals(""))
			return new BigDecimal(0);
		return BigDecimal.valueOf(Double.parseDouble(str));
	}
	
	/** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
*  
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
     *  
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
     * 192.168.1.100 
     *  
     * 用户真实IP为： 192.168.1.110 
     *  
     * @param request 
     * @return 
     */  
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
    
    /**
     * 获得与当前时间相差几分钟的时间
     * @param minute
     * @return
     */
    public static String getTimeByMinute(int minute) {

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MINUTE, minute);

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar
				.getTime());
	}
    
    /**
     * 当前时间先前或向后移动月份
     * 
     * @param m
     * @param fmt
     * @return
     * 2018年6月20日-下午5:28:54
     * YF
     */
    public static String getMonthDate(int m,String fmt) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
        c.add(Calendar.MONTH, m);
        Date d = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        return format.format(d);
	}
	
    
    /**
     * 是否含有中文 有返回true
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
			flg = true;

		return flg;
	}
    
    public static Map<String,Object> getRequestToken(){
    	Map<String,Object> requestTokenMap  = ProtocolConstant.requestTokenMap;
		if(null==requestTokenMap)requestTokenMap = new HashMap<String,Object>();
		return requestTokenMap;
    }
    
    public static String getReqToken(String key){
    	Map<String,Object> requestTokenMap  = ProtocolConstant.requestTokenMap;
		if(null==requestTokenMap){
			ProtocolConstant.requestTokenMap = new HashMap<String,Object>();
		}
		return sNull(ProtocolConstant.requestTokenMap.get(key));
    }
    
    public static void setReqToken(String key){
    	Map<String,Object> requestTokenMap  = ProtocolConstant.requestTokenMap;
		if(null==requestTokenMap){
			ProtocolConstant.requestTokenMap  = new HashMap<String,Object>();
		}
		ProtocolConstant.requestTokenMap.put(sNull(key), sNull(key));
		
    }
    
    public static void removeReqToken(String key){
    	Map<String,Object> requestTokenMap  = ProtocolConstant.requestTokenMap;
		if(null==requestTokenMap)ProtocolConstant.requestTokenMap = new HashMap<String,Object>();
		ProtocolConstant.requestTokenMap.remove(sNull(key));
    }
    
    /**
     * 创建请求token
     * 
     * 2019年4月11日-下午7:56:10
     * YF
     * @return 
     */
    public static String creatToken(){
    	String token = UUID.randomUUID().toString();
//		setReqToken(token);
		return token;
    }
    
    public static void setToken(String key){
    	Map<String,Object> requestTokenMap  = ProtocolConstant.tokenMap;
		if(null==requestTokenMap){
			ProtocolConstant.tokenMap  = new HashMap<String,Object>();
		}
		ProtocolConstant.tokenMap.put(sNull(key), sNull(key));
		
    }
    
    public static void setTokenMap(Map<String,Object> map){
		ProtocolConstant.tokenMap=map;
    }
    
    public static long generateRandomNumber(int n){
        if(n<1){
            throw new IllegalArgumentException("随机数位数必须大于0");
        }
        return (long)(Math.random()*9*Math.pow(10,n-1)) + (long)Math.pow(10,n-1);
    }
    
    public static <T> T getListIndexVal(List<T> list,int index) {
    	if(list!=null && list.size()>index) {
    		return list.get(index);
    	}
    	return null;
    }
    public static Object getNotNullObj(Object... objs) {
    	for(Object obj:objs) {
    		if(obj instanceof String) {
    			if(notBlank(obj)) {
    				return obj;
    			}
    		}else if(obj != null) {
    			return obj;
    		}
    	}
    	return null;
    }
    public static String getNotNullStr(Object... objs) {
    	return ValueOf(getNotNullObj(objs));
    }
    public static String getMatcher(String regex, String source) {  
        String result = "";  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(source);  
        while (matcher.find()) {  
            result = matcher.group(1);
        }  
        return result;  
    } 
    public static void main(String[] args) {
		System.out.println(getMatcher("doTabAction\\('(\\d*)'\\)",""));
	}
}





