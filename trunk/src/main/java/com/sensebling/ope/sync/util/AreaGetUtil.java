package com.sensebling.ope.sync.util;

import java.util.ArrayList;
import java.util.List;

public class AreaGetUtil {

	public static void main(String[] args) throws Exception {  
		String uname = "";
		String upass = "";
		
		String ids = "1101,1102,1201,1202,1301,1302,1303,1304,1305,1306,1307,1308,1309,1310,1311,1401,1402,1403,1404,1405,1406,1407,1408,1409,1410,1411,1501,1502,1503,1504,1505,1506,1507,1508,1509,1522,1525,1529,2101,2102,2103,2104,2105,2106,2107,2108,2109,2110,2111,2112,2113,2114,2201,2202,2203,2204,2205,2206,2207,2208,2224,2301,2302,2303,2304,2305,2306,2307,2308,2309,2310,2311,2312,2327,3101,3102,3201,3202,3203,3204,3205,3206,3207,3208,3209,3210,3211,3212,3213,3301,3302,3303,3304,3305,3306,3307,3308,3309,3310,3311,3401,3402,3403,3404,3405,3406,3407,3408,3410,3411,3412,3413,3414,3415,3416,3417,3418,3501,3502,3503,3504,3505,3506,3507,3508,3509,3510,3601,3602,3603,3604,3605,3606,3607,3608,3609,3610,3611,3701,3702,3703,3704,3705,3706,3707,3708,3709,3710,3711,3712,3713,3714,3715,3716,3717,4101,4102,4103,4104,4105,4106,4107,4108,4109,4110,4111,4112,4113,4114,4115,4116,4117,4201,4202,4203,4205,4206,4207,4208,4209,4210,4211,4212,4213,4228,4290,4301,4302,4303,4304,4305,4306,4307,4308,4309,4310,4311,4312,4313,4331,4401,4402,4403,4404,4405,4406,4407,4408,4409,4413,4414,4415,4416,4417,4418,4419,4420,4428,4451,4452,4453,4501,4502,4503,4504,4505,4506,4507,4508,4509,4510,4511,4512,4513,4514,4601,4602,4690,5001,5002,5003,5101,5103,5104,5105,5106,5107,5108,5109,5110,5111,5113,5114,5115,5116,5117,5118,5119,5120,5130,5132,5133,5134,5169,5201,5202,5203,5204,5222,5223,5224,5226,5227,5301,5303,5304,5305,5306,5307,5308,5309,5323,5325,5326,5328,5329,5331,5333,5334,5401,5421,5422,5423,5424,5425,5426,6101,6102,6103,6104,6105,6106,6107,6108,6109,6110,6201,6202,6203,6204,6205,6206,6207,6208,6209,6210,6211,6212,6229,6230,6301,6321,6322,6323,6325,6326,6327,6328,6401,6402,6403,6404,6405,6501,6502,6521,6522,6523,6527,6528,6529,6530,6531,6532,6540,6542,6543,6590";
		HttpUtil v = new HttpUtil();
		v.toLogin(uname, upass);
		String aoid = v.getAoid("/Redirector?ComponentURL=/Untitled.jsp");
		aoid = v.getAoid("/Redirector?OpenerClientID="+aoid+"&TargetWindow=ObjectList&ComponentURL=/Common/ToolsA/AreaVFrame.jsp&AreaCode=");
		v.httpGet("/Common/ToolsA/AreaCodeSelect.jsp?CompClientID="+aoid+"&AreaCode=&randp=04036475492664178");
		List<String> list = new ArrayList<String>();
		for(String id:ids.split(",")) {
			String htm = v.httpGet("/Common/ToolsA/AreaCodeSelect.jsp?CompClientID="+aoid+"&AreaCodeValue="+id+"&randp="+Math.random());
			for(String temp:htm.split("\n")) {
				if(temp.contains("addItem")) {  
					list.add(temp);
				}
			}
			Thread.sleep(100);  
		}
		System.out.println(list);   
	}
	
	public static void getIndustryType() throws Exception {
		String uname = "";
		String upass = "";
		
		HttpUtil v = new HttpUtil();
		v.toLogin(uname, upass);
		String aoid = v.getAoid("/Redirector?ComponentURL=/Untitled.jsp");
		aoid = v.getAoid("/Redirector?OpenerClientID="+aoid+"&TargetWindow=ObjectList&ComponentURL=/Common/ToolsA/IndustryVFrame.jsp&IndustryType=");
		v.httpGet("/Common/ToolsA/IndustryTypeSelect.jsp?CompClientID="+aoid+"&IndustryType=&randp=7168640123088755");
		String ids = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T";
		List<String> list = new ArrayList<String>();
		for(String id:ids.split(",")) {
			String htm = v.httpGet("/Common/ToolsA/IndustryTypeSelect.jsp?CompClientID="+aoid+"&IndustryTypeValue="+id+"&randp="+Math.random());
			for(String temp:htm.split("\n")) {
				if(temp.contains("addItem")) {  
					list.add(temp);
				}
			}
			Thread.sleep(100);  
		}
		System.out.println(list); 
	}
	
}
