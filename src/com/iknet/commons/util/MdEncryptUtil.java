package com.iknet.commons.util;
 	
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MdEncryptUtil {
	/**
	    * 把字节数组转换成16进制字符串	    * @param bArray
	    * @return
	    */
	public static final String bytesToHexString(byte[] bArray) {
	    StringBuffer sb = new StringBuffer(bArray.length);
	    String sTemp;
	    for (int i = 0; i < bArray.length; i++) {
	     sTemp = Integer.toHexString(0xFF & bArray[i]);
	     if (sTemp.length() < 2)
	    	 sb.append(0);
	     sb.append(sTemp.toUpperCase());
	    }
	    return sb.toString();
	}
	/**
	    * MD5加密字符串，返回加密后的16进制字符串

	    * @param origin
	    * @return
	    */
	public static final String MD5EncodeToHex(String origin) { 
	    return bytesToHexString(MD5Encode(origin));
	}

	/**
	    * MD5加密字符串，返回加密后的字节数组
	    * @param origin
	    * @return
	    */
	public static final byte[] MD5Encode(String origin){
	    return MD5Encode(origin.getBytes());
	}

	/**
	    * MD5加密字节数组，返回加密后的字节数组

	    * @param bytes
	    * @return
	    */
	public static final byte[] MD5Encode(byte[] bytes){
	    MessageDigest md=null;
	    try {
	    	md = MessageDigest.getInstance("MD5");
	    	return md.digest(bytes);
	    }catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	     return new byte[0];
	    }
	}
	public static void main(String[] args) {
		System.err.println( MdEncryptUtil.MD5EncodeToHex("111111"));;
	}
}
