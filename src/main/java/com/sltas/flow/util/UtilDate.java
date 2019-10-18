package com.sltas.flow.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理工具,包括有:
 * 获取当前数字型日期、
 * 数字型日期字符串转换成中文型日期字符串、
 * 根据要求格式化日期、
 * 按要求计算出相应日期、
 * 比较两个日期的先后顺序
 * @author wxj
 */
public class UtilDate {

	/**
	 * 格式：只有数字的日期字符串
	 */
	public final static String FROMAT_YMD="yyyyMMdd";
	/**
	 * 格式：只有数字的日期字符串
	 */
	public final static String FROMAT_YMDHMS="yyyyMMddHHmmss";
	/**
	 * 格式: 简单日期格式yyyy-MM-dd
	 */
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 格式: 长日期格式,带时分秒
     */
    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";  
    /**
     * 格式: 简单日期格式,中文显示年月日
     */
    public final static String FORMAT_DATE_ZH = "yyyy年MM月dd日";  
    /**
     * 格式: 长日期格式,中文显示年月日时分秒
     */
    public final static String FORMAT_DATETIME_ZH = "yyyy年MM月dd日 HH时mm分ss秒"; 	
    
    /**
     * 为应用系统传递的参数查询条件的开始时间追加上  00:00:00
     */
    public final static String START_TIME_ADD = " 00:00:00";
    /**
     * 为应用系统传递的参数查询条件的结束时间追加上  23:59:59
     */
    public final static String END_TIME_ADD = " 23:59:59";
	
