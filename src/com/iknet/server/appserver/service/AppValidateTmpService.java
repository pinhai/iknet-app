/**
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月3日 上午9:47:12
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
package com.iknet.server.appserver.service;

import java.util.Map;

import com.iknet.server.appserver.pojo.AppValidateTmp;

/**
 * 发送 验证码 临时表 Copyright: Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights
 * Reserved. Date: 2014年11月3日 上午9:47:41 Author: luozd Version: IKNET V:1.0.0
 * Description: Initialize
 */
public interface AppValidateTmpService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppValidateTmpMapper.";

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppValidateTmp(AppValidateTmp vo) throws Exception;

	/**
	 * 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int queryAppValidateTmpTotalByMap(Map<String, Object> map)
			throws Exception;

	/**
	 * 根据 map 查询 validateCode 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryLastValidateCodeByMap(Map<String, Object> map)
			throws Exception;

	/**
	 * 检查 无线激活码 是否有效
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryCountByAppValidateTmp(AppValidateTmp vo) throws Exception;
}