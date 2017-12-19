/**
 * @(#)SafetyFilter.java 1.0 2014-11-19
 * @Copyright:  Copyright 2014 - 2014 iknet Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2014-11-19
 * Author:      zhangzhongguo 00001
 * Version:     IKNET_PORTAL 1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.iknet.commons.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iknet.commons.baseCode.BusiConstants.System_ResourceFile;

public class SafetyFilter implements Filter
{
    
    
    private static ResourceFile system_Resource =
        System_ResourceFile.System_Resource;
    
    private static final Logger loger =
        LoggerFactory.getLogger(SafetyFilter.class);
    
    
    
    //通讯加密验证是否启动
    private boolean verification_status = true;
    
  
    
    
    @Override
    public void init(FilterConfig filterConfig)
    {
       
        
        //readProperties();
        
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        loger.debug("SafetyFilter---"+verification_status);
        boolean f = true;
        //开启加密验证
        if (verification_status){
            f = appRequestManage(req);
                if (f)
                {
                    chain.doFilter(req, res);
                    return;
                }
                else
                {
                    request.setCharacterEncoding("UTF-8");
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().print("{\"appStatus\":{\"errorCode\":\"00001\",\"success\":false,\"msg\":\"非法请求\"}");
                    return;
                }
            }else{//没有开启加密验证，则直接请求到实际方法
                
                chain.doFilter(req, res);
                return;
                
            }
            
        
        
    }
    
    /**
     * <p>处理APP请求</p>
     * @param chain
     * @param req
     * @param res
     * @author zhangzhongguo
     */
    private boolean appRequestManage(ServletRequest req)
    {
        HttpServletRequest request = (HttpServletRequest) req;
        String appVal = request.getHeader("1knet-app-key");
        loger.debug("appVal:"+appVal);
        
        boolean f = false;
        try
        {
                //验证加密KEY
                ThreeDES des = new ThreeDES();
                if (appVal != null && !("").equals(appVal))
                {
                   String key =  system_Resource.getPropertyValue("1knet-app-key");
                   String systemVal =  system_Resource.getPropertyValue("1knet-app-val");
                    String appRealyValue = des.desedeDecoder(appVal, key);
                    
                    if (systemVal.equals(appRealyValue))
                    {
                        f = true;
                    }
                    else
                    {
                        return false;
                    }
                    
                }
                else
                {
                    return false;
                }
               
        }
        catch (Exception e)
        {
            loger.error("解密KEY出现错误" + e.getMessage());
        }
        return f;
        
    }
    @Override
    public void destroy()
    {
        
    }
    
    
}
