package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.util.BaseService;
import com.iknet.commons.util.EasyStr;
import com.iknet.server.appserver.pojo.AppFamilyMembers;
import com.iknet.server.appserver.service.AppFamilyMembersService;

/**
 * APP端对应家庭成员
 * 
 * @author luozd
 * 
 */
@Service("appFamilyMembersService")
public class AppFamilyMembersServiceImpl extends BaseService implements
		AppFamilyMembersService {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SqlSessionTemplate sqlSession;

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppFamilyMembers(AppFamilyMembers vo) throws Exception {
		log.debug("addAppFamilyMembers");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = vo.getPersonId();
			log.debug("personId:" + personId);
			// if (personId > System_Key.default_0) {
			if (EasyStr.isNotEmpty(personId)) {
				String imageUrl = vo.getImageUrl();
				if (EasyStr.isEmpty(imageUrl)) {
					vo.setImageUrl(System_Key.defaultImage);
				}
				String membersUserId = System_Key.default_Clear;

				String membersMobile = EasyStr.strToTrim(vo.getMembersMobile());
				String userName = EasyStr.strToTrim(vo.getUserName());
				membersUserId = vo.getMembersUserId();
				log.debug("userName:" + userName);
				log.debug("membersUserId:" + membersUserId);
				// boolean flag = true;
				/**
				 * lzd 2014-09-27
				 */
				boolean flag = false;

				// if (membersUserId > System_Key.default_0) {
				if (EasyStr.isNotEmpty(membersUserId)) {
					flag = true;
				}

				log.debug("flag:" + flag);
				if (flag) {
					result = this.queryAppFamilyMemberCount(vo);
					if (result == 0) {
						vo.setMembersId(this.getIknetId());
						vo.setUserName(userName);
						vo.setMembersMobile(membersMobile);
						vo.setCreateTime(EasyStr.nowDate());
						vo.setUpdateTime(EasyStr.nowDate());
						result = sqlSession.insert(namespace
								+ "addAppFamilyMembers", vo);

					} else {
						result = System_ResultKey.Result_Check_IsNotExits;
					}
				}

			}

		}

		return result;
	}

	/**
	 * 根据 FamilyMembers 查询 条数
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryAppFamilyMemberCount(AppFamilyMembers vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppFamilyMemberCount");
		return sqlSession
				.selectOne(namespace + "queryAppFamilyMemberCount", vo);
	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppFamilyMembers(AppFamilyMembers vo) throws Exception {
		log.debug("updateAppFamilyMembers");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String userName = "";

			userName = EasyStr.strToTrim(vo.getUserName());
			String membersMobile = EasyStr.strToTrim(vo.getMembersMobile());
			log.debug("userName:" + userName);

			vo.setUserName(userName);
			vo.setMembersMobile(membersMobile);
			vo.setCreateTime(EasyStr.nowDate());
			vo.setUpdateTime(EasyStr.nowDate());
			result = sqlSession
					.update(namespace + "updateAppFamilyMembers", vo);

		}

		return result;
	}

	/**
	 * 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppFamilyMembers> queryAppFamilyMembersForList(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppFamilyMembersForList");
		return sqlSession.selectList(
				namespace + "queryAppFamilyMembersForList", map);
	}

	/**
	 * 根据 membersId 查询
	 * 
	 * @param membersId
	 * @return
	 * @throws Exception
	 */
	public AppFamilyMembers queryAppFamilyMembersByMembersId(String membersId)
			throws Exception {
		log.debug("queryAppFamilyMembersByMembersId");
		return sqlSession.selectOne(namespace
				+ "queryAppFamilyMembersByMembersId", membersId);
	}

	/**
	 * 根据 membersId 删除
	 * 
	 * @param membersId
	 * @return
	 * @throws Exception
	 */
	public int deleteAppFamilyMembersByMembersId(String membersId)
			throws Exception {
		log.debug("deleteAppFamilyMembersByMembersId");
		return sqlSession.delete(namespace
				+ "deleteAppFamilyMembersByMembersId", membersId);
	}
}
