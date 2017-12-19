package com.iknet.server.appserver.service;

import com.iknet.server.appserver.pojo.AppUseTotal;

/**
 * APP测试次数，异常数，正常数
 * 
 * @author luozd
 * 
 */
public interface AppUseTotalService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppUseTotalMapper.";

	/**
	 * 查询
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public AppUseTotal queryAppUseTotalByPersonId(String personId)
			throws Exception;
}
