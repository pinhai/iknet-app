package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP 社区用户
 * 
 * @author luozd
 * 
 */
public class AppCommunity extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String communityId;

	/**
	 * 社区代码
	 */
	private String communityCode;

	/**
	 * 用户ID
	 */
	private String personId;

	/**
	 * 用户名称
	 */
	private String personName;

	/**
	 * 年龄
	 */
	private int age;

	/**
	 * 性别 M:男;W:女
	 */
	private String sex;

	/**
	 * 用户绑定时间
	 */
	private Date createTime;
	/**
	 * 登录帐号
	 */
	private String loginNo;

	/**
	 * 身高
	 */
	private double height;

	/**
	 * 体重
	 */
	private double weight;

	/**
	 * 地址
	 */
	private String address;
	/**
	 * 用户所属社区名称
	 */
	private String communityCodeName;

	/**
	 * 身份证号码
	 */
	private String idCard;

	public String getCommunityId() {
		return communityId;
	}

	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommunityCodeName() {
		return communityCodeName;
	}

	public void setCommunityCodeName(String communityCodeName) {
		this.communityCodeName = communityCodeName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
