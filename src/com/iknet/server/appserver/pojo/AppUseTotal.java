package com.iknet.server.appserver.pojo;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP测试次数，异常数，正常数对应实体类
 * 
 * @author Administrator
 * 
 */
public class AppUseTotal extends BaseSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String personId;

	/**
	 * 测试总数
	 */
	private int testTotal;

	/**
	 * 测试正常总数
	 */
	private int testNormalTotal;

	/**
	 * 测试异常整数
	 */
	private int testAbnormalTotal;
	/**
	 * 三高仪治疗次数
	 */
	private int testThreeHeightTotal;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public int getTestTotal() {
		return testTotal;
	}

	public void setTestTotal(int testTotal) {
		this.testTotal = testTotal;
	}

	public int getTestNormalTotal() {
		return testNormalTotal;
	}

	public void setTestNormalTotal(int testNormalTotal) {
		this.testNormalTotal = testNormalTotal;
	}

	public int getTestAbnormalTotal() {
		return testAbnormalTotal;
	}

	public void setTestAbnormalTotal(int testAbnormalTotal) {
		this.testAbnormalTotal = testAbnormalTotal;
	}

	public int getTestThreeHeightTotal() {
		return testThreeHeightTotal;
	}

	public void setTestThreeHeightTotal(int testThreeHeightTotal) {
		this.testThreeHeightTotal = testThreeHeightTotal;
	}

}
