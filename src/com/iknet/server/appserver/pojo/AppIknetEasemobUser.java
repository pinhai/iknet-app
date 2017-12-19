package com.iknet.server.appserver.pojo;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * iknet-环信 好友 用户 VO
 * 
 * @author luozd
 * 
 */
public class AppIknetEasemobUser extends BaseSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8518225538593156191L;
	/**
	 * 环信 username
	 */
	private String personId;

	/**
	 * 环信 昵称
	 */
	private String nickName;

	/**
	 * 好友头像
	 */
	private String imageUrl;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
