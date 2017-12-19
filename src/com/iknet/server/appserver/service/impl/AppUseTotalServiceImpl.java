package com.iknet.server.appserver.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iknet.commons.util.BaseService;
import com.iknet.server.appserver.pojo.AppUseTotal;
import com.iknet.server.appserver.service.AppUseTotalService;

/**
 * APP测试次数，异常数，正常数
 * 
 * @author luozd
 * 
 */
@Service("appUseTotalService")
public class AppUseTotalServiceImpl extends BaseService implements
		AppUseTotalService {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SqlSessionTemplate sqlSession;

	/**
	 * 查询
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public AppUseTotal queryAppUseTotalByPersonId(String personId)
			throws Exception {
		log.debug("queryAppUseTotalByPersonId");
		AppUseTotal vo = sqlSession.selectOne(namespace
				+ "queryAppUseTotalByPersonId", personId);
		if(vo==null){
			vo=new AppUseTotal();
		}
		vo.setPersonId(personId);
		return vo;

	}

}
