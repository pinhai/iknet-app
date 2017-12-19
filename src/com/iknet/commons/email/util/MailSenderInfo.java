/**
 * 
 */
package com.iknet.commons.email.util;

/**
 * @author luozd
 * 
 */
public class MailSenderInfo
{
    // 用户名
    private String username;
    
    // 密码
    private String password;
    
    // 发送人地址
    private String fromAddress;
    
    // // 收件从地址
    // private String toAddresses;
    
    // 主题
    private String subject;
    
    // 邮件内容 正文 头部
    private String content_head;
    
    // 邮件内容 正文 中间
    private String content_body;
    
    // 邮件内容 正文 底部
    private String content_foot;
    
    // 邮件内容
    private String content;
    
    // 多个收件从地址
    private String[] toAddressesArr;
    
    // add by lzd 2013-07-03
    /**
     * 附件 标题 default
     */
    private String title;
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getFromAddress()
    {
        return fromAddress;
    }
    
    public void setFromAddress(String fromAddress)
    {
        this.fromAddress = fromAddress;
    }
    
    public String getSubject()
    {
        return subject;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public String getContent_head()
    {
        return content_head;
    }
    
    public void setContent_head(String content_head)
    {
        this.content_head = content_head;
    }
    
    public String getContent_body()
    {
        return content_body;
    }
    
    public void setContent_body(String content_body)
    {
        this.content_body = content_body;
    }
    
    public String getContent_foot()
    {
        return content_foot;
    }
    
    public void setContent_foot(String content_foot)
    {
        this.content_foot = content_foot;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String[] getToAddressesArr()
    {
        return toAddressesArr;
    }
    
    public void setToAddressesArr(String[] toAddressesArr)
    {
        this.toAddressesArr = toAddressesArr;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
}
