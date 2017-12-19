package com.iknet.server.appserver.pojo;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP名医表对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppDoctor extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 医生姓名
	 */
	private String doctorName;

	/**
	 * 医生性别
	 */
	private String sex;

	/**
	 * 职称
	 */
	private String title;

	/**
	 * 擅长疾病
	 */
	private String beGoodAt;

	/**
	 * 医生简介
	 */
	private String introduction;

	/**
	 * 是否显示 1显示 0不显示 用于判断是否显示医院
	 */
	private String isShow;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 科室名称
	 */
	private String departmentName;

	/**
	 * 点赞数
	 */
	private int niceTotal;

	/**
	 * 所属分类
	 */
	private long typeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBeGoodAt() {
		return beGoodAt;
	}

	public void setBeGoodAt(String beGoodAt) {
		this.beGoodAt = beGoodAt;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getNiceTotal() {
		return niceTotal;
	}

	public void setNiceTotal(int niceTotal) {
		this.niceTotal = niceTotal;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

}
