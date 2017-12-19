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
import com.iknet.server.appserver.pojo.AppFeedBack;
import com.iknet.server.appserver.service.AppFeedBackService;

/**
 * APP意见反馈
 * 
 * @author luozd
 * 
 */
@Service("appFeedBackService")
public class AppFeedBackServiceImpl extends BaseService implements
		AppFeedBackService {
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
	public int addAppFeedBack(AppFeedBack vo) throws Exception {
		log.debug("addAppFeedBack");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = vo.getPersonId();
			log.debug("personId:" + personId);
			// if (personId > 0) {
			if (EasyStr.isNotEmpty(personId)) {
				vo.setIsDispose(System_Key.isDispose_0);
				vo.setFeedBackDate(EasyStr.nowDate());
				vo.setUpdateTime(EasyStr.nowDate());
				result = sqlSession.insert(namespace + "addAppFeedBack", vo);
			}
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppFeedBack(AppFeedBack vo) throws Exception {
		log.debug("updateAppFeedBack");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = vo.getPersonId();
			log.debug("personId:" + personId);
			if (EasyStr.isNotEmpty(personId)) {
				vo.setUpdateTime(EasyStr.nowDate());
				result = sqlSession.update(namespace + "updateAppFeedBack", vo);
			}
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
	public List<AppFeedBack> queryAppFeedBackForList(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppFeedBackForList");
		return sqlSession
				.selectList(namespace + "queryAppFeedBackForList", map);
	}

	/**
	 * 根据 feedBackId 查询
	 * 
	 * @param feedBackId
	 * @return
	 * @throws Exception
	 */
	public AppFeedBack queryAppFeedBackByFeedBackId(long feedBackId)
			throws Exception {
		log.debug("queryAppFeedBackByFeedBackId");
		return sqlSession.selectOne(namespace + "queryAppFeedBackByFeedBackId",
				feedBackId);
	}
}