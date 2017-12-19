package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iknet.commons.baseCode.BusiConstants.System_Community_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Map;
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.util.BaseService;
import com.iknet.commons.util.CommonUtilis;
import com.iknet.commons.util.DateUtil;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.commons.util.MdEncryptUtil;
import com.iknet.commons.util.SequenceUtil;
import com.iknet.server.appserver.pojo.AppIknetEasemobUser;
import com.iknet.server.appserver.pojo.AppReviceData;
import com.iknet.server.appserver.pojo.AppUseTotal;
import com.iknet.server.appserver.pojo.AppUser;
import com.iknet.server.appserver.pojo.AppUserThreeInfo;
import com.iknet.server.appserver.service.AppMergeAccountService;
import com.iknet.server.appserver.service.AppReviceDataService;
import com.iknet.server.appserver.service.AppUseTotalService;
import com.iknet.server.appserver.service.AppUserService;
import com.iknet.server.appserver.service.AppUserThreeService;

/**
 * APP用户
 * 
 * @author luozd
 * 
 */
@Service("appUserService")
public class AppUserServiceImpl extends BaseService implements AppUserService {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SqlSessionTemplate sqlSession;
	@Resource
	private AppMergeAccountService appMergeAccountService;

	@Resource
	private AppReviceDataService appReviceDataService;

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Throwable.class)
	public int addAppUser(AppUser vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("addAppUser");

		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String loginNo = EasyStr.strToTrim(vo.getLoginNo());
			String mobilePhone = EasyStr.strToTrim(vo.getMobilePhone());
			String email = EasyStr.strToTrim(vo.getEmail());
			String loginReturnId = EasyStr.strToTrim(vo.getLoginReturnId());
			String loginSource = EasyStr.strToTrim(vo.getLoginSource());

			String accessToken = EasyStr.strToTrim(vo.getAccessToken());

			String loginPwd = EasyStr.strToTrim(vo.getLoginPwd());

			String personName = EasyStr.strToTrim(vo.getPersonName());

			String communityCode = EasyStr.strToTrim(vo.getCommunityCode());

			log.debug("loginNo:" + loginNo);
			log.debug("personName:" + personName);

			log.debug("communityCode:" + communityCode);

			boolean flag = false;
			if ((EasyStr.isNotEmpty(loginPwd))) {

				AppUser user = this
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								loginNo, mobilePhone, email, loginSource,
								loginReturnId);

				if (user == null) {
					flag = true;
					vo.setPersonId(this.getIknetId());
					vo.setLoginNo(loginNo);
					vo.setMobilePhone(mobilePhone);
					vo.setEmail(email);
					vo.setValidFlag(System_Key.ValidFlag_Y);
					vo.setRegisterSource(System_Key.RegisterSource_M);
					vo.setCreateTime(EasyStr.nowDate());
					vo.setUpdateTime(EasyStr.nowDate());
					if (EasyStr.isEmpty(loginSource)) {

						/**
						 * 初始化 默认登录 方式 I:医客
						 */
						vo.setLoginSource(System_Key.loginSource_I);
					}
					if (EasyStr.isEmpty(personName)) {
						personName = SequenceUtil.getSyncPersonName();
					}

					/**
					 * 初始化 用户 昵称
					 */

					vo.setPersonName(personName);

					if (EasyStr.isEmpty(communityCode)) {

						/**
						 * 初始化 默认用户所属社区代码 1KNET
						 */
						vo.setCommunityCode(System_Community_Key.Community_default);
					}

					vo.setUserFromFlag(System_Key.UserFromFlag_I_00);
				}

			}
			log.debug("flag:" + flag);
			if (flag) {
				String imageUrl = vo.getImageUrl();
				if (EasyStr.isEmpty(imageUrl)) {
					vo.setImageUrl(System_Key.defaultImage);
				}
				result = sqlSession.insert(namespace + "addAppUser", vo);
				if (result > 0) {
					String personId = vo.getPersonId();
					this.addAppUseTotal(personId);

					if ((System_Key.loginSource_T.equalsIgnoreCase(loginSource))
							|| (System_Key.loginSource_S
									.equalsIgnoreCase(loginSource))) {
						this.addAppUserThree(personId, accessToken,
								loginReturnId, loginSource);
					}

					// /**
					// * 初始化 用户 关联账户
					// */
					// this.addAppMergeAccount(personId, loginNo, mobilePhone,
					// email, loginReturnId);

				}
			} else {
				result = System_ResultKey.Result_Check_IsNotExits;
			}
		}
		/**
		 * -1:用户已存在 ;1:注册成功
		 */
		return result;

	}

	/**
	 * 初始化 用户 测量 统计信息
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	private int addAppUseTotal(String personId) throws Exception {
		// TODO Auto-generated method stub
		log.debug("addAppUseTotal");
		int result = System_ResultKey.Result_default;
		// if (personId > 0) {
		if (EasyStr.isNotEmpty(personId)) {
			AppUseTotal vo = new AppUseTotal();
			vo.setPersonId(personId);

			result = sqlSession.insert(AppUseTotalService.namespace
					+ "addAppUseTotal", vo);
		}

		return result;
	}

	/**
	 * 初始化 第三方登录 用户 认证信息
	 * 
	 * @param personId
	 * @param accessToken
	 * @param loginReturnId
	 * @return
	 * @throws Exception
	 */
	private int addAppUserThree(String personId, String accessToken,
			String loginReturnId, String loginSource) throws Exception {
		// TODO Auto-generated method stub
		log.debug("addAppUserThree");
		int result = System_ResultKey.Result_default;

		// if (personId > 0) {
		if (EasyStr.isNotEmpty(personId)) {
			AppUserThreeInfo vo = new AppUserThreeInfo();
			vo.setPersonId(personId);
			vo.setAccessToken(accessToken);
			vo.setLoginSource(loginSource);
			vo.setLoginReturnId(loginReturnId);
			vo.setRefreshToken(accessToken);
			vo.setLastAccessDate(DateUtil.getNowToday());

			result = sqlSession.insert(AppUserThreeService.namespace
					+ "addAppUserThree", vo);
		}

		return result;
	}

	// /**
	// * 初始化 用户 关联账户
	// *
	// * @param personId
	// * @param loginNo
	// * @param mobilePhone
	// * @param email
	// * @param loginReturnId
	// * @return
	// * @throws Exception
	// */
	// private int addAppMergeAccount(String personId, String loginNo,
	// String mobilePhone, String email, String loginReturnId)
	// throws Exception {
	// // TODO Auto-generated method stub
	// log.debug("addAppMergeAccount");
	// int result = System_ResultKey.Result_default;
	//
	// // if (personId > 0) {
	// if (EasyStr.isNotEmpty(personId)) {
	// AppMergeAccount vo = new AppMergeAccount();
	// vo.setPersonId(personId);
	// vo.setMergeAccountFlag(System_Key.MergeAccountFlag_S);
	// if (EasyStr.isNotEmpty(loginNo)) {
	// vo.setLoginNo(loginNo);
	// } else {
	// if (EasyStr.isNotEmpty(mobilePhone)) {
	// vo.setLoginNo(mobilePhone);
	// }
	//
	// if (EasyStr.isNotEmpty(email)) {
	// vo.setLoginNo(email);
	// }
	//
	// if (EasyStr.isNotEmpty(loginReturnId)) {
	// vo.setLoginNo(loginReturnId);
	// }
	// }
	//
	// result = appMergeAccountService.addAppMergeAccount(vo);
	// }
	//
	// return result;
	// }

	/**
	 * 变更 测试正常总数
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppUser(AppUser vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("updateAppUser");

		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = EasyStr.strToTrim(vo.getPersonId());
			String loginNo = EasyStr.strToTrim(vo.getLoginNo());
			boolean flag = false;
			int list_size = 0;
			// if (personId > 0) {
			if (EasyStr.isNotEmpty(personId)) {

				if (EasyStr.isNotEmpty(loginNo)) {
					int loginNo_length = loginNo.length();
					if (loginNo_length == 18) {
						Map<String, Object> queryMap = IKnetUtil.getQueryMap();
						queryMap.put(System_Key.deviceFlag,
								System_Key.deviceFlag_C);
						queryMap.put(System_Key.deviceSim, loginNo);
						List<AppReviceData> list = appReviceDataService
								.queryYlPersonDeviceForPersonIdList(queryMap);
						log.debug("list:" + list);

						if (list != null) {

							log.debug("list.size():" + list.size());
							for (int i = 0; i < list.size(); i++) {
								log.debug("list.get(" + i + "):" + list.get(i));
							}

							list_size = list.size();
							if (list_size > 1) {
								result = list_size;
							} else {
								if (list_size == 0) {
									flag = true;
								}
								if (!flag) {
									String old_personId = "";
									AppReviceData ylReviceData = list.get(0);
									if (ylReviceData != null) {
										old_personId = ylReviceData
												.getPersonId();
									}

									log.debug("old_personId：" + old_personId);
									if (personId.equals(old_personId)) {
										flag = true;
									} else {
										result = System_ResultKey.Result_Check_IsNot;
									}
								}

							}

						}

					} else {
						flag = true;
					}
				} else {
					flag = true;
				}
				vo.setUpdateTime(EasyStr.nowDate());
			}
			log.debug("flag:" + flag);
			if (flag) {
				vo.setUpdateTime(EasyStr.nowDate());

				Map<String, Object> map = CommonUtilis.initMap();
				map.put(System_Key.Easemob_easeMobFlag,
						System_Key.Auto_easemobFlag_U_N);
				map.put(System_Map.Map_VO, vo);
				log.debug("updateAppUser");
				result = sqlSession.update(namespace + "updateAppUser", map);
			}
		}
		return result;

		// int result = System_ResultKey.Result_default;
		// if (vo != null) {
		//
		// vo.setUpdateTime(EasyStr.nowDate());
		//
		// Map<String, Object> map = CommonUtilis.initMap();
		// map.put(System_Key.Easemob_easeMobFlag,
		// System_Key.Auto_easemobFlag_U_N);
		// map.put(System_Map.Map_VO, vo);
		// result = sqlSession.update(namespace + "updateAppUser", map);
		// }
		// return result;
	}

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
			String loginSource, String loginReturnId) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppUserByLoginNoMobilePhoneEmailLoginReturnId");
		AppUser user = null;
		Map<String, Object> map = CommonUtilis.initPortalUserMap(loginNo,
				mobilePhone, email, loginSource, loginReturnId);

		if (map != null) {
			user = sqlSession
					.selectOne(
							namespace
									+ "queryAppUserByLoginNoMobilePhoneEmailLoginReturnId",
							map);
		}
		return user;
	}

	/**
	 * 修改 密码
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppUserLoginPwd(AppUser vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("updateAppUserLoginPwd");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			vo.setUpdateTime(EasyStr.nowDate());

			Map<String, Object> map = CommonUtilis.initMap();
			map.put(System_Key.Easemob_easeMobFlag,
					System_Key.Auto_easemobFlag_P_N);
			map.put(System_Map.Map_VO, vo);
			result = sqlSession
					.update(namespace + "updateAppUserLoginPwd", map);
		}
		return result;
	}

	/**
	 * 修改 密码
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private int updateAppUserThree(String personId, String accessToken)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("updateAppUserThree");
		int result = System_ResultKey.Result_default;
		// if ((personId > System_Key.default_0)
		// if (personId > 0) {
		if ((EasyStr.isNotEmpty(personId)) && (EasyStr.isNotEmpty(accessToken))) {

			Map<String, Object> map = CommonUtilis.initMap();
			map.put(System_Key.personId, personId);
			map.put(System_Key.accessToken, accessToken);
			map.put(System_Key.lastAccessDate, EasyStr.nowDate());

			result = sqlSession.update(AppUserThreeService.namespace
					+ "updateAppUserThree", map);
		}
		return result;
	}

	/**
	 * 根据 personId 查询
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public AppUser queryAppUserByPersonId(String personId) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppUserByPersonId");
		return sqlSession.selectOne(namespace + "queryAppUserByPersonId",
				personId);

	}

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
			String loginReturnId, String accessToken) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppUserByLoginReturnId");
		loginReturnId = EasyStr.strToTrim(loginReturnId);
		loginSource = EasyStr.strToTrim(loginSource);
		AppUser appUser = this
				.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(null, null,
						null, loginSource, loginReturnId);

		if (appUser == null) {
			AppUser vo = new AppUser();
			/**
			 * 初始化 loginNo
			 */
			String loginNo = SequenceUtil.getSyncLoginNo(loginSource);
			log.debug("loginNo:" + loginNo);
			vo.setLoginNo(loginNo);

			vo.setLoginSource(loginSource);
			vo.setLoginReturnId(loginReturnId);
			vo.setLoginPwd(MdEncryptUtil
					.MD5EncodeToHex(System_Key.default_LoginPwd));
			vo.setAccessToken(accessToken);

			int result = this.addAppUser(vo);
			log.debug("result:" + result);
			if (result > 0) {
				appUser = this
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								null, null, null, loginSource, loginReturnId);
			}
		} else {
			this.updateAppUserThree(appUser.getPersonId(), accessToken);
			log.debug("同步 更新 accessToken 完毕");
		}
		log.debug("appUser:" + appUser);
		return appUser;
	}

	/**
	 * 查询 环信 好友 昵称
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppIknetEasemobUser> queryAppIknetEasemobUserByMap(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppIknetEasemobUserByMap");
		return sqlSession.selectList(namespace
				+ "queryAppIknetEasemobUserByMap", map);
	}

	/**
	 * 用户 班定 手机号码| 邮箱
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateAppUserValidateByMap(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("updateAppUserValidateByMap");
		int result = System_ResultKey.Result_default;
		if (map != null) {

			map.put(System_Key.updateTime, EasyStr.nowDate());
			result = sqlSession.update(
					namespace + "updateAppUserValidateByMap", map);
		}
		return result;
	}

}
