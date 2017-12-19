package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppIknetEasemobUser;
import com.iknet.server.appserver.pojo.AppUser;

/**
 * APP用户
 * 
 * @author luozd
 * 
 */
public interface AppUserService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppUserMapper.";

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppUser(AppUser vo) throws Exception;

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppUser(AppUser vo) throws Exception;

	/**
	 * AppUser 查询
	 * 
	 * @param loginNo
	 * @param mobilePhone
	 * @param email
	 * @param loginSource
	 * @param loginReturnId
	 * @return
	 * @throws Exception
	 */
	public AppUser queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
			String loginNo, String mobilePhone, String email,
			String loginSource, String loginReturnId) throws Exception;

	/**
	 * 修改 密码
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppUserLoginPwd(AppUser vo) throws Exception;

	/**
	 * 根据 personId 查询
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public AppUser queryAppUserByPersonId(String personId) throws Exception;

	/**
	 * AppUser 查询
	 * 
	 * @param loginSource
	 * @param loginReturnId
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public AppUser queryAppUserByLoginReturnId(String loginSource,
			String loginReturnId, String accessToken) throws Exception;

	/**
	 * 查询 环信 好友 昵称
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppIknetEasemobUser> queryAppIknetEasemobUserByMap(
			Map<String, Object> map) throws Exception;

	/**
	 * 用户 班定 手机号码| 邮箱
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateAppUserValidateByMap(Map<String, Object> map)
			throws Exception;
}
