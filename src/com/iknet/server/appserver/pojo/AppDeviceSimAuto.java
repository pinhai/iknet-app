package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * 动态血压计设置参数
 * 
 * @author luozd
 * 
 */
public class AppDeviceSimAuto extends BaseSerializable {

	/**
     * 
     */
	private static final long serialVersionUID = -5551959633333203439L;

	/**
	 * 设备编号
	 */
	private String deviceSim;

	/**
	 * 起始时
	 */
	private int startHours;

	/**
	 * 起始分钟
	 */
	private int startMinutes;

	/**
	 * 结束时
	 */
	private int endHours;

	/**
	 * 结束分钟
	 */
	private int endMinutes;

	/**
	 * 间隔分钟
	 */
	private int intervalTime;

	/**
	 * 绑定时间
	 */
	private Date createTime;
	/**
	 * 变更时间
	 */
	private Date updateTime;

	/**
	 * 设置状态
	 */
	private String useFlag;

	public String getDeviceSim() {
		return deviceSim;
	}

	public void setDeviceSim(String deviceSim) {
		this.deviceSim = deviceSim;
	}

	public int getStartHours() {
		return startHours;
	}

	public void setStartHours(int startHours) {
		this.startHours = startHours;
	}

	public int getStartMinutes() {
		return startMinutes;
	}

	public void setStartMinutes(int startMinutes) {
		this.startMinutes = startMinutes;
	}

	public int getEndHours() {
		return endHours;
	}

	public void setEndHours(int endHours) {
		this.endHours = endHours;
	}

	public int getEndMinutes() {
		return endMinutes;
	}

	public void setEndMinutes(int endMinutes) {
		this.endMinutes = endMinutes;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
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

	public String getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

}
