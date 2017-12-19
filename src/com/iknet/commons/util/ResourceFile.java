package com.iknet.commons.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取 配置 文件
 * 
 * @author luozd
 * 
 */
public class ResourceFile
{
    private static Logger log = Logger.getLogger(ResourceFile.class);
    
    private String filename = "Iknet_Email.properties";
    
    public ResourceFile(String filename)
    {
        this.filename = filename;
    }
    
    /**
     * 根据KEY查询一个resouce中定义的一个值
     * 
     * @param key
     * @return
     */
    public String getPropertyValue(String key)
    {
        Properties props = new Properties();
        try
        {
            log.debug("filename:" + filename);
            FileInputStream in = new FileInputStream(filename);
            props.load(in);
            return props.getProperty(key);
        }
        catch (Exception e)
        {
            try
            {
                props.load(getClass().getResourceAsStream(filename));
                return EasyStr.strToTrim(props.getProperty(key));
            }
            catch (Exception ex)
            {
                
                log.error("==========================================");
                log.error("Can not find the file of config!");
                log.error("==========================================");
                ex.printStackTrace();
            }
            log.error("==========================================");
            log.error("Can not find the file of config!");
            log.error("==========================================");
            e.printStackTrace();
            return null;
        }
    }
    
    public String getJNDI()
    {
        return getPropertyValue("JNDIName");
    }
    
    public int getDefaultPageSize()
    {
        return Integer.parseInt(getPropertyValue("PageSize"));
    }
    
    //    public static void main(String[] args)
    //    {
    //        ResourceFile System_Resource = System_ResourceFile.System_Resource;
    //        String IKnet_App_Version_Path =
    //            System_Resource.getPropertyValue(System_App_Version.IKnet_App_Version_Path)
    //                + System_Resource.getPropertyValue(System_App_Version.IKnet_App_Version_Name);
    //        System.err.println(System_Resource.getPropertyValue(System_App_Version.IKnet_App_Version_Path));
    //        System.err.println(System_Resource.getPropertyValue(System_App_Version.IKnet_App_Version_Name));
    //        
    //        ResourceFile IKnet_App_Version_Resource =
    //            new ResourceFile(IKnet_App_Version_Path);
    //        
    //        System.err.println(IKnet_App_Version_Resource.getPropertyValue(System_App_Version.Android_AppVersion));
    //        System.err.println(IKnet_App_Version_Resource.getPropertyValue(System_App_Version.Android_AppDownLoadUrl));
    //        
    //        System.err.println(IKnet_App_Version_Resource.getPropertyValue(System_App_Version.IOS_AppVersion));
    //        System.err.println(IKnet_App_Version_Resource.getPropertyValue(System_App_Version.IOS_AppDownLoadUrl));
    //        
    //        // ResourceFile emailresourceFile = new ResourceFile(
    //        // ResourceFile.Email_Config_Path);
    //        // log.debug("emailresourceFile-->emailresourceFile:" +
    //        // emailresourceFile);
    //        // String
    //        // Email_Smtp_Port=emailresourceFile.getPropertyValue("Email_Smtp_Port");
    //        // log.debug("Email_Smtp_Port:" + Email_Smtp_Port);
    //        
    //        //
    //        // ResourceFile jobconfigResourceFile =
    //        // SystemParameterConstant.jobconfigResourceFile;
    //        // System.err.println("jobconfigResourceFile:" + jobconfigResourceFile);
    //        // String delay_Key = SystemJobConstant.MyForwardWareHouse_Delay_Key;
    //        // long Delay_default = SystemParameterConstant.Delay_default;
    //        // delay_Key = StringUtil.strToTrim(delay_Key);
    //        // if (!StringUtil.isEmpty(delay_Key)) {
    //        // String delay = jobconfigResourceFile.getPropertyValue(delay_Key);
    //        // delay = StringUtil.strToTrim(delay);
    //        // if (StringUtil.isInteger(delay)) {
    //        // Delay_default = StringUtil.strToLong(delay);
    //        // }
    //        // }
    //        // log.debug("init_DelayForJob-->Delay_default:" + Delay_default);
    //        
    //        // ResourceFile resourceFile = new
    //        // ResourceFile(ResourceFile.APP_RESOURCE);
    //        // String dccFilePath =
    //        // resourceFile.getPropertyValue("DCC_IMPORT_PATH");
    //        
    //    }
}
