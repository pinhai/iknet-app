/**
 * 
 */
package com.iknet.commons.email;

import org.apache.log4j.Logger;

import com.iknet.commons.email.server.EmailServer;
import com.iknet.commons.email.server.impl.EmailServerForPortal;
import com.iknet.commons.email.util.MailSenderInfo;

/**
 * @author luozd
 * 
 */
public class EmailServerFactory
{
    private static Logger log = Logger.getLogger(EmailServerFactory.class);
    
    private static EmailServer emailServer;
    
    /**
     * 
     * @param emailServerType
     * @param mailInfo
     * @return
     */
    public static int sendEmail(MailSenderInfo mailInfo)
    {
        log.debug("------- sendEmail  ------");
        int status = 0;
        EmailServer emailServer = getEmailServer();
        if (emailServer != null)
        {
            status = emailServer.sendEmail(mailInfo);
            log.debug("邮件发送完毕");
        }
        
        log.debug("status:"+status);
        return status;
    }
    
    /**
     * 
     * @param emailServerType
     * @return
     */
    private static EmailServer getEmailServer()
    {
        if (emailServer == null)
        {
            emailServer = new EmailServerForPortal();
        }
        return emailServer;
    }
    
}
