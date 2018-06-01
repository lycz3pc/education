package com.xhpower.education.api.core;


/**
 */
public class APIBaseResponse implements java.io.Serializable{
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = -2238559398475854245L;
	
	/**
	 * 结果
	 */
	protected int result = APICode.SUCCESS;
	/**
	 * 服务器时间
	 * */
	protected long time;
	/**
	 * 定义异常
	 */
	protected int code = APICode.SUCCESS;
	/**
	 * 消息
	 */
	protected String message = "";
	
	public APIBaseResponse() {
	}
	
	public APIBaseResponse(int result, int code) {
		this.result = result;
		this.code = code;
	}
	
	public APIBaseResponse(int result, int code, String message) {
		this.result = result;
		this.code = code;
		this.message = message;
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public long getTime() {
		return System.currentTimeMillis();
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
