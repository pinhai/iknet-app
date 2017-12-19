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
import com.iknet.server.appserver.pojo.AppDeviceSimAuto;
import com.iknet.server.appserver.pojo.AppReviceData;
import com.iknet.server.appserver.pojo.AppThreeHeightRecord;
import com.iknet.server.appserver.pojo.AppValidateTmp;
import com.iknet.server.appserver.service.AppDeviceSimAutoService;
import com.iknet.server.appserver.service.AppReviceDataService;
import com.iknet.server.appserver.service.AppValidateTmpService;

/**
 * SIM 测量
 * 
 * @author luozd
 * 
 */
@Controller
public class AppReviceDataController {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private AppReviceDataService appReviceDataService;

	@Resource
	private AppDeviceSimAutoService appDeviceSimAutoService;

	@Resource
	private AppValidateTmpService appValidateTmpService;

	/**
	 * 绑定 微信
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppPersonDeviceForWeixin.json")
	@ResponseBody
	public Map<String, Object> addAppPersonDeviceForWeixin(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppPersonDeviceForWeixin");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					vo.setDeviceFlag(System_Key.deviceFlag_W);
				}
				int i = appReviceDataService.addAppPersonDevice(vo);

				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Faile);
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
	 * 绑定 SIM
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppPersonDevice.json")
	@ResponseBody
	public Map<String, Object> addAppPersonDevice(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppPersonDevice");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);

				boolean validate_flag = true;
				/**
				 * 激活码
				 */
				String validateCode = "";

				log.debug("vo:" + vo);
				String validate_deviceSim = "";
				if (vo != null) {
					vo.setDeviceFlag(System_Key.deviceFlag_S);
					validateCode = EasyStr.strToTrim(vo.getValidateCode());
					String deviceSim = EasyStr.strToTrim(vo.getDeviceSim());
					deviceSim = EasyStr.strToUpperCase(deviceSim);
					vo.setDeviceSim(deviceSim);
					validate_deviceSim = deviceSim;
				}
				if (EasyStr.isNotEmpty(validateCode)) {

					if (EasyStr.isNotEmpty(validate_deviceSim)) {
						int validate_deviceSim_length = validate_deviceSim
								.length();
						if ((validate_deviceSim.endsWith(System_Key.AorB_O))
								|| (validate_deviceSim
										.endsWith(System_Key.AorB_A))
								|| (validate_deviceSim
										.endsWith(System_Key.AorB_B))) {
							if (validate_deviceSim_length > 10) {
								validate_deviceSim = validate_deviceSim
										.substring(0,
												validate_deviceSim_length - 1);
							}
						}
					}

					validate_flag = false;
					AppValidateTmp appValidateTmp = CommonUtilis
							.initAppValidateTmp(validate_deviceSim,
									System_Key.AppValidateTmp_Type_V,
									EasyStr.today(), validateCode,
									System_Key.AppValidateTmp_Type_V);
					int validate_count = appValidateTmpService
							.queryCountByAppValidateTmp(appValidateTmp);
					log.debug("validate_count:" + validate_count);
					if (validate_count > 0) {
						validate_flag = true;
					}
				}

				log.debug("validate_flag:" + validate_flag);

