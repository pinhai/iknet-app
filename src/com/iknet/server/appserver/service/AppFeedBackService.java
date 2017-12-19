package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppFeedBack;

/**
 * APP意见反馈
 * 
 * @author luozd
 * 
 */
public interface AppFeedBackService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppFeedBackMapper.";
    
    /**
    * 新增 
    * @param vo
    * @return
    * @throws Exception
    */
    public int addAppFeedBack(AppFeedBack vo) throws Exception;
    
    /**
     * 修改
     * @param vo
     * @return
     * @throws Exception
     */
    public int updateAppFeedBack(AppFeedBack vo) throws Exception;
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppFeedBack> queryAppFeedBackForList(Map<String, Object> map)
        throws Exception;
    
    /**
     * 根据 feedBackId 查询
     * @param feedBackId
     * @return
     * @throws Exception
     */
    public AppFeedBack queryAppFeedBackByFeedBackId(long feedBackId)
        throws Exception;
}
