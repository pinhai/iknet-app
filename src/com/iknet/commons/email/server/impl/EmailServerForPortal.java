/**
 * 
 */
package com.iknet.commons.email.server.impl;

import org.springframework.stereotype.Service;

import com.iknet.commons.email.server.EmailServer;
import com.iknet.commons.email.util.SystemEmail;

/**
 * Web User 邮件通道
 * 
 * @author luozd
 * 
 */
@Service("emailServerForPortal")
public class EmailServerForPortal extends BaseEmailServer implements
    EmailServer
{
    
    /**
     * 配置文件中 邮件发送的 From
     */
    protected String initEmail_From_Key()
    {
        return SystemEmail.Email_From_Iknet;
    }
    
    protected String initEmail_FromAddress_Key()
    {
        // TODO Auto-generated method stub
        return SystemEmail.Email_FromAddress_register;
    }
    
    protected String initEmail_Username_Key()
    {
        // TODO Auto-generated method stub
        return SystemEmail.Email_Username_register;
    }
    
    protected String initEmail_Password_Key()
    {
        // TODO Auto-generated method stub
        return SystemEmail.Email_Password_register;
    }
    
    protected String initEmail_Subject_Key()
    {
        // TODO Auto-generated method stub
        return SystemEmail.Email_Subject_register;
    }
    
}
