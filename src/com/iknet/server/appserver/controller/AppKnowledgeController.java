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
import com.iknet.server.appserver.pojo.AppKnowledge;
import com.iknet.server.appserver.service.AppKnowledgeService;

/**
 * APP知识
 * 
 * @author luozd
 * 
 */
@Controller
public class AppKnowledgeController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private AppKnowledgeService appKnowledgeService;

	/**
	 * 查看 详情
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppKnowledgeById.json")
	@ResponseBody
	public Map<String, Object> queryAppKnowledgeById(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppKnowledgeById");
		log.debug("APP发送过来的参数appJson:" + appJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppKnowledge appKnowledge = null;
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				Map<String, Object> queryMap = IKnetUtil.getQueryMap();
				queryMap.put(System_Key.Id, mapJsonValue.get(System_Key.Id));
				queryMap.put(System_Key.IsShow, System_Key.IsShow_1);

				appKnowledge = appKnowledgeService
						.queryAppKnowledgeByMap(queryMap);

				if (appKnowledge == null) {
					appStatus.setErrorCode(AppConstants.APP.dataIsExit);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_IsExit);
					appStatus.setSuccess(false);

				} else {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appKnowledge, appKnowledge);

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
	 * 查询 列表 分页
	 * 
	 * @param appJson
	 * @param appPageJson
	 * @return
	 */
	@RequestMapping("/app-queryAppKnowledgeListForPage.json")
	@ResponseBody
	public Map<String, Object> queryAppKnowledgeListForPage(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appPageJson, required = false) final String appPageJson) {
		log.debug("queryAppKnowledgeListForPage");
		log.debug("APP发送过来的参数appJson:" + appJson);
		log.debug("APP发送过来的参数appPageJson:" + appPageJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {
				String appPageJsonValue = AppJsonUtil
						.getPageJsonValue(appPageJson);
				log.debug("appPageJsonValue:" + appPageJsonValue);
				AppKnowledge vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppKnowledge.class);

				if (vo != null) {
					vo.setIsShow(System_Key.IsShow_1);

					int pageNo = CommonUtilis.initPageNo(appPageJsonValue);

					Map<String, Object> queryMap = CommonUtilis
							.initMapPage(appPageJsonValue);
					queryMap.put(System_Map.Map_VO, vo);

					List<AppKnowledge> list = appKnowledgeService
							.queryAppKnowledgeListForPage(queryMap);

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appKnowledge, list);
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
	 * 文章 点赞
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-updateAppKnowledgeNiceTotal.json")
	@ResponseBody
	public Map<String, Object> updateAppKnowledgeNiceTotal(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("updateAppKnowledgeNiceTotal");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				int i = appKnowledgeService.updateAppKnowledgeNiceTotal(Long
						.parseLong(EasyStr.strToTrim(mapJsonValue
								.get(System_Key.Id) + "")));

				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_NiceTotal_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_NiceTotal_Faile);
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

}
