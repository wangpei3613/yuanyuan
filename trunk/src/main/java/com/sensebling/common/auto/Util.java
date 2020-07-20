package com.sensebling.common.auto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
	
	/**
	 * 读取文件
	 * @param path 文件路径
	 * @return
	 */
	public static String ReadFile(String path) {
		StringBuffer sb = new StringBuffer();  
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(new FileInputStream(path));  
			br = new BufferedReader(isr);  
			String line = null;  
			while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(isr != null) {
					isr.close();
				}
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	/**
	 * 写入文件
	 * @param path 文件路径
	 * @param str 文件内容
	 */
	public static void writeFile(String path, String str) {
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			File targetFile = new File(path); 
			osw = new OutputStreamWriter(new FileOutputStream(targetFile));  
			bw = new BufferedWriter(osw);  
			bw.write(str);  
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				if(osw != null) {
					osw.close();
				}
				if(bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static String collectionToStrings(Collection<String> collection, String separator){
        StringBuilder builder = new StringBuilder();
        if (collection != null && collection.size() > 0){
        		boolean b = true;
            for (Iterator<String> ite = collection.iterator(); ite.hasNext();){
            		if(b) {
            			b = false;
            		}else {
            			builder.append(separator);
            		}
            		builder.append(ite.next());
            	}
        }
        return builder.toString();
    }
}
