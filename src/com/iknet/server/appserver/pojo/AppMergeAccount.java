package com.iknet.server.appserver.pojo;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP 用户 关联帐号
 * 
 * @author luozd
 * 
 */
public class AppMergeAccount extends BaseSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mergeAccountId;
	/**
	 * 用户帐号
	 */
	private String personId;
	/**
	 * 
	 */
	private String accountPersonId;
	/**
	 * 帐号
	 */
	private String loginNo;

	/**
	 * 人员姓名
	 */
	private String personName;

	/**
	 * 关联帐号 标识 O:其他帐号;S:系统自我关联
	 */
	private String mergeAccountFlag;

	public String getMergeAccountId() {
		return mergeAccountId;
	}

	public void setMergeAccountId(String mergeAccountId) {
		this.mergeAccountId = mergeAccountId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getAccountPersonId() {
		return accountPersonId;
	}

	public void setAccountPersonId(String accountPersonId) {
		this.accountPersonId = accountPersonId;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getMergeAccountFlag() {
		return mergeAccountFlag;
	}

	public void setMergeAccountFlag(String mergeAccountFlag) {
		this.mergeAccountFlag = mergeAccountFlag;
	}

}
