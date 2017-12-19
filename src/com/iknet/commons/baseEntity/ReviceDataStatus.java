package com.iknet.commons.baseEntity;


/**
 * 接收第三方数据推送状态实体类
 * @author Administrator
 *
 */
public class ReviceDataStatus {

	
	private String statusCode;
	
	private String desc;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
