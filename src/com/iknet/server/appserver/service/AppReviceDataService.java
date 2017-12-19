package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppReviceData;
import com.iknet.server.appserver.pojo.AppThreeHeightRecord;

public interface AppReviceDataService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppReviceDataMapper.";

	/**
	 * 新增 SIM 绑定
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppPersonDevice(AppReviceData vo) throws Exception;

	/**
	 * 删除 用户 SIM 绑定记录
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteAppPersonDeviceByMap(Map<String, Object> map)
			throws Exception;

	/**
	 * 查询 用户 SIM 班定记录
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryAppPersonDeviceByMap(Map<String, Object> map)
			throws Exception;

	/**
	 * 查询 测量数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryAppReviceDataForPage(Map<String, Object> map)
			throws Exception;

	/**
	 * 查询 测量数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryAppReviceDataForIDCardForPage(
			Map<String, Object> map) throws Exception;

	/**
	 * 分页 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppThreeHeightRecord> queryAppThreeHeightRecordList(
			Map<String, Object> map) throws Exception;

	/**
	 * 获取 已经绑定的设备 记录的 personId
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryYlPersonDeviceForPersonIdList(
			Map<String, Object> map) throws Exception;

}
