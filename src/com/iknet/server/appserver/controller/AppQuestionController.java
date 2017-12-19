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
import com.iknet.server.appserver.pojo.AppQuestion;
import com.iknet.server.appserver.service.AppQuestionService;

/**
 * APP问答
 * 
 * @author luozd
 * 
 */
@Controller
public class AppQuestionController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private AppQuestionService appQuestionService;

	/**
	 * 新增 提问
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppQuestion.json")
	@ResponseBody
	public Map<String, Object> addAppQuestion(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppQuestion");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppQuestion vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppQuestion.class);
				log.debug("vo:" + vo);
				int i = appQuestionService.addAppQuestion(vo);
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
	 * 查询 列表 分页
	 * 
	 * @param appJson
	 * @param appPageJson
	 * @return
	 */
	@RequestMapping("/app-queryAppQuestionListForPage.json")
	@ResponseBody
	public Map<String, Object> queryAppQuestionListForPage(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appPageJson, required = false) final String appPageJson) {
		log.debug("queryAppQuestionListForPage");
		log.debug("APP发送过来的参数appJson:" + appJson);
		log.debug("APP发送过来的参数appPageJson:" + appPageJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {
				String appPageJsonValue = AppJsonUtil
						.getPageJsonValue(appPageJson);

				AppQuestion vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppQuestion.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					vo.setIsShow(System_Key.IsShow_1);
					log.debug("typeId:" + vo.getTypeId());
					int pageNo = CommonUtilis.initPageNo(appPageJsonValue);

					Map<String, Object> queryMap = CommonUtilis
							.initMapPage(appPageJsonValue);
					queryMap.put(System_Map.Map_VO, vo);

					List<AppQuestion> list = appQuestionService
							.queryAppQuestionListForPage(queryMap);

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appQuestion, list);
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
	 * 查询 列表 分页
	 * 
	 * @param appJson
	 * @param appPageJson
	 * @return
	 */
	@RequestMapping("/app-queryMyAppQuestionListForPage.json")
	@ResponseBody
	public Map<String, Object> queryMyAppQuestionListForPage(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appPageJson, required = false) final String appPageJson) {
		log.debug("queryMyAppQuestionListForPage");
		log.debug("APP发送过来的参数appJson:" + appJson);
		log.debug("APP发送过来的参数appPageJson:" + appPageJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {
				String appPageJsonValue = AppJsonUtil
						.getPageJsonValue(appPageJson);

				AppQuestion vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppQuestion.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					vo.setIsShow(System_Key.IsShow_1);
					log.debug("personId:" + vo.getPersonId());
					int pageNo = CommonUtilis.initPageNo(appPageJsonValue);

					Map<String, Object> queryMap = CommonUtilis
							.initMapPage(appPageJsonValue);
					queryMap.put(System_Map.Map_VO, vo);

					List<AppQuestion> list = appQuestionService
							.queryMyAppQuestionListForPage(queryMap);

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appQuestion, list);
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
}
