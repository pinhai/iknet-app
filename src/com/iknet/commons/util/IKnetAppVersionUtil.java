/**
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月11日 下午5:05:56
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
package com.iknet.commons.util;

import org.apache.log4j.Logger;

import com.iknet.commons.baseCode.BusiConstants.System_App_Version;
import com.iknet.commons.baseCode.BusiConstants.System_ResourceFile;
import com.iknet.server.appserver.pojo.AppVersion;

/**
 * App Version Util Copyright: Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All
 * Rights Reserved. Date: 2014年11月11日 下午5:06:01 Author: luozd Version: IKNET
 * V:1.0.0 Description: Initialize
 */
public class IKnetAppVersionUtil {
	private static Logger log = Logger.getLogger(IKnetAppVersionUtil.class);

	private static ResourceFile System_Resource = System_ResourceFile.System_Resource;

	private static String IKnet_App_Version_Path = "";

	private static ResourceFile IKnet_App_Version_Resource = null;
	static {
		IKnet_App_Version_Path = IKnetAppVersionUtil
				.getIKnet_App_Version_Path();
		IKnet_App_Version_Resource = IKnetAppVersionUtil
				.getIKnetAppVersionResourceFile();
	}

	private static String getIKnet_App_Version_Path() {
		if (EasyStr.isEmpty(IKnet_App_Version_Path)) {
			String iknet_App_Version_Path = EasyStr
					.strToTrim(System_Resource
							.getPropertyValue(System_App_Version.IKnet_App_Version_Path));

			String iknet_App_Version_Name = EasyStr
					.strToTrim(System_Resource
							.getPropertyValue(System_App_Version.IKnet_App_Version_Name));
			if ((EasyStr.isNotEmpty(iknet_App_Version_Path))
					&& (EasyStr.isNotEmpty(iknet_App_Version_Name))) {
				IKnet_App_Version_Path = iknet_App_Version_Path
						+ iknet_App_Version_Name;
			}

		}
		return IKnet_App_Version_Path;
	}

	private static ResourceFile getIKnetAppVersionResourceFile() {
		if (IKnet_App_Version_Resource == null) {
			String IKnet_App_Version_Path = IKnetAppVersionUtil
					.getIKnet_App_Version_Path();
			if (EasyStr.isNotEmpty(IKnet_App_Version_Path)) {

				IKnet_App_Version_Resource = new ResourceFile(
						IKnet_App_Version_Path);
			}

		}
		return IKnet_App_Version_Resource;
	}

	private static String getAppVersion_Android() {
		String appVersion = "";

		if (IKnet_App_Version_Resource != null) {

			appVersion = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.Android_AppVersion);
		}

