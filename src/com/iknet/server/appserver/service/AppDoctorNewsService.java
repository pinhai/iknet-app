package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppDoctorNews;

/**
 * APP名医文章
 * 
 * @author luozd
 * 
 */
public interface AppDoctorNewsService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppDoctorNewsMapper.";
    
//    /**
//    * 新增 
//    * @param vo
//    * @return
//    * @throws Exception
//    */
//    public int addAppDoctorNews(AppDoctorNews vo) throws Exception;
    
    /**
    *  修改
    * @param vo
    * @return
    * @throws Exception
    */
    public int updateAppDoctorNews(AppDoctorNews vo) throws Exception;
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppDoctorNews> queryAppDoctorNewsForList(Map<String, Object> map)
        throws Exception;
    
    /**
     * 根据  id 查询 
     * @param id
     * @return
     * @throws Exception
     */
    public AppDoctorNews queryAppDoctorNewsById(long id) throws Exception;
}
