package com.iknet.server.appserver.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.iknet.commons.util.DateUtil;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.IKnetUtil;
import com.iknet.commons.util.SequenceUtil;
import com.iknet.server.appserver.pojo.AppMedicalRecord;
import com.iknet.server.appserver.pojo.AppMedicalRecordVO;
import com.iknet.server.appserver.pojo.AppUseTotal;
import com.iknet.server.appserver.service.AppMedicalRecordService;
import com.iknet.server.appserver.service.AppUseTotalService;

/**
 * APP病例
 * 
 * @author luozd
 * 
 */
@Service("appMedicalRecordService")
public class AppMedicalRecordServiceImpl extends BaseService implements
		AppMedicalRecordService {
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
	public int addAppMedicalRecord(AppMedicalRecord vo) throws Exception {
		log.debug("addAppMedicalRecord");
		int result = System_ResultKey.Result_default;
		if (vo != null) {
			String personId = vo.getPersonId();
			String checkResult = vo.getCheckResult();
			String uploadDate = vo.getUploadDate();
			log.debug("checkResult:" + checkResult);
			log.debug("personId:" + personId);
			log.debug("uploadDate:" + uploadDate);

			// if (personId > 0) {
			if (EasyStr.isNotEmpty(personId)) {
				/**
				 * 初始化 主键 id
				 */
				vo.setMedicalRecordId(this.getIknetId());

				if (uploadDate == null) {
					uploadDate = DateUtil.getToday();
				}
				vo.setUploadDate(uploadDate);
				vo.setCreateTime(EasyStr.nowDate());
				vo.setUpdateTime(EasyStr.nowDate());
				// vo.setMedicalRecordRemark(System_Key.default_Clear);
				vo.setMedicalRecordNo(SequenceUtil.getSyncMedicalRecordNo());
				/**
				 * 初始化 同步第三方标识
				 */
				vo.setThreeAutoFlag(System_Key.ThreeAutoFlag_N);
				result = sqlSession.insert(namespace + "addAppMedicalRecord",
						vo);
			}
			if (result > 0) {
				if (System_Key.checkResult_1.equalsIgnoreCase(checkResult)) {
					updateAppUseTotalForTestNormalTotal(personId,
							System_Key.default_1, System_Key.default_1);
				} else {
					updateAppUseTotalForTestAbnormalTotal(personId,
							System_Key.default_1, System_Key.default_1);
				}
			}
		}
		return result;
	}

	/**
	 * 批量 新增 病例
	 * 
	 * @param appJsonList
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Throwable.class)
	public int addBatchAppMedicalRecordByAppJsonList(List<?> appJsonList)
			throws Exception {
		int result = System_ResultKey.Result_default;
		AppMedicalRecordVO vo = initAppMedicalRecordVOByList(appJsonList);
		if (vo != null) {
			List<AppMedicalRecord> list = vo.getList();
			int list_size = 0;
			if (list != null) {
				list_size = list.size();
			}
			log.debug("list_size:" + list_size);
			if (list_size > 0) {

				result = sqlSession.insert(namespace
						+ "addBatchAppMedicalRecordByAppJsonList", list);
			}
		}
		if (result > 0) {

			this.updateAppUseTotalForTestAbnormalTotal(vo.getPersonId(),
					vo.getTestAbnormalTotal(), vo.getTestAbnormalTotal());
			this.updateAppUseTotalForTestNormalTotal(vo.getPersonId(),
					vo.getTestNormalTotal(), vo.getTestNormalTotal());
		}

		return result;
	}

	/**
	 * 每一个 批量 新增 病例
	 * 
	 * @param appJsonList
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Throwable.class)
	public int addBatchAppMedicalRecordForEveryOneByAppJsonList(
			List<?> appJsonList) throws Exception {
		int result = System_ResultKey.Result_default;
		AppMedicalRecordVO vo = initAppMedicalRecordVOForEveryOneByList(appJsonList);
		if (vo != null) {
			List<AppMedicalRecord> list = vo.getList();
			List<AppUseTotal> userTotallist = vo.getUserTotallist();
			int list_size = 0;
			if (list != null) {
				list_size = list.size();
			}
			log.debug("list_size:" + list_size);

			int userTotallist_size = 0;
			if (userTotallist != null) {
				userTotallist_size = userTotallist.size();
			}
			log.debug("userTotallist_size:" + userTotallist_size);
			if (list_size > 0) {

				result = sqlSession.insert(namespace
						+ "addBatchAppMedicalRecordByAppJsonList", list);
			}
			if (result > 0) {
				if (userTotallist_size > 0) {
					this.batchUpdateAppUseTotal(userTotallist);

				}
			}
		}

		return result;
	}

	/**
	 * 转换 批量上传的 病例 数据
	 * 
	 * @param appJsonList
	 * @return
	 */
	private AppMedicalRecordVO initAppMedicalRecordVOForEveryOneByList(
			List<?> appJsonList) {
		AppMedicalRecordVO vo = null;
		int appJsonList_Size = 0;
		if (appJsonList != null) {
			appJsonList_Size = appJsonList.size();
		}
		if (appJsonList_Size > 0) {
			vo = new AppMedicalRecordVO();
			List<AppMedicalRecord> list = new ArrayList<AppMedicalRecord>();
			List<AppUseTotal> userTotallist = new ArrayList<AppUseTotal>();

			for (int i = 0; i < appJsonList_Size; i++) {

				/**
				 * 总总数
				 */
				int testTotal = System_Key.default_0;

				/**
				 * 测试正常总数
				 */
				int testNormalTotal = System_Key.default_0;

				/**
				 * 测试异常整数
				 */
				int testAbnormalTotal = System_Key.default_0;

				String personId = System_Key.default_Clear;

				String UploadDate = DateUtil.getToday();
				Date createTime = EasyStr.nowDate();
				String appJsonValue = IKnetUtil.binder.toJson(appJsonList
						.get(i));

				appJsonValue = EasyStr.strToTrim(appJsonValue);
				log.debug("appJsonValue:" + appJsonValue);
				if (EasyStr.isNotEmpty(appJsonValue)) {
					log.debug("initAppMedicalRecordVOByList-->appJsonValue:"
							+ appJsonValue);
					AppMedicalRecord appVO = IKnetUtil.binder.fromJson(
							appJsonValue, AppMedicalRecord.class);
					log.debug("appVO:" + appVO);
					if (appVO != null) {
						String uploadDate = EasyStr.strToTrim(appVO
								.getUploadDate());
						log.debug("uploadDate:" + uploadDate);
						appVO.setMedicalRecordId(this.getIknetId());
						// if (uploadDate == null) {
						if (EasyStr.isNotEmpty(uploadDate)) {
							uploadDate = UploadDate;
						}
						appVO.setUploadDate(uploadDate);
						appVO.setCreateTime(createTime);
						appVO.setUpdateTime(createTime);
						// appVO.setMedicalRecordRemark(System_Key.default_Clear);
						appVO.setMedicalRecordNo(SequenceUtil
								.getSyncMedicalRecordNo());
						/**
						 * 初始化 同步第三方标识
						 */
						appVO.setThreeAutoFlag(System_Key.ThreeAutoFlag_N);

						/**
						 * 初始化 QQ 健康中心 必填字段
						 */
						appVO.setCheckHeartResult(System_Key.checkHeartResult_1);
						appVO.setCheckHeartBeat(System_Key.checkHeartBeat_2);
						appVO.setCheckBloodResult(System_Key.checkBloodResult_2);

						String checkResult = appVO.getCheckResult();
						personId = appVO.getPersonId();
						// if (personId == System_Key.default_0) {

						log.debug("personId:" + personId);
						// if (personId > System_Key.default_0) {
						if (EasyStr.isNotEmpty(personId)) {
							if (System_Key.checkResult_1
									.equalsIgnoreCase(checkResult)) {
								++testNormalTotal;
							} else {
								++testAbnormalTotal;
							}
							++testTotal;
							AppUseTotal appUseTotal = new AppUseTotal();
							appUseTotal.setPersonId(personId);
							appUseTotal.setTestAbnormalTotal(testAbnormalTotal);
							appUseTotal.setTestNormalTotal(testNormalTotal);
							appUseTotal.setTestTotal(testTotal);
							log.debug("personId:" + personId
									+ "<-->testAbnormalTotal:"
									+ testAbnormalTotal
									+ "<-->testNormalTotal:" + testNormalTotal);
							list.add(appVO);
							userTotallist.add(appUseTotal);
						}
					}
				}
			}

			vo.setList(list);
			vo.setUserTotallist(userTotallist);

		}
		return vo;
	}

	/**
	 * 转换 批量上传的 病例 数据
	 * 
	 * @param appJsonList
	 * @return
	 */
	private AppMedicalRecordVO initAppMedicalRecordVOByList(List<?> appJsonList) {
		AppMedicalRecordVO vo = null;
		int appJsonList_Size = 0;
		if (appJsonList != null) {
			appJsonList_Size = appJsonList.size();
		}
		if (appJsonList_Size > 0) {
			vo = new AppMedicalRecordVO();
			List<AppMedicalRecord> list = new ArrayList<AppMedicalRecord>();
			/**
			 * 测试正常总数
			 */
			int testNormalTotal = System_Key.default_0;

			/**
			 * 测试异常整数
			 */
			int testAbnormalTotal = System_Key.default_0;

			String personId = System_Key.default_Clear;

			for (int i = 0; i < appJsonList_Size; i++) {

				String UploadDate = DateUtil.getToday();
				Date createTime = EasyStr.nowDate();
				String appJsonValue = IKnetUtil.binder.toJson(appJsonList
						.get(i));

				appJsonValue = EasyStr.strToTrim(appJsonValue);
				log.debug("appJsonValue:" + appJsonValue);
				if (EasyStr.isNotEmpty(appJsonValue)) {
					log.debug("initAppMedicalRecordVOByList-->appJsonValue:"
							+ appJsonValue);
					AppMedicalRecord appVO = IKnetUtil.binder.fromJson(
							appJsonValue, AppMedicalRecord.class);
					log.debug("appVO:" + appVO);
					if (appVO != null) {
						String uploadDate = EasyStr.strToTrim(appVO
								.getUploadDate());
						log.debug("uploadDate:" + uploadDate);
						appVO.setMedicalRecordId(this.getIknetId());
						// if (uploadDate == null) {
						if (EasyStr.isNotEmpty(uploadDate)) {
							uploadDate = UploadDate;
						}
						appVO.setUploadDate(uploadDate);
						appVO.setCreateTime(createTime);
						appVO.setUpdateTime(createTime);
						// appVO.setMedicalRecordRemark(System_Key.default_Clear);
						appVO.setMedicalRecordNo(SequenceUtil
								.getSyncMedicalRecordNo());
						/**
						 * 初始化 同步第三方标识
						 */
						appVO.setThreeAutoFlag(System_Key.ThreeAutoFlag_N);

						/**
						 * 初始化 QQ 健康中心 必填字段
						 */
						appVO.setCheckHeartResult(System_Key.checkHeartResult_1);
						appVO.setCheckHeartBeat(System_Key.checkHeartBeat_2);
						appVO.setCheckBloodResult(System_Key.checkBloodResult_2);

						String checkResult = appVO.getCheckResult();
						// if (personId == System_Key.default_0) {
						if (EasyStr.isEmpty(personId)) {

							personId = appVO.getPersonId();
						}
						log.debug("personId:" + personId);
						// if (personId > System_Key.default_0) {
						if (EasyStr.isNotEmpty(personId)) {
							if (System_Key.checkResult_1
									.equalsIgnoreCase(checkResult)) {
								++testNormalTotal;
							} else {
								++testAbnormalTotal;
							}
							list.add(appVO);
						}
					}
				}
			}

			log.debug("personId:" + personId + "<-->testAbnormalTotal:"
					+ testAbnormalTotal + "<-->testNormalTotal:"
					+ testNormalTotal);
			vo.setList(list);
			vo.setPersonId(personId);
			vo.setTestAbnormalTotal(testAbnormalTotal);
			vo.setTestNormalTotal(testNormalTotal);
		}
		return vo;
	}

	/**
	 * 变更 测量 数
	 * 
	 * @param userTotallist
	 * @return
	 * @throws Exception
	 */
	private int batchUpdateAppUseTotal(List<AppUseTotal> userTotallist)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("batchUpdateAppUseTotal");
		int result = System_ResultKey.Result_default;
		if (userTotallist != null) {
			int userTotallist_size = 0;
			if (userTotallist != null) {
				userTotallist_size = userTotallist.size();
			}
			for (int i = 0; i < userTotallist_size; i++) {
				AppUseTotal vo = userTotallist.get(i);
				result = sqlSession.update(AppUseTotalService.namespace
						+ "batchUpdateAppUseTotal", vo);
			}
		}
		return result;
	}

	/**
	 * 变更 测量 异常整数
	 * 
	 * @param personId
	 * @param testTotal
	 * @param testAbnormalTotal
	 * @return
	 * @throws Exception
	 */
	private int updateAppUseTotalForTestAbnormalTotal(String personId,
			long testTotal, long testAbnormalTotal) throws Exception {
		// TODO Auto-generated method stub
		log.debug("updateAppUseTotalForTestAbnormalTotal");
		int result = System_ResultKey.Result_default;
		// if (personId > 0) {
		if (EasyStr.isNotEmpty(personId)) {
			Map<String, Object> map = IKnetUtil.getResponseMap();
			map.put(System_Key.personId, personId);
			map.put(System_Key.testTotal, testTotal);
			map.put(System_Key.testAbnormalTotal, testAbnormalTotal);
			log.debug("map:" + map);
			result = sqlSession.update(AppUseTotalService.namespace
					+ "updateAppUseTotalForTestAbnormalTotal", map);
		}
		return result;
	}

	/**
	 * 变更 测量 正常整数
	 * 
	 * @param personId
	 * @param testTotal
	 * @param testNormalTotal
	 * @return
	 * @throws Exception
	 */
	private int updateAppUseTotalForTestNormalTotal(String personId,
			long testTotal, long testNormalTotal) throws Exception {
		// TODO Auto-generated method stub
		log.debug("updateAppUseTotalForTestNormalTotal");
		int result = System_ResultKey.Result_default;
		// if (personId > 0) {
		if (EasyStr.isNotEmpty(personId)) {
			Map<String, Object> map = IKnetUtil.getResponseMap();
			map.put(System_Key.personId, personId);
			map.put(System_Key.testTotal, testTotal);
			map.put(System_Key.testNormalTotal, testNormalTotal);
			log.debug("map:" + map);
			result = sqlSession.update(AppUseTotalService.namespace
					+ "updateAppUseTotalForTestNormalTotal", map);
		}
		return result;
	}

	/**
	 * 查询用户 某一段时间 内的 病例 [每天取最后一条]
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordByMapForEveryDay(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppMedicalRecordByMapForEveryDay");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace
				+ "queryAppMedicalRecordByMapForEveryDay", map);
	}

	/**
	 * 查询用户 某一天的 病例 [显示该天的 所有 上传的 病例]
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordByMapForOneDay(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppMedicalRecordByMapForOneDay");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace
				+ "queryAppMedicalRecordByMapForOneDay", map);
	}

	/**
	 * 查询用户的 病例 默认 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordByMapForDefault(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppMedicalRecordByMapForDefault");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace
				+ "queryAppMedicalRecordByMapForDefault", map);
	}

	/**
	 * 分页 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppMedicalRecord> queryAppMedicalRecordListForPage(
			Map<String, Object> map) throws Exception {
		log.debug("queryAppMedicalRecordListForPage");
		log.debug("map:" + map);
		return sqlSession.selectList(namespace
				+ "queryAppMedicalRecordListForPage", map);
	}

	/**
	 * 根据 personId 查询 最后一次 测量数据
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public AppMedicalRecord queryAppMedicalRecordForLastOneByPersonId(
			String personId) throws Exception {
		log.debug("queryAppMedicalRecordForLastOneByPersonId");
		log.debug("personId:" + personId);
		return sqlSession.selectOne(namespace
				+ "queryAppMedicalRecordForLastOneByPersonId", personId);
	}

	/**
	 * 删除 测量报告
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int deleteAppMedicalRecord(AppMedicalRecord vo) throws Exception {
		// TODO Auto-generated method stub
		log.debug("deleteAppMedicalRecord");
		log.debug("vo:" + vo);
		return sqlSession.delete(namespace + "deleteAppMedicalRecord", vo);
	}
}
