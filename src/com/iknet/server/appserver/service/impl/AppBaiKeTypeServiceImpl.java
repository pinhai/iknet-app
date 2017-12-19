package com.iknet.server.appserver.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iknet.commons.util.BaseService;
import com.iknet.server.appserver.pojo.AppBaiKeType;
import com.iknet.server.appserver.service.AppBaiKeTypeService;

/**
 * 百科类型
 * 
 * @author luozd
 * 
 */
@Service("appBaiKeTypeService")
public class AppBaiKeTypeServiceImpl extends BaseService implements
		AppBaiKeTypeService {
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
	public List<AppBaiKeType> queryAppBaiKeTypeForList(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppBaiKeTypeForList");
		return sqlSession.selectList(namespace + "queryAppBaiKeTypeForList",
				map);
	}

}
