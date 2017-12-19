package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iknet.commons.baseCode.BusiConstants.System_Flag;
import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.util.BaseService;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.commons.util.MdEncryptUtil;
import com.iknet.commons.util.SequenceUtil;
import com.iknet.server.appserver.pojo.AppCommunity;
import com.iknet.server.appserver.pojo.AppLevel;
import com.iknet.server.appserver.pojo.AppUser;
import com.iknet.server.appserver.service.AppCommunityService;
import com.iknet.server.appserver.service.AppUserService;

/**
 * APP 社区用户
 * 
 * @author luozd
 * 
 */
@Service("appCommunityService")
public class AppCommunityServiceImpl extends BaseService implements
		AppCommunityService {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private SqlSessionTemplate sqlSession;

	@Resource
	private AppUserService appUserService;

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Throwable.class)
	public Map<String, Object> addAppCommunity(AppCommunity vo)
			throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = IKnetUtil.getResponseMap();
		String returnNo = "";
		int result = System_ResultKey.Result_default;

		String communityCode = EasyStr.strToTrim(vo.getCommunityCode());
		/**
		 * 登录帐号
		 */
		String loginNo = EasyStr.strToTrim(vo.getLoginNo());
		String loginPwd = System_Key.default_LoginPwd;

		String idCard = EasyStr.strToTrim(vo.getIdCard());
		log.debug("communityCode:" + communityCode);
		log.debug("loginNo:" + loginNo);
		log.debug("idCard:" + idCard);
		boolean flag = true;
		AppUser appUser_community = null;

		String email = "";
		String mobilePhone = "";

		if ((EasyStr.isEmpty(loginNo)) && (EasyStr.isNotEmpty(idCard))) {
			loginNo = idCard;
		}

		int loginNo_length = 0;
		if (EasyStr.isNotEmpty(loginNo)) {
			loginNo_length = loginNo.length();
			
			// 判断用户登录方式
			if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
				appUser_community = appUserService
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								null, null, loginNo, null, null);
				email = loginNo;
				loginNo = idCard;
			} else if ((loginNo.matches(System_Key.Mobile_RegExp))
					&& (loginNo.length() == 11)) {
				appUser_community = appUserService
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								null, loginNo, null, null, null);
				mobilePhone = loginNo;
				loginNo = idCard;
			} else {
				appUser_community = appUserService
						.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
								loginNo, null, null, null, null);
			}

			if (appUser_community == null) {
				flag = false;
				// if ((EasyStr.isEmpty(email)) && EasyStr.isEmpty(mobilePhone))
				// {
				// loginNo = "";
				// }

			}
		}
		log.debug("flag：" + flag);
		if ((EasyStr.isEmpty(loginNo)) || (!flag)) {
			AppUser appUser = new AppUser();
			appUser.setSex(vo.getSex());
			appUser.setPersonName(vo.getPersonName());
			appUser.setAge(vo.getAge());
			appUser.setAddress(vo.getAddress());
			appUser.setHeight(vo.getHeight());
			appUser.setWeight(vo.getWeight());
			appUser.setEmail(email);
			appUser.setMobilePhone(mobilePhone);

			if ((loginNo_length > 0) && (loginNo_length != 18)) {
				/**
				 * 初始化 loginNo
				 */
				loginNo = SequenceUtil
						.getSyncLoginNo(System_Key.loginSource_I);

			}
			
//			if (((EasyStr.isNotEmpty(email)) && EasyStr.isNotEmpty(mobilePhone))
//					|| (EasyStr.isEmpty(loginNo))) {
//
//				/**
//				 * 初始化 loginNo
//				 */
//				loginNo = SequenceUtil.getSyncLoginNo(System_Key.loginSource_I);
//				// appUser.setLoginNo(loginNo);
//			}

			appUser.setLoginNo(loginNo);

			appUser.setLoginPwd(MdEncryptUtil.MD5EncodeToHex(loginPwd));
			result = appUserService.addAppUser(appUser);
			if (result == System_ResultKey.Result_default) {
				flag = false;
			} else {
				flag = true;
				map.put(System_Key.loginPwd, loginPwd);
			}
			log.debug("代替 注册");
			if (flag) {
				// 判断用户登录方式
				if (loginNo.indexOf(System_Flag.Split_Email) > 0) {
					appUser_community = appUserService
							.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
									null, null, loginNo, null, null);
				} else if ((loginNo.matches(System_Key.Mobile_RegExp))
						&& (loginNo.length() == 11)) {
					appUser_community = appUserService
							.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
									null, loginNo, null, null, null);
				} else {
					appUser_community = appUserService
							.queryAppUserByLoginNoMobilePhoneEmailLoginReturnId(
									loginNo, null, null, null, null);
				}

			}
		}

		if (appUser_community != null) {

			vo.setPersonId(appUser_community.getPersonId());

			if (EasyStr.isNotEmpty(communityCode)) {

				if (EasyStr.isNotEmpty(email)) {
					loginNo = email;
				}

				if (EasyStr.isNotEmpty(mobilePhone)) {
					loginNo = mobilePhone;
				}

				int count = this.queryAppCommunityCount(vo);
				if (count == 0) {
					vo.setLoginNo(loginNo);
					vo.setCommunityId(this.getIknetId());
					vo.setCreateTime(EasyStr.nowDate());
					result = sqlSession.insert(namespace + "addAppCommunity",
							vo);
					if (result > System_ResultKey.Result_default) {
						returnNo = loginNo;
					}
				}
			}
			map.put(System_Key.loginNo, returnNo);
		} else {

			map = null;
		}

		log.debug("loginNo:" + loginNo);
		return map;
	}

	/**
	 * 分页 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppCommunity> queryAppCommunityListForPage(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppCommunityListForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(
				namespace + "queryAppCommunityListForPage", map);
	}

	/**
	 * 根据 communityId 删除
	 * 
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	public int deleteAppCommunityByCommunityId(String communityId)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("deleteAppCommunityByCommunityId");
		return sqlSession.delete(namespace + "deleteAppCommunityByCommunityId",
				communityId);
	}

	/**
	 * 根据 vo 查询 条数
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryAppCommunityCount(AppCommunity vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppCommunityCount");
		return sqlSession.selectOne(namespace + "queryAppCommunityCount", vo);
	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppCommunity(AppCommunity vo) throws Exception {
		log.debug("updateAppCommunity");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personName = EasyStr.strToTrim(vo.getPersonName());
			log.debug("personName:" + personName);

			if (EasyStr.isNotEmpty(personName)) {

				result = sqlSession
						.update(namespace + "updateAppCommunity", vo);
			}

		}

		return result;
	}

	/**
	 * 查询 一级机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AppLevel> queryAppLevelListForCommunity() throws Exception {
		log.debug("queryAppLevelListForCommunity");
		return sqlSession.selectList(namespace
				+ "queryAppLevelListForCommunity");
	}
}