				if (validate_flag) {
					int i = appReviceDataService.addAppPersonDevice(vo);
					if (i > 0) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Bind_Success);
						appStatus.setSuccess(true);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Bind_Faile);
						appStatus.setSuccess(false);
					}
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_ValidateCode_Validate_Error);
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
	 * 绑定 身份证 号码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppPersonDeviceForIDCard.json")
	@ResponseBody
	public Map<String, Object> addAppPersonDeviceForIDCard(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppPersonDeviceForIDCard");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					vo.setDeviceFlag(System_Key.deviceFlag_C);
				}
				int i = appReviceDataService.addAppPersonDevice(vo);

				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Faile);
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
	 * 查询 绑定的 SIM 测量数据
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppReviceData.json")
	@ResponseBody
	public Map<String, Object> queryAppReviceData(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appPageJson, required = false) final String appPageJson) {
		log.debug("queryAppReviceData");
		log.debug("APP发送过来的参数appJson:" + appJson);
		log.debug("APP发送过来的参数appPageJson:" + appPageJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				String appPageJsonValue = AppJsonUtil
						.getPageJsonValue(appPageJson);

				int pageNo = CommonUtilis.initPageNo(appPageJsonValue);
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				Map<String, Object> queryMap = CommonUtilis
						.initMapPage(appPageJsonValue);
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);
				queryMap.put(System_Map.Map_VO, vo);

				List<AppReviceData> list = appReviceDataService
						.queryAppReviceDataForPage(queryMap);

				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				map.put(System_App_Key.appReviceData, list);
				map.put(System_Map.Map_pageNo, pageNo);
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
	 * 通过 身份证号码 查询 测量数据
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppReviceDataForIDCard.json")
	@ResponseBody
	public Map<String, Object> queryAppReviceDataForIDCard(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appPageJson, required = false) final String appPageJson) {
		log.debug("queryAppReviceDataForIDCard");
		log.debug("APP发送过来的参数appJson:" + appJson);
		log.debug("APP发送过来的参数appPageJson:" + appPageJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				String appPageJsonValue = AppJsonUtil
						.getPageJsonValue(appPageJson);

				int pageNo = CommonUtilis.initPageNo(appPageJsonValue);
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				Map<String, Object> queryMap = CommonUtilis
						.initMapPage(appPageJsonValue);
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);
				queryMap.put(System_Map.Map_VO, vo);

				List<AppReviceData> list = appReviceDataService
						.queryAppReviceDataForIDCardForPage(queryMap);

				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				map.put(System_App_Key.appReviceData, list);
				map.put(System_Map.Map_pageNo, pageNo);
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
	 * 查询 用户 SIM 绑定记录
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppPersonDevice.json")
	@ResponseBody
	public Map<String, Object> queryAppPersonDevice(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppPersonDevice");
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
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);
				queryMap.put(System_Map.Map_VO, vo);

				List<AppReviceData> list = appReviceDataService
						.queryAppPersonDeviceByMap(queryMap);

				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				map.put(System_App_Key.appReviceData, list);
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
	 * 解除 绑定 SIM
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-deleteAppPersonDevice.json")
	@ResponseBody
	public Map<String, Object> deleteAppPersonDevice(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("deleteAppPersonDevice");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> queryMap = IKnetUtil.getQueryMap();
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);
				queryMap.put(System_Map.Map_VO, vo);
				log.debug("vo:" + vo);
				int i = appReviceDataService
						.deleteAppPersonDeviceByMap(queryMap);

				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Delete_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Delete_Faile);
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
	@RequestMapping("/app-queryAppThreeHeightRecordList.json")
	@ResponseBody
	public Map<String, Object> queryAppThreeHeightRecordList(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppThreeHeightRecordList");
		log.debug("APP发送过来的参数appJson:" + appJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> queryMap = IKnetUtil.getQueryMap();
				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);
				queryMap.put(System_Map.Map_VO, vo);
				log.debug("vo:" + vo);
				if (vo != null) {
					log.debug("deviceSim:" + vo.getDeviceSim());
					log.debug("uploadDate:" + vo.getUploadDate());

					List<AppThreeHeightRecord> list = appReviceDataService
							.queryAppThreeHeightRecordList(queryMap);

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);

					map.put(System_App_Key.appThreeHeightRecord, list);
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
	 * 班定 DeviceSim
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppDeviceSimAuto.json")
	@ResponseBody
	public Map<String, Object> addAppDeviceSimAuto(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppDeviceSimAuto");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppDeviceSimAuto vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppDeviceSimAuto.class);
				log.debug("vo:" + vo);
				if (vo != null) {
					String deviceSim = EasyStr.strToTrim(vo.getDeviceSim());
					deviceSim = EasyStr.strToUpperCase(deviceSim);
					if (EasyStr.isNotEmpty(deviceSim)) {
						int deviceSim_length = deviceSim.length();
						if ((deviceSim.endsWith(System_Key.AorB_O))
								|| (deviceSim.endsWith(System_Key.AorB_A))
								|| (deviceSim.endsWith(System_Key.AorB_B))) {
							if (deviceSim_length > 10) {
								deviceSim = deviceSim.substring(0,
										deviceSim_length - 1);
							}
						}
					}
					vo.setDeviceSim(deviceSim);
				}

				int i = appDeviceSimAutoService.addAppDeviceSimAuto(vo);

				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Bind_Faile);
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
	 * 查询 DeviceSim 绑定 参数信息
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppDeviceSimAuto.json")
	@ResponseBody
	public Map<String, Object> queryAppDeviceSimAuto(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppDeviceSimAuto");
		log.debug("APP发送过来的参数appJson:" + appJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {

				AppDeviceSimAuto vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppDeviceSimAuto.class);

				log.debug("vo:" + vo);
				if (vo != null) {
					log.debug("deviceSim:" + vo.getDeviceSim());

					AppDeviceSimAuto appDeviceSimAuto = appDeviceSimAutoService
							.queryAppDeviceSimAuto(vo);
					if (appDeviceSimAuto != null) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
						appStatus.setSuccess(true);

						map.put(System_App_Key.appDeviceSimAuto,
								appDeviceSimAuto);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_DeviceSimAuto_IsNotExit);
						appStatus.setSuccess(false);
					}

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
	 * 绑定 SIM 前 发送 系统激活码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addAppPersonDeviceForValidateCode.json")
	@ResponseBody
	public Map<String, Object> addAppPersonDeviceForValidateCode(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addAppPersonDeviceForValidateCode");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {

				AppReviceData vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppReviceData.class);

				log.debug("vo:" + vo);
				if (vo != null) {
					String deviceSim = vo.getDeviceSim();
					log.debug("deviceSim:" + deviceSim);

					if (EasyStr.isNotEmpty(deviceSim)) {
						int deviceSim_length = deviceSim.length();
						if ((deviceSim.endsWith(System_Key.AorB_O))
								|| (deviceSim.endsWith(System_Key.AorB_A))
								|| (deviceSim.endsWith(System_Key.AorB_B))) {
							if (deviceSim_length > 10) {
								deviceSim = deviceSim.substring(0,
										deviceSim_length - 1);
							}
						}
					}

					// 内容
					String content = EasyStr
							.initRandomByLength(System_Key.default_SendCode_Length);

					log.debug("content:" + content);
					AppValidateTmp appValidateTmp = CommonUtilis
							.initAppValidateTmp(deviceSim,
									System_Key.AppValidateTmp_Type_V,
									EasyStr.today(), content,
									System_Key.AppValidateTmp_Type_V);

					int sendResult = appValidateTmpService
							.addAppValidateTmp(appValidateTmp);
					log.debug("sendResult:" + sendResult);

					if (sendResult > 0) {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_ValidateCode_Success);
						appStatus.setSuccess(true);

					} else {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_ValidateCode_Faile);
						appStatus.setSuccess(false);
					}

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
