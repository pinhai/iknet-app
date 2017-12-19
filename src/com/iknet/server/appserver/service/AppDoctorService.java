package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppDoctor;

/**
 * APP名医
 * 
 * @author luozd
 * 
 */
public interface AppDoctorService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppDoctorMapper.";
    
    /**
     * 查询
     * @param map
     * @return
     * @throws Exception
     */
    public List<AppDoctor> queryAppDoctorListForPage(Map<String, Object> map)
        throws Exception;
    
    /**
     * 根据 map 查询 
     * @param map
     * @return
     * @throws Exception
     */
    public AppDoctor queryAppDoctorByMap(Map<String, Object> map)
        throws Exception;
    
    /**
     * 用户 点赞
     * @param id
     * @return
     * @throws Exception
     */
    public int updateAppDoctorNiceTotal(String id) throws Exception;
}
