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
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.baseCode.BusiConstants.System_Result_Message_Key;
import com.iknet.commons.baseEntity.AppStatus;
import com.iknet.commons.util.AppJsonUtil;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.server.appserver.pojo.AppMergeAccount;
import com.iknet.server.appserver.service.AppMergeAccountService;

/**
 * APP 用户 关联帐号
 * 
 * @author luozd
 * 
 */
@Controller
public class AppMergeAccountController {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private AppMergeAccountService appMergeAccountService;

	/**
	 * 新增 关联账户
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppMergeAccount.json")
	@ResponseBody
	public Map<String, Object> addAppMergeAccount(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppMergeAccount");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppMergeAccount vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppMergeAccount.class);

				log.debug("vo:" + vo);

				if (vo != null) {
					vo.setMergeAccountFlag(System_Key.MergeAccountFlag_O);

					int result = appMergeAccountService.addAppMergeAccount(vo);

					if (result > System_ResultKey.Result_default) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_MergeAccount_Success);
						appStatus.setSuccess(true);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_MergeAccount_Faile);
						appStatus.setSuccess(false);
					}
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_MergeAccount_Delete_Faile);
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
	 * 删除关联帐号
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-deleteAppMergeAccount.json")
	@ResponseBody
	public Map<String, Object> deleteAppMergeAccount(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("deleteAppMergeAccount");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppMergeAccount vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppMergeAccount.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					int result = appMergeAccountService
							.deleteAppMergeAccount(vo);

					if (result > System_ResultKey.Result_default) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_MergeAccount_Delete_Success);
						appStatus.setSuccess(true);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_MergeAccount_Delete_Faile);
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

	/**
	 * 查询 用户 的 关联账户
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppMergeAccount.json")
	@ResponseBody
	public Map<String, Object> queryAppMergeAccount(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppMergeAccount");
		log.debug("APP发送过来的参数appJson:" + appJson);

		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {

				AppMergeAccount vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppMergeAccount.class);
				log.debug("vo:" + vo);

				if (vo != null) {
					vo.setMergeAccountFlag(System_Key.MergeAccountFlag_O);
					List<AppMergeAccount> list = appMergeAccountService
							.queryAppMergeAccount(vo);

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appMergeAccount, list);

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
