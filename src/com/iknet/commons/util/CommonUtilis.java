package com.iknet.commons.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.iknet.commons.baseCode.BusiConstants.System_Map;
import com.iknet.commons.baseCode.BusiConstants.System_UserKey;
import com.iknet.server.appserver.pojo.AppValidateTmp;

public class CommonUtilis
{
    private static Logger log = Logger.getLogger(CommonUtilis.class);
    
    /**
     * 初始化并 获取 分页 参数 并返回 Map
     * 
     * @param request
     * @return
     */
    public static Map<String, Object> initMapPage(String appPageJsonValue)
    {
        log.debug("initMapPage");
        
        Map<String, Object> map = new HashMap<String, Object>();
        SimplePage page = null;
        
        if (EasyStr.isNotEmpty(appPageJsonValue))
        {
            page =
                IKnetUtil.binder.fromJson(appPageJsonValue, SimplePage.class);
        }
        if (page == null)
        {
            page = new SimplePage();
        }
        
        if (page != null)
        {
            
            map.put(System_Map.Map_page, page);
        }
        
        return map;
    }
    
    /**
     * 从 appPageJson 中获取 当前页
     * @param appPageJsonValue
     * @return
     */
    public static int initPageNo(String appPageJsonValue)
    {
        log.debug("initPageNo");
        SimplePage page = null;
        
        if (EasyStr.isNotEmpty(appPageJsonValue))
        {
            page =
                IKnetUtil.binder.fromJson(appPageJsonValue, SimplePage.class);
        }
        if (page == null)
        {
            page = new SimplePage();
        }
        int pageNo = page.getPageNo();
        log.debug("pageNo:" + pageNo);
        return pageNo;
    }
    
    //    /**
    //     * 初始化并 获取 动态参数 并返回 Map
    //     * 
    //     * @param request
    //     * @param fieldKeyArr
    //     * @return
    //     */
    //    public static Map<String, Object> initRequestMap(
    //        HttpServletRequest request, String fieldKeyArr[])
    //    {
    //        
    //        log.debug("initRequestMap");
    //        Map<String, Object> map = new HashMap<String, Object>();
    //        int fieldKeyArr_Length = 0;
    //        if (fieldKeyArr != null)
    //        {
    //            fieldKeyArr_Length = fieldKeyArr.length;
    //        }
    //        for (int i = 0; i < fieldKeyArr_Length; i++)
    //        {
    //            
    //            map.put(fieldKeyArr[i], request.getParameter(fieldKeyArr[i]));
    //        }
    //        
    //        return map;
    //    }
    
    /**
     * 初始化 Login Map
     * @param loginNo
     * @param mobilePhone
     * @param email
     * @param loginSource
     * @param loginReturnId
     * @return
     */
    public static Map<String, Object> initPortalUserMap(String loginNo,
        String mobilePhone, String email, String loginSource,
        String loginReturnId)
    {
        
        log.debug("initPortalUserMap");
        loginNo = EasyStr.strToTrim(loginNo);
        mobilePhone = EasyStr.strToTrim(mobilePhone);
        email = EasyStr.strToTrim(email);
        loginReturnId = EasyStr.strToTrim(loginReturnId);
        loginSource = EasyStr.strToTrim(loginSource);
        Map<String, Object> map = null;
        boolean flag = true;
        if ((EasyStr.isEmpty(loginNo)) && (EasyStr.isEmpty(mobilePhone))
            && (EasyStr.isEmpty(email)) && (EasyStr.isEmpty(loginSource))
            && (EasyStr.isEmpty(loginReturnId)))
        {
            flag = false;
        }
        if (flag)
        {
            map = new HashMap<String, Object>();
            if (EasyStr.isNotEmpty(loginNo))
            {
                map.put(System_UserKey.PortalUser_loginNo, loginNo);
            }
            if (EasyStr.isNotEmpty(mobilePhone))
            {
                map.put(System_UserKey.PortalUser_mobilePhone, mobilePhone);
            }
            if (EasyStr.isNotEmpty(email))
            {
                map.put(System_UserKey.PortalUser_email, email);
            }
            
            if (EasyStr.isNotEmpty(loginSource))
            {
                map.put(System_UserKey.PortalUser_loginSource, loginSource);
            }
            
            if (EasyStr.isNotEmpty(loginReturnId))
            {
                map.put(System_UserKey.PortalUser_loginReturnId, loginReturnId);
            }
        }
        
        return map;
    }
    
    /**
     * 初始化 map
     * @return
     */
    public static Map<String, Object> initMap()
    {
        
        log.debug(" initMap");
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        return map;
    }
    
    public static AppValidateTmp initAppValidateTmp(String mobilePhone,
        String type, String sendDate, String validateCode, String sendWay)
    {
        AppValidateTmp vo = null;
        mobilePhone = EasyStr.strToTrim(mobilePhone);
        type = EasyStr.strToTrim(type);
        sendDate = EasyStr.strToTrim(sendDate);
        validateCode = EasyStr.strToTrim(validateCode);
        boolean flag = false;
        if ((EasyStr.isNotEmpty(mobilePhone)) && (EasyStr.isNotEmpty(type))
            && (EasyStr.isNotEmpty(sendDate)))
        {
            flag = true;
        }
        
        if (flag)
        {
            vo = new AppValidateTmp();
            vo.setMobilePhone(mobilePhone);
            vo.setSendDate(sendDate);
            vo.setType(type);
            vo.setValidateCode(validateCode);
            vo.setSendWay(sendWay);
        }
        return vo;
    }
    //    public static void main(String[] args)
    //    {
    //        String appJson = "appJson={\"id\":\"8\"} appPageJson={\"pageNo\":\"1\"}";
    ////        String appJson = "appJson={\"id\":\"8\"}";
    //        String appJsonValue = AppJsonUtil.getJsonValue(appJson);
    //        System.err.println(appJsonValue);
    //        String appPageJson = AppJsonUtil.getPageJsonValue(appJson);
    //        System.err.println(appPageJson);
    //    }
}
