/**
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年10月28日 下午4:41:44
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
package com.iknet.server.appserver.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iknet.commons.baseCode.AppConstants;
import com.iknet.commons.baseCode.BusiConstants.System_App_Key;
import com.iknet.commons.baseCode.BusiConstants.System_IKnet_Server_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Result_Message_Key;
import com.iknet.commons.baseEntity.AppStatus;
import com.iknet.commons.util.AppJsonUtil;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.FileUtil;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.commons.util.SendRequest;
import com.iknet.commons.util.SystemUtil;

/**
 * 文件上传 Copyright: Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights
 * Reserved. Date: 2014年10月28日 下午4:42:01 Author: luozd Version: IKNET V:1.0.0
 * Description: Initialize
 */
@Controller
public class AppFileUploadController {
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 上传文件到 固定的目录下
	 * 
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/app-uploadIKnetFile.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadIKnetFile(
			@RequestParam(value = System_Key.RequestParam_appJson, required = false) final String appJson,
			HttpServletRequest request) throws Exception {
		log.debug("uploadIKnetFile");
		log.debug("APP发送过来的参数appJson:" + appJson);

		Map<String, Object> map = IKnetUtil.getResponseMap();
		AppStatus appStatus = new AppStatus();
		try {

			String appJsonValue = AppJsonUtil.getJsonValue(appJson);

			if (EasyStr.isNotEmpty(appJsonValue)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Map<String, Object> mapJsonValue = IKnetUtil
						.appJsonValueToMap(appJsonValue);
				log.debug("mapJsonValue:" + mapJsonValue);

				String file_key = "file";
				if (mapJsonValue != null) {
					file_key = mapJsonValue
							.get(System_IKnet_Server_Key.defaultFileName) + "";
				}
				log.debug("file_key:" + file_key);
				MultipartFile multipartFile = multipartRequest
						.getFile(file_key);

				String uploadFileName = "";
				String fileName = "";
				if (multipartFile != null) {
					fileName = multipartFile.getOriginalFilename();
					if (EasyStr.isNotEmpty(fileName)) {
						uploadFileName = this.getRemoteUploadFileName(fileName);
					}
				}

				log.debug("uploadFileName:" + uploadFileName);
				String tmp = SystemUtil.getIKnet_Portal_Server_Tmp();
				boolean flag = false;
				if ((EasyStr.isNotEmpty(tmp))
						&& (EasyStr.isNotEmpty(uploadFileName))
						&& (EasyStr.isNotEmpty(fileName))) {
					String tmp_fileName = tmp + "/" + fileName;

					File file = new File(tmp_fileName);
					try {
						multipartFile.transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (EasyStr.isNotEmpty(uploadFileName)) {
						boolean uploadFlag = this.remoteUploadFile(
								tmp_fileName, uploadFileName);
						if (uploadFlag) {

							FileUtil.deleteFile(tmp_fileName);
							flag = true;
							appStatus.setMsg(uploadFileName);
						}
					}

					if (flag) {
						appStatus.setSuccess(true);
						appStatus.setErrorCode(AppConstants.APP.actionSuccess);
					} else {
						appStatus
								.setMsg(System_Result_Message_Key.Result_Message_Upload_Faile);
						appStatus.setSuccess(false);
						appStatus.setErrorCode(AppConstants.APP.jsonDataError);
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
			e.getStackTrace();
			log.error(e.getMessage());
			appStatus
					.setMsg(System_Result_Message_Key.Result_Message_Exception);
			appStatus.setSuccess(false);
			appStatus.setErrorCode(AppConstants.APP.actionError);

		}
		map.put(System_App_Key.appStatus, appStatus);
		return map;

	}

	private String getRemoteUploadFileName(String fileName) {
		log.debug("fileName:" + fileName);
		String uploadFileName = "";
		String url_UploadFileName = SystemUtil
				.getUrlForIKnet_Server_Method_UploadFileName();
		Map<String, Object> resultMap = IKnetUtil.getResponseMap();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Map<String, Object> m = IKnetUtil.getResponseMap();
		m.put(System_IKnet_Server_Key.defaultFileName, fileName);
		String json = IKnetUtil.binder.toJson(m);
		log.debug("json:" + json);
		nvps.add(new BasicNameValuePair(System_IKnet_Server_Key.file, json));
		String resultJsonData = SendRequest.sendMessage(url_UploadFileName,
				nvps);

		log.debug("resultJsonData:" + resultJsonData);
		if (EasyStr.isNotEmpty(resultJsonData)) {
			resultMap = IKnetUtil.jsonDataToMap(resultJsonData);
			uploadFileName = (String) resultMap
					.get(System_IKnet_Server_Key.msg);
		}

		return uploadFileName;
	}

	@SuppressWarnings("finally")
	private boolean remoteUploadFile(String fileName, String uploadFileName) {
		boolean uploadFlag = false;
		File targetFile = null;// TODO 指定上传文件

		targetFile = new File(fileName);

		String url = SystemUtil.getUrlForIKnet_Server_Method_Upload();

		PostMethod filePost = new PostMethod(url); // 若没有commons-codec-1.4-bin.zip,

		try {
			org.apache.commons.httpclient.NameValuePair pa = new org.apache.commons.httpclient.NameValuePair();
			pa.setName(System_IKnet_Server_Key.uploadFileName);
			pa.setValue(uploadFileName);
			org.apache.commons.httpclient.NameValuePair arr[] = { pa };
			filePost.setQueryString(arr);
			Part[] parts = { new FilePart(System_IKnet_Server_Key.file,
					targetFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);

			log.debug("status:" + status);
			if (status == HttpStatus.SC_OK) {
				log.debug("上传成功");
				uploadFlag = true;
				// 上传成功
			} else {
				log.error("上传失败");
				uploadFlag = false;
				// 上传失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
			return uploadFlag;
		}

	}
}
