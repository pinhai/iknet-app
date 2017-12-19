package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP意见反馈表对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppFeedBack extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * 反馈意见id
	 */
	private long feedBackId;

	/**
	 * 反馈人id
	 */
	private String personId;

	/**
	 * 反馈人姓名
	 */
	private String personName;

	/**
	 * 反馈内容
	 */
	private String feedBackContent;

	/**
	 * 反馈时间
	 */
	private Date feedBackDate;

	/**
	 * 是否处理 1表示处理0表示没处理
	 */
	private String isDispose;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	public long getFeedBackId() {
		return feedBackId;
	}

	public void setFeedBackId(long feedBackId) {
		this.feedBackId = feedBackId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getFeedBackContent() {
		return feedBackContent;
	}

	public void setFeedBackContent(String feedBackContent) {
		this.feedBackContent = feedBackContent;
	}

	public Date getFeedBackDate() {
		return feedBackDate;
	}

	public void setFeedBackDate(Date feedBackDate) {
		this.feedBackDate = feedBackDate;
	}

	public String getIsDispose() {
		return isDispose;
	}

	public void setIsDispose(String isDispose) {
		this.isDispose = isDispose;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
