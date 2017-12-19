/**
 * 
 */
package com.iknet.commons.email.server.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.iknet.commons.baseCode.BusiConstants.System_ResourceFile;
import com.iknet.commons.email.server.EmailServer;
import com.iknet.commons.email.util.MailAuthenticator;
import com.iknet.commons.email.util.MailSenderInfo;
import com.iknet.commons.email.util.SystemEmail;
import com.iknet.commons.util.EasyStr;
import com.iknet.commons.util.ResourceFile;

/**
 * 
 * @author luozd
 * 
 */
public abstract class BaseEmailServer implements EmailServer
{
    
    private static Logger log = Logger.getLogger(BaseEmailServer.class);
    
    /**
     * Email 配置
     */
    protected static ResourceFile resourceFile =
        System_ResourceFile.Email_Resource;
    
    private static Properties prop = null;
    
    /**
     * 发送邮件的服务器
     */
    private static String serverHost;
    
    /**
     * 发送邮件的服务器端口
     */
    private static String serverPort;
    
    /**
     * 是否需要身份验证
     */
    private static boolean validate = false;
    
    static
    {
        String Email_Smtp =
            resourceFile.getPropertyValue(SystemEmail.Email_Smtp);
        String Email_Smtp_Port =
            resourceFile.getPropertyValue(SystemEmail.Email_Smtp_Port);
        String Email_Smtp_Auth =
            resourceFile.getPropertyValue(SystemEmail.Email_Smtp_Auth);
        log.debug("Email_Smtp:" + Email_Smtp);
        log.debug("Email_Smtp_Port:" + Email_Smtp_Port);
        log.debug("Email_Smtp_Auth:" + Email_Smtp_Auth);
        if (!EasyStr.isEmpty(Email_Smtp))
        {
            serverHost = Email_Smtp;
        }
        if (!EasyStr.isEmpty(Email_Smtp_Port))
        {
            serverPort = Email_Smtp_Port;
        }
        if (!EasyStr.isEmpty(Email_Smtp_Auth))
        {
            validate = Boolean.parseBoolean(Email_Smtp_Auth);
        }
        
        if ((!EasyStr.isEmpty(serverHost)) && (!EasyStr.isEmpty(serverPort)))
        {
            prop = new Properties();
            prop.put("mail.smtp.host", serverHost);
            prop.put("mail.smtp.port", serverPort);
            prop.put("mail.smtp.auth", validate ? "true" : "false");
        }
        log.debug("prop:" + prop);
        
    }
    
    private Session initEmailSession(String emailSendUser,
        String emailSendPassword)
    {
        emailSendUser = EasyStr.strToTrim(emailSendUser);
        emailSendPassword = EasyStr.strToTrim(emailSendPassword);
        log.debug("emailSendUser:" + emailSendUser + "<-->emailSendPassword:"
            + emailSendPassword);
        Session sendMailSession = null;
        MailAuthenticator mailAuth = null;
        log.debug("validate:" + validate);
        if (validate)
        {
            mailAuth = new MailAuthenticator(emailSendUser, emailSendPassword);
        }
        
        Properties email_Properties = prop;
        log.debug("prop:" + prop);
        if (email_Properties != null)
        {
            
            sendMailSession = Session.getInstance(email_Properties, mailAuth);
            // 20110803 修改
            // 是否以调试模式 运行
            sendMailSession.setDebug(false);
        }
        log.debug("sendMailSession:" + sendMailSession);
        return sendMailSession;
    }
    
    /**
     * 设置邮件内容
     * 
     * @param mp
     * @param mailContent
     * @throws MessagingException
     */
    private void setMailContent(MimeMultipart mp, String mailContent)
        throws MessagingException
    {
        mailContent = EasyStr.strToTrim(mailContent);
        MimeBodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(mailContent, "text/html;charset=UTF-8");
        mp.addBodyPart(contentPart);
    }
    
    /**
     * 初始化 邮件 主题
     * 
     * @param subject
     * @return
     */
    private String initEmailSubject(String subject)
    {
        subject = EasyStr.strToTrim(subject);
        if (EasyStr.isEmpty(subject))
        {
            subject = "";
            
        }
        log.debug("initEmailSubject:" + subject);
        return subject;
    }
    
    private void initMailSenderInfo(MailSenderInfo mailInfo)
    {
        if (mailInfo != null)
        {
            mailInfo.setFromAddress(this.initEmail_FromAddress());
            mailInfo.setUsername(this.initEmail_Username());
            mailInfo.setPassword(this.initEmail_Password());
            mailInfo.setSubject(this.initEmail_Subject());
        }
        log.debug("initMailSenderInfo 完毕");
    }
    
