package com.xhpower.education.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Encrypt {  
	  
	/**
	 * md5????��?
	 * 
	 * @param plainText
	 * @return string
	 */
	public static String encodeMD5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(plainText.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(encodeMD5("version=1.0&orderID=123465789146&submitTime=20170302095747&partnerID=11000001614&payType=1&noticeUrl=http://admin.taiyangcaifu.com/admin/index&charset=1&signType=0&realName=陈涛&idNumber=430903199309226016&remark=新生支付&key=kvreCBCIVBuDKqC74qeLN3P7oKRxAxrDIHb+hUdZMKSwi0GUTfRBb4UT55l2nWrMlZk6ndbPsHc++mIn+eHg2UbCWuPQ9kstgqfYaI2GGlkuTbZ5gZNZeoRLF2evPQwmbtZ6NMmM3ujTrwvx8mn+28SZyVuSQWyD5K/NN7A/huyazQC/Fs5zGK2c/EtB27HW/UyvOaVBmZ6WKDZoP/rgFy09"));
	}
	
      
}  