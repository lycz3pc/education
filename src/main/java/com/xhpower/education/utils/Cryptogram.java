package com.xhpower.education.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author guoxc
 * @create 2009-10-22
 */
public class Cryptogram {

	private static byte[] defaultIV = {1,2,3,4,5,6,7,8};

	private static byte chr2hex(String chr) {
		if (chr.equals("0")) {
			return 0x00;
		} else if (chr.equals("1")) {
			return 0x01;
		} else if (chr.equals("2")) {
			return 0x02;
		} else if (chr.equals("3")) {
			return 0x03;
		} else if (chr.equals("4")) {
			return 0x04;
		} else if (chr.equals("5")) {
			return 0x05;
		} else if (chr.equals("6")) {
			return 0x06;
		} else if (chr.equals("7")) {
			return 0x07;
		} else if (chr.equals("8")) {
			return 0x08;
		} else if (chr.equals("9")) {
			return 0x09;
		} else if (chr.equals("A")) {
			return 0x0a;
		} else if (chr.equals("B")) {
			return 0x0b;
		} else if (chr.equals("C")) {
			return 0x0c;
		} else if (chr.equals("D")) {
			return 0x0d;
		} else if (chr.equals("E")) {
			return 0x0e;
		} else if (chr.equals("F")) {
			return 0x0f;
		}
		return 0x00;
	}

	public static byte[] HexStringToByteArray(String s) {
		byte[] buf = new byte[s.length() / 2];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) (chr2hex(s.substring(i * 2, i * 2 + 1)) * 0x10 + chr2hex(s
					.substring(i * 2 + 1, i * 2 + 2)));
		}
		System.out.println("buf!!!" + buf);
		return buf;
	}

	
	/**
	 * Decrypt the encrypted data with the key.
	 * @param strData
	 * @return strResult
	 * @throws Exception
	 */
	public static String getBase64HashString(String str) throws Exception{
		
		byte[] testSrc = str.getBytes();
		MessageDigest alga = MessageDigest.getInstance("SHA-1");
		alga.update(testSrc);
		byte[] digesta = alga.digest();
		return Base64.getBASE64_byte(digesta);
	}
	/**
	 * Encrypt the data by the key.
	 * @param OriSource
	 * @return strResult
	 * @throws Exception
	 */
	public static String encryptByKey(String OriSource, String key) throws Exception {
		
		String strResult = "";
		try {
			
			Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKeySpec myKey = new SecretKeySpec(HexStringToByteArray(key),
					"DESede");

			IvParameterSpec ivspec = new IvParameterSpec(defaultIV);
			
			System.out.println("******getIV:"+ivspec.getIV());
			
			c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);

			byte[] testSrc = OriSource.getBytes();
			byte[] encoded = c3des.doFinal(testSrc);
			
			strResult = Base64.getBASE64_byte(encoded);
			
		} catch (Exception e) {
			strResult="";
			System.out.println("Encrypt failure!!!");
		}

		return strResult;
	}
	
	/**
	 * Decrypt the encrypted data with the key.
	 * @param strData
	 * @return strResult
	 * @throws Exception
	 */
	public static String getAuthenicator(String sourceStr,String key) throws Exception{
		
		String strResult = "";
		try {
			
			Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			SecretKeySpec myKey = new SecretKeySpec(HexStringToByteArray(key),
					"DESede");

			IvParameterSpec ivspec = new IvParameterSpec(defaultIV);
			c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
			
			
			byte[] testSrc = sourceStr.getBytes();
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
			alga.update(testSrc);
			byte[] digesta = alga.digest();

			byte[] encoded = c3des.doFinal(digesta);
			strResult = Base64.getBASE64_byte(encoded);
			
		} catch (Exception e) {
			strResult="";
			System.out.println("Decrypt failure!!!" + e.getMessage());
		}

		return strResult;
	}
	
	public static String getBASE64(String s) { 
		if (s == null) return null; 
		return (new sun.misc.BASE64Encoder()).encode( s.getBytes() ); 
		} 

	public static void main(String args[]) throws Exception {

		String ReturnURL = "23";
		String Key = "86A659D3035B51B1B66DF3139F1AEC33F6651334F1E65170";
		String encryptThreeDESECB = encryptThreeDESECB(ReturnURL,Key);
		System.out.println(encryptThreeDESECB);
		System.out.println(decryptThreeDESECB(encryptThreeDESECB,Key));
		

	}
	
	
	
	public static String encryptThreeDESECB(String src,String key) throws Exception
	{
	    DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    
	    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, securekey);
	    byte[] b=cipher.doFinal(src.getBytes());
	    
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");
	    
	}

	//3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	public static String decryptThreeDESECB(String src,String key) throws Exception
	{
	    //--通过base64,将字符串转成byte数组
	    BASE64Decoder decoder = new BASE64Decoder();
	    byte[] bytesrc = decoder.decodeBuffer(src);
	    //--解密的key
	    DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
	    SecretKey securekey = keyFactory.generateSecret(dks);
	    
	    //--Chipher对象解密
	    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, securekey);
	    byte[] retByte = cipher.doFinal(bytesrc);
	    
	    return new String(retByte);
	}
	
	/**
	 * Decrypt the encrypted data with the key.
	 * @param strData
	 * @return strResult
	 * @throws Exception
	 */
	public static String decryptByKey(String encryptedData, String key) throws Exception {
		Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		SecretKeySpec myKey = new SecretKeySpec(HexStringToByteArray(key),
				"DESede");

		IvParameterSpec ivspec = new IvParameterSpec(defaultIV);
		c3des.init(Cipher.DECRYPT_MODE, myKey, ivspec);

		byte[] s= Base64.getByteArrFromBase64(encryptedData);
		byte[] encoded = c3des.doFinal(s);
		return new String(encoded);
	}
	

}