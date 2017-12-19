package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppAnswer;

/**
 * 医疗问答
 * 
 * @author luozd
 * 
 */
public interface AppAnswerService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppAnswerMapper.";
    
    /**
    * 新增 
    * @param vo
    * @return
    * @throws Exception
    */
    public int addAppAnswer(AppAnswer vo) throws Exception;
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppAnswer> queryAppAnswerListForPage(Map<String, Object> map)
        throws Exception;
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppAnswer> queryMyAppAnswerListForPage(Map<String, Object> map)
        throws Exception;
}
