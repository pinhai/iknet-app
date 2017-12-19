package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP用户对应实体类
 * 
 * @author zzg
 * 
 */
public class AppUser extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * id，唯一标识
	 */
	private String personId;

	/**
	 * 登录名 [mobilePhone|email 皆可 登录]
	 */
	private String loginNo;

	/**
	 * 移动电话
	 */
	private String mobilePhone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 登录密码
	 */
	private String loginPwd;

	/**
	 * 人员姓名
	 */
	private String personName;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 年龄
	 */
	private int age;

	/**
	 * 省会
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;
	/**
	 * 地区
	 */
	private String region;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 是否结婚
	 */
	private String isMarry;

	/**
	 * 座机电话
	 */
	private String phone;

	/**
	 * 病史
	 */
	private String medicalHistory;

	/**
	 * 注册来源
	 */
	private String registerSource;

	/**
	 * 有效标识
	 */
	private String validFlag;

	/**
	 * 身高
	 */
	private double height;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 修改日期
	 */
	private Date updateTime;

	private String imageUrl;

	private String oldLoginPwd;

	/**
	 * 体重
	 */
	private double weight;

	/**
	 * 过敏症状
	 */
	private String allergySymptoms;

	/**
	 * 用药史
	 */
	private String drugHistory;

	/**
	 * 生日
	 */
	private String dateBirth;

	/**
	 * 显示名
	 */
	private String showName;

	/**
	 * 验证码
	 */
	private String validateCode;

	/**
	 * 登录来源
	 */
	private String loginSource;

	/**
	 * 登录返回ID
	 */
	private String loginReturnId;
	/**
	 * 第三方 授权令牌
	 */
	private String accessToken;
	/**
	 * 用户 平台 标识
	 */
	private String userFromFlag;
	/**
	 * 用户所属社区代码
	 */
	private String communityCode;
	/**
	 * 用户所属社区名称
	 */
	private String communityCodeName;
	/**
	 * 联系电话
	 */
	private String contactMobile;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIsMarry() {
		return isMarry;
	}

	public void setIsMarry(String isMarry) {
		this.isMarry = isMarry;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getRegisterSource() {
		return registerSource;
	}

	public void setRegisterSource(String registerSource) {
		this.registerSource = registerSource;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOldLoginPwd() {
		return oldLoginPwd;
	}

	public void setOldLoginPwd(String oldLoginPwd) {
		this.oldLoginPwd = oldLoginPwd;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getAllergySymptoms() {
		return allergySymptoms;
	}

	public void setAllergySymptoms(String allergySymptoms) {
		this.allergySymptoms = allergySymptoms;
	}

	public String getDrugHistory() {
		return drugHistory;
	}

	public void setDrugHistory(String drugHistory) {
		this.drugHistory = drugHistory;
	}

	public String getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(String dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserFromFlag() {
		return userFromFlag;
	}

	public void setUserFromFlag(String userFromFlag) {
		this.userFromFlag = userFromFlag;
	}

	public String getCommunityCode() {
		return communityCode;
	}

	public void setCommunityCode(String communityCode) {
		this.communityCode = communityCode;
	}

	public String getCommunityCodeName() {
		return communityCodeName;
	}

	public void setCommunityCodeName(String communityCodeName) {
		this.communityCodeName = communityCodeName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

}
