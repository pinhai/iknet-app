package com.iknet.server.appserver.service.impl;

import java.util.List;

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
import com.iknet.server.appserver.pojo.AppMergeAccount;
import com.iknet.server.appserver.pojo.AppUser;
import com.iknet.server.appserver.service.AppMergeAccountService;
import com.iknet.server.appserver.service.AppUserService;

/**
 * APP 用户 关联帐号
 * 
 * @author luozd
 * 
 */
@Service("appMergeAccount")
public class AppMergeAccountServiceImpl extends BaseService implements
		AppMergeAccountService {
	private Logger log = Logger.getLogger(this.getClass());
	@Resource
	private SqlSessionTemplate sqlSession;

	@Resource
	private AppUserService appUserService;

	/**
	 * 新增 关联账户
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Throwable.class)
	public int addAppMergeAccount(AppMergeAccount vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("addAppMergeAccount");
		log.debug("vo:" + vo);
		int result = System_ResultKey.Result_default;

		/**
		 * 登录帐号
		 */
		String loginNo = EasyStr.strToTrim(vo.getLoginNo());
		log.debug("loginNo:" + loginNo);
		boolean flag = false;
		AppUser appUser = null;

		if (EasyStr.isNotEmpty(loginNo)) {
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
		if (appUser != null) {
			flag = true;
		}
		log.debug("flag：" + flag);

		if (flag) {
			vo.setAccountPersonId(appUser.getPersonId());

			int count = this.queryAppMergeAccountCount(vo);
			if (count == 0) {
				vo.setLoginNo(loginNo);
				vo.setMergeAccountId(this.getIknetId());

				result = sqlSession.insert(namespace + "addAppMergeAccount", vo);

			}

		}
		return result;
	}

	/**
	 * 查询 关联帐号
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<AppMergeAccount> queryAppMergeAccount(AppMergeAccount vo)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppMergeAccount");
		log.debug("vo:" + vo);
		return sqlSession.selectList(namespace + "queryAppMergeAccount", vo);
	}

	/**
	 * 删除 关联 帐号
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int deleteAppMergeAccount(AppMergeAccount vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("deleteAppMergeAccount");
		log.debug("vo:" + vo);
		return sqlSession.delete(namespace + "deleteAppMergeAccount", vo);
	}

	/**
	 * 根据 vo 查询 条数
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryAppMergeAccountCount(AppMergeAccount vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppMergeAccountCount");
		log.debug("vo:" + vo);
		return sqlSession
				.selectOne(namespace + "queryAppMergeAccountCount", vo);
	}

}
