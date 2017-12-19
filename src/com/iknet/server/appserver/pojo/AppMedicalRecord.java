package com.iknet.server.appserver.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP病例对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppMedicalRecord extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * 病例id
	 */
	private String medicalRecordId;

	/**
	 * 病例号
	 */
	private String medicalRecordNo;

	/**
	 * 用户ID
	 */
	private String personId;

	/**
	 * 姓名
	 */
	private String personName;

	/**
	 * 血压舒张值
	 */
	private BigDecimal checkDiastole;

	/**
	 * 血压收缩值
	 */
	private BigDecimal checkShrink;

	/**
	 * 心率值
	 */
	private BigDecimal checkHeartRate;

	/**
	 * 检测时间
	 */
	private String uploadDate;

	/**
	 * 设备类型
	 */
	private String equipType;

	/**
	 * 检查结果 1表示正常，0表示不正常
	 */
	private String checkResult;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 病例备注
	 */
	private String medicalRecordRemark;
	/**
	 * 同步第三方标识 N:未同步第三方;Y:已同步第三方
	 */
	private String threeAutoFlag;

	/**
	 * 心率测试结果 ，是否丌齐 ，“ 1 ，齐； 2 ，不齐”
	 */
	private String checkHeartResult;
	/**
	 * 心跳次数判定结果， “ 1 ， 心跳过慢 ； 2 ， 心跳正常； 3 ，心 跳过快
	 */
	private String checkHeartBeat;
	/**
	 * 血压测量 结果， “ 1 ， 低血压 ； 2 ， 理想血压 ； 3 ， 正常血压 ； 4 ， 正常高值 ； 5 ， 轻度高血压 ； 6 ，
	 * 中度高血压 ； 7 ， 重度 高血压 ”
	 */
	private String checkBloodResult;

	/**
	 * 测量数据来源 I:IOS;A:Android;W:Wireless
	 */
	private String checkAutoFrom;
	/**
	 * 自动测量标识 Y:自动测量 N:手动输入测量
	 */
	private String checkAutoFlag;
	/**
	 * 蓝牙名称
	 */
	private String bluetoothName;

	/**
	 * 无线用户等级 A:A用户 B:B用户 O:其他途径用户
	 */
	private String aOrB;

	public String getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(String medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}

	public String getMedicalRecordNo() {
		return medicalRecordNo;
	}

	public void setMedicalRecordNo(String medicalRecordNo) {
		this.medicalRecordNo = medicalRecordNo;
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

	public BigDecimal getCheckDiastole() {
		return checkDiastole;
	}

	public void setCheckDiastole(BigDecimal checkDiastole) {
		this.checkDiastole = checkDiastole;
	}

	public BigDecimal getCheckShrink() {
		return checkShrink;
	}

	public void setCheckShrink(BigDecimal checkShrink) {
		this.checkShrink = checkShrink;
	}

	public BigDecimal getCheckHeartRate() {
		return checkHeartRate;
	}

	public void setCheckHeartRate(BigDecimal checkHeartRate) {
		this.checkHeartRate = checkHeartRate;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getEquipType() {
		return equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
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

	public String getMedicalRecordRemark() {
		return medicalRecordRemark;
	}

	public void setMedicalRecordRemark(String medicalRecordRemark) {
		this.medicalRecordRemark = medicalRecordRemark;
	}

	public String getThreeAutoFlag() {
		return threeAutoFlag;
	}

	public void setThreeAutoFlag(String threeAutoFlag) {
		this.threeAutoFlag = threeAutoFlag;
	}

	public String getCheckHeartResult() {
		return checkHeartResult;
	}

	public void setCheckHeartResult(String checkHeartResult) {
		this.checkHeartResult = checkHeartResult;
	}

	public String getCheckHeartBeat() {
		return checkHeartBeat;
	}

	public void setCheckHeartBeat(String checkHeartBeat) {
		this.checkHeartBeat = checkHeartBeat;
	}

	public String getCheckBloodResult() {
		return checkBloodResult;
	}

	public void setCheckBloodResult(String checkBloodResult) {
		this.checkBloodResult = checkBloodResult;
	}

	public String getCheckAutoFrom() {
		return checkAutoFrom;
	}

	public void setCheckAutoFrom(String checkAutoFrom) {
		this.checkAutoFrom = checkAutoFrom;
	}

	public String getCheckAutoFlag() {
		return checkAutoFlag;
	}

	public void setCheckAutoFlag(String checkAutoFlag) {
		this.checkAutoFlag = checkAutoFlag;
	}

	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}

	public String getaOrB() {
		return aOrB;
	}

	public void setaOrB(String aOrB) {
		this.aOrB = aOrB;
	}

}
