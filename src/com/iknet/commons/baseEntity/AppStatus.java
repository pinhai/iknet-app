package com.iknet.commons.baseEntity;

/**
 * 管理系统与APP交互反馈消息头定义类
 * @author zzg
 *
 */
public class AppStatus {

	/**
	 * 1 业务处理成功
	 * 0 业务处理失败
	 */
	private  boolean  success ; 
	
    /**
     * 抛出信息
     */
    private String msg;
    
    /**
     * 错误码
     */
    private String errorCode;
    
    

	public AppStatus() {
		super();
	}

	public AppStatus(boolean success, String msg, String errorCode) {
		super();
		this.success = success;
		this.msg = msg;
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
