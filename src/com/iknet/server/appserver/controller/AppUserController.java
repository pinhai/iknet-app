package com.iknet.server.appserver.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iknet.commons.baseCode.AppConstants;
import com.iknet.commons.baseCode.BusiConstants.System_App_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Flag;
import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Map;
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.baseCode.BusiConstants.System_Result_Message_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Send_Message_Key;
import com.iknet.commons.baseCode.ErrorCode;
import com.iknet.commons.baseEntity.AppStatus;
import com.iknet.commons.email.EmailServerFactory;
import com.iknet.commons.email.util.MailSenderInfo;
import com.iknet.commons.util.AppJsonUtil;
import com.iknet.commons.util.CommonUtilis;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.IKnetAppVersionUtil;
import com.iknet.commons.util.IKnetSendMessageUtil;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.commons.util.MdEncryptUtil;
import com.iknet.commons.util.SequenceUtil;
import com.iknet.server.appserver.pojo.AppFamilyMembers;
import com.iknet.server.appserver.pojo.AppIknetEasemobUser;
import com.iknet.server.appserver.pojo.AppIknetUser;
import com.iknet.server.appserver.pojo.AppMedicalRecord;
import com.iknet.server.appserver.pojo.AppUseTotal;
import com.iknet.server.appserver.pojo.AppUser;
import com.iknet.server.appserver.pojo.AppValidateTmp;
import com.iknet.server.appserver.pojo.AppVersion;
import com.iknet.server.appserver.service.AppFamilyMembersService;
import com.iknet.server.appserver.service.AppMedicalRecordService;
import com.iknet.server.appserver.service.AppUseTotalService;
import com.iknet.server.appserver.service.AppUserService;
import com.iknet.server.appserver.service.AppValidateTmpService;

/**
 * APP用户控制器，抛出接口拱APP（IOS和安卓）调用
 * 
 * @author zhangzhongguo
 * 
 */
