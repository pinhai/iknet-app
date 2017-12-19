package com.iknet.server.appserver.service;

import com.iknet.server.appserver.pojo.AppDeviceSimAuto;

/**
 * 动态血压计设置参数
 * 
 * @author luozd
 * 
 */
public interface AppDeviceSimAutoService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppDeviceSimAutoMapper.";

	/**
	 * 班定 DeviceSim 信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppDeviceSimAuto(AppDeviceSimAuto vo) throws Exception;

	/**
	 * 查询 DeviceSim 绑定 参数信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public AppDeviceSimAuto queryAppDeviceSimAuto(AppDeviceSimAuto vo)
			throws Exception;

}
