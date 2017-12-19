package com.iknet.commons.util;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.iknet.commons.baseCode.BusiConstants.System_Flag;
import com.iknet.commons.baseCode.BusiConstants.System_IKnet_Server_Key;
import com.iknet.commons.baseCode.BusiConstants.System_ResourceFile;

/**
 * 从配置文件中 读取 参数 并放入到 内存中
 * 
 * @author luozd
 * 
 */
public class SystemUtil
{
    private static Logger log = Logger.getLogger(SystemUtil.class);
    
    protected static ResourceFile system_Resource =
        System_ResourceFile.System_Resource;
    
    public static String initServerMethod(String serverKey, String methodKey)
    {
        String serverMethod = "";
        StringBuffer serverMethodBuffer = new StringBuffer();
        serverKey = EasyStr.strToTrim(serverKey);
        methodKey = EasyStr.strToTrim(methodKey);
        String serverValue = "";
        if (EasyStr.isNotEmpty(serverKey))
        {
            serverValue = system_Resource.getPropertyValue(serverKey);
        }
        String methodValue = "";
        if (EasyStr.isNotEmpty(methodKey))
        {
            
            methodValue = system_Resource.getPropertyValue(methodKey);
        }
        
        if (EasyStr.isNotEmpty(serverValue))
        {
            serverMethodBuffer.append(serverValue);
        }
        
        if (EasyStr.isNotEmpty(methodValue))
        {
            serverMethodBuffer.append(System_Flag.Split_Flag_Flag);
            serverMethodBuffer.append(methodValue);
        }
        
        serverMethod = serverMethodBuffer.toString();
        return serverMethod;
    }
    
    private static String getKeyValueFromGlobalSession(String key)
    {
        String key_Value = "";
        if (RequestContextHolder.getRequestAttributes() != null)
        {
            key_Value =
                (String) RequestContextHolder.getRequestAttributes()
                    .getAttribute(key, RequestAttributes.SCOPE_GLOBAL_SESSION);
            
        }
        
        return key_Value;
    }
    
    private static void setKeyValueToGlobalSession(String key, String key_Value)
    {
        
        if (RequestContextHolder.getRequestAttributes() != null)
        {
            
            RequestContextHolder.getRequestAttributes().setAttribute(key,
                key_Value,
                RequestAttributes.SCOPE_GLOBAL_SESSION);
            
        }
        
    }
    
    /**
     * 获取 文件服务器 显示 站点
     * 
     * @return
     */
    public static String getIKnet_Server_Site()
    {
        String IKnet_Server_Site =
            getKeyValueFromGlobalSession(System_IKnet_Server_Key.IKnet_Server_Site_Key);
        
        if (EasyStr.isEmpty(IKnet_Server_Site))
        {
            IKnet_Server_Site =
                initServerMethod(System_IKnet_Server_Key.IKnet_Server_Site, null);
            if (EasyStr.isNotEmpty(IKnet_Server_Site))
            {
                setKeyValueToGlobalSession(System_IKnet_Server_Key.IKnet_Server_Site_Key,
                    IKnet_Server_Site);
            }
        }
        
        return IKnet_Server_Site;
    }
    
    /**
     * 获取 iknet server UploadFileName url
     * 
     * @return
     */
    public static String getUrlForIKnet_Server_Method_UploadFileName()
    {
        String url_UploadFileName =
            getKeyValueFromGlobalSession(System_IKnet_Server_Key.IKnet_Server_Method_UploadFileName_Key);
        
        if (EasyStr.isEmpty(url_UploadFileName))
        {
            url_UploadFileName =
                initServerMethod(System_IKnet_Server_Key.IKnet_Server,
                    System_IKnet_Server_Key.IKnet_Server_Method_UploadFileName);
            if (EasyStr.isNotEmpty(url_UploadFileName))
            {
                setKeyValueToGlobalSession(System_IKnet_Server_Key.IKnet_Server_Method_UploadFileName_Key,
                    url_UploadFileName);
            }
        }
        
        log.debug("url_UploadFileName:" + url_UploadFileName);
        return url_UploadFileName;
    }
    
    /**
     * 获取 iknet server Upload url
     * 
     * @return
     */
    public static String getUrlForIKnet_Server_Method_Upload()
    {
        String url_Upload =
            getKeyValueFromGlobalSession(System_IKnet_Server_Key.IKnet_Server_Method_Upload_Key);
        
        if (EasyStr.isEmpty(url_Upload))
        {
            
            url_Upload =
                initServerMethod(System_IKnet_Server_Key.IKnet_Server,
                    System_IKnet_Server_Key.IKnet_Server_Method_Upload);
            if (EasyStr.isNotEmpty(url_Upload))
            {
                setKeyValueToGlobalSession(System_IKnet_Server_Key.IKnet_Server_Method_Upload_Key,
                    url_Upload);
            }
        }
        
        log.debug("url_Upload:" + url_Upload);
        return url_Upload;
    }
    
    /**
     * 获取 文件 上传 临时 目录
     * 
     * @return
     */
    public static String getIKnet_Portal_Server_Tmp()
    {
        String IKnet_Portal_Server_Tmp =
            getKeyValueFromGlobalSession(System_IKnet_Server_Key.IKnet_Portal_Server_Tmp_Key);
        
        if (EasyStr.isEmpty(IKnet_Portal_Server_Tmp))
        {
            IKnet_Portal_Server_Tmp =
                initServerMethod(System_IKnet_Server_Key.IKnet_Portal_Server_Tmp, null);
            
            if (EasyStr.isNotEmpty(IKnet_Portal_Server_Tmp))
            {
                setKeyValueToGlobalSession(System_IKnet_Server_Key.IKnet_Portal_Server_Tmp_Key,
                    IKnet_Portal_Server_Tmp);
            }
        }
        return IKnet_Portal_Server_Tmp;
    }
    
    public static String getIKnet_portalKey()
    {
        
        String IKnet_portalKey =
            getKeyValueFromGlobalSession(System_IKnet_Server_Key.IKnet_portalKey_Key);
        
        if (EasyStr.isEmpty(IKnet_portalKey))
        {
            IKnet_portalKey =
                initServerMethod(System_IKnet_Server_Key.IKnet_portalKey, null);
            if (EasyStr.isNotEmpty(IKnet_portalKey))
            {
                setKeyValueToGlobalSession(System_IKnet_Server_Key.IKnet_portalKey_Key,
                    IKnet_portalKey);
            }
            
        }
        
        return IKnet_portalKey;
    }
}
