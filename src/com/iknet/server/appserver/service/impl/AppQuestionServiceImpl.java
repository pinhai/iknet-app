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
import com.iknet.server.appserver.pojo.AppQuestion;
import com.iknet.server.appserver.service.AppQuestionService;

/**
 * APP问答
 * 
 * @author luozd
 * 
 */
@Service("appQuestionService")
public class AppQuestionServiceImpl extends BaseService implements
		AppQuestionService {
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
	public int addAppQuestion(AppQuestion vo) throws Exception {
		log.debug("addAppQuestion");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			vo.setId(this.getIknetId());
			vo.setDoctorAnswerFlag(System_Key.DoctorAnswerFlag_N);
			vo.setAnswerTotal(System_Key.default_0);
			vo.setIsShow(System_Key.IsShow_1);
			vo.setCreateTime(EasyStr.nowDate());
			result = sqlSession.insert(namespace + "addAppQuestion", vo);

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
	public List<AppQuestion> queryAppQuestionListForPage(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppQuestionListForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace + "queryAppQuestionListForPage",
				map);
	}

	/**
	 * 根据 id 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AppQuestion queryAppQuestionById(long id) throws Exception {
		log.debug("queryAppQuestionById");
		return sqlSession.selectOne(namespace + "queryAppQuestionById", id);
	}

	/**
	 * 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppQuestion> queryMyAppQuestionListForPage(
			Map<String, Object> map) throws Exception {
		log.debug("queryMyAppQuestionListForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace
				+ "queryMyAppQuestionListForPage", map);
	}
}
