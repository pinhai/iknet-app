package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppFamilyMembers;

/**
 * APP端对应家庭成员
 * 
 * @author luozd
 * 
 */
public interface AppFamilyMembersService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppFamilyMembersMapper.";

	/**
	 * 新增
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppFamilyMembers(AppFamilyMembers vo) throws Exception;

	/**
	 * 修改
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updateAppFamilyMembers(AppFamilyMembers vo) throws Exception;

	/**
	 * 查询
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<AppFamilyMembers> queryAppFamilyMembersForList(
			Map<String, Object> map) throws Exception;

	/**
	 * 根据 membersId 查询
	 * 
	 * @param membersId
	 * @return
	 * @throws Exception
	 */
	public AppFamilyMembers queryAppFamilyMembersByMembersId(String membersId)
			throws Exception;

	/**
	 * 根据 membersId 删除
	 * 
	 * @param membersId
	 * @return
	 * @throws Exception
	 */
	public int deleteAppFamilyMembersByMembersId(String membersId)
			throws Exception;

	/**
	 * 根据 FamilyMembers 查询 条数
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int queryAppFamilyMemberCount(AppFamilyMembers vo) throws Exception;

}
