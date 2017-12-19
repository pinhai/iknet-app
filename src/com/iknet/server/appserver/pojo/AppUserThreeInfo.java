package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP用户对应实体类
 * 
 * @author zzg
 * 
 */
public class AppUserThreeInfo extends BaseSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 人员ID
	 */
	private String personId;
	/**
	 * 授权令牌
	 */
	private String accessToken;

	private String loginSource;
	/**
	 * 第三方登录返回ID
	 */
	private String loginReturnId;

	/**
	 * 在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。
	 */
	private String refreshToken;
	/**
	 * 更新授权令牌时间
	 */
	private Date lastAccessDate;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(String loginSource) {
		this.loginSource = loginSource;
	}

	public String getLoginReturnId() {
		return loginReturnId;
	}

	public void setLoginReturnId(String loginReturnId) {
		this.loginReturnId = loginReturnId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

}
