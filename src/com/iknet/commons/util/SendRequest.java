package com.iknet.commons.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iknet.commons.baseEntity.AppStatus;

@SuppressWarnings("deprecation")
public class SendRequest
{
    
    private static final Logger loger =
        LoggerFactory.getLogger(SendRequest.class);
    
    /**
     * 发送请求数据，返回响应结果
     * 
     * @return
     * @throws Exception
     */
    public static String sendMessage(String url, List<NameValuePair> list)
    {
        
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        method.setHeader("Accept", "application/*");
        String result = "";
        
        try
        {
            // 设置加密KEY
            DesToPostValue(method, list);
            
            method.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8)); // 设置参数给Post
            HttpResponse response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();
            // 处理后台返回系统错误
            String r = doError(response);
            if (!"".equals(r))
            {
                result = r;
            }
            else
            {
                result = EntityUtils.toString(entity, "gbk");
            }
            // 关闭链接
            shutDown(httpclient);
            
        }
        catch (ClientProtocolException e)
        {
            loger.error(e.getMessage());
        }
        catch (UnsupportedEncodingException e1)
        {
            loger.error(e1.getMessage());
        }
        catch (IOException e)
        {
            loger.error(e.getMessage());
        }
        
        return result;
        
    }
    
    /**
     * 关闭链接
     * 
     * @param httpclient
     */
    public static void shutDown(HttpClient httpclient)
    {
        
        // 关闭连接,释放资源
        httpclient.getConnectionManager().shutdown();
        
    }
    
    /**
     * 处理后台返回信息错误
     * 
     * @param response
     */
    public static String doError(HttpResponse response)
    {
        String result = "";
        AppStatus p = new AppStatus();
        Header xcode = response.getFirstHeader("x-mgt-code");
        if (xcode != null)
        {
            p.setErrorCode(xcode.getValue());
            p.setMsg("系统后台发生错误！");
            p.setSuccess(false);
            result = JsonBinder.getInstance().toJson(p);
            loger.error("---- 系统后台抛出错误---- start--------------------------------------------------");
            loger.error(" 错误代码 ：" + xcode.getValue());
            loger.error("--系统后台抛出错误-----------end---------------------------------------------------");
        }
        return result;
    }
    
    /**
     * 生成数据加密KEY
     * 
     * @throws Exception
     */
    public static void DesToPostValue(HttpPost method, List<NameValuePair> list)
    {
        
        String portalKEY = SystemUtil.getIKnet_portalKey();
        String val = "";
        for (NameValuePair ls : list)
        {
            if (ls.getName().equals("jsonData"))
            {
                val = ls.getValue();
            }
        }
        try
        {
            int portalValue = val.hashCode();
            ThreeDES des = new ThreeDES();
            String por;
            por = des.desedeEncoder(String.valueOf(portalValue), portalKEY);
            method.setHeader("iknet-protal-key", por);
        }
        catch (Exception e)
        {
            loger.error("加密KEY出现错误" + e.getMessage());
        }
        
    }
    
}
