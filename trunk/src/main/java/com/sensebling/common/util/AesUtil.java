package com.sensebling.common.util;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.system.finals.BasicsFinal;

public class AesUtil {

	public static void main(String[] args) throws Exception {

		String pwd = deCrypt(parseHexStr2Byte("369237396BB355EA8E1AA5C4709B6AB3"), "admin666666");
		System.out.println(pwd);
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String en(String content) {
		try {
			return parseByte2HexStr(enCrypt(content, BasicsFinal.getParamVal("url.key")));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String dn(String content) {
		try {
			return deCrypt(parseHexStr2Byte(content), BasicsFinal.getParamVal("url.key"));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 加密函数
	 * 
	 * @param content
	 *            加密的内容
	 * @param strKey
	 *            密钥
	 * @return 返回二进制字符数组
	 * @throws Exception
	 */
	public static byte[] enCrypt(String content, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;
		String str = content;

		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(strKey.getBytes());
		keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, random);

		desKey = keygen.generateKey();
		c = Cipher.getInstance("AES");

		c.init(Cipher.ENCRYPT_MODE, desKey);

		cByte = c.doFinal(str.getBytes("UTF-8"));

		return cByte;
	}

	/**
	 * 解密函数
	 * 
	 * @param src
	 *            加密过的二进制字符数组
	 * @param strKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String deCrypt(byte[] src, String strKey) throws Exception {
		KeyGenerator keygen;
		SecretKey desKey;
		Cipher c;
		byte[] cByte;
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(strKey.getBytes());
		keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, random);

		desKey = keygen.generateKey();
		c = Cipher.getInstance("AES");

		c.init(Cipher.DECRYPT_MODE, desKey);

		cByte = c.doFinal(src);

		return new String(cByte, "UTF-8");
	}

	/**
	 * 2进制转化成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 生成签名15分钟后过期
	 * 
	 * @param username
	 * @param userId
	 * @return 加密token 2019年4月9日-上午9:38:35 YF
	 */
	public static String sign(String username, String userId) {
		try {
			// 过期时间
			Date date = new Date(System.currentTimeMillis() + ProtocolConstant.EXPIRE_TIME);
			// 私钥及加密算法
			Algorithm algorithm = Algorithm.HMAC256(ProtocolConstant.TOKEN_SECRET);
			// 设置头部信息
			Map<String, Object> header = new HashMap<String, Object>();
			header.put("typ", "JWT");
			header.put("alg", "HS256");
			// 附带username,userId信息，生成签名
			return JWT.create()
					.withHeader(header)
					.withClaim("loginName", username)
					.withClaim("userId", userId)
					.withExpiresAt(date)
					.sign(algorithm);

		} catch (UnsupportedEncodingException e) {
			return null;
		}

	}
	
	/**
	 * 检验token是否正确
	 * 
	 * @param token
	 * @return
	 * 2019年4月9日-上午9:46:27
	 * YF
	 */
	public static boolean verify(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(ProtocolConstant.TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;

		}

	}
	
	/**
	 * 
	 * 
	 * @param name ：key
	 * @param token token
	 * @return value
	 * 2019年4月9日-下午1:54:23
	 * YF
	 */
	public static String getValue(String name,String token){
		try {
			DecodedJWT jwt = JWT.decode(token);
			return StringUtil.sNull(jwt.getClaim(name));
		} catch (JWTDecodeException e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
