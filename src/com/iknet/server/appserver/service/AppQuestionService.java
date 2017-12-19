package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppQuestion;

/**
 * APP问答
 * 
 * @author luozd
 * 
 */
public interface AppQuestionService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppQuestionMapper.";
    
    /**
    * 新增 
    * @param vo
    * @return
    * @throws Exception
    */
    public int addAppQuestion(AppQuestion vo) throws Exception;
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppQuestion> queryAppQuestionListForPage(Map<String, Object> map)
        throws Exception;
    
    /**
     *  根据 id 查询
     * @param id
     * @return
     * @throws Exception
     */
    public AppQuestion queryAppQuestionById(long id) throws Exception;
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppQuestion> queryMyAppQuestionListForPage(
        Map<String, Object> map) throws Exception;
}
