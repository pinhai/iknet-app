package com.iknet.server.appserver.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.util.BaseService;
import com.iknet.commons.util.EasyStr;
import com.iknet.server.appserver.pojo.AppDeviceSimAuto;
import com.iknet.server.appserver.service.AppDeviceSimAutoService;

/**
 * 动态血压计设置参数
 * 
 * @author luozd
 * 
 */
@Service("appDeviceSimAutoService")
public class AppDeviceSimAutoServiceImpl extends BaseService implements
		AppDeviceSimAutoService {
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
	public int addAppDeviceSimAuto(AppDeviceSimAuto vo) throws Exception {
		log.debug("addAppDeviceSimAuto");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			AppDeviceSimAuto countAppDeviceSimAuto = this
					.queryAppDeviceSimAuto(vo);

			String useFlag = EasyStr.strToTrim(vo.getUseFlag());
			String deviceSim = EasyStr.strToTrim(vo.getDeviceSim());
			vo.setDeviceSim(deviceSim);
			if (!System_Key.UseFlag_D.equalsIgnoreCase(useFlag)) {
				vo.setUseFlag(System_Key.UseFlag_N);
			}

			vo.setUpdateTime(EasyStr.nowDate());
			vo.setCreateTime(EasyStr.nowDate());
			if (countAppDeviceSimAuto == null) {
				result = sqlSession.insert(namespace + "addAppDeviceSimAuto",
						vo);
			} else {
				result = sqlSession.update(
						namespace + "updateAppDeviceSimAuto", vo);
			}
		}
		return result;
	}

	/**
	 * 查询
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public AppDeviceSimAuto queryAppDeviceSimAuto(AppDeviceSimAuto vo)
			throws Exception {
		log.debug("queryAppDeviceSimAuto");
		log.debug("vo:" + vo);
		return sqlSession.selectOne(namespace + "queryAppDeviceSimAuto", vo);
	}

}
