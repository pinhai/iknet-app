package com.iknet.commons.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.iknet.commons.baseCode.BusiConstants.System_Key;

/**
 * app json 转换 util
 * 
 * @author luozd
 *
 */
public class AppJsonUtil
{
    private static Logger log = Logger.getLogger(AppJsonUtil.class);
    
    /**
     * 获取 appJson 数据
     * @param appJson
     * @return
     */
    public static String getJsonValue(String appJson)
    {
        String appJsonValue = "";
        appJson = EasyStr.strToTrim(appJson);
        if (EasyStr.isNotEmpty(appJson))
        {
            String[] appJsonArr = appJson.split(System_Key.appJson);
            if ((appJsonArr != null) && (appJsonArr.length > 1))
            {
                appJsonValue = appJsonArr[1];
            }
            
        }
        log.debug("getJsonValue-->appJsonValue:" + appJsonValue);
        return appJsonValue;
    }
    
    /**
     * 获取 appVersionJson 数据
     * @param appVersionJson
     * @return
     */
    public static String getVersionJsonValue(String appVersionJson)
    {
        String appVersionJsonValue = "";
        appVersionJson = EasyStr.strToTrim(appVersionJson);
        if (EasyStr.isNotEmpty(appVersionJson))
        {
            String[] appJsonArr =
                appVersionJson.split(System_Key.appVersionJson);
            if ((appJsonArr != null) && (appJsonArr.length > 1))
            {
                appVersionJsonValue = appJsonArr[1];
            }
            
        }
        log.debug("getVersionJsonValue-->appVersionJsonValue:"
            + appVersionJsonValue);
        return appVersionJsonValue;
    }
    
    /**
     * 转换 appBatchJson 为 List 类型的 appJson
     * @param appBatchJsonValue
     * @return
     */
    public static List<?> getListJsonValueByAppBatchJsonValue(
        Map<String, ?> jsonMap, String appBatchJsonKey)
    {
        
        List<?> appJsonValueList = null;
        
        if (jsonMap != null)
        {
            appJsonValueList = (List<?>) jsonMap.get(appBatchJsonKey);
        }
        
        log.debug("getListJsonValueByAppBatchJsonValue-->appJsonValueList:"
            + appJsonValueList);
        return appJsonValueList;
    }
    
    /**
     * 获取 appPageJson 数据
     * @param appPageJson
     * @return
     */
    public static String getPageJsonValue(String appPageJson)
    {
        String appJsonValue = "";
        appPageJson = EasyStr.strToTrim(appPageJson);
        if (EasyStr.isNotEmpty(appPageJson))
        {
            String[] appJsonArr = appPageJson.split(System_Key.appPageJson);
            if ((appJsonArr != null) && (appJsonArr.length > 1))
            {
                appJsonValue = appJsonArr[1];
            }
            
        }
        log.debug("getPageJsonValue-->appPageJsonValue:" + appJsonValue);
        return appJsonValue;
    }
    
}