@Controller
public class AppUserController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private AppUserService appUserService;

	@Resource
	private AppUseTotalService appUseTotalService;

	@Resource
	private AppFamilyMembersService appFamilyMembersService;

	@Resource
	private AppValidateTmpService appValidateTmpService;

	@Resource
	private AppMedicalRecordService appMedicalRecordService;

	/**
	 * 发送 [短信|邮件]验证码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-sendPhoneCode.json")
	@ResponseBody
	public Map<String, Object> sendPhoneCode(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("sendPhoneCode");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				// 内容
				String content = EasyStr
						.initRandomByLength(System_Key.default_SendCode_Length);

				log.debug("content:" + content);

				// 号码
				String mobilePhone = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.loginNo) + "");

				log.debug("mobilePhone:" + mobilePhone);

				boolean validate_flag = false;
				AppValidateTmp vo = CommonUtilis.initAppValidateTmp(
						mobilePhone, System_Key.AppValidateTmp_Type_R,
						EasyStr.today(), content, System_Key.default_Clear);

				if (vo != null) {
					Map<String, Object> queryMap = IKnetUtil.getQueryMap();
					queryMap.put(System_Map.Map_VO, vo);
					int sendd_count = appValidateTmpService
							.queryAppValidateTmpTotalByMap(queryMap);
					if (sendd_count <= System_Key.default_3) {
						validate_flag = true;

					}
				}
				if (validate_flag) {
					AppIknetUser appIknetUser = this
							.initAppIknetUser(mobilePhone);

					if (appIknetUser == null) {
						boolean sendMessage_flag = false;
						// 判断用户登录方式
						if (mobilePhone.indexOf(System_Flag.Split_Email) > 0) {

							log.debug("邮箱发送 验证码");
							MailSenderInfo mailInfo = new MailSenderInfo();
							String[] toAddressesArr = { mobilePhone };
							mailInfo.setToAddressesArr(toAddressesArr);
							String email_content = IKnetSendMessageUtil
									.initContents(
											System_Send_Message_Key.Send_Message_Content_Prefix_register,
											content);
							mailInfo.setContent(email_content);
							int send_status = EmailServerFactory
									.sendEmail(mailInfo);
							log.debug("发送邮件状态 send_status" + send_status);
							if (send_status > System_Key.default_0) {

								sendMessage_flag = true;
							}
							vo.setSendWay(System_Key.AppValidateTmp_SendWay_E);

							/**
							 * upd by lzd 20151119 邮箱注册无需验证码
							 */
							sendMessage_flag = true;
						} else if ((mobilePhone
								.matches(System_Key.Mobile_RegExp))
								&& (mobilePhone.length() == 11)) {
							log.debug("手机号码发送 验证码");
							String result = IKnetSendMessageUtil
									.sendMessage(
											System_Send_Message_Key.Send_Message_Content_Prefix_register,
											mobilePhone, content);
							log.debug("result:" + result);
							if ((System_Key.SendPhoneCode_Result_0
									.equalsIgnoreCase(result))) {
								sendMessage_flag = true;
							}
							vo.setSendWay(System_Key.AppValidateTmp_SendWay_S);
						} else {
							log.error("非手机 、邮箱 发送 验证码 ");
						}

						log.debug("sendMessage_flag:" + sendMessage_flag);

						if (sendMessage_flag) {

							int sendResult = appValidateTmpService
									.addAppValidateTmp(vo);
							log.debug("sendResult:" + sendResult);

							appStatus
									.setErrorCode(AppConstants.APP.actionSuccess);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_SendPhoneCode_Success);
							appStatus.setSuccess(true);
						} else {
							appStatus
									.setErrorCode(AppConstants.APP.actionError);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_SendPhoneCode_Faile);
							appStatus.setSuccess(false);
						}
					} else {
						appStatus.setErrorCode(AppConstants.APP.dataSendError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_User_IsExit);
						appStatus.setSuccess(false);
					}

				} else {
					appStatus.setErrorCode(AppConstants.APP.dataSendError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_SendError);
					appStatus.setSuccess(false);
				}

			} else {
				appStatus.setErrorCode(AppConstants.APP.actionError);
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
		log.debug("map:" + map);
		return map;
	}

	/**
	 * APP注册
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-register.json")
	@ResponseBody
	public Map<String, Object> register(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("register");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppUser user = mapper.readValue(appJsonValue, AppUser.class);
				log.debug("user:" + user);
				String loginNo = "";
				String email = "";
				String mobilePhone = "";
				String loginPwd = "";
				String validateCode = "";
				if (user != null) {
					email = EasyStr.strToTrim(user.getEmail());
					mobilePhone = EasyStr.strToTrim(user.getMobilePhone());
					loginNo = EasyStr.strToTrim(user.getLoginNo());
					loginPwd = EasyStr.strToTrim(user.getLoginPwd());
					validateCode = EasyStr.strToTrim(user.getValidateCode());
				}

				boolean flag = false;
				/**
				 * upd lzd 20151119 邮箱注册 无需验证 验证码
				 */
				// 判断用户登录方式
				if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
					flag = true;

				} else {
					flag = this.checkValidateCode(
							System_Key.AppValidateTmp_Type_R, loginNo,
							validateCode);
				}

				if (flag) {
					if ((EasyStr.isEmpty(loginNo)) && (EasyStr.isEmpty(email))
							&& (EasyStr.isEmpty(mobilePhone))) {
						flag = false;
					}

					if ((flag) && (EasyStr.isNotEmpty(loginPwd))) {
						user.setLoginPwd(MdEncryptUtil.MD5EncodeToHex(loginPwd));

						if (EasyStr.isNotEmpty(loginNo)) {
							// 判断用户登录方式
							if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
								email = loginNo;
								loginNo = null;

							} else if ((loginNo
									.matches(System_Key.Mobile_RegExp))
									&& (loginNo.length() == 11)) {
								mobilePhone = loginNo;
								loginNo = null;

							} else {
								log.debug("loginNo 注册");
							}
						}

						user.setEmail(email);
						user.setLoginNo(loginNo);
						user.setMobilePhone(mobilePhone);

						int res = appUserService.addAppUser(user);
						if (res == -1) {
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_User_IsExit);
							appStatus.setSuccess(false);
							appStatus
									.setErrorCode(AppConstants.APP.useNameExist);
						} else if (res == 1) {
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_User_Add_Success);
							appStatus.setSuccess(true);
							appStatus
									.setErrorCode(AppConstants.APP.actionSuccess);
						}

					} else {
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_User_LoginPwd_IsNull);
						appStatus.setSuccess(false);
						appStatus.setErrorCode(AppConstants.APP.actionError);
					}
				} else {
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_ValidateCode_Error);
					appStatus.setSuccess(false);
					appStatus.setErrorCode(AppConstants.APP.actionError);
				}

			} else {
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				map.put(System_App_Key.appStatus, appStatus);

			}

		} catch (Exception e) {
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			appStatus.setErrorCode(AppConstants.APP.actionError);
			log.error(e.getMessage());

		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;
	}

	/**
	 * APP登录
	 * 
	 * @param appJson
	 * @param appVersionJson
	 * @return
	 */
	@RequestMapping("/app-login.json")
	@ResponseBody
	public Map<String, Object> login(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appVersionJson, required = false) final String appVersionJson) {
		log.debug("login");
		log.debug("APP发送过来的参数 appJson:" + appJson);
		log.debug("APP发送过来的参数 appVersionJson:" + appVersionJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppUser appUser = null;
		AppStatus appStatus = new AppStatus();
		AppVersion appVersion = null;
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			String appVersionJsonValue = AppJsonUtil
					.getVersionJsonValue(appVersionJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				AppUser user = IKnetUtil.binder.fromJson(appJsonValue,
						AppUser.class);

				appVersion = IKnetUtil.binder.fromJson(appVersionJsonValue,
						AppVersion.class);

				appVersion = IKnetAppVersionUtil.checkAppVersion(appVersion);

				log.debug("user:" + user);
				String loginNo = "";
				String loginPwd = "";
				if (user != null) {
					loginNo = EasyStr.strToTrim(user.getLoginNo());
					loginPwd = EasyStr.strToTrim(user.getLoginPwd());
				}
				if ((EasyStr.isNotEmpty(loginNo))
						&& (EasyStr.isNotEmpty(loginPwd))) {
					// 判断用户登录方式
					if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
						appUser = appUserService
								.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
										null, null, loginNo, null, null);

					} else if ((loginNo.matches(System_Key.Mobile_RegExp))
							&& (loginNo.length() == 11)) {
						appUser = appUserService
								.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
										null, loginNo, null, null, null);
					} else {
						appUser = appUserService
								.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
										loginNo, null, null, null, null);
					}

				}
				log.debug("appUser:" + appUser);
				if (appUser != null) {
					/**
					 * 登录 输入名 作为 显示名
					 */
					appUser.setShowName(loginNo);
					if (MdEncryptUtil.MD5EncodeToHex(loginPwd).equals(
							appUser.getLoginPwd())) {
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Login_Success);
						appStatus.setSuccess(true);
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						map.put(System_App_Key.appUser, appUser);
					} else {
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_LoginPwd_Error);
						appStatus.setSuccess(false);
						appStatus.setErrorCode(AppConstants.APP.passwordError);
					}
				} else {
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_User_IsNotExit);
					appStatus.setSuccess(false);
					appStatus.setErrorCode(AppConstants.APP.passwordError);
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

		log.debug("appVersion:" + appVersion);
		map.put(System_App_Key.appVersion, appVersion);
		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * 查询个人信息
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-getUserInfo.json")
	@ResponseBody
	public Map<String, Object> getUserInfo(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("getUserInfo");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				String personIdKeyValue = "";
				if (mapJsonValue != null) {
					personIdKeyValue = mapJsonValue.get(System_Key.personId)
							+ "";
				}

				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);

				String personId = System_Key.default_Clear;

				// if (EasyStr.isNotEmpty(personIdKeyValue)) {
				// personId = EasyStr.parseLong(personIdKeyValue);
				// }

				if (EasyStr.isNotEmpty(personIdKeyValue)) {
					personId = personIdKeyValue;
				}

				log.debug("personId:" + personId);
				AppUser user = null;
				if (EasyStr.isNotEmpty(personIdKeyValue)) {
					user = appUserService.queryAppUserByPersonId(personId);

					if (user == null) {
						appStatus.setErrorCode(AppConstants.APP.userNotLogin);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_User_IsNotExit);
						appStatus.setSuccess(false);
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
						appStatus.setSuccess(true);
						user = appUserService.queryAppUserByPersonId(user
								.getPersonId());
						map.put(System_App_Key.appUser, user);
					}
				} else {
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
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
	 * 修改个人信息
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-updateUserInfo.json")
	@ResponseBody
	public Map<String, Object> updateUserInfo(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("updateUserInfo");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				AppUser user = IKnetUtil.binder.fromJson(appJsonValue,
						AppUser.class);
				log.debug("user:" + user);
				int i = appUserService.updateAppUser(user);
				log.debug("i:" + i);
				if (i > System_ResultKey.Result_default) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Update_Success);
					appStatus.setSuccess(true);
				} else {

					if (i == System_ResultKey.Result_default) {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Update_Faile);
						appStatus.setSuccess(false);
					} else {
						appStatus.setSuccess(false);
						appStatus
								.setErrorCode(ErrorCode.BusinessErrorCode.errorInsertData);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Bind_IdCard_Faile);
					}

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
	 * APP用户注销
	 * 
	 * @return
	 */
	@RequestMapping("/app-logout.json")
	@ResponseBody
	public Map<String, Object> logout() {

		log.debug("logout");
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			appStatus.setErrorCode(AppConstants.APP.actionSuccess);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_logout_Success);
			appStatus.setSuccess(true);
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
	 * 查询我的家属成员
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-getMyFamilyMembers.json")
	@ResponseBody
	public Map<String, Object> getMyFamilyMembers(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("getMyFamilyMembers");
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

				List<AppFamilyMembers> list = appFamilyMembersService
						.queryAppFamilyMembersForList(queryMap);
				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				map.put(System_App_Key.appFamilyMembers, list);
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
	 * 删除家庭成员
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-deleteMyFamilyMembers.json")
	@ResponseBody
	public Map<String, Object> deleteMyFimalyMembers(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("deleteMyFimalyMembers");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);
				int i = appFamilyMembersService
						.deleteAppFamilyMembersByMembersId(EasyStr
								.strToTrim(mapJsonValue
										.get(System_Key.membersId) + ""));

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
	 * 新增我的家庭成员
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-addMyFamilyMembers.json")
	@ResponseBody
	public Map<String, Object> addMyFamilyMembers(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("addMyFamilyMembers");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				AppFamilyMembers vo = IKnetUtil.binder.fromJson(appJsonValue,
						AppFamilyMembers.class);
				log.debug("vo:" + vo);
				boolean validate_flag = false;
				String membersMobile = "";
				String user_mobilePhone = "";
				String user_email = "";

				if (vo != null) {
					membersMobile = vo.getMembersMobile();
					validate_flag = this.checkValidateCode(
							System_Key.AppValidateTmp_Type_I, membersMobile,
							vo.getValidateCode());
				}

				log.debug("validate_flag:" + validate_flag);
				if (validate_flag) {
					AppUser user = null;
					if (EasyStr.isNotEmpty(membersMobile)) {
						// 判断用户登录方式
						if (membersMobile.indexOf(System_Flag.Split_Email) > 0) {
							user = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, null, membersMobile, null,
											null);
							user_email = membersMobile;

						} else if ((membersMobile
								.matches(System_Key.Mobile_RegExp))
								&& (membersMobile.length() == 11)) {
							user = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, membersMobile, null, null,
											null);
							user_mobilePhone = membersMobile;

						}
					}

					if (user != null) {
						vo.setMembersUserId(user.getPersonId());
					} else {
						user = new AppUser();
						/**
						 * 初始化 loginNo
						 */
						String loginNo = SequenceUtil
								.getSyncLoginNo(System_Key.loginSource_I);
						log.debug("loginNo:" + loginNo);
						user.setMobilePhone(user_mobilePhone);
						user.setEmail(user_email);
						user.setLoginNo(loginNo);
						user.setLoginPwd(MdEncryptUtil
								.MD5EncodeToHex(System_Key.default_LoginPwd));
						user.setValidFlag(System_Key.ValidFlag_Y);
						appUserService.addAppUser(user);

						AppUser user_new = appUserService
								.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
										null, user_mobilePhone, user_email,
										null, null);

						if (user_new != null) {
							vo.setMembersUserId(user_new.getPersonId());
						}

					}
					int i = appFamilyMembersService.addAppFamilyMembers(vo);

					if (i > System_ResultKey.Result_default) {

						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Add_Success);
						appStatus.setSuccess(true);
					} else {
						if (i == System_ResultKey.Result_default) {
							appStatus
									.setErrorCode(AppConstants.APP.actionError);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_Add_Faile);
							appStatus.setSuccess(false);
						} else {
							appStatus.setSuccess(false);
							appStatus
									.setErrorCode(ErrorCode.BusinessErrorCode.errorInsertData);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_FamilyMember_IsNotExit);
						}

					}

				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Invite_Code_Error);
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
	 * 修改 密码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-updateAppUserLoginPwd.json")
	@ResponseBody
	public Map<String, Object> updateAppUserLoginPwd(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("updateAppUserLoginPwd");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				AppUser user = IKnetUtil.binder.fromJson(appJsonValue,
						AppUser.class);
				log.debug("user:" + user);
				boolean flag = false;
				if (user != null) {
					AppUser u = appUserService.queryAppUserByPersonId(user
							.getPersonId());
					if (u != null) {
						String oldLoginPwd = MdEncryptUtil
								.MD5EncodeToHex(EasyStr.strToTrim(user
										.getOldLoginPwd()));
						if ((EasyStr.isNotEmpty(oldLoginPwd))
								&& (oldLoginPwd.equals(EasyStr.strToTrim(u
										.getLoginPwd())))) {
							flag = true;
						}

					}

					user.setLoginPwd(MdEncryptUtil.MD5EncodeToHex(EasyStr
							.strToTrim(user.getLoginPwd())));

				}
				log.debug("flag:" + flag);
				int i = 0;
				if (flag) {
					i = appUserService.updateAppUserLoginPwd(user);
				}
				if (i > 0) {
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Update_Success);
					appStatus.setSuccess(true);
				} else {
					appStatus.setErrorCode(AppConstants.APP.actionError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_OldLoginPwd_Error);
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
	 * 找回密码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-forgetLoginPwd.json")
	@ResponseBody
	public Map<String, Object> forgetLoginPwd(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("forgetLoginPwd");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				// 内容
				String content = EasyStr
						.initRandomByLength(System_Key.default_SendCode_Length);

				log.debug("content:" + content);

				// 号码
				String loginNo = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.loginNo) + "");

				if (EasyStr.isNotEmpty(loginNo)) {

					boolean validate_flag = false;
					AppValidateTmp vo = CommonUtilis.initAppValidateTmp(
							loginNo, System_Key.AppValidateTmp_Type_F,
							EasyStr.today(), content, System_Key.default_Clear);

					if (vo != null) {
						Map<String, Object> queryMap = IKnetUtil.getQueryMap();
						queryMap.put(System_Map.Map_VO, vo);
						int send_count = appValidateTmpService
								.queryAppValidateTmpTotalByMap(queryMap);
						if (send_count <= System_Key.default_3) {
							validate_flag = true;

						}
					}

					if (validate_flag) {
						boolean sendMessage_flag = false;
						AppUser appUser = null;
						// 判断用户登录方式
						if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
							log.debug("邮箱找回 密码");
							appUser = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, null, loginNo, null, null);

							if (appUser != null) {

								log.debug("邮件发送 验证码");
								MailSenderInfo mailInfo = new MailSenderInfo();
								String[] toAddressesArr = { loginNo };
								mailInfo.setToAddressesArr(toAddressesArr);
								String email_content = IKnetSendMessageUtil
										.initContents(
												System_Send_Message_Key.Send_Message_Content_Prefix_forget,
												content);
								mailInfo.setContent(email_content);
								int send_status = EmailServerFactory
										.sendEmail(mailInfo);
								log.debug("发送邮件状态 send_status" + send_status);
								if (send_status > System_Key.default_0) {

									sendMessage_flag = true;
								}
								vo.setSendWay(System_Key.AppValidateTmp_SendWay_E);
							}

						} else if ((loginNo.matches(System_Key.Mobile_RegExp))
								&& (loginNo.length() == 11)) {
							appUser = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, loginNo, null, null, null);
							if (appUser != null) {

								log.debug("手机 找回 密码");
								String result = IKnetSendMessageUtil
										.sendMessage(
												System_Send_Message_Key.Send_Message_Content_Prefix_forget,
												loginNo, content);
								log.debug("result:" + result);
								if ((System_Key.SendPhoneCode_Result_0
										.equalsIgnoreCase(result))) {
									sendMessage_flag = true;
								}
								vo.setSendWay(System_Key.AppValidateTmp_SendWay_S);
							}
						} else {
							appUser = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											loginNo, null, null, null, null);
							if (vo != null) {

								log.debug(" 通过  登录名 找回 密码");

							}
						}

						if (appUser == null) {

							log.debug(" 用户不存在 loginNo：" + loginNo);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_User_IsNotExit);
							appStatus.setSuccess(false);
						} else {
							if (sendMessage_flag) {
								int sendResult = appValidateTmpService
										.addAppValidateTmp(vo);
								log.debug("sendResult:" + sendResult);

								appStatus
										.setErrorCode(AppConstants.APP.actionSuccess);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_SendPhoneCode_Success);
								appStatus.setSuccess(true);
							} else {

								appStatus
										.setErrorCode(AppConstants.APP.actionError);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_SendPhoneCode_Faile);
								appStatus.setSuccess(false);
							}
						}

					} else {
						appStatus.setErrorCode(AppConstants.APP.dataSendError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_SendError);
						appStatus.setSuccess(false);
					}

				}

				else {
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);

				}
			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * 重置 密码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-resetLoginPwd.json")
	@ResponseBody
	public Map<String, Object> resetLoginPwd(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("forgetLoginPwd");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				// 内容
				String content = System_Key.default_LoginPwd;

				log.debug("content:" + content);

				// 号码
				String loginNo = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.loginNo) + "");

				// 号码
				String validateCode = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.validateCode) + "");

				if ((EasyStr.isNotEmpty(loginNo))
						&& (EasyStr.isNotEmpty(validateCode))) {

					AppValidateTmp vo = CommonUtilis.initAppValidateTmp(
							loginNo, System_Key.AppValidateTmp_Type_C,
							EasyStr.today(), content, System_Key.default_Clear);

					boolean validate_flag = this.checkValidateCode(
							System_Key.AppValidateTmp_Type_F, loginNo,
							validateCode);
					log.debug("validate_flag:" + validate_flag);
					if (validate_flag) {
						String loginPwd = MdEncryptUtil.MD5EncodeToHex(content);
						boolean sendMessage_flag = false;
						AppUser appUser = null;
						// 判断用户登录方式
						if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
							log.debug("邮箱找回 密码");
							appUser = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, null, loginNo, null, null);

							if (appUser != null) {
								appUser.setLoginPwd(loginPwd);
								int updateResult = appUserService
										.updateAppUserLoginPwd(appUser);
								log.debug("邮件发送 验证码");
								if (updateResult > System_Key.default_0) {
									MailSenderInfo mailInfo = new MailSenderInfo();
									String[] toAddressesArr = { loginNo };
									mailInfo.setToAddressesArr(toAddressesArr);
									String email_content = IKnetSendMessageUtil
											.initContents(
													System_Send_Message_Key.Send_Message_Content_Prefix_resetLoginPwd,
													content);
									mailInfo.setContent(email_content);
									int send_status = EmailServerFactory
											.sendEmail(mailInfo);
									log.debug("发送邮件状态 send_status"
											+ send_status);
									if (send_status > System_Key.default_0) {

										sendMessage_flag = true;
									}
									vo.setSendWay(System_Key.AppValidateTmp_SendWay_E);

								}
							}

						} else if ((loginNo.matches(System_Key.Mobile_RegExp))
								&& (loginNo.length() == 11)) {

							appUser = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, loginNo, null, null, null);

							if (appUser != null) {
								appUser.setLoginPwd(loginPwd);
								int updateResult = appUserService
										.updateAppUserLoginPwd(appUser);
								log.debug("手机 找回 密码");
								if (updateResult > System_Key.default_0) {
									String result = IKnetSendMessageUtil
											.sendMessage(
													System_Send_Message_Key.Send_Message_Content_Prefix_resetLoginPwd,
													loginNo, content);
									log.debug("result:" + result);
									if ((System_Key.SendPhoneCode_Result_0
											.equalsIgnoreCase(result))) {
										sendMessage_flag = true;
									}
									vo.setSendWay(System_Key.AppValidateTmp_SendWay_S);
								}
							}

						} else {
							appUser = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											loginNo, null, null, null, null);
							if (appUser != null) {

								log.debug(" 通过  登录名 找回 密码");
								appUser.setLoginPwd(loginPwd);
								appUserService.updateAppUserLoginPwd(appUser);
							}
						}

						if (appUser == null) {

							log.debug(" 用户不存在 loginNo：" + loginNo);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_User_IsNotExit);
							appStatus.setSuccess(false);
						} else {

							if (sendMessage_flag) {
								if (vo != null) {

									int sendResult = appValidateTmpService
											.addAppValidateTmp(vo);
									log.debug("sendResult:" + sendResult);
								}
								appStatus
										.setErrorCode(AppConstants.APP.actionSuccess);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_ResetLoginPwd_Success);
								appStatus.setSuccess(true);
								map.put(System_App_Key.appStatus, appStatus);
							} else {
								appStatus
										.setErrorCode(AppConstants.APP.actionError);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_ResetLoginPwd_Faile);
								appStatus.setSuccess(false);
							}
						}
					} else {
						appStatus.setErrorCode(AppConstants.APP.actionError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_ValidateCode_Error);
						appStatus.setSuccess(false);
					}

				}

				else {
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);

				}
			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * APP 查询 用户 测量 次数
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppUseTotalByPersonId.json")
	@ResponseBody
	public Map<String, Object> queryAppUseTotalByPersonId(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppUseTotalByPersonId");
		log.debug("APP发送过来的参数appJson:" + appJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		AppUseTotal appUserTotal = new AppUseTotal();
		AppMedicalRecord appMedicalRecord = new AppMedicalRecord();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				String personIdValue = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.personId) + "");
				log.debug("personIdValue:" + personIdValue);
				if (EasyStr.isNotEmpty(personIdValue)) {
					String personId = personIdValue;
					log.debug("personId:" + personId);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					// 查询用户测量次数
					appUserTotal = appUseTotalService
							.queryAppUseTotalByPersonId(personId);
					map.put(System_App_Key.appUserTotal, appUserTotal);

					appMedicalRecord = appMedicalRecordService
							.queryAppMedicalRecordForLastOneByPersonId(personId);
					map.put(System_App_Key.appMedicalRecord, appMedicalRecord);
				} else {
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Faile);
					appStatus.setSuccess(false);
					appStatus.setErrorCode(AppConstants.APP.passwordError);
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
	 * APP 检测版本更新
	 * 
	 * @param appVersionJson
	 * @return
	 */
	@RequestMapping("/app-checkAppVersion.json")
	@ResponseBody
	public Map<String, Object> checkAppVersion(
			@RequestParam(value = System_Key.RequestParam_appVersionJson, required = false) final String appVersionJson) {
		log.debug("checkAppVersion");
		log.debug("APP发送过来的参数 appVersionJson:" + appVersionJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		AppVersion appVersion = null;
		try {
			String appVersionJsonValue = AppJsonUtil
					.getVersionJsonValue(appVersionJson);
			if (EasyStr.isNotEmpty(appVersionJsonValue)) {

				appVersion = IKnetUtil.binder.fromJson(appVersionJsonValue,
						AppVersion.class);

				appVersion = IKnetAppVersionUtil.checkAppVersion(appVersion);
				appStatus.setSuccess(true);
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

		log.debug("appVersion:" + appVersion);
		map.put(System_App_Key.appVersion, appVersion);
		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * APP 第三方 登录
	 * 
	 * @param appJson
	 * @param appVersionJson
	 * @return
	 */
	@RequestMapping("/app-loginByOther.json")
	@ResponseBody
	public Map<String, Object> loginByOther(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			@RequestParam(value = System_Key.RequestParam_appVersionJson, required = false) final String appVersionJson) {
		log.debug("loginByOther");
		log.debug("APP发送过来的参数 appJson:" + appJson);
		log.debug("APP发送过来的参数 appVersionJson:" + appVersionJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppUser appUser = null;
		AppStatus appStatus = new AppStatus();
		AppVersion appVersion = null;
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			String appVersionJsonValue = AppJsonUtil
					.getVersionJsonValue(appVersionJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				AppUser user = IKnetUtil.binder.fromJson(appJsonValue,
						AppUser.class);

				appVersion = IKnetUtil.binder.fromJson(appVersionJsonValue,
						AppVersion.class);

				appVersion = IKnetAppVersionUtil.checkAppVersion(appVersion);

				log.debug("user:" + user);
				String loginSource = "";
				String loginReturnId = "";
				String accessToken = "";
				if (user != null) {
					loginSource = EasyStr.strToTrim(user.getLoginSource());
					loginReturnId = EasyStr.strToTrim(user.getLoginReturnId());
					accessToken = EasyStr.strToTrim(user.getAccessToken());
				}
				if ((EasyStr.isNotEmpty(loginSource))
						&& (EasyStr.isNotEmpty(loginReturnId))) {
					appUser = appUserService.queryAppUserByLoginReturnId(
							loginSource, loginReturnId, accessToken);
				}

				if (appUser != null) {
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Login_Success);
					appStatus.setSuccess(true);
					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					/**
					 * 登录 输入名 作为 显示名
					 */
					appUser.setShowName(appUser.getLoginNo());
					map.put(System_App_Key.appUser, appUser);
				} else {
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_User_IsNotExit);
					appStatus.setSuccess(false);
					appStatus.setErrorCode(AppConstants.APP.passwordError);
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

		log.debug("appVersion:" + appVersion);
		map.put(System_App_Key.appVersion, appVersion);
		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * 发送 家属 邀请码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-sendFamilyMembersInviteCode.json")
	@ResponseBody
	public Map<String, Object> sendFamilyMembersInviteCode(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("sendFamilyMembersInviteCode");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				// 内容
				String content = EasyStr
						.initRandomByLength(System_Key.default_SendCode_Length);

				log.debug("content:" + content);

				// 用户
				String personId = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.personId) + "");

				// 号码
				String loginNo = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.loginNo) + "");

				log.debug("personId:" + personId);
				log.debug("loginNo:" + loginNo);

				if (EasyStr.isNotEmpty(loginNo)) {
					/**
					 * 优先 判断用户的 发送 次数
					 */
					boolean validate_flag = false;
					AppValidateTmp vo = CommonUtilis.initAppValidateTmp(
							loginNo, System_Key.AppValidateTmp_Type_I,
							EasyStr.today(), content, System_Key.default_Clear);

					if (vo != null) {
						Map<String, Object> queryMap = IKnetUtil.getQueryMap();
						queryMap.put(System_Map.Map_VO, vo);
						int send_count = appValidateTmpService
								.queryAppValidateTmpTotalByMap(queryMap);
						if (send_count <= System_Key.default_3) {
							validate_flag = true;

						}
					}

					if (validate_flag) {
						/**
						 * 判断 邀请的 家属 是否是 重复邀请
						 */
						boolean familyFlag = false;

						AppUser user = null;
						// 判断用户登录方式
						if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
							user = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, null, loginNo, null, null);

						} else if ((loginNo.matches(System_Key.Mobile_RegExp))
								&& (loginNo.length() == 11)) {
							user = appUserService
									.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
											null, loginNo, null, null, null);

						}

						if (user != null) {
							AppFamilyMembers appFamilyMembers = new AppFamilyMembers();
							String membersUserId = user.getPersonId();
							appFamilyMembers.setMembersUserId(membersUserId);
							appFamilyMembers.setPersonId(personId);
							int cout_result = appFamilyMembersService
									.queryAppFamilyMemberCount(appFamilyMembers);
							if (cout_result == System_ResultKey.Result_default) {
								familyFlag = true;
							}

						} else {
							familyFlag = true;
						}

						log.debug("familyFlag:" + familyFlag);
						if (familyFlag) {
							boolean sendMessage_flag = false;

							// 判断用户登录方式
							if (loginNo.indexOf(System_Flag.Split_Email) > 0) {

								log.debug("邮件发送 邀请码");
								MailSenderInfo mailInfo = new MailSenderInfo();
								String[] toAddressesArr = { loginNo };
								mailInfo.setToAddressesArr(toAddressesArr);
								String email_content = IKnetSendMessageUtil
										.initContents(
												System_Send_Message_Key.Send_Message_Content_Prefix_InviteCode,
												content);
								mailInfo.setContent(email_content);
								int send_status = EmailServerFactory
										.sendEmail(mailInfo);
								log.debug("发送邮件状态 send_status" + send_status);
								if (send_status > System_Key.default_0) {

									sendMessage_flag = true;
								}
								vo.setSendWay(System_Key.AppValidateTmp_SendWay_E);

							} else if ((loginNo
									.matches(System_Key.Mobile_RegExp))
									&& (loginNo.length() == 11)) {

								log.debug("手机 发送 邀请码");
								String result = IKnetSendMessageUtil
										.sendMessage(
												System_Send_Message_Key.Send_Message_Content_Prefix_InviteCode,
												loginNo, content);
								log.debug("result:" + result);
								if ((System_Key.SendPhoneCode_Result_0
										.equalsIgnoreCase(result))) {
									sendMessage_flag = true;
								}
								vo.setSendWay(System_Key.AppValidateTmp_SendWay_S);

							}

							if (sendMessage_flag) {
								int sendResult = appValidateTmpService
										.addAppValidateTmp(vo);
								log.debug("sendResult:" + sendResult);

								appStatus
										.setErrorCode(AppConstants.APP.actionSuccess);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_Send_Invite_Code_Success);
								appStatus.setSuccess(true);
							} else {

								appStatus
										.setErrorCode(AppConstants.APP.actionError);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_Send_Invite_Code_Faile);
								appStatus.setSuccess(false);
							}

						} else {

							appStatus.setSuccess(false);
							appStatus
									.setErrorCode(ErrorCode.BusinessErrorCode.errorInsertData);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_FamilyMember_IsNotExit);
						}

					} else {
						appStatus.setErrorCode(AppConstants.APP.dataSendError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_SendError);
						appStatus.setSuccess(false);
					}

				}

				else {
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);

				}
			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * APP 检测 注册用户
	 * 
	 * @param appVersionJson
	 * @return
	 */
	@RequestMapping("/app-checkIknetUser.json")
	@ResponseBody
	public Map<String, Object> checkIknetUser(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("checkIknetUser");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				// 号码
				String mobilePhone = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.loginNo) + "");

				log.debug("mobilePhone:" + mobilePhone);

				AppIknetUser appIknetUser = this.initAppIknetUser(mobilePhone);

				if (appIknetUser != null) {

					appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
					appStatus.setSuccess(true);
					map.put(System_App_Key.appIknetUser, appIknetUser);
				} else {
					appStatus.setErrorCode(AppConstants.APP.dataSendError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_User_IsNotExit);
					appStatus.setSuccess(false);
				}

			} else {
				appStatus.setErrorCode(AppConstants.APP.actionError);
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
		log.debug("map:" + map);
		return map;

	}

	/**
	 * 查询 环信 好友 昵称
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-queryAppIknetEasemobUser.json")
	@ResponseBody
	public Map<String, Object> queryAppIknetEasemobUser(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("queryAppIknetEasemobUser");
		log.debug("APP发送过来的参数appJson:" + appJson);
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				// 查询 环信 好友 昵称
				List<AppIknetEasemobUser> list = null;

				String[] personIdArr = (String[]) IKnetUtil
						.appJsonValueToString(appJsonValue);
				log.debug("personIdArr:" + personIdArr);

				if (personIdArr != null) {

					Map<String, Object> queryMap = IKnetUtil.getQueryMap();
					queryMap.put(System_Key.arr, personIdArr);
					list = appUserService
							.queryAppIknetEasemobUserByMap(queryMap);
				}
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Query_Success);
				appStatus.setSuccess(true);
				appStatus.setErrorCode(AppConstants.APP.actionSuccess);
				map.put(System_App_Key.appIknetEasemobUser, list);

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
	 * 发送 用户 手机号码|邮箱 绑定码
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-sendAppUserValidateInviteCode.json")
	@ResponseBody
	public Map<String, Object> sendAppUserValidateInviteCode(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("sendAppUserValidateInviteCode");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				// 内容
				String content = EasyStr
						.initRandomByLength(System_Key.default_SendCode_Length);

				log.debug("content:" + content);

				// 号码
				String loginNo = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.loginNo) + "");

				if (EasyStr.isNotEmpty(loginNo)) {
					AppIknetUser appIknetUser = this.initAppIknetUser(loginNo);

					if (appIknetUser == null) {

						boolean validate_flag = false;
						AppValidateTmp vo = null;
						if (loginNo.indexOf(System_Flag.Split_Email) > 0) {

							vo = CommonUtilis.initAppValidateTmp(loginNo,
									System_Key.AppValidateTmp_Type_E,
									EasyStr.today(), content,
									System_Key.default_Clear);
						} else if ((loginNo.matches(System_Key.Mobile_RegExp))
								&& (loginNo.length() == 11)) {

							vo = CommonUtilis.initAppValidateTmp(loginNo,
									System_Key.AppValidateTmp_Type_M,
									EasyStr.today(), content,
									System_Key.default_Clear);
						}

						if (vo != null) {
							Map<String, Object> queryMap = IKnetUtil
									.getQueryMap();
							queryMap.put(System_Map.Map_VO, vo);
							int send_count = appValidateTmpService
									.queryAppValidateTmpTotalByMap(queryMap);
							if (send_count <= System_Key.default_3) {
								validate_flag = true;

							}
						}

						if (validate_flag) {
							boolean sendMessage_flag = false;

							// 判断用户登录方式
							if (loginNo.indexOf(System_Flag.Split_Email) > 0) {

								log.debug("邮件发送 邀请码");
								MailSenderInfo mailInfo = new MailSenderInfo();
								String[] toAddressesArr = { loginNo };
								mailInfo.setToAddressesArr(toAddressesArr);
								String email_content = IKnetSendMessageUtil
										.initContents(
												System_Send_Message_Key.Send_Message_Content_Prefix_Email_InviteCode,
												content);
								mailInfo.setContent(email_content);
								int send_status = EmailServerFactory
										.sendEmail(mailInfo);
								log.debug("发送邮件状态 send_status" + send_status);
								if (send_status > System_Key.default_0) {

									sendMessage_flag = true;
								}
								vo.setSendWay(System_Key.AppValidateTmp_SendWay_E);

							} else if ((loginNo
									.matches(System_Key.Mobile_RegExp))
									&& (loginNo.length() == 11)) {

								log.debug("手机 发送 邀请码");
								String result = IKnetSendMessageUtil
										.sendMessage(
												System_Send_Message_Key.Send_Message_Content_Prefix_MobilePhone_InviteCode,
												loginNo, content);
								log.debug("result:" + result);
								if ((System_Key.SendPhoneCode_Result_0
										.equalsIgnoreCase(result))) {
									sendMessage_flag = true;
								}
								vo.setSendWay(System_Key.AppValidateTmp_SendWay_S);

							}

							if (sendMessage_flag) {
								int sendResult = appValidateTmpService
										.addAppValidateTmp(vo);
								log.debug("sendResult:" + sendResult);

								appStatus
										.setErrorCode(AppConstants.APP.actionSuccess);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_Send_Validate_Invite_Code_Success);
								appStatus.setSuccess(true);
							} else {

								appStatus
										.setErrorCode(AppConstants.APP.actionError);
								appStatus
										.setMsg(System_Result_Message_Key.Result_Message_Send_Validate_Invite_Code_Faile);
								appStatus.setSuccess(false);
							}
						} else {
							appStatus
									.setErrorCode(AppConstants.APP.dataSendError);
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_SendError);

							appStatus.setSuccess(false);
						}
					} else {
						appStatus.setErrorCode(AppConstants.APP.dataSendError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_User_Validate_IsExit);
						appStatus.setSuccess(false);
					}

				} else {
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);

				}
			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * 验证 用户的 绑定码 并修改对应的 用户 手机号码|邮箱
	 * 
	 * @param appJson
	 * @return
	 */
	@RequestMapping("/app-updateAppUserByValidateInviteCode.json")
	@ResponseBody
	public Map<String, Object> updateAppUserByValidateInviteCode(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson) {
		log.debug("updateAppUserByValidateInviteCode");
		log.debug("APP发送过来的参数appJson:" + appJson);
		AppStatus appStatus = new AppStatus();
		Map<String, Object> map = IKnetUtil.getResponseMap();
		try {
			String appJsonValue = AppJsonUtil.getJsonValue(appJson);
			if (EasyStr.isNotEmpty(appJsonValue)) {

				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				// 号码
				String personId = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.personId) + "");

				// 号码
				String loginNo = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.loginNo) + "");

				/**
				 * 绑定码
				 */
				String validateCode = EasyStr.strToTrim(mapJsonValue
						.get(System_Key.validateCode) + "");
				log.debug("personId:" + personId);
				log.debug("loginNo:" + loginNo);
				log.debug("validateCode:" + validateCode);

				if ((EasyStr.isNotEmpty(loginNo))
						&& (EasyStr.isNotEmpty(validateCode))) {
					Map<String, Object> userValidateMap = IKnetUtil
							.getQueryMap();
					userValidateMap.put(System_Key.personId, personId);

					boolean flag = false;

					if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
						flag = this.checkValidateCode(
								System_Key.AppValidateTmp_Type_E, loginNo,
								validateCode);
						userValidateMap.put(System_Key.email, loginNo);

					}

					else if ((loginNo.matches(System_Key.Mobile_RegExp))
							&& (loginNo.length() == 11)) {
						flag = this.checkValidateCode(
								System_Key.AppValidateTmp_Type_M, loginNo,
								validateCode);
						userValidateMap.put(System_Key.mobilePhone, loginNo);
					}
					if (flag) {
						int res = appUserService
								.updateAppUserValidateByMap(userValidateMap);
						if (res > System_ResultKey.Result_default) {
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_User_Validate_Success);
							appStatus.setSuccess(true);
							appStatus
									.setErrorCode(AppConstants.APP.actionSuccess);
						} else {
							appStatus
									.setMsg(System_Result_Message_Key.Result_Message_User_Validate_Faile);
							appStatus.setSuccess(false);
							appStatus
									.setErrorCode(AppConstants.APP.actionError);
						}
					} else {
						appStatus.setErrorCode(AppConstants.APP.jsonDataError);
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Validate_Invite_Code_Error);
						appStatus.setSuccess(false);
					}
				} else {
					appStatus.setErrorCode(AppConstants.APP.jsonDataError);
					appStatus
							.setMsg(System_Result_Message_Key.Result_Message_Error);
					appStatus.setSuccess(false);
				}

			} else {
				appStatus.setErrorCode(AppConstants.APP.jsonDataError);
				appStatus
						.setMsg(System_Result_Message_Key.Result_Message_Error);
				appStatus.setSuccess(false);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			appStatus.setErrorCode(AppConstants.APP.actionError);
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
		}

		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	/**
	 * 校验 验证码
	 * 
	 * @param validateType
	 * @param loginNo
	 * @param validateCode
	 * @return
	 */
	private boolean checkValidateCode(String validateType, String loginNo,
			String validateCode) {
		log.debug("validateCode:" + validateCode);
		validateCode = EasyStr.strToTrim(validateCode);
		boolean flag = false;
		if (EasyStr.isNotEmpty(validateCode)) {
			AppValidateTmp vo = CommonUtilis
					.initAppValidateTmp(loginNo, validateType, EasyStr.today(),
							"", System_Key.default_Clear);
			String last_validateCode = "";
			if (vo != null) {
				Map<String, Object> queryMap = IKnetUtil.getQueryMap();
				queryMap.put(System_Map.Map_VO, vo);
				try {
					last_validateCode = appValidateTmpService
							.queryLastValidateCodeByMap(queryMap);
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}

			log.debug("last_validateCode:" + last_validateCode);
			if (validateCode.equalsIgnoreCase(last_validateCode)) {
				flag = true;
			}

		}
		log.debug("checkValidateCode-->flag:" + flag);
		return flag;
	}

	/**
	 * 注册 发送 验证码 前 先判断该用户 是否 注册过
	 * 
	 * @param appUser
	 * @return
	 */
	private AppIknetUser initAppIknetUser(String loginNo) {
		AppIknetUser vo = null;
		AppUser appUser = null;

		try {
			// 判断用户登录方式
			if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
				appUser = appUserService
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								null, null, loginNo, null, null);

			} else if ((loginNo.matches(System_Key.Mobile_RegExp))
					&& (loginNo.length() == 11)) {
				appUser = appUserService
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								null, loginNo, null, null, null);
			} else {
				appUser = appUserService
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								loginNo, null, null, null, null);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		boolean iknetUserFlag = false;
		String personId = System_Key.default_Clear;
		if (appUser != null) {
			personId = appUser.getPersonId();
			iknetUserFlag = true;
		}

		if (iknetUserFlag) {
			vo = new AppIknetUser();
			vo.setPersonId(personId);
			vo.setIknetUserFlag(iknetUserFlag);

		}
		log.debug("initAppIknetUser-->vo:" + vo);
		return vo;
	}

	/**
	 * APP 购买设备
	 * 
	 * @param appVersionJson
	 * @return
	 */
	@RequestMapping("/app-buyEquipMent.json")
	@ResponseBody
	public Map<String, Object> buyEquipMent() {
		log.debug("buyEquipMent");
		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		String buyEquipMentUrl = IKnetAppVersionUtil.getBuyEquipMentUrl();
		log.debug("buyEquipMentUrl:" + buyEquipMentUrl);
		try {
			if (EasyStr.isNotEmpty(buyEquipMentUrl)) {
				map.put(System_App_Key.buyEquipMentUrl, buyEquipMentUrl);
				appStatus.setSuccess(true);
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
