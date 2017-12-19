package com.iknet.commons.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.iknet.commons.baseCode.BusiConstants.System_Flag;
import com.iknet.commons.baseCode.BusiConstants.System_Key;
import com.iknet.commons.baseCode.BusiConstants.System_Month_Hours;

/**
 * 常用字符串操作
 */
public class EasyStr {
	private static Logger log = Logger.getLogger(EasyStr.class);

	public static boolean isEmpty(String value) {
		return nil(value);
	}

	public static boolean isNotEmpty(String value) {
		return !nil(value);
	}

	/**
	 * 判断字符串是否为Null或空字符串
	 * 
	 * @param String
	 *            要判断的字符串
	 * @return boolean
	 */
	public static boolean nil(String s) {
		return s == null || s.trim().equals("");
	}

	/**
	 * 字符串数组转换为字符串,使用逗号分隔 String s = {"Micheal","David","Wendy"};
	 * EasyStr.join(s,","); //-> "Micheal,David,Wendy"
	 * 
	 * @param String
	 *            []
	 * @param String
	 * @return String
	 */
	public static String join(String[] s, String sp) {
		if (EasyStr.nil(s))
			return "";
		if (s.length == 1)
			return s[0];
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			sb.append(s[i]).append(sp);
		}
		if (!EasyStr.nil(sp))
			sb.deleteCharAt(sb.lastIndexOf(sp));
		return sb.toString();
	}

	/**
	 * 当第一个字符串不为空则返回第一个字符串,否则返回第二个
	 * 
	 * @param String
	 * @param String
	 * @return String
	 */
	public static String nil(String s, String _default) {
		return EasyStr.nil(s) ? _default : s;
	}

	/**
	 * 判断字符串数组是否为空
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean nil(String[] s) {
		return (s == null || s.length == 0);
	}

	/**
	 * 改变字符串编码到UTF-8
	 * 
	 * @param String
	 * @return String
	 */
	public static String toUTF(String src) {
		if (nil(src))
			return "";
		String s = null;
		try {
			s = new String(src.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 改变字符串编码到UTF-8
	 * 
	 * @param StringBuffer
	 * @return String
	 */
	public static String toGBK(StringBuffer src) {
		return toUTF(src.toString());
	}

	/**
	 * 改变字符串为指定编码
	 * 
	 * @param String
	 *            encode
	 * @param String
	 *            src
	 * @return String
	 */
	public static String trans(String encode, String src) {
		if (nil(src))
			return "";
		String s = null;
		try {
			s = new String(src.getBytes("ISO-8859-1"), encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 改变字符串为指定编码
	 * 
	 * @param String
	 *            encode
	 * @param String
	 *            src
	 * @return String
	 */
	public static String trans(String encode, StringBuffer src) {
		return trans(encode, src.toString());
	}

	/**
	 * 转换String到boolean null或""时返回false "1"返回true,"0"返回false
	 * "是"返回true,"否"返回false "yes"返回true,"no"返回false
	 */
	public static boolean parseBoolean(String flag) {
		if (nil(flag))
			return false;
		else if (flag.equals("1") || flag.equalsIgnoreCase("true")
				|| flag.equals("是") || flag.equalsIgnoreCase("yes"))
			return true;
		else if (flag.equals("0") || flag.equalsIgnoreCase("false")
				|| flag.equals("否") || flag.equalsIgnoreCase("no"))
			return false;
		return false;
	}

	/**
	 * 转换int到boolean,参数为1时返回true,否则返回false
	 * 
	 * @param i
	 *            0=false,1=true
	 */
	public static boolean parseBoolean(int i) {
		return i == 1;
	}

	/**
	 * 转换String到int null或"",返回0 "true"返回1,"false"返回0 "123"返回123
	 * 
	 * @param String
	 * @return int
	 */
	public static int parseInt(String flag) {
		if (nil(flag))
			return 0;
		else if (flag.equalsIgnoreCase("true"))
			return 1;
		else if (flag.equalsIgnoreCase("false"))
			return 0;
		else if (flag.equalsIgnoreCase("null"))
			return 0;
		try {
			return Integer.parseInt(flag);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 转换String到long null或""返回0
	 */
	public static long parseLong(String flag) {
		if (nil(flag))
			return 0;
		return Long.parseLong(flag);
	}

	/**
	 * 转换String到float null或""返回0
	 */
	public static float parseFloat(String flag) {
		if (nil(flag))
			return 0;
		return Float.parseFloat(flag);
	}

	/**
	 * 转换String到byte null或""返回0
	 */
	public static byte parseByte(String flag) {
		if (nil(flag))
			return 0;
		return Byte.parseByte(flag);
	}

	/**
	 * 转换字符串数组返回一个整数数组
	 * 
	 * @param String
	 *            []
	 * @return int[]
	 */
	public static int[] parseInt(String[] s) throws NumberFormatException {
		if (s == null) {
			return (new int[0]);
		}
		int[] result = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			result[i] = parseInt(s[i]);
		}
		return result;
	}

	private static final DecimalFormat df = new DecimalFormat("#.0000");

	/**
	 * 转换科学计数法格式的浮点类型到普通格式
	 * 
	 * @param srcDouble
	 *            源数字
	 * @return result 转换后的格式
	 */
	public static String withoutSN(double d) {
		return df.format(d);
	}

	/**
	 * 将字串转成日期，字串格式: yyyy-MM-dd
	 * 
	 * @param String
	 * @return Date
	 */
	public static Date parseDate(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return (Date) formatter.parse(string);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符填充,fill("123","0",6,-1); //->"000123"; fill("123","0",6,1);
	 * //->"123000"
	 * 
	 * @param source
	 *            源字符串
	 * @param filler
	 *            填充字符,如0或*等
	 * @param length
	 *            最终填充后字符串的长度
	 * @param local
	 *            位置,-1表示在在字符串前面填充,1表示在字符串后面填充
	 * @return 最终填充后字符串
	 */
	public static String fill(String source, String filler, int length,
			int local) {
		StringBuffer sb = null;
		if (local == -1) {
			sb = new StringBuffer();
			for (int i = 0; i < length - source.length(); i++) {
				sb.append(filler);
			}
			sb.append(source);
		} else if (local == 1) {
			sb = new StringBuffer(source);
			while (sb.length() < length) {
				sb.append(filler);
			}
		}
		return sb.toString();
	}

	/**
	 * 字符填充,fill("123","0",6); //->"000123"
	 * 
	 * @param source
	 *            源字符串
	 * @param filler
	 *            填充字符,如0或*等
	 * @param length
	 *            最终填充后字符串的长度
	 * @return 最终填充后字符串
	 */
	public static String fill(String source, String filler, int length) {
		return fill(source, filler, length, -1);
	}

	/**
	 * 遍历数组中所有的字符串,从开头到分隔符进行截取,返回截取后的数组. String arr[] =
	 * {"Micheal Wazowski","John Leno"}; subStrBefore(arr," "); //->
	 * {"Micheal","John"}
	 * 
	 * @param arr
	 *            源字符串数组
	 * @param spliter
	 *            切割符
	 * @return 切割后的字符串数组
	 */
	public static String[] subStrBefore(String[] arr, String spliter) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].substring(0, arr[i].indexOf(spliter));
		}
		return arr;
	}

	/**
	 * 判断数组中是否包含指定的值。大小写敏感。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 */

	public static boolean contains(String[] strings, String string) {
		return contains(strings, string, true);
	}

	/**
	 * 判断数组中是否包含指定的值。大小写敏感。
	 * 
	 * @param arr
	 *            源数组
	 * @param n
	 *            要查找的值
	 * @return 包含时返回true，否则返回false
	 */

	public static boolean contains(int[] arr, int n) {
		for (int i = 0, l = arr.length; i < l; i++) {
			if (n == arr[i])
				return true;
		}
		return false;
	}

	/**
	 * 字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @param caseSensitive
	 *            是否大小写敏感
	 * @return 包含时返回true，否则返回false
	 */

	public static boolean contains(String[] strings, String string,
			boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 不区分大小写判定字符串数组中是否包含指定的字符串。
	 * 
	 * @param strings
	 *            字符串数组
	 * @param string
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 */

	public static boolean containsIgnoreCase(String[] strings, String string) {
		return contains(strings, string, false);
	}

	/**
	 * 转换字符串数组返回一个浮点数组
	 * 
	 * @param String
	 *            []
	 * @return float[]
	 */
	public static float[] parseFloat(String[] s) throws NumberFormatException {
		if (s == null) {
			return (new float[0]);
		}
		float[] result = new float[s.length];
		for (int i = 0; i < s.length; i++) {
			result[i] = Float.parseFloat(s[i]);
		}
		return result;
	}

	/**
	 * 分割字符串,返回整型数组
	 * 
	 * @param String
	 * @return int[]
	 */
	public static int[] split(String s) {
		if (nil(s))
			return new int[0];
		if (s.indexOf(",") > -1) {
			return EasyStr.split(s, ",");
		} else {
			int[] i = new int[1];
			i[0] = Integer.parseInt(s);
			return i;
		}
	}

	/**
	 * 分割字符串,返回整型数组
	 * 
	 * @param String
	 * @param String
	 *            分隔符如逗号
	 * @return int[]
	 */
	public static int[] split(String s, String spliter)
			throws NumberFormatException {
		if (s == null || s.indexOf(spliter) == -1) {
			return (new int[0]);
		}
		String[] ary = s.split(spliter);
		int[] result = new int[ary.length];
		for (int i = 0; i < ary.length; i++) {
			result[i] = Integer.parseInt(ary[i]);
		}
		return result;
	}

	/**
	 * 将整型数组合并为用字符分割的字符串
	 * 
	 * @param int[]
	 * @return String
	 */
	public static String join(int[] arr) {
		if (arr == null || arr.length == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = arr.length; i < len; i++) {
			sb.append(",").append(arr[i]);
		}
		sb.deleteCharAt(0);
		return sb.toString();
	}

	/**
	 * 删除StringBuffer最后一个字符,一般用于StringBuffer链接字符串后,删除最后一个逗号
	 * 
	 * @param StringBuffer
	 * @param String
	 * @return StringBuffer
	 */
	public static StringBuffer deleteLast(StringBuffer sb, String s) {
		if (sb == null || sb.length() == 0)
			return sb;

		int i = sb.lastIndexOf(s);
		if (i > -1)
			sb.deleteCharAt(i);
		return sb;
	}

	/**
	 * 将指定字符串复制n次,如EasyStr.times("echo ",3) //=> echo echo echo ;
	 * 
	 * @param src
	 *            源字符串
	 * @param n
	 *            复制次数
	 * @return rst 复制结果
	 */
	public static String times(String src, int n) {
		if (n < 1)
			return "";
		StringBuffer sb = new StringBuffer();
		for (--n; n > -1; sb.append(src), n--) {
		}
		return sb.toString();
	}

	/**
	 * 生成长度为n的字符串数组,且每一项的值都为src
	 * 
	 * @param src
	 *            源字符串
	 * @param n
	 *            数组长度
	 * @return rst 生成的数组
	 */
	public static String[] arrayTimes(String src, int n) {
		if (n < 1)
			return new String[0];
		String[] rst = new String[n];
		for (--n; n > -1; rst[n] = src, n--) {
		}
		return rst;
	}

	/**
	 * 英文日期格式
	 */
	public final static int ENGLISH_DATE = 0;

	/**
	 * 中文日期格式
	 */
	public final static int CHINESE_DATE = 1;

	/**
	 * 短日期格式,2008-01-01
	 */
	public final static int SHORT_DATE = 2;

	/**
	 * 仅时间,10:10:10
	 */
	public final static int ONLY_TIME = 3;

	private static String[] types = { "yyyy-MM-dd HH:mm:ss", // ENGLISH_DATE
			"yyyy年MM月dd日HH时mm分ss秒", // CHINESE_DATE
			"yyyy-MM-dd", // SHORT_DATE
			"HH:mm:ss" // ONLY_TIME
	};

	/**
	 * 获取当前系统日期,格式为yyyy-MM-dd hh:mm:ss或yyyy年MM月dd日hh时mm分ss秒
	 * 
	 * @param 类型
	 *            ,0-2008-08-08 08:08:08; 1-2008年08月08日08时08分08秒;
	 * @return 当前系统日期
	 */
	public static String now(int i) {
		return new SimpleDateFormat(types[i]).format(new Date());
	}

	public static String now() {
		return now(ENGLISH_DATE);
	}

	public static Date nowDate() {
		return new Date();
	}

	public static String today() {
		return now(SHORT_DATE);
	}

	/**
	 * 获取服务器当前时间中的年
	 * 
	 * @return year 今年
	 */
	public static int year() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取服务器当前时间中的月
	 * 
	 * @return month 月
	 */
	public static int month() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取服务器当前时间中的日
	 * 
	 * @return dayOfMonth 日
	 */
	public static int dayOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/***
	 * 一个红色的*号的HTML代码
	 */
	public final static String REDSTAR = "<span style='color:red;'>*</span>";

	/**
	 * URL字符串解码: 中文 -> %D6%D0%CE%C4
	 */
	public static String decode(String s) {
		try {
			return URLDecoder.decode(s, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * URL字符串转码: %D6%D0%CE%C4 -> 中文
	 */
	public static String encode(String s) {
		try {
			return URLEncoder.encode(s, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encode(String s, String encod) {
		try {
			return URLEncoder.encode(s, encod);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 给定一个yyyy-MM-dd格式的日期字符串,加上n天,得出新的日期
	 * 
	 * @param oldDate
	 *            原日期
	 * @param n
	 *            天数
	 * @return newDate 新日期
	 */
	public static String appendDate(String oldDate, int n) {
		Calendar c = Calendar.getInstance();
		int[] ymd = EasyStr.parseInt(oldDate.split("-"));

		c.set(Calendar.YEAR, ymd[0]);
		c.set(Calendar.MONTH, ymd[1] - 1);
		c.set(Calendar.DAY_OF_MONTH, ymd[2]);
		c.add(Calendar.DAY_OF_MONTH, n);

		String y = c.get(Calendar.YEAR) + "";

		String m = (c.get(Calendar.MONDAY) + 1) + "";
		if (m.length() == 1)
			m = 0 + m;

		String d = c.get(Calendar.DAY_OF_MONTH) + "";
		if (d.length() == 1)
			d = 0 + d;

		return y + "-" + m + "-" + d;
	}

	/**
	 * 两个日期之间的天数
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @param is_abs
	 *            是否时绝对值
	 * @return n 天数
	 */
	public static int dateSpacing(String date1, String date2, boolean is_abs) {
		Calendar c = Calendar.getInstance();
		int[] ymd1 = EasyStr.parseInt(date1.split("-"));
		c.set(Calendar.YEAR, ymd1[0]);
		c.set(Calendar.MONTH, ymd1[1] - 1);
		c.set(Calendar.DAY_OF_MONTH, ymd1[2]);
		long m1 = c.getTimeInMillis();

		int[] ymd2 = EasyStr.parseInt(date2.split("-"));
		c.set(Calendar.YEAR, ymd2[0]);
		c.set(Calendar.MONTH, ymd2[1] - 1);
		c.set(Calendar.DAY_OF_MONTH, ymd2[2]);
		long m2 = c.getTimeInMillis();

		if (is_abs)
			return Math.abs((int) ((m2 - m1) / 86400000));
		else
			return (int) ((m2 - m1) / 86400000);
	}

	/**
	 * 得到某年某月的第一天和最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return firstNLastDay 第一天的日期和最后一天的日期,如EasyStr.firstAndLastDay(2008,2) ->
	 *         ["2008-02-01","2008-02-29"]
	 */
	public static String[] firstAndLastDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);

		String _month = (month < 10 ? "0" : "") + month;

		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		return new String[] { year + "-" + _month + "-01",
				year + "-" + _month + "-" + lastDay };
	}

	/**
	 * 得到当月的第一天和最后一天
	 * 
	 * @return firstNLastDay 第一天的日期和最后一天的日期,如["2008-02-01","2008-02-29"]
	 */
	public static String[] firstAndLastDay() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int _month = c.get(Calendar.MONTH) + 1;
		String month = (_month < 10 ? "0" : "") + _month;
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		return new String[] { year + "-" + month + "-01",
				year + "-" + month + "-" + lastDay };
	}

	/**
	 * 得到当天的前几天和后几天的日期
	 * 
	 * @param n
	 *            前n天
	 * @param m
	 *            后m天
	 * @return days[2] days[0]前n天的日期,days[1]后m天的日期
	 */
	public static String[] preAndNextDay(int n, int m) {
		Calendar c = Calendar.getInstance();

		c.add(Calendar.DAY_OF_MONTH, -n);
		int month = c.get(Calendar.MONTH) + 1;
		String _month = (month < 10 ? "0" : "") + month;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String _day = (day < 10 ? "0" : "") + day;
		String pre = c.get(Calendar.YEAR) + "-" + _month + "-" + _day;

		c.add(Calendar.DAY_OF_MONTH, n + m);
		month = c.get(Calendar.MONTH) + 1;
		_month = (month < 10 ? "0" : "") + month;
		day = c.get(Calendar.DAY_OF_MONTH);
		_day = (day < 10 ? "0" : "") + day;
		String next = c.get(Calendar.YEAR) + "-" + _month + "-" + _day;

		return new String[] { pre, next };
	}

	/**
	 * 汉字星期
	 */
	private static final String[] CN_WEEKS = new String[] { "日", "一", "二", "三",
			"四", "五", "六", "七" };

	/**
	 * 获取当天是星期几
	 * 
	 * @param chinese
	 *            ? true:中文,false:英文
	 * @return dayOfWeek 返回当天是星期几，chinese=true时返回“一”，chinese=false时返回"1"
	 */
	public static String todayInWeek(boolean chinese) {
		Calendar c = Calendar.getInstance();
		int dow = c.get(Calendar.DAY_OF_WEEK) - 1;
		return chinese ? "星期" + CN_WEEKS[dow] : "" + dow;
	}

	/**
	 * 获取当天是星期几
	 * 
	 * @return dayOfWeek 返回当天是星期几
	 */
	public static String todayInWeek() {
		return todayInWeek(false);
	}

	/**
	 * 判断两个日期范围之间是否有交集，日期格式必须为 2009-09-09，且必须是有效的日期
	 */
	public static boolean hasIntersection(String start_date1, String end_date1,
			String start_date2, String end_date2) {
		int Y = Calendar.YEAR;
		int M = Calendar.MONTH;
		int D = Calendar.DAY_OF_MONTH;

		String[] dates = new String[] { start_date1, end_date1, start_date2,
				end_date2 };
		long[] cs = new long[4];
		for (int i = 0; i < dates.length; i++) {
			String[] ds = dates[i].split("-");
			Calendar c = Calendar.getInstance();
			c.set(Y, Integer.parseInt(ds[0]));
			c.set(M, Integer.parseInt(ds[1]) - 1);
			c.set(D, Integer.parseInt(ds[2]));
			cs[i] = c.getTimeInMillis();
		}
		long tmp = 0;
		long s1 = cs[0];
		long e1 = cs[1];
		if (s1 > e1) {
			tmp = e1;
			e1 = s1;
			s1 = tmp;
		}
		long s2 = cs[2];
		long e2 = cs[3];
		if (s2 > e2) {
			tmp = e2;
			e2 = s2;
			s2 = tmp;
		}

		// 判断交叉
		boolean flag =
		// 开始1<开始2,结束1<结束2,开始2<结束1
		(s1 <= s2 && e1 <= e2 && s2 <= e1) ||
		// 开始2<开始1,结束2<结束1,开始1<结束2
				(s2 <= s1 && e2 <= e1 && s1 <= e2) ||
				// 开始1<开始2,结束2<结束1
				(s1 <= s2 && e2 <= e1) ||
				// 开始2<开始1,结束1<结束2
				(s2 <= s1 && e1 <= e2);
		return flag;
	}

	/**
	 * JAVA类中输出JSON字符串
	 * 
	 * @param response
	 * @param json
	 */
	public static void outJsonString(HttpServletResponse response, String json) {
		response.setContentType("text/javascript;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 日期字符转数值类型
	 * 
	 * @param String
	 *            Data
	 */
	public static int DateToNum(String date) {
		String[] d = date.split("-");
		String num = "";
		for (int i = 0; i < d.length; i++) {
			num = num + d[i].toString();
		}
		return Integer.parseInt(num);
	}

	/**
	 * 打印堆栈的错误信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getErrorStack(Exception e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
		return "";
	}

	/**
	 * 将整型数组合并为用字符分割的字符串
	 * 
	 * @param int[]
	 * @return String
	 */
	public static String strArrToFlag(String strArr[], String splitFlag) {
		String str = "";
		int strArr_Length = 0;
		if (str != null) {
			strArr_Length = strArr.length;
		}

		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < strArr_Length; i++) {
			if (i > 0) {
				strBuffer.append(System_Flag.Split_Underline);
			}

			strBuffer.append(strArr[i]);
		}

		str = strBuffer.toString();
		if (isNotEmpty(str)) {
			str = str.toUpperCase();
		}
		return str;
	}

	/**
	 * 将 字符串 转换为大写
	 * 
	 * @param str
	 * @return
	 */
	public static String strToUpperCase(String str) {
		if (isNotEmpty(str)) {
			str = str.toUpperCase();
		}
		return str;
	}

	/**
	 * 将 字符串 转换为小写
	 * 
	 * @param str
	 * @return
	 */
	public static String strToLowerCase(String str) {
		if (isNotEmpty(str)) {
			str = str.toLowerCase();
		}
		return str;
	}

	/**
	 * 超过某一长度后 截取
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String subStrByLength(String str, int length) {
		int str_length = 0;
		if (isNotEmpty(str)) {
			str_length = str.length();
		}

		if (str_length > length) {
			str = str.substring(0, length);
		}
		return str;
	}

	/**
	 * 将 字符串 去掉空格
	 * 
	 * @param str
	 * @return
	 */
	public static String strToTrim(String str) {
		if (isNotEmpty(str)) {
			str = str.trim();
		} else {
			str = "";
		}
		return str;
	}

	public static String initZeroRight(String strSource, int left_Num) {
		String strDouble = "";
		int left = 0;
		if (!EasyStr.isEmpty(strSource)) {
			left = left_Num - strSource.length();

		}
		StringBuffer zeroBuffer = new StringBuffer();
		zeroBuffer.append(strSource);
		if (left > 0) {
			for (int i = 0; i < left; i++) {
				zeroBuffer.append("0");
			}
		}
		strDouble = zeroBuffer.toString();
		log.debug("strDouble：" + strDouble);
		return strDouble;
	}

	/**
	 * 截取 固定长度的 随机数
	 * 
	 * @param num
	 * @return
	 */
	public static String initRandomByLength(int num) {
		StringBuffer randomBuffer = new StringBuffer();
		randomBuffer.append(initRandom());
		randomBuffer.append(initRandom());
		randomBuffer.append(initRandom());
		randomBuffer.append(initRandom());
		randomBuffer.append(initRandom());

		String randomStr = randomBuffer.toString();
		if (num > 0) {
			randomStr = new Random().nextLong() + "";
		}
		if (randomStr.length() > 0 && randomStr.length() >= num) {
			randomStr = randomStr.substring(1, num + 1);
		}
		log.debug("randomStr:" + randomStr);
		return randomStr;

	}

	/**
	 * 产生 随机数
	 * 
	 * @return
	 */
	public static String initRandom() {

		String randomStr = new Random().nextLong() + "";
		if (EasyStr.isEmpty(randomStr) || (randomStr.length() < 6)) {
			randomStr = new Random().nextLong() + "";
		}
		return randomStr;

	}

	public static String initMonthTo(String mm) {
		String mmStr = "";

		if (System_Month_Hours.Month_01.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_01_To;
		}
		if (System_Month_Hours.Month_02.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_02_To;
		}
		if (System_Month_Hours.Month_03.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_03_To;
		}
		if (System_Month_Hours.Month_04.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_04_To;
		}
		if (System_Month_Hours.Month_05.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_05_To;
		}
		if (System_Month_Hours.Month_06.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_06_To;
		}
		if (System_Month_Hours.Month_07.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_07_To;
		}
		if (System_Month_Hours.Month_08.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_08_To;
		}
		if (System_Month_Hours.Month_09.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_09_To;
		}
		if (System_Month_Hours.Month_10.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_10_To;
		}
		if (System_Month_Hours.Month_11.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_11_To;
		}
		if (System_Month_Hours.Month_12.equalsIgnoreCase(mm)) {
			mmStr = System_Month_Hours.Month_12_To;

		}

		return mmStr;

	}

	public static String initDayTo(String day) {
		String dayStr = "";

		if (System_Month_Hours.Day_01.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_01_To;
		}
		if (System_Month_Hours.Day_02.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_02_To;
		}
		if (System_Month_Hours.Day_03.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_03_To;
		}
		if (System_Month_Hours.Day_04.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_04_To;
		}
		if (System_Month_Hours.Day_05.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_05_To;
		}
		if (System_Month_Hours.Day_06.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_06_To;
		}
		if (System_Month_Hours.Day_07.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_07_To;
		}
		if (System_Month_Hours.Day_08.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_08_To;
		}
		if (System_Month_Hours.Day_09.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_09_To;
		}
		if (System_Month_Hours.Day_10.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_10_To;
		}

		if (System_Month_Hours.Day_11.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_11_To;
		}
		if (System_Month_Hours.Day_12.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_12_To;
		}
		if (System_Month_Hours.Day_13.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_13_To;
		}
		if (System_Month_Hours.Day_14.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_14_To;
		}
		if (System_Month_Hours.Day_15.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_15_To;
		}
		if (System_Month_Hours.Day_16.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_16_To;
		}
		if (System_Month_Hours.Day_17.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_17_To;
		}
		if (System_Month_Hours.Day_18.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_18_To;
		}
		if (System_Month_Hours.Day_19.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_19_To;
		}
		if (System_Month_Hours.Day_20.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_20_To;
		}

		if (System_Month_Hours.Day_21.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_21_To;
		}
		if (System_Month_Hours.Day_22.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_22_To;
		}
		if (System_Month_Hours.Day_23.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_23_To;
		}
		if (System_Month_Hours.Day_24.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_24_To;
		}
		if (System_Month_Hours.Day_25.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_25_To;
		}
		if (System_Month_Hours.Day_26.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_26_To;
		}
		if (System_Month_Hours.Day_27.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_27_To;
		}
		if (System_Month_Hours.Day_28.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_28_To;
		}
		if (System_Month_Hours.Day_29.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_29_To;
		}
		if (System_Month_Hours.Day_30.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_30_To;
		}
		if (System_Month_Hours.Day_31.equalsIgnoreCase(day)) {
			dayStr = System_Month_Hours.Day_31_To;
		}
		return dayStr;

	}

	public static String initHourTo(String hour) {
		String hourStr = "";
		if (System_Month_Hours.Hours_00.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_00_To;
		}

		if (System_Month_Hours.Hours_01.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_01_To;
		}
		if (System_Month_Hours.Hours_02.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_02_To;
		}
		if (System_Month_Hours.Hours_03.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_03_To;
		}
		if (System_Month_Hours.Hours_04.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_04_To;
		}
		if (System_Month_Hours.Hours_05.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_05_To;
		}
		if (System_Month_Hours.Hours_06.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_06_To;
		}
		if (System_Month_Hours.Hours_07.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_07_To;
		}
		if (System_Month_Hours.Hours_08.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_08_To;
		}
		if (System_Month_Hours.Hours_09.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_09_To;
		}
		if (System_Month_Hours.Hours_10.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_10_To;
		}
		if (System_Month_Hours.Hours_11.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_11_To;
		}
		if (System_Month_Hours.Hours_12.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_12_To;
		}
		if (System_Month_Hours.Hours_13.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_13_To;
		}
		if (System_Month_Hours.Hours_14.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_14_To;
		}
		if (System_Month_Hours.Hours_15.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_15_To;
		}
		if (System_Month_Hours.Hours_16.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_16_To;
		}
		if (System_Month_Hours.Hours_17.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_17_To;
		}
		if (System_Month_Hours.Hours_18.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_18_To;
		}
		if (System_Month_Hours.Hours_19.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_19_To;
		}
		if (System_Month_Hours.Hours_20.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_20_To;
		}
		if (System_Month_Hours.Hours_21.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_21_To;
		}
		if (System_Month_Hours.Hours_22.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_22_To;
		}
		if (System_Month_Hours.Hours_23.equalsIgnoreCase(hour)) {
			hourStr = System_Month_Hours.Hours_23_To;
		}

		return hourStr;
	}

	/**
	 * 校验用户注册密码，密码只能包含数字和字母
	 */
	public static boolean checkPwd(String pwd) {
		String regex = "^[0-9A-Za-z]{6,16}$";
		return pwd.matches(regex);
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static void main(String arg[]) {
		System.err.println(now());

		System.err.println(today());
		String str = "考几科书法大第三方范德萨发大水赛的";
		System.err.println(subStrByLength(str, 15));

		// String s = "000000";
		// System.err.println(checkPwd(s));
		String validate_deviceSim = "";
		if (EasyStr.isNotEmpty(validate_deviceSim)) {
			validate_deviceSim = EasyStr.strToUpperCase(validate_deviceSim);
			int validate_deviceSim_length = validate_deviceSim.length();
			System.err.println("validate_deviceSim_length:"
					+ validate_deviceSim_length);
			if ((validate_deviceSim.endsWith(System_Key.AorB_O))
					|| (validate_deviceSim.endsWith(System_Key.AorB_A))
					|| (validate_deviceSim.endsWith(System_Key.AorB_B))) {
				if (validate_deviceSim_length > 10) {
					validate_deviceSim = validate_deviceSim.substring(0,
							validate_deviceSim_length - 1);
				}
			}
		}
		System.err.println("validate_deviceSim:" + validate_deviceSim);

		System.err.println(stampToDate("1480464676000"));
	}

}
