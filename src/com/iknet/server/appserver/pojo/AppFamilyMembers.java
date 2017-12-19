package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP端对应家庭成员实体类
 * 
 * @author Administrator
 * 
 */
public class AppFamilyMembers extends BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 家属表主键
	 */
	private String membersId;
	/**
	 * 用户 Id
	 */
	private String personId;

	/**
	 * 成员姓名
	 */
	private String membersName;

	/**
	 * 家属用户名
	 */
	private String userName;

	/**
	 * 家属用户电话
	 */
	private String membersMobile;

	/**
	 * 添加家属时候同时生成一个对应的账号，这个字段表示此账号id
	 */
	private String membersUserId;

	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 修改日期
	 */
	private Date updateTime;
	/**
	 * 成员关系
	 */
	private String membersRelation;
	/**
	 * 有效标识
	 */
	private String validFlag;
	/**
	 * 图片路径
	 */
	private String imageUrl;
	/**
	 * 邀请码
	 */
	private String validateCode;

	public String getMembersId() {
		return membersId;
	}

	public void setMembersId(String membersId) {
		this.membersId = membersId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getMembersName() {
		return membersName;
	}

	public void setMembersName(String membersName) {
		this.membersName = membersName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMembersMobile() {
		return membersMobile;
	}

	public void setMembersMobile(String membersMobile) {
		this.membersMobile = membersMobile;
	}

	public String getMembersUserId() {
		return membersUserId;
	}

	public void setMembersUserId(String membersUserId) {
		this.membersUserId = membersUserId;
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

	public String getMembersRelation() {
		return membersRelation;
	}

	public void setMembersRelation(String membersRelation) {
		this.membersRelation = membersRelation;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

}
