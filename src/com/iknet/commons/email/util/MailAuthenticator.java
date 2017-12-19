/**
 * 
 */
package com.iknet.commons.email.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件服务器验证信息
 * 
 * @author luozd
 * 
 */
public class MailAuthenticator extends Authenticator
{
    private String username;
    
    private String password;
    
    public MailAuthenticator(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(username, password);
    }
}
