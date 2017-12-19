package com.iknet.commons.baseCode;

/**
 * 管理系统与APP交互错误码定义类（暂时定义的如下几种错误类型，APP端如果有新的错误类型，可以加入新的错误类型，通知后台及时更新）
 * @author Administrator
 *
 */
public class AppConstants
{
    
    public static final class APP
    {
        
        /**
         * 动作执行成功
         */
        public static final String actionSuccess = "00000";
        
        /**
         * 动作执行失败
         */
        public static final String actionError = "11111";
        
        /**
         * 请求数据有误
         */
        public static final String jsonDataError = "00001";
        
        /**
         * 用户名存在
         */
        public static final String useNameExist = "00002";
        
        /**
         * 验证码错误
         */
        public static final String valiadCodeError = "00003";
        
        /**
         * 用户名错误
         */
        public static final String userNameError = "00004";
        
        /**
         * 密码错误
         */
        public static final String passwordError = "00005";
        
        /**
         * 用户未登录
         */
        public static final String userNotLogin = "00006";
        
        /**
         * 数据已不存在
         */
        public static final String dataIsExit = "00007";
        
        /**
         * 
         */
        public static final String dataSendError = "00100";
    }
    
}
