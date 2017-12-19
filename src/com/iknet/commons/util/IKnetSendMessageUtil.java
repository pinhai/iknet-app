/**
  * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月3日 下午1:47:21
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
package com.iknet.commons.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.iknet.commons.baseCode.BusiConstants.System_Flag;
import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Send_Message_Key;

/**
 * 
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月3日 下午1:50:26
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
public class IKnetSendMessageUtil
{
    private static Logger log = Logger.getLogger(IKnetSendMessageUtil.class);
    
    public static String sendMessage(String content_Prefix_Flag,
        String mobilePhone, String middleContent)
    {
        String sendResult = "";
        String contents = initContents(content_Prefix_Flag, middleContent);
        //        String contents = "尊敬的用户，您注册的验证码为：" + content + "，请及时输入【医客网络】";
        
        //        String contents = "尊敬的用户，您注册的验证码为：" + content + " 【医客网络】";
        
        //解释返回结果
        Map<String, String> sendMessageMap = new HashMap<String, String>();
        sendMessageMap.put(System_Send_Message_Key.Send_Message_enterpriseID,
            SendMessageUtil.getSend_Message_enterpriseID_Key());
        sendMessageMap.put(System_Send_Message_Key.Send_Message_loginName,
            SendMessageUtil.getSend_Message_loginName_Key());
        sendMessageMap.put(System_Send_Message_Key.Send_Message_password,
            MD5MSG.toMD5(SendMessageUtil.getSend_Message_password_Key()));
        sendMessageMap.put(System_Send_Message_Key.Send_Message_smsId,
            SendMessageUtil.getSend_Message_smsId_Key());
        sendMessageMap.put(System_Send_Message_Key.Send_Message_subPort,
            SendMessageUtil.getSend_Message_subPort_Key());
        sendMessageMap.put(System_Send_Message_Key.Send_Message_content,
            EasyStr.encode(contents, System_Key.UTF_8));
        sendMessageMap.put(System_Send_Message_Key.Send_Message_mobiles,
            mobilePhone);
        sendMessageMap.put(System_Send_Message_Key.Send_Message_sendTime,
            getSend_Message_sendTime());
        //post内容
        String postContent = initSendMessageContentMap(sendMessageMap);
        //        postContent =
        //            "enterpriseID=" + enterpriseID + "&loginName=" + loginName
        //                + "&password=" + MD5MSG.toMD5(password) + "&smsId=" + smsId
        //                + "&subPort=" + subPort + "&content="
        //                + EasyStr.encode(contents, "UTF-8") + "&mobiles=" + mobilePhone
        //                + "&sendTime=" + sendTime;
        log.debug("postContent:" + postContent);
        //返回结果
        String returnXML = "";
        try
        {
            returnXML =
                EasyStr.strToTrim(HttpUtil.httpPostRequest(new URL(
                    SendMessageUtil.getSendURLKey()), postContent));
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.debug("sendURL returnXML = " + returnXML);
        if (EasyStr.isNotEmpty(returnXML))
        {
            
            //解释xml结果
            Map<Object, Object> sendResultMap =
                XmlUtil.getNodeValues(XmlUtil.getRootElement(returnXML));
            log.debug("sendResultMap:" + sendResultMap);
            if (sendResultMap != null)
            {
                
                sendResult =
                    EasyStr.strToTrim(sendResultMap.get(System_Key.SendMessage_Result_Key)
                        + ""); //应用时判断，若
            }
            
        }
        return sendResult;
    }
    
    private static String initSendMessageContentMap(
        Map<String, String> sendMessageMap)
    {
        String postContent = "";
        StringBuffer postContentBuffer = new StringBuffer();
        if (sendMessageMap != null)
        {
            Set<Entry<String, String>> entrySet = sendMessageMap.entrySet();
            
            Iterator<Entry<String, String>> iterator = entrySet.iterator();
            
            while (iterator.hasNext())
            {
                Entry<String, String> entry = iterator.next();
                String key = EasyStr.strToTrim(entry.getKey());
                String value = EasyStr.strToTrim(entry.getValue());
                postContentBuffer.append(key);
                postContentBuffer.append(System_Flag.Split_Equal);
                postContentBuffer.append(value);
                postContentBuffer.append(System_Flag.Split_And);
            }
            
        }
        postContent = postContentBuffer.toString();
        
        log.debug("postContent:" + postContent);
        
        return postContent;
        
    }
    
    private static String getSend_Message_sendTime()
    {
        String Send_Message_sendTime = "";
        
        return Send_Message_sendTime;
    }
    
    public static String initContents(String content_Prefix_Flag,
        String middleContent)
    {
        StringBuffer contentBuffer = new StringBuffer();
        String prefix_Content =
            SendMessageUtil.getSend_Message_Content_Prefix_Key(content_Prefix_Flag);
        String suffix_Content = SendMessageUtil.getSend_Message_suffix_Key();
        
        if (EasyStr.isNotEmpty(prefix_Content))
        {
            contentBuffer.append(prefix_Content);
        }
        if (EasyStr.isNotEmpty(middleContent))
        {
            contentBuffer.append(middleContent);
        }
        if (EasyStr.isNotEmpty(suffix_Content))
        {
            contentBuffer.append(suffix_Content);
        }
        String conent = contentBuffer.toString();
        log.debug("conent:" + conent);
        return conent;
    }
    //    public String getMessageContent(){
    //        
    //    }
    
}
