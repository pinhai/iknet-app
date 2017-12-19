/**
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月3日 上午9:47:21
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
package com.iknet.server.appserver.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.iknet.commons.baseCode.BusiConstants.System_ResultKey;
import com.iknet.commons.util.BaseService;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.SequenceUtil;
import com.iknet.server.appserver.pojo.AppValidateTmp;
import com.iknet.server.appserver.service.AppValidateTmpService;

/**
 * 发送 验证码 临时表 Copyright: Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights
 * Reserved. Date: 2014年11月3日 上午9:48:15 Author: luozd Version: IKNET V:1.0.0
 * Description: Initialize
 */
@Service("appValidateTmpService")
public class AppValidateTmpServiceImpl extends BaseService implements
		AppValidateTmpService {
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
	public int addAppValidateTmp(AppValidateTmp vo) throws Exception {
		log.debug("addAppValidateTmp");
		int result = System_ResultKey.Result_default;
		if (vo != null) {

			/**
			 * 手机号码
			 */
			String mobilePhone = EasyStr.strToTrim(vo.getMobilePhone());
			/**
			 * 类型
			 */
			String type = EasyStr.strToTrim(vo.getType());
			/**
			 * 发送日期
			 */
			String sendDate = EasyStr.strToTrim(vo.getSendDate());

			boolean flag = false;
			if ((EasyStr.isNotEmpty(mobilePhone)) && (EasyStr.isNotEmpty(type))) {
				flag = true;
			}
			log.debug("flag:" + flag + "<-->mobilePhone:" + mobilePhone
					+ "<-->type:" + type + "<-->sendDate:" + sendDate);
			if (flag) {
				vo.setId(SequenceUtil.getSyncSeqNoId());
				vo.setMobilePhone(mobilePhone);
				vo.setType(type);

				if (EasyStr.isEmpty(sendDate)) {
					vo.setSendDate(EasyStr.today());
				} else {
					vo.setSendDate(sendDate);
				}
				vo.setCreateTime(EasyStr.nowDate());
				result = sqlSession.insert(namespace + "addAppValidateTmp", vo);
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
	public int queryAppValidateTmpTotalByMap(Map<String, Object> map)
			throws Exception {
		log.debug("queryAppValidateTmpTotalByMap");
		log.debug("map:" + map);
		return sqlSession.selectOne(
				namespace + "queryAppValidateTmpTotalByMap", map);
	}

	/**
	 * 根据 map 查询 validateCode 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String queryLastValidateCodeByMap(Map<String, Object> map)
			throws Exception {
		log.debug("queryLastValidateCodeByMap");
		log.debug("map:" + map);
		return sqlSession.selectOne(namespace + "queryLastValidateCodeByMap",
				map);
	}

	/**
	 * 检查 无线激活码 是否有效
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryCountByAppValidateTmp(AppValidateTmp vo) throws Exception {
		log.debug("queryCountByAppValidateTmp");
		return sqlSession.selectOne(namespace + "queryCountByAppValidateTmp",
				vo);
	}

}
