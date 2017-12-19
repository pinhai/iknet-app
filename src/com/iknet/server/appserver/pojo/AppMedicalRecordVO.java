/**
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月4日 下午2:16:44
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
package com.iknet.server.appserver.pojo;

import java.util.List;

/**
 * 批量 病例 转换 VO Copyright: Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights
 * Reserved. Date: 2014年11月4日 下午3:42:54 Author: luozd Version: IKNET V:1.0.0
 * Description: Initialize
 */
public class AppMedicalRecordVO {
	private List<AppMedicalRecord> list;
	private List<AppUseTotal> userTotallist;

	/**
	 * 测试正常总数
	 */
	private int testNormalTotal;

	/**
	 * 测试异常整数
	 */
	private int testAbnormalTotal;
	/**
	 * 三高仪测量数
	 */
	private int testThreeHeightTotal;
	private String personId;

	public List<AppMedicalRecord> getList() {
		return list;
	}

	public void setList(List<AppMedicalRecord> list) {
		this.list = list;
	}

	public List<AppUseTotal> getUserTotallist() {
		return userTotallist;
	}

	public void setUserTotallist(List<AppUseTotal> userTotallist) {
		this.userTotallist = userTotallist;
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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

}
