package com.iknet.commons.util;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.iknet.commons.baseCode.BusiConstants.System_ResourceFile;
import com.iknet.commons.baseCode.BusiConstants.System_Send_Message_Key;

/**
 * 从配置文件中 读取 参数 并放入到 内存中
 * 
 * @author luozd
 * 
 */
public class SendMessageUtil
{
    private static Logger log = Logger.getLogger(SendMessageUtil.class);
    
    protected static ResourceFile send_Message_Resource =
        System_ResourceFile.Send_Message_Resource;
    
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
    
    private static String initValueFromSend_Message_Resource(String key)
    {
        String value = "";
        key = EasyStr.strToTrim(key);
        if (EasyStr.isNotEmpty(key))
        {
            
            value = send_Message_Resource.getPropertyValue(key);
        }
        value = EasyStr.strToTrim(value);
        log.debug("value:" + value);
        return value;
    }
    
    public static String getSendURLKey()
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(System_Send_Message_Key.Send_Message_sendURL_key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(System_Send_Message_Key.Send_Message_sendURL_key);
            
            setKeyValueToGlobalSession(System_Send_Message_Key.Send_Message_sendURL_key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
    
    public static String getSend_Message_enterpriseID_Key()
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(System_Send_Message_Key.Send_Message_enterpriseID_Key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(System_Send_Message_Key.Send_Message_enterpriseID_Key);
            
            setKeyValueToGlobalSession(System_Send_Message_Key.Send_Message_enterpriseID_Key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
    
    public static String getSend_Message_loginName_Key()
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(System_Send_Message_Key.Send_Message_loginName_Key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(System_Send_Message_Key.Send_Message_loginName_Key);
            
            setKeyValueToGlobalSession(System_Send_Message_Key.Send_Message_loginName_Key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
    
    public static String getSend_Message_password_Key()
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(System_Send_Message_Key.Send_Message_password_Key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(System_Send_Message_Key.Send_Message_password_Key);
            
            setKeyValueToGlobalSession(System_Send_Message_Key.Send_Message_password_Key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
    
    public static String getSend_Message_smsId_Key()
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(System_Send_Message_Key.Send_Message_smsId_Key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(System_Send_Message_Key.Send_Message_smsId_Key);
            
            setKeyValueToGlobalSession(System_Send_Message_Key.Send_Message_smsId_Key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
    
    public static String getSend_Message_subPort_Key()
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(System_Send_Message_Key.Send_Message_subPort_Key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(System_Send_Message_Key.Send_Message_subPort_Key);
            
            setKeyValueToGlobalSession(System_Send_Message_Key.Send_Message_subPort_Key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
    
    public static String getSend_Message_suffix_Key()
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(System_Send_Message_Key.Send_Message_suffix_Key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(System_Send_Message_Key.Send_Message_suffix_Key);
            
            setKeyValueToGlobalSession(System_Send_Message_Key.Send_Message_suffix_Key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
    
    public static String getSend_Message_Content_Prefix_Key(
        String send_Message_prefix_Key)
    {
        String Send_Message_sendURL_key =
            getKeyValueFromGlobalSession(send_Message_prefix_Key);
        
        if (EasyStr.isEmpty(Send_Message_sendURL_key))
        {
            Send_Message_sendURL_key =
                initValueFromSend_Message_Resource(send_Message_prefix_Key);
            
            setKeyValueToGlobalSession(send_Message_prefix_Key,
                Send_Message_sendURL_key);
            
        }
        
        return Send_Message_sendURL_key;
    }
}
