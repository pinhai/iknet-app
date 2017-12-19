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
import com.iknet.server.appserver.pojo.AppReviceData;
import com.iknet.server.appserver.pojo.AppThreeHeightRecord;
import com.iknet.server.appserver.service.AppReviceDataService;
import com.iknet.server.appserver.service.AppValidateTmpService;

/**
 * 
 * @author luozd
 * 
 */
@Service("appReviceDataService")
public class AppReviceDataServiceImpl extends BaseService implements
		AppReviceDataService {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private SqlSessionTemplate sqlSession;

	/**
	 * 新增 SIM 绑定
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppPersonDevice(AppReviceData vo) throws Exception {
		log.debug("addAppPersonDevice");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = EasyStr.strToTrim(vo.getPersonId());
			String validate_deviceSim = EasyStr.strToTrim(vo.getDeviceSim());

			if (EasyStr.isNotEmpty(validate_deviceSim)) {
				int validate_deviceSim_length = validate_deviceSim.length();
				if ((validate_deviceSim.endsWith(System_Key.AorB_O))
						|| (validate_deviceSim.endsWith(System_Key.AorB_A))
						|| (validate_deviceSim.endsWith(System_Key.AorB_B))) {
					if (validate_deviceSim_length > 10) {
						validate_deviceSim = validate_deviceSim.substring(0,
								validate_deviceSim_length - 1);
					}
				}
			}

			String validateCode = EasyStr.strToTrim(vo.getValidateCode());

			if ((EasyStr.isNotEmpty(personId))
					&& (EasyStr.isNotEmpty(validate_deviceSim))) {

				int count = this.queryAppPersonDeviceCount(vo);
				if (count == 0) {

					result = sqlSession.insert(
							namespace + "addAppPersonDevice", vo);

					if (EasyStr.isNotEmpty(validateCode)) {
						sqlSession.update(AppValidateTmpService.namespace
								+ "updateAppValidateTmpForValidateFlag",
								validate_deviceSim);
					}
				}
			}

		}
		return result;
	}

	/**
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppPersonDeviceForValidateCode(AppReviceData vo)
			throws Exception {
		log.debug("addAppPersonDevice");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = EasyStr.strToTrim(vo.getPersonId());
			String deviceSim = EasyStr.strToTrim(vo.getDeviceSim());

			if ((EasyStr.isNotEmpty(personId))
					&& (EasyStr.isNotEmpty(deviceSim))) {

				int count = this.queryAppPersonDeviceCount(vo);
				if (count == 0) {

					result = sqlSession.insert(
							namespace + "addAppPersonDevice", vo);
				}
			}

		}
		return result;
	}

	/**
	 * 根据 vo 查询 条数
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private int queryAppPersonDeviceCount(AppReviceData vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("queryAppPersonDeviceCount");
		return sqlSession
				.selectOne(namespace + "queryAppPersonDeviceCount", vo);
	}

	/**
	 * 删除 用户 SIM 绑定记录
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteAppPersonDeviceByMap(Map<String, Object> map)
			throws Exception {
		log.debug("deleteAppPersonDeviceByMap");
		log.debug("map:" + map);
		return sqlSession.delete(namespace + "deleteAppPersonDeviceByMap", map);
	}

	/**
	 * 查询 SIM 班定记录
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryAppPersonDeviceByMap(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppPersonDeviceByMap");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace + "queryAppPersonDeviceByMap",
				map);
	}

	/**
	 * 查询 测量数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryAppReviceDataForPage(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppReviceDataForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace + "queryAppReviceDataForPage",
				map);
	}

	/**
	 * 查询 测量数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryAppReviceDataForIDCardForPage(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppReviceDataForIDCardForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace
				+ "queryAppReviceDataForIDCardForPage", map);
	}

	/**
	 * 分页 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppThreeHeightRecord> queryAppThreeHeightRecordList(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppThreeHeightRecordList");
		log.debug("map:" + map);

		return sqlSession.selectList(namespace
				+ "queryAppThreeHeightRecordList", map);
	}

	/**
	 * 获取 已经绑定的设备 记录的 personId
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppReviceData> queryYlPersonDeviceForPersonIdList(
			Map<String, Object> map) throws Exception {
		log.debug("queryYlPersonDeviceForPersonIdList");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace
				+ "queryYlPersonDeviceForPersonIdList", map);
	}
}
