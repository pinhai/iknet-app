package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppKnowledge;

/**
 * APP知识
 * 
 * @author luozd
 * 
 */
public interface AppKnowledgeService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppKnowledgeMapper.";
    
    /**
     * 分页查询
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppKnowledge> queryAppKnowledgeListForPage(
        Map<String, Object> map) throws Exception;
    
    /**
     * 根据 map 查询 
     * @param map
     * @return
     * @throws Exception
     */
    public AppKnowledge queryAppKnowledgeByMap(Map<String, Object> map)
        throws Exception;
    
    /**
     * 用户 点赞
     * @param id
     * @return
     * @throws Exception
     */
    public int updateAppKnowledgeNiceTotal(long id) throws Exception;
}
