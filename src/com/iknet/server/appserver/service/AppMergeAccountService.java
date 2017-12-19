package com.iknet.server.appserver.service;

import java.util.List;

import com.iknet.server.appserver.pojo.AppMergeAccount;

/**
 * 用户 关联帐号
 * 
 * @author luozd
 * 
 */
public interface AppMergeAccountService {
	public final String namespace = "com.iknet.server.appserver.mpper.AppMergeAccountMapper.";

	/**
	 * 新增 关联账户
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int addAppMergeAccount(AppMergeAccount vo) throws Exception;

	/**
	 * 查询 关联帐号
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public List<AppMergeAccount> queryAppMergeAccount(AppMergeAccount vo)
			throws Exception;

	/**
	 * 删除 关联 帐号
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int deleteAppMergeAccount(AppMergeAccount vo) throws Exception;

}
