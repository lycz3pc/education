package com.xhpower.education.utils;

import java.util.regex.Pattern;

public final class FileHelper {
	private static Pattern fileSuffixPattern = Pattern.compile("[a-bA-B]+");
	/**
	 * 获取文件名的后缀
	 * @Author lisf
	 * @Version 
	 * @param filename
	 * @param defaultSuffix 默认后缀
	 * @return
	 */
	public static String getSuffix(String filename, String defaultSuffix) {
		int find = filename.lastIndexOf('.') + 1;
		if (find < 1)
			return defaultSuffix;
		String suffix = filename.substring(find).toLowerCase();
		return fileSuffixPattern.matcher(suffix).matches() ? defaultSuffix: suffix;
	}
	
	/**
	 * 
	* @Title: getSuffix 
	* @Description: 获取文件名后缀  如过没有取到后缀返回null 
	* @param @param filename
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getSuffix(String filename) {
		return getSuffix(filename, null);
	}
	
	/**
	 * 是否是图片
	 * @Author lisf
	 * @Version 
	 * @param filetype 文件类型的字符串
	 * @return
	 */
	public static boolean isImager(String contentType) {
		return contentType.startsWith("image/");
	}
	/**
	 * 是否是视频
	 * @Author lisf
	 * @param contentType
	 * @return
	 */
	public static boolean isVideo(String contentType) {
		return contentType.startsWith("video/");
	}
	
	/**
	 * 
	* @Title: isAudio 
	* @Description: 是否是音频 
	* @param @param contentType
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean isAudio(String contentType) {
		return contentType.startsWith("audio/");
	}
}
