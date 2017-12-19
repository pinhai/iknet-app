package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iknet.commons.util.BaseService;
import com.iknet.server.appserver.pojo.AppDoctor;
import com.iknet.server.appserver.service.AppDoctorService;

/**
 * APP名医
 * 
 * @author luozd
 * 
 */
@Service("appDoctorService")
public class AppDoctorServiceImpl extends BaseService implements
		AppDoctorService {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SqlSessionTemplate sqlSession;

	/**
	 * 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppDoctor> queryAppDoctorListForPage(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppDoctorListForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace + "queryAppDoctorListForPage",
				map);
	}

	/**
	 * 根据 map 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public AppDoctor queryAppDoctorByMap(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppDoctorByMap");
		return sqlSession.selectOne(namespace + "queryAppDoctorByMap", map);
	}

	/**
	 * 用户 点赞
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int updateAppDoctorNiceTotal(String id) throws Exception {
		log.debug("updateAppDoctorNiceTotal");
		log.debug("id:" + id);
		return sqlSession.update(namespace + "updateAppDoctorNiceTotal", id);
	}

}
