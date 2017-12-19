package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.util.BaseService;
import com.iknet.commons.util.EasyStr;
import com.iknet.server.appserver.pojo.AppAnswer;
import com.iknet.server.appserver.service.AppAnswerService;
import com.iknet.server.appserver.service.AppQuestionService;

/**
 * 医疗问答
 * 
 * @author luozd
 * 
 */
@Service("appAnswerService")
public class AppAnswerServiceImpl extends BaseService implements
		AppAnswerService {
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
	@Transactional(rollbackFor = Throwable.class)
	public int addAppAnswer(AppAnswer vo) throws Exception {
		log.debug("addAppAnswer");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String questionId = vo.getQuestionId();
			log.debug("questionId:" + questionId);
			// if (questionId > 0) {
			if (EasyStr.isNotEmpty(questionId)) {
				vo.setId(this.getIknetId());
				vo.setAnswerFlag(System_Key.AnswerFlag_1);
				vo.setCreateTime(EasyStr.nowDate());
				result = sqlSession.insert(namespace + "addAppAnswer", vo);
				this.updateAnswerTotal(questionId);
			}

		}
		return result;
	}

	private void updateAnswerTotal(String questionId) {
		log.debug("questionId:" + questionId);
		sqlSession.update(AppQuestionService.namespace + "updateAnswerTotal",
				questionId);
	}

	/**
	 * 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppAnswer> queryAppAnswerListForPage(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppAnswerForList");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace + "queryAppAnswerListForPage",
				map);
	}

	/**
	 * 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppAnswer> queryMyAppAnswerListForPage(Map<String, Object> map)
			throws Exception {
		log.debug("queryMyAppAnswerListForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace + "queryMyAppAnswerListForPage",
				map);
	}
}
