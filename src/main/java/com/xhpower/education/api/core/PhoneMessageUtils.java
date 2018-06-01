package com.xhpower.education.api.core;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneMessageUtils {

	private final static int OFFSET = 538309;
	
	public static  String MobileVfCode() {
		long seed = System.currentTimeMillis() + OFFSET;
		SecureRandom secureRandom = null; // 安全随机�?
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(seed);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String codeList = "1234567890"; // 验证码数字取值范�?
		String sRand = ""; // 定义�?个验证码字符串变�?

		for (int i = 0; i < 6; i++) {
			int code = secureRandom.nextInt(codeList.length() - 1); // 随即生成�?�?0-9之间的整�?
			String rand = codeList.substring(code, code + 1);
			sRand += rand; // 将生成的随机数拼成一个六位数验证�?
		}
		return sRand; // 返回�?个六位随机数验证�?

	}
	
	//随机生成十位数ValidCode码，用于邮件的验�?
	public static String generateMailValidCode() {
		long seed = System.currentTimeMillis() + OFFSET;
		SecureRandom secureRandom = null; // 安全随机�?
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(seed);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String codeList = "ABCDEFGHIJKLMNOPQRSTUVWXYZabckefghijklmnopqrstuvwxyz1234567890"; // 验证码数字取值范�?
		String sRand = new String(""); // 定义�?个验证码字符串变�?

		for (int i = 0; i < 10; i++) {
			int code = secureRandom.nextInt(codeList.length() - 1); // 随即生成�?个整�?
			String rand = codeList.substring(code, code + 1);
			sRand += rand; // 将生成的随机数拼成一个十位数验证�?
		}
		return sRand; // 返回�?个六位随机数验证�?
	}
	
	//�?查是不是符合为合法的中国的手机号�?
	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null) {
			return false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
