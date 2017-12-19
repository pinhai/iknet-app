package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iknet.commons.util.BaseService;
import com.iknet.server.appserver.pojo.AppKnowledge;
import com.iknet.server.appserver.service.AppKnowledgeService;

/**
 * APP知识
 * 
 * @author luozd
 * 
 */
@Service("appKnowledgeService")
public class AppKnowledgeServiceImpl extends BaseService implements
		AppKnowledgeService {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SqlSessionTemplate sqlSession;

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppKnowledge> queryAppKnowledgeListForPage(
			Map<String, Object> map) throws Exception {

		log.debug("queryAppKnowledgeListForPage");
		log.debug(map);
		return sqlSession.selectList(
				namespace + "queryAppKnowledgeListForPage", map);
	}

	/**
	 * 根据 map 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public AppKnowledge queryAppKnowledgeByMap(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppKnowledgeByMap");
		log.debug(map);
		return sqlSession.selectOne(namespace + "queryAppKnowledgeByMap", map);
	}

	/**
	 * 用户 点赞
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int updateAppKnowledgeNiceTotal(long id) throws Exception {
		log.debug("id:" + id);
		return sqlSession.update(namespace + "updateAppKnowledgeNiceTotal", id);
	}

}
