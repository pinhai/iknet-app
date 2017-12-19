package com.iknet.server.appserver.pojo;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 * APP 实体类 版本更新 
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月11日 下午4:51:13
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
public class AppVersion extends BaseSerializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * App类型I:IOS;A:Android
     */
    private String appFlag;
    
    /**
     * 版本号
     */
    private String appVersion;
    
    /**
     * app下载 Url
     */
    private String appDownLoadUrl;
    
    /**
     * 是否有 新版本
     */
    private boolean newVersionFlag;
    
    public String getAppFlag()
    {
        return appFlag;
    }
    
    public void setAppFlag(String appFlag)
    {
        this.appFlag = appFlag;
    }
    
    public String getAppVersion()
    {
        return appVersion;
    }
    
    public void setAppVersion(String appVersion)
    {
        this.appVersion = appVersion;
    }
    
    public String getAppDownLoadUrl()
    {
        return appDownLoadUrl;
    }
    
    public void setAppDownLoadUrl(String appDownLoadUrl)
    {
        this.appDownLoadUrl = appDownLoadUrl;
    }
    
    public boolean isNewVersionFlag()
    {
        return newVersionFlag;
    }
    
    public void setNewVersionFlag(boolean newVersionFlag)
    {
        this.newVersionFlag = newVersionFlag;
    }
    
}
