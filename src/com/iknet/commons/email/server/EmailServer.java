/**
 * 
 */
package com.iknet.commons.email.server;

import com.iknet.commons.email.util.MailSenderInfo;

/**
 * @author luozd
 *
 */
public interface EmailServer
{
    public int sendEmail(MailSenderInfo mailInfo);
}
