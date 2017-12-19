/**
 * 
 */
package com.iknet.commons.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.iknet.commons.baseCode.BusiConstants.System_Formate;

/**
 * @author luozd
 *
 */
public class DateUtil
{
    private static Logger log = Logger.getLogger(DateUtil.class);
    
    /**
     * 得到当前时间 23 小时制
     * 
     * @return
     */
    public static String getNowDate()
    {
        // 20110804 修改
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // 东八区
        sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT-8"));
        
        // 西八区
        // sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT+8"));
        
        return sdf.format(new Date());
        
    }
    
    /**
     * 得到当前系统日期
     * 
     * @return String
     */
    public static String getToday()
    {
        
        // 20110804 修改
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // 东八区
        sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT-8"));
        
        // 西八区
        // sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT+8"));
        
        return sdf.format(new Date());
        
    }
    
    /**
     * 得到当前系统日期
     * 
     * @return String
     */
    public static String getToday(String formate)
    {
        
        // 20110804 修改
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        
        // 东八区
        sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT-8"));
        
        // 西八区
        // sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT+8"));
        
        return sdf.format(new Date());
        
    }
    
    /**
     * 按照"yyyy-MM-dd"的格式输出一个date的日期
     * 
     * @param date
     * @return
     */
    public static String formatDateToStr(Date date)
    {
        if (date == null)
        {
            return "";
        }
        return initSimpleDateFormatToStrDateByFormate("yyyy-MM-dd", date);
        
    }
    
    public static String formatDateToStr(Timestamp date)
    {
        if (date == null)
        {
            return "";
        }
        return initSimpleDateFormatToStrDateByFormate("yyyy-MM-dd HH:mm:ss",
            date);
        
    }
    
    /**
     * 得到星期
     * 
     * @return
     */
    public static int getDtw(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 得到下n天的日期
     * 
     * @return
     */
    public static String getNextNDay(int n)
    {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, n);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(day.getTime());
    }
    
    /**
     * 得到下n天的时间
     * 
     * @return
     */
    public static String getNextNDayTime(Date date, String formate, int n)
    {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.add(Calendar.DATE, n);
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        
        return sdf.format(day.getTime());
    }
    
    // /**
    // * 得到星期
    // *
    // * @return
    // */
    // public static String getDtw(String formate, String source) {
    // String Week = "";
    // int week = 0;
    // Date date = initSimpleDateFormatToDateByFormate(formate, source);
    // if (date != null) {
    // week = getDtw(date);
    // }
    // switch (week) {
    // case 1:
    // return SystemParameterConstant.Week_0;
    // case 2:
    // return SystemParameterConstant.Week_1;
    // case 3:
    // return SystemParameterConstant.Week_2;
    // case 4:
    // return SystemParameterConstant.Week_3;
    // case 5:
    // return SystemParameterConstant.Week_4;
    // case 6:
    // return SystemParameterConstant.Week_5;
    // case 7:
    // return SystemParameterConstant.Week_6;
    // // case 1:
    // // return "日";
    // // case 2:
    // // return "一";
    // // case 3:
    // // return "二";
    // // case 4:
    // // return "三";
    // // case 5:
    // // return "四";
    // // case 6:
    // // return "五";
    // // case 7:
    // // return "六";
    // }
    // return Week;
    // }
    
    /**
     * 得到星期
     * 
     * @return
     */
    public static String getNextNDayTime(String formate, String source, int n)
    {
        Date date = initSimpleDateFormatToDateByFormate(formate, source);
        String strDate = "";
        if (date != null)
        {
            strDate = getNextNDayTime(date, formate, n);
        }
        return strDate;
    }
    
