package com.iknet.server.appserver.pojo;

import java.math.BigDecimal;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP 三高仪测量 对应实体类
 * 
 * @author luozd
 * 
 */
public class AppThreeHeightRecord extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * 三高仪档位
	 */
	private String threeHeightType;

	/**
	 * 三高仪测量时长
	 */
	private BigDecimal threeHeightTimeLength;

	/**
	 * 测量时间
	 */
	private String threeHeightUploadDate;

	/**
	 * 检测时间
	 */
	private String uploadDate;

	/**
	 * 创建时间
	 */
	private String createTime;

	public String getThreeHeightType() {
		return threeHeightType;
	}

	public void setThreeHeightType(String threeHeightType) {
		this.threeHeightType = threeHeightType;
	}

	public BigDecimal getThreeHeightTimeLength() {
		return threeHeightTimeLength;
	}

	public void setThreeHeightTimeLength(BigDecimal threeHeightTimeLength) {
		this.threeHeightTimeLength = threeHeightTimeLength;
	}

	public String getThreeHeightUploadDate() {
		return threeHeightUploadDate;
	}

	public void setThreeHeightUploadDate(String threeHeightUploadDate) {
		this.threeHeightUploadDate = threeHeightUploadDate;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
