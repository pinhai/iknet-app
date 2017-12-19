/**
  * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月3日 上午9:42:22
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
package com.iknet.server.appserver.pojo;

import java.util.Date;

import com.iknet.commons.baseEntity.BaseSerializable;

/**
 *发送 验证码 临时表
 * Copyright:   Copyright 2000 - 2014 IKNET Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014年11月3日 上午9:43:21
 * Author:      luozd 
 * Version:     IKNET V:1.0.0
 * Description: Initialize
 */
public class AppValidateTmp extends BaseSerializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5551959633333203439L;
    
    private String id;
    
    /**
     * 手机号码
     */
    private String mobilePhone;
    
    /**
     * 类型
     */
    private String type;
    
    /**
     * 发送日期
     */
    private String sendDate;
    
    /**
     * 验证码
     */
    private String validateCode;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 发送时间
     */
    private Date createTime;
    
    /**
     * 发送途径 E:邮件 ;S:短信;V:激活码
     */
    private String sendWay;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMobilePhone()
    {
        return mobilePhone;
    }
    
    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(String sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public String getValidateCode()
    {
        return validateCode;
    }
    
    public void setValidateCode(String validateCode)
    {
        this.validateCode = validateCode;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getSendWay()
    {
        return sendWay;
    }
    
    public void setSendWay(String sendWay)
    {
        this.sendWay = sendWay;
    }
    
}
