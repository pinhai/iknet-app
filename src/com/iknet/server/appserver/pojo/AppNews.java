package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP对应 消息实体类
 * 
 * @author Administrator
 * 
 */
public class AppNews extends BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消息id
	 */
	private long newsId;

	/**
	 * 消息所属用户
	 */
	private String personId;

	/**
	 * 消息标题
	 */
	private String newsTitle;

	/**
	 * 消息内容
	 */
	private String newContent;

	/**
	 * 消息创建时间
	 */
	private Date createTime;

	/**
	 * 消息修改时间
	 */
	private Date updateTime;

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewContent() {
		return newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
