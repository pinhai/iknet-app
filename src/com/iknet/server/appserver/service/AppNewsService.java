package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppNews;

/**
 * APP对应 消息
 * 
 * @author luozd
 * 
 */
public interface AppNewsService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppNewsMapper.";
    
    /**
    * 新增 
    * @param vo
    * @return
    * @throws Exception
    */
    public int addAppNews(AppNews vo) throws Exception;
    
    /**
     * 修改
     * @param vo
     * @return
     * @throws Exception
     */
    public int updateAppNews(AppNews vo) throws Exception;
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppNews> queryAppNewsForList(Map<String, Object> map)
        throws Exception;
    
    /**
     * 根据 newsId 查询
     * @param newsId
     * @return
     * @throws Exception
     */
    public AppNews queryAppNewsByNewsId(long newsId) throws Exception;
}
