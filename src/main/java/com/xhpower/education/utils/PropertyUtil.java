package com.xhpower.education.utils;
import java.io.InputStream;
import java.util.Properties;
/**
 * 
* @ClassName: PropertyUtil 
* @Description: 配置文件加载工具
* @author lisf 
* @date 2016年9月12日 下午1:53:33 
*
 */
public class PropertyUtil
{
	
	
	public static String CONFIG = "/config.properties";
	/**
	 * 根据配置文件名称加载配置
	 * 
	 * @param propsName
	 * @return
	 */
	public static Properties getProperties(String propsName)
	{
		Properties props = new Properties();
		InputStream is;
		try
		{
			is = PropertyUtil.class.getClassLoader().getResourceAsStream(
					propsName);
			props.load(is);
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return props;
	}
	
	/**
	 * 根据配置文件加载值
	 * 
	 * @param propsName
	 * @return
	 */
	public static String getPropertiesValue(String propsName,String key)
	{
		Properties props = new Properties();
		InputStream is;
		try
		{
			is = PropertyUtil.class.getClassLoader().getResourceAsStream(
					propsName);
			props.load(is);
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return props.getProperty(key);
	}
	
	/**
	 * 从默认配置文件中加载值
	 * 
	 * @param propsName
	 * @return
	 */
	public static String getPropertiesValue(String key)
	{
		Properties props = new Properties();
		InputStream is;
		try
		{
			is = PropertyUtil.class.getClassLoader().getResourceAsStream(
					CONFIG);
			props.load(is);
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return props.getProperty(key);
	}
}
