package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppCommunity;
import com.iknet.server.appserver.pojo.AppLevel;

public interface AppCommunityService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppCommunityMapper.";

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> addAppCommunity(AppCommunity vo)
			throws Exception;

	/**
	 * 分页 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppCommunity> queryAppCommunityListForPage(
			Map<String, Object> map) throws Exception;

	// /**
	// * 根据 communityId 查询
	// *
	// * @param communityId
	// * @return
	// * @throws Exception
	// */
	// public AppCommunity queryAppCommunityByCommunityId(String communityId)
	// throws Exception;

	/**
	 * 根据 communityId 删除
	 * 
	 * @param communityId
	 * @return
	 * @throws Exception
	 */
	public int deleteAppCommunityByCommunityId(String communityId)
			throws Exception;

	/**
	 * 根据 vo 查询 条数
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryAppCommunityCount(AppCommunity vo) throws Exception;

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppCommunity(AppCommunity vo) throws Exception;

	/**
	 * 查询 一级机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<AppLevel> queryAppLevelListForCommunity() throws Exception;
}
