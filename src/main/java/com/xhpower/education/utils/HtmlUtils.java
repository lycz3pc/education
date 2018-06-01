package com.xhpower.education.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xhpower.plugins.common.utils.StringHelper;

/** 
 * @ClassName: HtmlUtils 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author lisf 
 * @date 2017年2月15日 上午10:10:43 
 *  
 */
public class HtmlUtils {
	public static String getHtml(String htmlStr,String path) {
		if(StringHelper.isStringEmpty(path))
		   path = PropertyUtil.getPropertiesValue("IMGBASEPATH_ADMIN");
		Matcher m1 = Pattern.compile("<[(img)|(embed)][^>]*src[^>]*>",
				Pattern.CASE_INSENSITIVE).matcher(htmlStr);
		while (m1.find()) {
			String srcStr = m1.group();
			System.out.println(srcStr);
			String img = "";
			// String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址

			String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";

			Pattern p_image = Pattern.compile(regEx_img,
					Pattern.CASE_INSENSITIVE);
			Matcher m_image = p_image.matcher(srcStr);
			while (m_image.find()) {
				img = img + "," + m_image.group();
				// Matcher m =
				// Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img);
				// //匹配src
				Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)")
						.matcher(img);
				while (m.find()) {
					// pics.add("<img src=\""+m.group(1)+"\" width=\"100%\" >");
					if (m.group(1).startsWith("http"))
						htmlStr = htmlStr
								.replace(
										srcStr,
										"<img src=\""
												+ m.group(1)
//												+ "\" onclick=\"javascript:document.location.href ='"
//												+ m.group(1)
												+ "\" width=\"100%\" >");
					// htmlStr = htmlStr.replace(srcStr,
					// "<img src=\""+m.group(1)+"\" width=\"100%\" >");
					else {
						if(m.group(1).startsWith("../")){
							String imgUrl = m.group(1).replace("../", "");
							htmlStr = htmlStr.replace(srcStr,
									"<img src=\""
											+ path
											+ imgUrl
//											+ "\" onclick=\"javascript:document.location.href ='"+path
//											+ imgUrl
											+ "\" width=\"100%\" >");
						}else{
							htmlStr = htmlStr
							.replace(
									srcStr,
									"<img src=\""
											+ path
											+ m.group(1)
//											+ "\" onclick=\"javascript:document.location.href ='"+path
//											+ m.group(1)
											+ "\" width=\"100%\" >");
						}
						
					}
				}
			}
		}
		return htmlStr;
	}
}
