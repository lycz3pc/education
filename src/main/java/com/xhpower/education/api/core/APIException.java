package com.xhpower.education.api.core;



/**
 * 
* @ClassName: APIException 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author lisf 
* @date 2016年9月9日 下午2:08:27 
*
 */
public class APIException extends Exception {
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 9216901466083993314L;
	
	protected int code = APICode.ERROR;
	/**
	 * 控制输出  
	 * 5:系统错误
	 * 4:程序错误 
	 * 3:逻辑错误 
	 * 2:控制输出 
	 * 1:验证输出  
	 * 0:无关输出
	 */
	protected int level = 1;
	
	public APIException(int code) {
		this(code, "");
	}
	
	public APIException(int code, Throwable cause) {
		this(code, "", cause);
	}
	
	public APIException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public APIException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public APIException setCode(int code) {
		this.code = code;
		return this;
	}

	public int getLevel() {
		return level;
	}

	public APIException setLevel(int level) {
		this.level = level;
		return this;
	}
}