    /**
     * 将source 转换为 formate 类型的 Date
     * 
     * @param formate
     * @param source
     * @return
     */
    private static Date initSimpleDateFormatToDateByFormate(String formate,
        String source)
    {
        Date date = null;
        if (!EasyStr.isEmpty(formate))
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("ETC/GMT-8"));
            if (!EasyStr.isEmpty(source))
            {
                try
                {
                    date = simpleDateFormat.parse(source);
                }
                catch (ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
        return date;
    }
    
    public static Date getNowToday()
    {
        Date date =
        
        DateUtil.initSimpleDateFormatToDateByFormate("yyyy-MM-dd", getToday());
        
        return date;
    }
    
    /**
     * 按照 formate 将 date 转换为 字符串
     * 
     * @param date
     * @return
     */
    private static String initSimpleDateFormatToStrDateByFormate(
        String formate, Date date)
    {
        String strDate = "";
        if (!EasyStr.isEmpty(formate))
        {
            
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
            if (date != null)
            {
                
                strDate = simpleDateFormat.format(date);
            }
        }
        
        return strDate;
    }
    
    /**
     * 按照format的格式将一个字符串转成DATE
     * 
     * @param date
     * @return
     */
    public static String formatStrToformatDate(String old_format,
        String new_format, String source)
    {
        log.debug("source");
        //		log.debug("formatStrToformatDate");
        //		log.debug("old_format:" + old_format);
        //		log.debug("new_format:" + new_format);
        //		log.debug("source:" + source);
        
        String strDate = "1900-01-01";
        Date date = null;
        if (!EasyStr.isEmpty(source))
        {
            if ((!EasyStr.isEmpty(old_format)) && (!EasyStr.isEmpty(source)))
            {
                date = initSimpleDateFormatToDateByFormate(old_format, source);
            }
            if (date != null)
            {
                if (!EasyStr.isEmpty(new_format))
                {
                    
                    strDate =
                        initSimpleDateFormatToStrDateByFormate(new_format, date);
                }
            }
        }
        
        return strDate;
    }
    
    /**
     * 得到当前时间
     * 
     * @return
     */
    public static String getNow(String format)
    {
        if (format == null || format.trim().equals(""))
        {
            return getNowDate();
        }
        return new SimpleDateFormat(format).format(new Date());
    }
    
    public static void main(String args[])
    {
        String old_format = "yyyy-MM-dd HH:mm";
        String new_format = "yyyy-MM-dd";
        String source = "2014-05-26 14:59";
        String s =
            DateUtil.formatStrToformatDate(old_format, new_format, source);
        System.err.println("s:" + s);
        
        // String ssw = DateUtil.getDtw(old_format, source);
        // System.err.println("ssw:" + ssw);
        
        String ss = DateUtil.getNextNDayTime(old_format, source, 1);
        System.err.println("ss:" + ss);
        
        String new_orderDate_last_Time =
            DateUtil.getToday(System_Formate.Formate_Date_yyyyMMddHHmm);
        System.err.println("new_orderDate_last_Time:" + new_orderDate_last_Time);
    }
    // /**
    // * 按照format的格式输出一个date的字符串
    // *
    // * @param date
    // * @return
    // */
    // public static String formatDateToStr(String format, Date date) {
    // if (date == null) {
    // return "";
    // }
    // return new SimpleDateFormat(format).format(date);
    //
    // // return new SimpleDateFormat(format)..parseObject(date);
    // }
    //
    // /**
    // * 按照format的格式将一个字符串转成DATE
    // *
    // * @param date
    // * @return
    // * @throws ParseException
    // */
    // public static String formatStrToformatDate(String format, String strDate)
    // {
    //
    // if (strDate == null || "".equals(strDate)) {
    // strDate = "1900-01-01";
    // } else {
    // Date date;
    // try {
    // // // 20110804 修改
    // // SimpleDateFormat sdf = new SimpleDateFormat(format);
    // //
    // // // 东八区
    // // sdf.setTimeZone(TimeZone.getTimeZone("ETC/GMT-8"));
    // // date = sdf.parse(strDate);
    // date = new SimpleDateFormat(format).parse(strDate);
    //
    // if (date != null) {
    // strDate = formatDateToStr(format, date);
    // } else {
    // strDate = "1900-01-01";
    // }
    // } catch (ParseException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // }
    //
    // return strDate;
    // }
    
}
