package com.iknet.server.appserver.pojo;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * iknet-环信 用户 VO
 * 
 * @author luozd
 * 
 */
public class AppIknetUser extends BaseSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8518225538593156191L;
	/**
	 * 环信 username
	 */
	private String personId;

	/**
	 * 用户存在 标识
	 */
	private boolean iknetUserFlag;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public boolean isIknetUserFlag() {
		return iknetUserFlag;
	}

	public void setIknetUserFlag(boolean iknetUserFlag) {
		this.iknetUserFlag = iknetUserFlag;
	}

}
