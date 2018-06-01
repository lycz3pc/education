package com.xhpower.education.common.response;

/**
 * 
* @ClassName: UploadFileRspVO 
* @Description: 文件上传返回VO
* @author xiong li 
* @date 2017年11月1日 下午5:23:33 
*
 */
public class UploadFileRspVO {

	/**云储存ID*/
	private String cloud_id;
	/**云储存文件名*/
	private String cloud_name;
	/**云储存路径*/
	private String qcloud_file_wwwurl;
	/**原文件名*/
	private String file_name;
	/**原文件后缀*/
	private String file_format;
	/**原文件大小 单位字节*/
	private String file_size;
	/**文件在服务器的文件夹地址*/
	private String attach_localurl;
	public String getCloud_id() {
		return cloud_id;
	}
	public void setCloud_id(String cloud_id) {
		this.cloud_id = cloud_id;
	}
	public String getCloud_name() {
		return cloud_name;
	}
	public void setCloud_name(String cloud_name) {
		this.cloud_name = cloud_name;
	}
	public String getQcloud_file_wwwurl() {
		return qcloud_file_wwwurl;
	}
	public void setQcloud_file_wwwurl(String qcloud_file_wwwurl) {
		this.qcloud_file_wwwurl = qcloud_file_wwwurl;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_format() {
		return file_format;
	}
	public void setFile_format(String file_format) {
		this.file_format = file_format;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getAttach_localurl() {
		return attach_localurl;
	}
	public void setAttach_localurl(String attach_localurl) {
		this.attach_localurl = attach_localurl;
	}

}
