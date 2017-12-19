package com.iknet.server.appserver.service;

import java.util.List;
import java.util.Map;

import com.iknet.server.appserver.pojo.AppBaiKeType;

/**
 * 百科类型
 * 
 * @author luozd
 * 
 */
public interface AppBaiKeTypeService
{
    public final String namespace = "com.iknet.server.appserver.mpper.AppBaiKeTypeMapper.";
    
    /**
    * 查询
    * @param map
    * @return
    * @throws Exception
    */
    public List<AppBaiKeType> queryAppBaiKeTypeForList(Map<String, Object> map)
        throws Exception;
    
}
