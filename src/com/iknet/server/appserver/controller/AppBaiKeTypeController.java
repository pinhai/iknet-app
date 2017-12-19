package com.iknet.server.appserver.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iknet.commons.baseCode.AppConstants;
import com.iknet.commons.baseCode.BusiConstants.System_App_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Result_Message_Key;
import com.iknet.commons.baseEntity.AppStatus;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.server.appserver.pojo.AppBaiKeType;
import com.iknet.server.appserver.service.AppBaiKeTypeService;

/**
 * 百科类型
 * 
 * @author luozd
 * 
 */
@Controller
public class AppBaiKeTypeController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private AppBaiKeTypeService appBaiKeTypeService;

	/**
	 * 查询 百科 类别
	 * 
	 * @return
	 */
	@RequestMapping("/app-queryAppBaiKeTypeForList.json")
	@ResponseBody
	public Map<String, Object> queryAppBaiKeTypeForList() {
		log.debug("queryAppBaiKeTypeForList");

		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();

		try {
			Map<String, Object> queryMap = IKnetUtil.getQueryMap();

			List<AppBaiKeType> list = appBaiKeTypeService
					.queryAppBaiKeTypeForList(queryMap);

			appStatus.setErrorCode(AppConstants.APP.actionSuccess);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
			appStatus.setSuccess(true);
			map.put(System_App_Key.appBaiKeType, list);
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
