package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppMedicalRecord;

/**
 * APP病例
 * 
 * @author luozd
 * 
 */
public interface AppMedicalRecordService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppMedicalRecordMapper.";

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppMedicalRecord(AppMedicalRecord vo) throws Exception;

	// /**
	// // * 根据 medicalRecordId 查询
	// // *
	// // * @param medicalRecordId
	// // * @return
	// // * @throws Exception
	// // */
	// public AppMedicalRecord queryAppMedicalRecordByMedicalRecordId(
	// String medicalRecordId) throws Exception;

	/**
	 * 查询用户 某一段时间 内的 病例 [每天取最后一条]
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordByMapForEveryDay(
			Map<String, Object> map) throws Exception;

	/**
	 * 查询用户 某一天的 病例 [显示该天的 所有 上传的 病例]
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordByMapForOneDay(
			Map<String, Object> map) throws Exception;

	/**
	 * 查询用户的 病例 默认 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordByMapForDefault(
			Map<String, Object> map) throws Exception;

	/**
	 * 批量 新增 病例
	 * 
	 * @param appJsonList
	 * @return
	 * @throws Exception
	 */
	public int addBatchAppMedicalRecordByAppJsonList(List<?> appJsonList)
			throws Exception;

	/**
	 * 每一个 批量 新增 病例
	 * 
	 * @param appJsonList
	 * @return
	 * @throws Exception
	 */
	public int addBatchAppMedicalRecordForEveryOneByAppJsonList(
			List<?> appJsonList) throws Exception;

	/**
	 * 分页 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordListForPage(
			Map<String, Object> map) throws Exception;

	/**
	 * 根据 personId 查询 最后一次 测量数据
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public AppMedicalRecord queryAppMedicalRecordForLastOneByPersonId(
			String personId) throws Exception;

	/**
	 * 删除 测量报告
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int deleteAppMedicalRecord(AppMedicalRecord vo) throws Exception;
}
