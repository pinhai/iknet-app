package com.iknet.commons.baseEntity;
/**
 * 
     * IKNET后台管理系统  
     * 版本信息：  1.0 
     * 功能描述： 返回类数据集合 
     * zzg by 2014-9-4 
     *
 */
public class ResultData {
	
	/**
	 * 成功或者失败
	 */
	private boolean success ;
	
	/**
	 * 错误代码详见错误代码表
	 * 000000为执行成功
	 * 000001为session过期
	 */
	private  String errorCode;

	/**
	 * 返回消息
	 */
	private String  msg;
	
	/**
	 * 返回数据实体
	 */
	private Object  data;

	public ResultData(){};
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ResultData(boolean success, String errorCode, String msg,
			Object data) {
		super();
		this.success = success;
		this.errorCode = errorCode;
		this.msg = msg;
		this.setData(data);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	

}
