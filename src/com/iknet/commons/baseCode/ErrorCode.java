package com.iknet.commons.baseCode;

/**  
*IKNET后台管理系统  
* 版本信息：  1.0
* 功能描述：系统运行错误代码表
* 张忠国 by 2014-9-13 
* 
*/
public class ErrorCode {
	
	/**
	 * 业务正常
	 */
	public static final String normal = "00000";
	
	/**
	 * 异常
	 */
	public static final String error = "11111";
	
	/**
	 *系统运行异常类
	 */
	public static final class systemErrorCode {
		/**
		 * io输入异常
	     */
		public static final String IOException ="40001";
		/**
		 * SQL执行异常
		 */
		public static final String SQLException ="40002";
		/**
		 * 空指针
		 */
		public static final String NullPointerException ="40003";
		/**
		 * 文件未找到
		 */
		public static final String FileNotFoundException ="40004";
		/**
		 * 方法未找到
		 */
		public static final String NoSuchMethodException ="40005";	
		/**
		 * 数据库SQL 异常
		 */
		public static final String BadSqlGrammarException ="40006";
		/**
		 * 其他未知异常
		 */
		public static final String exception ="40007";	
		
		
		/**
		 * session 过期
		 */
		public static final String SessionException ="40008";	
		
		/**
		 * 权限错误
		 */
		public static final String PermissionException ="40009";	
	}
	
	/**
	 * 业务运行异常类
	 */
	public static final class BusinessErrorCode {
		
		
		/**
		 * 登陆错误的用户名
		 */
		public static  final String errorLoginName = "50001";
		/**
		 * 登陆系统错误的密码
		 */
		public static  final String errorLoginPsw = "50002";
		/**
		 * 添加用户ID重复
		 */
		public static  final String errorLogin_name="50003";
		
		/**
		 * 插入数据失败
		 */
		public static final String errorInsertData = "50003";
		
		/**
		 * 更新数据失败
		 */
		public static final String errorUpdateData = "50004";
		
		
		/**
		 * 删除数据失败
		 */
		public static final String errorDeleteData = "50005";
		
		
		
		/**
		 * 门户请求json数据错误
		 */
		public static final String jsonDataError ="50006";
	}
	
	
}
