package com.sensebling.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.http.client.ClientProtocolException;
/**
 * 公共的工具类
 * @author 
 *
 */
public class CommonUtil 
{
	/**
	 * 输出随机验证码,直接使用out输出流输出产生的图像数据
	 * @param codeNum 验证码的长度
	 * @param out 输出流
	 * @return 产生的验证码字符串
	 * @throws IOException
	 */
	public static String randomImageCode(int codeNum,OutputStream out) throws IOException
	{
		BufferedImage img = new BufferedImage(120,30,BufferedImage.TYPE_INT_RGB);  
        // 得到该图片的绘图对象    
        Graphics g = img.getGraphics();  
        Random r = new Random();  
        Color c = new Color(255, 255, 255);  
        g.setColor(c);  
        // 填充整个图片的颜色    
        g.fillRect(0, 0, 140, 30);  
        // 向图片中输出数字和字母    
        StringBuffer sb = new StringBuffer();  
        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();  
        int index, len = ch.length;  
        for (int i = 0; i < codeNum; i++)
        {  
            index = r.nextInt(len);  
            g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));  
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));  
            // 输出的  字体和大小                      
            g.drawString("" + ch[index], (i * 17) + 14, 25);  
            //写什么数字，在图片 的什么位置画    
            sb.append(ch[index]);  
        } 
        ImageIO.write(img, "JPG", out); 
        return sb.toString();
	}
	
	
	public static String post(String strURL, String params) throws ClientProtocolException, IOException {  
        try {  
            URL url = new URL(strURL);// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(params);  
            out.flush();  
            out.close();  
            // 读取响应  
            int length = (int) connection.getContentLength();// 获取长度  
            InputStream is = connection.getInputStream();  
            if (length != -1) {  
                byte[] data = new byte[length];  
                byte[] temp = new byte[512];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                String result = new String(data, "UTF-8"); // utf-8编码  
//                System.out.println(result);  
                return result;  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return "error"; // 自定义错误信息  
    }  
}
