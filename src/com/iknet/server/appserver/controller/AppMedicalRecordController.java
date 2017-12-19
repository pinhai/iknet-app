package com.iknet.server.appserver.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iknet.commons.baseCode.AppConstants;
import com.iknet.commons.baseCode.BusiConstants.System_App_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Map;
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.baseCode.BusiConstants.System_Result_Message_Key;
import com.iknet.commons.baseEntity.AppStatus;
import com.iknet.commons.util.AppJsonUtil;
import com.iknet.commons.util.CommonUtilis;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.server.appserver.pojo.AppMedicalRecord;
import com.iknet.server.appserver.service.AppMedicalRecordService;

/**
 * APP病例
 * 
 * @author luozd
 * 
 */
@Controller
public class AppMedicalRecordController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private AppMedicalRecordService appMedicalRecordService;

	/**
	 * 新增 病例
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppMedicalRecord.json")
	@ResponseBody
	public Map<String, Object> addAppMedicalRecord(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppMedicalRecord");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppMedicalRecord vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppMedicalRecord.class);
				log.debug("vo:" + vo);
				int i = appMedicalRecordService.addAppMedicalRecord(vo);
				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Add_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Add_Faile);
					appStatus.setSuccess(false);
				}
			} else {
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
			}
		} catch (Exception e) {
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			log.error(e.getMessage());
		}
		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

	/**
	 * 批量新增 病例
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addBatchAppMedicalRecord.json")
	@ResponseBody
	public Map<String, Object> addBatchAppMedicalRecord(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addBatchAppMedicalRecord");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, ?> jsonMap = IKnetUtil
						.appJsonValueToMap(appJsonValue);

				log.debug("jsonMap" + jsonMap);

				List<?> appJsonValueList = AppJsonUtil
						.getListJsonValueByAppBatchJsonValue(jsonMap,
								System_Key.appBatchJson);

				log.debug("appJsonValueList:" + appJsonValueList);
				int appJsonValueList_Size = 0;
				if (appJsonValueList != null) {
					appJsonValueList_Size = appJsonValueList.size();

				}
				if (appJsonValueList_Size > 0) {

					int i = appMedicalRecordService
							.addBatchAppMedicalRecordByAppJsonList(appJsonValueList);
					if (i > 0) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Add_Success);
						appStatus.setSuccess(true);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Add_Faile);
						appStatus.setSuccess(false);
					}
				} else {
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				}

			} else {
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
			}
		} catch (Exception e) {
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			log.error(e.getMessage());
		}
		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

	/**
	 * 批量新增 病例
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addBatchAppMedicalRecordForEveryOne.json")
	@ResponseBody
	public Map<String, Object> addBatchAppMedicalRecordForEveryOne(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addBatchAppMedicalRecordForEveryOne");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, ?> jsonMap = IKnetUtil
						.appJsonValueToMap(appJsonValue);

				log.debug("jsonMap" + jsonMap);

				List<?> appJsonValueList = AppJsonUtil
						.getListJsonValueByAppBatchJsonValue(jsonMap,
								System_Key.appBatchJson);

				log.debug("appJsonValueList:" + appJsonValueList);
				int appJsonValueList_Size = 0;
				if (appJsonValueList != null) {
					appJsonValueList_Size = appJsonValueList.size();

				}
				if (appJsonValueList_Size > 0) {

					int i = appMedicalRecordService
							.addBatchAppMedicalRecordForEveryOneByAppJsonList(appJsonValueList);
					if (i > 0) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Add_Success);
						appStatus.setSuccess(true);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Add_Faile);
						appStatus.setSuccess(false);
					}
				} else {
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				}

			} else {
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
			}
		} catch (Exception e) {
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			log.error(e.getMessage());
		}
		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

	/**
	 * 查询用户的 病例 默认 查询
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppMedicalRecordByMapForDefault.json")
	@ResponseBody
	public Map<String, Object> queryAppMedicalRecordByMapForDefault(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppMedicalRecordByMapForDefault");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				Map<String, Object> queryMap = IKnetUtil.getQueryMap();
				queryMap.put(System_Key.personId,
						mapJsonValue.get(System_Key.personId));
				queryMap.put(System_Key.endRowNum, System_Key.default_endRowNum);
				List<AppMedicalRecord> list = appMedicalRecordService
						.queryAppMedicalRecordByMapForDefault(queryMap);

				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				map.put(System_App_Key.appMedicalRecord, list);
			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}
		} catch (Exception e) {
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			log.error(e.getMessage());
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

	/**
	 * 查询用户 某一天的 病例 [显示该天的 所有 上传的 病例]
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppMedicalRecordByMapForOneDay.json")
	@ResponseBody
	public Map<String, Object> queryAppMedicalRecordByMapForOneDay(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppMedicalRecordByMapForOneDay");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				Map<String, Object> queryMap = IKnetUtil.getQueryMap();
				queryMap.put(System_Key.personId,
						mapJsonValue.get(System_Key.personId));
				queryMap.put(System_Key.equalsTime,
						mapJsonValue.get(System_Key.equalsTime));
				List<AppMedicalRecord> list = appMedicalRecordService
						.queryAppMedicalRecordByMapForOneDay(queryMap);

				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				map.put(System_App_Key.appMedicalRecord, list);
			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}

		} catch (Exception e) {
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			log.error(e.getMessage());
		}
		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

	/**
	 * 查询用户 某一段时间 内的 病例 [每天取最后一条]
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppMedicalRecordByMapForEveryDay.json")
	@ResponseBody
	public Map<String, Object> queryAppMedicalRecordByMapForEveryDay(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppMedicalRecordByMapForEveryDay");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				Map<String, Object> queryMap = IKnetUtil.getQueryMap();
				queryMap.put(System_Key.personId,
						mapJsonValue.get(System_Key.personId));
				queryMap.put(System_Key.startTime,
						mapJsonValue.get(System_Key.startTime));
				queryMap.put(System_Key.endTime,
						mapJsonValue.get(System_Key.endTime));

				List<AppMedicalRecord> list = appMedicalRecordService
						.queryAppMedicalRecordByMapForEveryDay(queryMap);

				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				map.put(System_App_Key.appMedicalRecord, list);
			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}
		} catch (Exception e) {
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			log.error(e.getMessage());
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

	/**
	 * 查询 列表 分页
	 * 
	 * @param appJson
	 * @param appPageJson
	 * @return
	 */
	@RequestMapping("/app-queryAppMedicalRecordListForPage.json")
	@ResponseBody
	public Map<String, Object> queryAppMedicalRecordListForPage(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appPageJson, required = false) final String appPageJson) {
		log.debug("queryAppMedicalRecordListForPage");
		log.debug("APP发送过来的参数appJson:" + appJson);
		log.debug("APP发送过来的参数appPageJson:" + appPageJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {
				String appPageJsonValue = AppJsonUtil
						.getPageJsonValue(appPageJson);

				AppMedicalRecord vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppMedicalRecord.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					log.debug("personId:" + vo.getPersonId());
					int pageNo = CommonUtilis.initPageNo(appPageJsonValue);

					Map<String, Object> queryMap = CommonUtilis
							.initMapPage(appPageJsonValue);
					queryMap.put(System_Map.Map_VO, vo);

					List<AppMedicalRecord> list = appMedicalRecordService
							.queryAppMedicalRecordListForPage(queryMap);

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appMedicalRecord, list);
					map.put(System_Map.Map_pageNo, pageNo);
				} else {
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);
				}

			} else {
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			appStatus.setErrorCode(AppConstants.APP.actionError);
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * 删除 测量报告
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-deleteAppMedicalRecord.json")
	@ResponseBody
	public Map<String, Object> deleteAppMedicalRecord(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("deleteAppMedicalRecord");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppMedicalRecord vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppMedicalRecord.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					int result = appMedicalRecordService
							.deleteAppMedicalRecord(vo);

					if (result > System_ResultKey.Result_default) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Delete_Success);
						appStatus.setSuccess(true);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Delete_Faile);
						appStatus.setSuccess(false);
					}
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_MergeAccount_Delete_Faile);
					appStatus.setSuccess(false);
				}

			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}
		} catch (Exception e) {
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			log.error(e.getMessage());
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

}
