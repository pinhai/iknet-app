package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.util.BaseService;
import com.iknet.commons.util.EasyStr;
import com.iknet.server.appserver.pojo.AppNews;
import com.iknet.server.appserver.service.AppNewsService;

/**
 * APP对应 消息
 * 
 * @author luozd
 * 
 */
@Service("appNewsService")
public class AppNewsServiceImpl extends BaseService implements AppNewsService {
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
	public int addAppNews(AppNews vo) throws Exception {
		log.debug("addAppNews");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = vo.getPersonId();
			log.debug("personId:" + personId);
			// if (personId > 0) {
			if (EasyStr.isNotEmpty(personId)) {
				vo.setCreateTime(EasyStr.nowDate());
				vo.setUpdateTime(EasyStr.nowDate());
				result = sqlSession.insert(namespace + "addAppNews", vo);
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
	public int updateAppNews(AppNews vo) throws Exception {
		log.debug("updateAppNews");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = vo.getPersonId();
			log.debug("personId:" + personId);
			// if (personId > 0) {
			if (EasyStr.isNotEmpty(personId)) {
				vo.setUpdateTime(EasyStr.nowDate());
				result = sqlSession.update(namespace + "updateAppNews", vo);
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
	public List<AppNews> queryAppNewsForList(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppNewsForList");
		return sqlSession.selectList(namespace + "queryAppNewsForList", map);
	}

	/**
	 * 根据 newsId 查询
	 * 
	 * @param newsId
	 * @return
	 * @throws Exception
	 */
	public AppNews queryAppNewsByNewsId(long newsId) throws Exception {
		log.debug("queryAppNewsByNewsId");
		return sqlSession.selectOne(namespace + "queryAppNewsByNewsId", newsId);
	}
}