    /**
     * 发送邮件
     */
    public int sendEmail(MailSenderInfo mailInfo)
    {
        log.debug("------- sendEmail  ------");
        int status = 0;
        
        String emailSendUser = "";
        String emailSendPassword = "";
        String[] toAddressesArr = null;
        this.initMailSenderInfo(mailInfo);
        if (mailInfo != null)
        {
            emailSendUser = mailInfo.getUsername();
            emailSendPassword = mailInfo.getPassword();
            toAddressesArr = mailInfo.getToAddressesArr();
        }
        
        Session sendMailSession =
            initEmailSession(emailSendUser, emailSendPassword);
        
        if (sendMailSession != null)
        {
            Message mailMessage = new MimeMessage(sendMailSession);
            String toAddresses = initToAddresses(toAddressesArr);
            log.debug("-- fromAddress  --" + mailInfo.getFromAddress());
            log.debug("-- toAddresses  --" + toAddresses);
            
            try
            {
                
                try
                {
                    mailMessage.setFrom(new InternetAddress(
                        mailInfo.getFromAddress(), this.initEmail_From()));
                }
                catch (UnsupportedEncodingException e)
                {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
                // mailMessage.setRecipients(Message.RecipientType.TO,
                // InternetAddress.parse(mailInfo.getToAddresses()));
                
                mailMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddresses));
                
                mailMessage.setSubject(this.initEmailSubject(mailInfo.getSubject()));
                
                MimeMultipart mp = new MimeMultipart();
                setMailContent(mp, mailInfo.getContent());
                
                mailMessage.setContent(mp);
                
                Transport.send(mailMessage);
                status = 1;
                log.debug("邮件发送完毕");
            }
            catch (MessagingException e)
            {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
                e.printStackTrace();
                
                status = 0;
            }
            finally
            {
                sendMailSession = null;
            }
            
        }
        
        return status;
    }
    
    private String initToAddresses(String[] toAddressesArr)
    {
        String toAddresses;
        int toAddressesArr_Length = 0;
        if (toAddressesArr != null)
        {
            toAddressesArr_Length = toAddressesArr.length;
        }
        StringBuffer toAddressesBuffer = new StringBuffer();
        if (toAddressesArr_Length > 0)
        {
            for (int i = 0; i < toAddressesArr_Length; i++)
            {
                String toAddress = EasyStr.strToTrim(toAddressesArr[i]);
                if (i == 0)
                {
                    toAddressesBuffer.append(toAddress);
                }
                else
                {
                    
                    if (!EasyStr.isEmpty(toAddress))
                    {
                        toAddressesBuffer.append(",");
                        toAddressesBuffer.append(toAddress);
                    }
                }
            }
            
        }
        
        toAddresses = toAddressesBuffer.toString();
        log.debug("toAddresses:" + toAddresses);
        return toAddresses;
    }
    
    /**
     * 配置文件中 邮件发送的 From
     * 
     * @return
     */
    protected abstract String initEmail_From_Key();
    
    protected abstract String initEmail_FromAddress_Key();
    
    protected abstract String initEmail_Username_Key();
    
    protected abstract String initEmail_Password_Key();
    
    protected abstract String initEmail_Subject_Key();
    
    /**
     * 
     * 邮件发送的 From
     */
    private String initEmail_From()
    {
        String key = initEmail_From_Key();
        String Email_From = resourceFile.getPropertyValue(key);;
        log.debug("Email_From:" + Email_From);
        return Email_From;
    }
    
    private String initEmail_FromAddress()
    {
        String key = initEmail_FromAddress_Key();
        String Email_FromAddress = resourceFile.getPropertyValue(key);
        log.debug("Email_FromAddress:" + Email_FromAddress);
        return Email_FromAddress;
    }
    
    private String initEmail_Username()
    {
        String key = initEmail_Username_Key();
        String Email_Username = resourceFile.getPropertyValue(key);
        log.debug("Email_Username:" + Email_Username);
        return Email_Username;
    }
    
    private String initEmail_Password()
    {
        String key = initEmail_Password_Key();
        String iEmail_Password = resourceFile.getPropertyValue(key);
        log.debug("iEmail_Password:" + iEmail_Password);
        return iEmail_Password;
    }
    
    private String initEmail_Subject()
    {
        String key = initEmail_Subject_Key();
        String Email_Subject = resourceFile.getPropertyValue(key);
        log.debug("Email_Subject:" + Email_Subject);
        return Email_Subject;
    }
}