		return appVersion;
	}

	private static String getAppVersion_IOS() {
		String appVersion = "";

		if (IKnet_App_Version_Resource != null) {

			appVersion = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.IOS_AppVersion);
		}

		return appVersion;
	}

	private static String getAppVersion_IOS_PAD() {
		String appVersion = "";

		if (IKnet_App_Version_Resource != null) {

			appVersion = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.IOS_PAD_AppVersion);
		}

		return appVersion;
	}

	private static String getAppVersion_Android_PAD() {
		String appVersion = "";

		if (IKnet_App_Version_Resource != null) {

			appVersion = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.Android_PAD_AppVersion);
		}

		return appVersion;
	}

	private static String getAppDownLoadUrl_IOS() {
		String appDownLoadUrl = "";

		if (IKnet_App_Version_Resource != null) {

			appDownLoadUrl = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.IOS_AppDownLoadUrl);
		}

		return appDownLoadUrl;
	}

	private static String getAppDownLoadUrl_Android() {
		String appDownLoadUrl = "";

		if (IKnet_App_Version_Resource != null) {

			appDownLoadUrl = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.Android_AppDownLoadUrl);
		}

		return appDownLoadUrl;
	}

	private static String getAppDownLoadUrl_Android_PAD() {
		String appDownLoadUrl = "";

		if (IKnet_App_Version_Resource != null) {

			appDownLoadUrl = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.Android_PAD_AppDownLoadUrl);
		}

		return appDownLoadUrl;
	}

	private static String getAppVersion(String appFlag) {
		String appVersion = "";

		if (System_App_Version.App_IOS.equalsIgnoreCase(appFlag)) {
			appVersion = IKnetAppVersionUtil.getAppVersion_IOS();
		}

		if (System_App_Version.App_Android.equalsIgnoreCase(appFlag)) {
			appVersion = IKnetAppVersionUtil.getAppVersion_Android();
		}

		if (System_App_Version.App_Android_PAD.equalsIgnoreCase(appFlag)) {
			appVersion = IKnetAppVersionUtil.getAppVersion_Android_PAD();
		}
		if (System_App_Version.App_IOS_PAD.equalsIgnoreCase(appFlag)) {
			appVersion = IKnetAppVersionUtil.getAppVersion_IOS_PAD();
		}
		return appVersion;
	}

	private static boolean checkAppVersionByVersion(String appFlag,
			String appVersion) {
		boolean newVersionFlag = false;
		String default_appVersion = IKnetAppVersionUtil.getAppVersion(appFlag);
		log.debug("default_appVersion:" + default_appVersion);
		if (EasyStr.isNotEmpty(default_appVersion)) {

			if (!default_appVersion.equalsIgnoreCase(appVersion)) {
				newVersionFlag = true;
			}

		}

		log.debug("newVersionFlag:" + newVersionFlag);
		return newVersionFlag;
	}

	private static String getAppDownLoadUrl(String appFlag) {
		String appDownLoadUrl = "";
		if (System_App_Version.App_IOS.equalsIgnoreCase(appFlag)) {
			appDownLoadUrl = IKnetAppVersionUtil.getAppDownLoadUrl_IOS();
		}

		if (System_App_Version.App_Android.equalsIgnoreCase(appFlag)) {
			appDownLoadUrl = IKnetAppVersionUtil.getAppDownLoadUrl_Android();
		}

		if (System_App_Version.App_Android_PAD.equalsIgnoreCase(appFlag)) {
			appDownLoadUrl = IKnetAppVersionUtil
					.getAppDownLoadUrl_Android_PAD();
		}
		if (System_App_Version.App_IOS_PAD.equalsIgnoreCase(appFlag)) {
			appDownLoadUrl = IKnetAppVersionUtil
					.getAppDownLoadUrl_Android_PAD();
		}
		log.debug("appDownLoadUrl:" + appDownLoadUrl);
		return appDownLoadUrl;
	}

	/**
	 * 检查 app 版本号
	 * 
	 * <pre>
	 * 当有 新的 版本号 后 将对应的  appDownLoadUrl 返回 必将  将  newVersionFlag:设置为  true
	 * </pre>
	 * 
	 * @param appVersionVO
	 * @return
	 */
	public static AppVersion checkAppVersion(AppVersion appVersionVO) {

		if (appVersionVO == null) {
			appVersionVO = new AppVersion();
		}

		String appFlag = appVersionVO.getAppFlag();
		String appVersion = appVersionVO.getAppVersion();
		String appDownLoadUrl = appVersionVO.getAppDownLoadUrl();

		boolean newVersionFlag = IKnetAppVersionUtil.checkAppVersionByVersion(
				appFlag, appVersion);
		if (newVersionFlag) {
			appDownLoadUrl = IKnetAppVersionUtil.getAppDownLoadUrl(appFlag);
		}
		log.debug("newVersionFlag:" + newVersionFlag);
		log.debug("appDownLoadUrl:" + appDownLoadUrl);

		appVersionVO.setNewVersionFlag(newVersionFlag);
		appVersionVO.setAppDownLoadUrl(appDownLoadUrl);
		return appVersionVO;
	}

	public static String getBuyEquipMentUrl() {
		String buyEquipMentUrl = "";

		if (IKnet_App_Version_Resource != null) {

			buyEquipMentUrl = IKnet_App_Version_Resource
					.getPropertyValue(System_App_Version.BuyEquipMentUrl);
		}

		log.debug("buyEquipMentUrl:" + buyEquipMentUrl);

		return buyEquipMentUrl;
	}
}