	private static Map<String,String> yearmap = new HashMap<String, String>();
	private static Map<String,String> monthmap = new HashMap<String, String>();
	private static Map<String,String> daymap = new HashMap<String, String>();
	private static Map<String,String> hourmap = new HashMap<String, String>();
	private static Map<String,String> minutemap = new HashMap<String, String>();
	private static Map<String,String> secondmap = new HashMap<String, String>();
	private static Map<String,String> lettersmap = new HashMap<String, String>();

	
	static {
		lettersmap.put("00", "0");
		lettersmap.put("01", "1");
		lettersmap.put("02", "2");
		lettersmap.put("03", "3");
		lettersmap.put("04", "4");
		lettersmap.put("05", "5");
		lettersmap.put("06", "6");
		lettersmap.put("07", "7");
		lettersmap.put("08", "8");
		lettersmap.put("09", "9");
		lettersmap.put("10", "a");
		lettersmap.put("11", "b");
		lettersmap.put("12", "c");
		lettersmap.put("13", "d");
		lettersmap.put("14", "e");
		lettersmap.put("15", "f");
		lettersmap.put("16", "g");
		lettersmap.put("17", "h");
		lettersmap.put("18", "i");
		lettersmap.put("19", "j");
		lettersmap.put("20", "k");
		lettersmap.put("21", "l");
		lettersmap.put("22", "m");
		lettersmap.put("23", "n");
		lettersmap.put("24", "o");
		lettersmap.put("25", "p");
		lettersmap.put("26", "q");
		lettersmap.put("27", "r");
		lettersmap.put("28", "s");
		lettersmap.put("29", "t");
		lettersmap.put("30", "u");
		lettersmap.put("31", "v");
		lettersmap.put("32", "w");
		lettersmap.put("33", "x");
		lettersmap.put("34", "y");
		lettersmap.put("35", "z");
		
		yearmap.put("1", "一");
		yearmap.put("2", "二");
		yearmap.put("3", "三");
		yearmap.put("4", "四");
		yearmap.put("5", "五");
		yearmap.put("6", "六");
		yearmap.put("7", "七");
		yearmap.put("8", "八");
		yearmap.put("9", "九");
		yearmap.put("0", "零");
		
		monthmap.put("01", "一月");
		monthmap.put("02", "二月");
		monthmap.put("03", "三月");
		monthmap.put("04", "四月");
		monthmap.put("05", "五月");
		monthmap.put("06", "六月");
		monthmap.put("07", "七月");
		monthmap.put("08", "八月");
		monthmap.put("09", "九月");
		monthmap.put("10", "十月");
		monthmap.put("11", "十一月");
		monthmap.put("12", "十二月");
		
		daymap.put("01", "一日");
		daymap.put("02", "二日");
		daymap.put("03", "三日");
		daymap.put("04", "四日");
		daymap.put("05", "五日");
		daymap.put("06", "六日");
		daymap.put("07", "七日");
		daymap.put("08", "八日");
		daymap.put("09", "九日");
		daymap.put("10", "十日");
		daymap.put("11", "十一日");
		daymap.put("12", "十二日");
		daymap.put("13", "十三日");
		daymap.put("14", "十四日");
		daymap.put("15", "十五日");
		daymap.put("16", "十六日");
		daymap.put("17", "十七日");
		daymap.put("18", "十八日");
		daymap.put("19", "十九日");
		daymap.put("20", "二十日");
		daymap.put("21", "二十一日");
		daymap.put("22", "二十二日");
		daymap.put("23", "二十三日");
		daymap.put("24", "二十四日");
		daymap.put("25", "二十五日");
		daymap.put("26", "二十六日");
		daymap.put("27", "二十七日");
		daymap.put("28", "二十八日");
		daymap.put("29", "二十九日");
		daymap.put("30", "三十日");
		daymap.put("31", "三十一日");
		
		hourmap.put("00", "零点");
		hourmap.put("01", "一点");
		hourmap.put("02", "二点");
		hourmap.put("03", "三点");
		hourmap.put("04", "四点");
		hourmap.put("05", "五点");
		hourmap.put("06", "六点");
		hourmap.put("07", "七点");
		hourmap.put("08", "八点");
		hourmap.put("09", "九点");
		hourmap.put("10", "十点");
		hourmap.put("11", "十一点");
		hourmap.put("12", "十二点");
		hourmap.put("13", "十三点");
		hourmap.put("14", "十四点");
		hourmap.put("15", "十五点");
		hourmap.put("16", "十六点");
		hourmap.put("17", "十七点");
		hourmap.put("18", "十八点");
		hourmap.put("19", "十九点");
		hourmap.put("20", "二十点");
		hourmap.put("21", "二十一点");
		hourmap.put("22", "二十二点");
		hourmap.put("23", "二十三点");
		hourmap.put("24", "二十四点");
		
		minutemap.put("00", "");
		minutemap.put("01", "零一分");
		minutemap.put("02", "零二分");
		minutemap.put("03", "零三分");
		minutemap.put("04", "零四分");
		minutemap.put("05", "零五分");
		minutemap.put("06", "零六分");
		minutemap.put("07", "零七分");
		minutemap.put("08", "零八分");
		minutemap.put("09", "零九分");
		minutemap.put("10", "十分");
		minutemap.put("11", "十一分");
		minutemap.put("12", "十二分");
		minutemap.put("13", "十三分");
		minutemap.put("14", "十四分");
		minutemap.put("15", "十五分");
		minutemap.put("16", "十六分");
		minutemap.put("17", "十七分");
		minutemap.put("18", "十八分");
		minutemap.put("19", "十九分");
		minutemap.put("20", "二十分");
		minutemap.put("21", "二十一分");
		minutemap.put("22", "二十二分");
		minutemap.put("23", "二十三分");
		minutemap.put("24", "二十四分");
		minutemap.put("25", "二十五分");
		minutemap.put("26", "二十六分");
		minutemap.put("27", "二十七分");
		minutemap.put("28", "二十八分");
		minutemap.put("29", "二十九分");
		minutemap.put("30", "三十分");
		minutemap.put("31", "三十一分");
		minutemap.put("32", "三十二分");
		minutemap.put("33", "三十三分");
		minutemap.put("34", "三十四分");
		minutemap.put("35", "三十五分");
		minutemap.put("36", "三十六分");
		minutemap.put("37", "三十七分");
		minutemap.put("38", "三十八分");
		minutemap.put("39", "三十九分");
		minutemap.put("40", "四十分");
		minutemap.put("41", "四十一分");
		minutemap.put("42", "四十二分");
		minutemap.put("43", "四十三分");
		minutemap.put("44", "四十四分");
		minutemap.put("45", "四十五分");
		minutemap.put("46", "四十六分");
		minutemap.put("47", "四十七分");
		minutemap.put("48", "四十八分");
		minutemap.put("49", "四十九分");
		minutemap.put("50", "五十分");
		minutemap.put("51", "五十一分");
		minutemap.put("52", "五十二分");
		minutemap.put("53", "五十三分");
		minutemap.put("54", "五十四分");
		minutemap.put("55", "五十五分");
		minutemap.put("56", "五十六分");
		minutemap.put("57", "五十七分");
		minutemap.put("58", "五十八分");
		minutemap.put("59", "五十九分");
		minutemap.put("60", "六十分");
		
		secondmap.put("00", "");
		secondmap.put("01", "零一秒");
		secondmap.put("02", "零二秒");
		secondmap.put("03", "零三秒");
		secondmap.put("04", "零四秒");
		secondmap.put("05", "零五秒");
		secondmap.put("06", "零六秒");
		secondmap.put("07", "零七秒");
		secondmap.put("08", "零八秒");
		secondmap.put("09", "零九秒");
		secondmap.put("10", "十秒");
		secondmap.put("11", "十一秒");
		secondmap.put("12", "十二秒");
		secondmap.put("13", "十三秒");
		secondmap.put("14", "十四秒");
		secondmap.put("15", "十五秒");
		secondmap.put("16", "十六秒");
		secondmap.put("17", "十七秒");
		secondmap.put("18", "十八秒");
		secondmap.put("19", "十九秒");
		secondmap.put("20", "二十秒");
		secondmap.put("21", "二十一秒");
		secondmap.put("22", "二十二秒");
		secondmap.put("23", "二十三秒");
		secondmap.put("24", "二十四秒");
		secondmap.put("25", "二十五秒");
		secondmap.put("26", "二十六秒");
		secondmap.put("27", "二十七秒");
		secondmap.put("28", "二十八秒");
		secondmap.put("29", "二十九秒");
		secondmap.put("30", "三十秒");
		secondmap.put("31", "三十一秒");
		secondmap.put("32", "三十二秒");
		secondmap.put("33", "三十三秒");
		secondmap.put("34", "三十四秒");
		secondmap.put("35", "三十五秒");
		secondmap.put("36", "三十六秒");
		secondmap.put("37", "三十七秒");
		secondmap.put("38", "三十八秒");
		secondmap.put("39", "三十九秒");
		secondmap.put("40", "四十秒");
		secondmap.put("41", "四十一秒");
		secondmap.put("42", "四十二秒");
		secondmap.put("43", "四十三秒");
		secondmap.put("44", "四十四秒");
		secondmap.put("45", "四十五秒");
		secondmap.put("46", "四十六秒");
		secondmap.put("47", "四十七秒");
		secondmap.put("48", "四十八秒");
		secondmap.put("49", "四十九秒");
		secondmap.put("50", "五十秒");
		secondmap.put("51", "五十一秒");
		secondmap.put("52", "五十二秒");
		secondmap.put("53", "五十三秒");
		secondmap.put("54", "五十四秒");
		secondmap.put("55", "五十五秒");
		secondmap.put("56", "五十六秒");
		secondmap.put("57", "五十七秒");
		secondmap.put("58", "五十八秒");
		secondmap.put("59", "五十九秒");
		secondmap.put("60", "六十秒");
	}
	
