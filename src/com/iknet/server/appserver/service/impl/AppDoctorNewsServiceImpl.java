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
import com.iknet.server.appserver.pojo.AppDoctorNews;
import com.iknet.server.appserver.service.AppDoctorNewsService;

/**
 * APP名医文章
 * 
 * @author luozd
 * 
 */
@Service("appDoctorNewsService")
public class AppDoctorNewsServiceImpl extends BaseService implements
		AppDoctorNewsService {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SqlSessionTemplate sqlSession;

	// /**
	// * 新增
	// *
	// * @param vo
	// * @return
	// * @throws Exception
	// */
	// public int addAppDoctorNews(AppDoctorNews vo) throws Exception {
	// log.debug("addAppDoctorNews");
	// int result = System_ResultKey.Result_default;
	// if (vo != null) {
	// long doctorId = vo.getDoctorId();
	// log.debug("doctorId:" + doctorId);
	// if (doctorId > 0) {
	// vo.setCreateTime(EasyStr.nowDate());
	// result = sqlSession.insert(namespace + "addAppDoctorNews", vo);
	// }
	// }
	// return result;
	// }

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppDoctorNews(AppDoctorNews vo) throws Exception {
		log.debug("updateAppDoctorNews");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String doctorId = vo.getDoctorId();
			log.debug("doctorId:" + doctorId);
			// if (doctorId > 0) {
			if (EasyStr.isNotEmpty(doctorId)) {
				vo.setCreateTime(EasyStr.nowDate());
				result = sqlSession.update(namespace + "updateAppDoctorNews",
						vo);
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
	public List<AppDoctorNews> queryAppDoctorNewsForList(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppDoctorNewsForList");
		return sqlSession.selectList(namespace + "queryAppDoctorNewsForList",
				map);
	}

	/**
	 * 根据 id 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AppDoctorNews queryAppDoctorNewsById(long id) throws Exception {
		log.debug("queryAppDoctorNewsById");
		return sqlSession.selectOne(namespace + "queryAppDoctorNewsById", id);
	}
}
