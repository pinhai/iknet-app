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
import com.iknet.commons.baseCode.BusiConstants.System_Result_Message_Key;
import com.iknet.commons.baseEntity.AppStatus;
import com.iknet.commons.util.AppJsonUtil;
import com.iknet.commons.util.CommonUtilis;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.server.appserver.pojo.AppCommunity;
import com.iknet.server.appserver.pojo.AppLevel;
import com.iknet.server.appserver.service.AppCommunityService;

/**
 * APP 社区用户
 * 
 * @author luozd
 * 
 */
@Controller
public class AppCommunityController {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private AppCommunityService appCommunityService;

	/**
	 * 新增 社区用户
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppCommunity.json")
	@ResponseBody
	public Map<String, Object> addAppCommunity(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppCommunity");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppCommunity vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppCommunity.class);
				log.debug("vo:" + vo);
				Map<String, Object> resultMap = appCommunityService
						.addAppCommunity(vo);

				if (resultMap != null) {

					String loginNo = EasyStr.strToTrim((String) resultMap
							.get(System_Key.loginNo));
					String loginPwd = EasyStr.strToTrim((String) resultMap
							.get(System_Key.loginPwd));

					if (EasyStr.isNotEmpty(loginNo)) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Add_Success);
						appStatus.setSuccess(true);

						map.put(System_App_Key.loginNo, loginNo);
						if (EasyStr.isNotEmpty(loginPwd)) {
							map.put(System_App_Key.loginPwd, loginPwd);
						}

					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Community_User_IsExit);
						appStatus.setSuccess(false);
					}
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_User_IsNotExit);
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
	 * 根据 communityId 删除
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-deleteAppCommunity.json")
	@ResponseBody
	public Map<String, Object> deleteAppCommunity(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("deleteAppCommunity");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				int i = appCommunityService
						.deleteAppCommunityByCommunityId(EasyStr
								.strToTrim(mapJsonValue
										.get(System_Key.communityId) + ""));

				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Delete_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Delete_Faile);
					appStatus.setSuccess(true);
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

	/**
	 * 查询 列表 分页
	 * 
	 * @param appJson
	 * @param appPageJson
	 * @return
	 */
	@RequestMapping("/app-queryAppCommunityListForPage.json")
	@ResponseBody
	public Map<String, Object> queryAppCommunityListForPage(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appPageJson, required = false) final String appPageJson) {
		log.debug("queryAppCommunityListForPage");
		log.debug("APP发送过来的参数appJson:" + appJson);
		log.debug("APP发送过来的参数appPageJson:" + appPageJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {
				String appPageJsonValue = AppJsonUtil
						.getPageJsonValue(appPageJson);

				AppCommunity vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppCommunity.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					log.debug("communityCode:" + vo.getCommunityCode());
					int pageNo = CommonUtilis.initPageNo(appPageJsonValue);

					Map<String, Object> queryMap = CommonUtilis
							.initMapPage(appPageJsonValue);
					queryMap.put(System_Map.Map_VO, vo);

					List<AppCommunity> list = appCommunityService
							.queryAppCommunityListForPage(queryMap);

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appCommunity, list);
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
	 * 社区 编辑
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-updateAppCommunity.json")
	@ResponseBody
	public Map<String, Object> updateAppCommunity(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("updateAppCommunity");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppCommunity vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppCommunity.class);
				log.debug("vo:" + vo);

				int i = appCommunityService.updateAppCommunity(vo);

				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Update_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Update_Faile);
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
	 * 查询 一级机构
	 * 
	 * @return
	 */
	@RequestMapping("/app-queryAppLevelListForCommunity.json")
	@ResponseBody
	public Map<String, Object> queryAppLevelListForCommunity() {
		log.debug("queryAppLevelListForCommunity");

		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {

			List<AppLevel> list = appCommunityService
					.queryAppLevelListForCommunity();

			appStatus.setErrorCode(AppConstants.APP.actionSuccess);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
			appStatus.setSuccess(true);

			map.put(System_App_Key.appLevel, list);

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

}
