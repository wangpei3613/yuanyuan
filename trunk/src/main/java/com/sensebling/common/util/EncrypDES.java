package com.sensebling.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncrypDES {
	
	//SecretKey 负责保存对称密钥
	private SecretKey deskey;
	//Cipher负责完成加密或解密工作
	private Cipher c;
	//该字节数组负责保存加密的结果
	private byte[] cipherByte;
	
	@SuppressWarnings("unused")
	private EncrypDES()
	{
		
	}
	/**
	 * 构造一个加密器
	 * @param privateKey 解密的密匙(长度不能低于8位)
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public EncrypDES(String privateKey)
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	    byte[] keyData = privateKey.getBytes();
		//生成密钥
	    //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
		deskey = new SecretKeySpec(keyData, "DES");
		//生成Cipher对象,指定其支持的DES算法
		try {
			c = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对字符串加密
	 * @param str 需要加密的字符串
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] Encrytor(String str) throws Exception {
		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = str.getBytes();
		// 加密，结果保存进cipherByte
		cipherByte = c.doFinal(src);
		return cipherByte;
	}

	/**
	 * 对字符串解密,解密后通过new String(字节数组) 来获取此字节数组表示的字符串
	 * @param buff 需要解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] Decryptor(byte[] buff) throws Exception {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(buff);
		return cipherByte;
	}

}