	/**
	 * 获取当前日期的年份字符串
	 * @return 字符串(格式为:yyyy)
	 */
	public static String getYear()	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return sdf.format(date);
	}
	
	
	/**
	 * 获取当前日期的年月日字符串
	 * @return 字符串(格式为:yyyy)
	 */
	public static String getYearMonthDay()	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * 获取短日期格式的当前日期字符串(日期内容包括年月日)
	 * @return 字符串(格式为:yyyyMMdd)
	 */
	public static String getShortDate()	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * 获取长日期格式的当前日期字符串(日期内容包括年月日时分秒)
	 * @return 字符串(格式为:yyyyMMddHHmmss)
	 */
	public static String getLongDate()	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return sdf.format(date);
	}
	/**
	 * 按格式获取当前时间(日期内容包括年月日时分)
	 * 具体结构：两位年1位月1位日1位时2位分
	 * @return 字符串
	 */
	public static String getMinShortDate()	{
		String nowdate = getLongDate();
		String year = nowdate.substring(2,4);
		String month = lettersmap.get(nowdate.substring(4,6));
		String day = lettersmap.get(nowdate.substring(6,8));
		String hour = lettersmap.get(nowdate.substring(8,10));
		String min = nowdate.substring(10,12);
		StringBuffer resultdate = new StringBuffer();//结果日期
		resultdate.append(year).append(month).append(day).append(hour).append(min);
		return resultdate.toString().toUpperCase();
	}
	
	/**
	 * 数字型日期字符串转换成短日期格式的中文日期字符串(日期内容包括月日),如果字符串长度不足,则会抛出StringIndexOutOfBoundsException异常
	 * @param datestr
	 * (数字日期字符串,格式必须为"yyyyMMdd"或者"yyyyMMddHHmmss")
	 * @return 中文日期字符串(格式为：X月X日)
	 */
	public static String toCNShort(String datestr){
		if(datestr == null){
			return "";
		}else{
			datestr = datestr.trim();
			if (datestr.equals("")){
				return "";
			}
		}
		String monthstr = datestr.substring(4,6);
		String daystr 	= datestr.substring(6,8);
		String dateCN = monthmap.get(monthstr) + daymap.get(daystr);
		return dateCN;
	}
	
	/**
	 * 数字型日期字符串转换成长日期格式的中文日期字符串(日期内容包括月日时分),如果字符串长度不足,则会抛出StringIndexOutOfBoundsException异常
	 * @param datestr
	 * (数字日期字符串,格式必须为"yyyyMMddHHmmss")
	 * @return 中文日期字符串(格式为：X月X日X点X分)
	 */
	public static String toCN(String datestr){
		if(datestr == null){
			return "";
		}else{
			datestr = datestr.trim();
			if (datestr.equals("")){
				return "";
			}
		}
		String monthstr = datestr.substring(4,6);
		String daystr 	= datestr.substring(6,8);
		String hourstr	= datestr.substring(8,10);
		String minutestr= datestr.substring(10,12);
		String dateCN = monthmap.get(monthstr) + daymap.get(daystr) + hourmap.get(hourstr) + 
		minutemap.get(minutestr);
		
		return dateCN;
	}
	
	/**
	 * 数字型日期字符串转换成完整格式的中文日期字符串(日期内容包括年月日时分秒),如果字符串长度不足,则会抛出StringIndexOutOfBoundsException异常
	 * @param datestr
	 * (数字日期字符串,格式必须为"yyyyMMddHHmmss")
	 * @return 中文日期字符串(格式为：X年X月X日X点X分X秒)
	 */
	public static String toCNLong(String datestr){
		if(datestr == null){
			return "";
		}else{
			datestr = datestr.trim();
			if (datestr.equals("")){
				return "";
			}
		}
		String yearstr1 = datestr.substring(0,1);
		String yearstr2 = datestr.substring(1,2);
		String yearstr3 = datestr.substring(2,3);
		String yearstr4 = datestr.substring(3,4);
		String monthstr = datestr.substring(4,6);
		String daystr 	= datestr.substring(6,8);
		String hourstr	= datestr.substring(8,10);
		String minutestr= datestr.substring(10,12);
		String secondstr= datestr.substring(12,14);
		String dateCN = yearmap.get(yearstr1) + yearmap.get(yearstr2) + yearmap.get(yearstr3) +  
		yearmap.get(yearstr4) + "年" + monthmap.get(monthstr) + daymap.get(daystr) + hourmap.get(hourstr) + 
		minutemap.get(minutestr) + secondmap.get(secondstr);
		
		return dateCN;
	}

	/**
	 * 转换日期字符串的格式,如果参数字符串转换失败,则返回原字符串
	 * @param datestr
	 * (数字型日期字符串,格式必须为"yyyyMMdd")
	 * @return 日期字符串(格式为：MM月dd日)
	 */
	public static String formatShortDateStr(String datestr)	{
		if (datestr == null){
			return "";
		}else{
			datestr = datestr.trim();
			if (datestr.equals("")){
				return "";
			}
		}
		SimpleDateFormat sdfold = new SimpleDateFormat("yyyyMMdd");
		Date date;
		try {
			date = sdfold.parse(datestr);
		} catch (ParseException e) {
			return datestr;
		}
		SimpleDateFormat sdfnew = new SimpleDateFormat("MM月dd日");
		return sdfnew.format(date);
	}
	
	/**
	 * 转换日期时间字符串的格式,如果参数字符串格式化失败,则返回原字符串
	 * @param datestr
	 * (数字型日期字符串,格式必须为"yyyyMMddHHmmss")
	 * @return 日期字符串(格式为：yyyy年MM月dd日HH点mm分ss秒)
	 */
	public static String formatLongDateStr(String datestr)	{
		if (datestr == null){
			return "";
		}else{
			datestr = datestr.trim();
			if (datestr.equals("")){
				return "";
			}
		}
		SimpleDateFormat sdfold = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date;
		try {
			date = sdfold.parse(datestr);
		} catch (ParseException e) {
			return datestr;
		}
		SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
		return sdfnew.format(date);
	}
	
	/**
	 * 转换时间字符串的格式,如果参数字符串格式化失败,则返回原字符串
	 * @param datestr
	 * (数字型时间字符串,格式必须为"HH:mm")
	 * @return 日期字符串(格式为：HH点mm分)
	 */
	public static String FormatTimeStr(String datestr)	{
		if (datestr == null){
			return "";
		}else{
			datestr = datestr.trim();
			if (datestr.equals("")){
				return "";
			}
		}
		SimpleDateFormat sdfold = new SimpleDateFormat("HH:mm");
		Date date;
		try {
			date = sdfold.parse(datestr);
		} catch (ParseException e) {
			return datestr;
		}
		SimpleDateFormat sdfnew = new SimpleDateFormat("HH点mm分");
		return sdfnew.format(date);
	}
	
	/**
	 * 转换时间字符串的格式,如果参数字符串格式化失败,则返回原字符串
	 * @param datestr
	 * (数字型时间字符串,格式必须为"HH:mm")
	 * @return 日期字符串(格式为：HHmm)
	 */
	public static String formatTimeStr2(String datestr)	{
		if (datestr == null){
			return "";
		}else{
			datestr = datestr.trim();
			if (datestr.equals("")){
				return "";
			}
		}
		SimpleDateFormat sdfold = new SimpleDateFormat("HH:mm");
		Date date;
		try {
			date = sdfold.parse(datestr);
		} catch (ParseException e) {
			return datestr;
		}
		SimpleDateFormat sdfnew = new SimpleDateFormat("HHmm");
		return sdfnew.format(date);
	}
	
	/**
	 * 将日期按指定模板格式化
	 * @param date
	 * (准备格式化的日期)
	 * @param pattern
	 * (格式化模板)
	 * @return 字符串
	 */
	public static String format(Date date,String pattern)	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 按指定模板解析出日期
	 * @param datestr
	 * (需要解析的字符串)
	 * @param pattern
	 * (解析的模板)
	 * @return 日期(类型为:java.util.Date)
	 * @throws ParseException
	 */
	public static Date parse(String datestr,String pattern) throws ParseException	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(datestr);
	}
	
	/**
	 * 判断date1是否在date2之前
	 * @param date1
	 * @param date2
	 * @param pattern
	 * (解析的模板格式)
	 * @return boolean
	 * @throws ParseException 
	 */
	public static boolean isDateBefore(String date1,String date2,String pattern) throws ParseException	{
		return parse(date1, pattern).before(parse(date2, pattern));
	}
	
	/**
	 * 取得指定秒数之后的日期
	 * @param date
	 * @param seconds
	 * @return 日期(类型为:java.util.Date)
	 */
	public static Date getDateAfterSeconds(Date date,int seconds)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}
	
	/**
	 * 取得指定天数之后的日期
	 * @param date
	 * @param days
	 * @return 日期(类型为:java.util.Date)
	 */
	public static Date getDateAfterDays(Date date,int days)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}
	
	/**
	 * 取得指定月数之后的日期
	 * @param date
	 * @param months
	 * @return 日期(类型为:java.util.Date)
	 */
	public static Date getDateAfterMonths(Date date,int months)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	
	/**
	 * 取得下月第一天
	 * @param month
	 * (格式必须为:"yyyyMM")
	 * @return 日期
	 * (类型为:java.util.Date)
	 * @throws ParseException 
	 */
	public static Date getNextMonth(String month) throws ParseException
	{
		Date temp = parse(month, "yyyyMM");
		Date rtn = getDateAfterMonths(temp, 1);
		return rtn;
	}
	
	
	/**
	 * 获取日期字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date,String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 获取日期字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	
	/**
	 * 字符串转日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateStr){
		SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DATETIME);
		
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /** 
     * 日期天数加减操作,返回加减天数后的日期
     * @param date 待处理日期 
     * @param day 加减的天数 
     * @return 日期 
     */  
    public static Date dateAdd(Date date, int day) {  
            Calendar strDate = Calendar.getInstance();  
            strDate.setTime(date);  
            strDate.add(Calendar.DATE, day);
            return new Date(strDate.getTime().getTime());  
    }  
    
    /** 
     * 日期天数加减操作,返回加减天数后的日期
     * @param date 待处理日期 
     * @param day 加减的天数 
     * @return 日期 
     */  
    public static Date dateAdd(int day) {  
            return  dateAdd(new Date(),day);
    }  
    
    /** 
     * 日期加减操作,返回加减后的日期
     * @param date 待处理日期 
     * @param year 加减的年数
     * @param month 加减的月数
     * @param day 加减的天数 
     * @return 日期 
     */  
    public static Date dateAdd(Date date,int year,int month, int day) {  
            Calendar strDate = Calendar.getInstance();  
            strDate.setTime(date);  
            strDate.add(Calendar.YEAR, year);
            strDate.add(Calendar.MONTH, month);
            strDate.add(Calendar.DATE, day);
            return new Date(strDate.getTime().getTime());  
    }  
    
    /** 
     * 日期加减操作,返回加减后的日期
     * @param date 待处理日期 
     * @param year 加减的年数
     * @param month 加减的月数
     * @param day 加减的天数 
     * @return 日期 
     */  
    public static Date dateAdd(int year,int month, int day) {  
    	return dateAdd(new Date(),year,month,day);  
    } 
    
    /**
     * 比较两个日期时间的大小
     * @param d1
     * @param d2
     * @return
     */
    public boolean compareDate(Date d1, Date d2) {  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        c1.setTime(d1);  
        c2.setTime(d2);  
      
        int result = c1.compareTo(c2);  
        if (result >= 0)  
            return true;  
        else  
            return false;  
    }  
    
    /**
     * 比较两个字符串日期的大小
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
                Date dt1 = df.parse(DATE1);
                Date dt2 = df.parse(DATE2);
                if (dt1.getTime() >= dt2.getTime()) {
                        return 1;
                } else if (dt1.getTime() < dt2.getTime()) {
                        return -1;
                } else {
                        return 0;
                }
        } catch (Exception exception) {
                exception.printStackTrace();
        }
        return 0;
}
    
    /**
     * 获取当月第一天与当月最后一天
     * @return
     */
	public static Map<String,String> getFLDate(){
    	Date d = new Date();  
		int m = d.getMonth();  
		int y = d.getYear();  
		Date firstDay = new Date(y,m+1,1) ;  
		int min = 24*60*60*1000;  
		Date from = new Date(y,m,1);  
		Date to = new Date(firstDay.getTime()-min);  
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(FORMAT_DATE);
		String first=simpleDateFormat.format(from)+START_TIME_ADD;
		String last=simpleDateFormat.format(to)+END_TIME_ADD;
		Map<String,String> dateMap=new HashMap<String,String>();
		dateMap.put("first", first);
		dateMap.put("last", last);
		return dateMap;
    }
	
	
	 //把日期转为字符串  
    public static String ConverToString(Date date)  
    {  
        DateFormat df = new SimpleDateFormat(FORMAT_DATETIME);  
          
        return df.format(date);  
    }  
    
    /**
     * <p>
     * Title: parseStringDate
     * </p>
     * <p>
     * Description: 解析时间类型
     * </p>
     * <p>
     * 
     * </p>
     * @tags @return 
     * 
     * @author zsy
     * @date 2017年6月9日下午2:46:16
     */
    public static String[] parseStringDate(){
    	Date date = new Date();
    	String dataString = ConverToString(date);
    	String day = dataString.substring(0,10);
    	String time = dataString.substring(11);
    	return new String[]{day,time};
    }
    
    /** 
     * 将timestamp转换成date 
     * @author hellostoy 
     * @param tt 
     * @return 
     */  
    public static Date timestampToDate(Timestamp tt){  
        return new Date(tt.getTime());  
    }  
	
    /**
     * @author: x1ao贱
     * @date: 2018年4月11日 下午7:41:16
     * @Title: minConvertDayHourMin 
     * @Description: 将分钟转成天小时分
     * @param: @param min
     * @param: @return
     * @return: String
     * @throws
     */
    public static String minConvertDayHourMin(Double min){
		String  html="0分";
		if(min!=null){
			Double m=(Double) min;
			String format;
			Object[] array;
			Integer days =(int) (m/(60*24));
			Integer hours = (int) (m/(60)-days*24);
			Integer minutes = (int) (m-hours*60-days*24*60);
			if(days>0){
			    format="%1$,d天%2$,d小时%3$,d分";
			    array=new Object[]{days,hours,minutes};
			}else if(hours>0){
			    format="%1$,d小时%2$,d分";
			    array=new Object[]{hours,minutes};
			}else{
			    format="%1$,d分";
			    array=new Object[]{minutes};
			}
			html= String.format(format, array);
		}
		return html;
	}
		 
	/**
	  * 
	  * @param day
	  * @param hour
	  * @param min
	  * @return min
	  */
	 public static int dayHourMinConvertMin(int day,int hour,int min){
		  int days=day*24*60;
		  int hours=hour*60;
		  return days+hours+min;
	 }
}
