package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * SIM 无线 测量数据
 * 
 * @author luozd
 * 
 */
public class AppReviceData extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String personId;

	/**
	 * SIM卡
	 */
	private String deviceSim;

	/**
	 * 检测时间
	 */
	private String uploadDate;

	/** 收缩压 **/
	private String highPressure;

	/** 舒张压 **/
	private String lowVoltage;

	/** 心率 **/
	private String pulse;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/** 身份证号码 **/
	private String idCard;

	/**
	 * 绑定标识
	 */
	private String deviceFlag;

	/**
	 * 激活码
	 */
	private String validateCode;

	
	/**设备到期时间**/
	private String expireTime;
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getDeviceSim() {
		return deviceSim;
	}

	public void setDeviceSim(String deviceSim) {
		this.deviceSim = deviceSim;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getHighPressure() {
		return highPressure;
	}

	public void setHighPressure(String highPressure) {
		this.highPressure = highPressure;
	}

	public String getLowVoltage() {
		return lowVoltage;
	}

	public void setLowVoltage(String lowVoltage) {
		this.lowVoltage = lowVoltage;
	}

	public String getPulse() {
		return pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getDeviceFlag() {
		return deviceFlag;
	}

	public void setDeviceFlag(String deviceFlag) {
		this.deviceFlag = deviceFlag;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}
