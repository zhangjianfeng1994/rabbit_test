package com.sltas.order.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * Title: DateTimeUtils.java
 * </p>
 * <p>
 * Description: 时间工具类 日期分区必备
 * </p>
 * <p>
 * 
 * </p>
 * @author 周顺宇 
 * @date 2019年5月17日 下午5:40:41  
 */
public class DateTimeUtils {
    
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
	public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
	public static final DateTimeFormatter YEAR_FORMATTER = DateTimeFormatter.ofPattern("yyyy");
	public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter DATETIME_FORMATTER =  DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATE_FORMATTER_SPLIT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMATTER_SPLIT =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATETIME_FORMATTER_SPLIT_ZH =  DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
    
    /**
     * @Fields CRITICAL_DATE: 订单项目启动日
     * @author 周顺宇 
     * @date 2019年5月17日 下午5:40:39 
     */
    public static final LocalDate CRITICAL_DATE = LocalDate.parse("2017-06-23");
    
    /**
     * 返回当前的日期
     * @return	
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }
    
    /**
     * 返回当前时间
     * @return
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }
    
    /**
     * 返回当前日期时间
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * yyyyMMdd
     * 
     * @return
     */
    public static String getCurrentDateStr() {
        return LocalDate.now().format(DATE_FORMATTER);
    }
    
    /**
     * yyMMdd
     * 
     * @return
     */
    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(SHORT_DATE_FORMATTER);
    }
    
    /**
     * yyMM
     */
    public static String getCurrentMonthStr() {
        return LocalDate.now().format(MONTH_FORMATTER);
    }
    
    /**
     * yyyyMMddHHmmss
     * @return
     */
    public static String getCurrentDateTimeStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }
    
    /**
     * yyMMddHHmmss
     * @return
     */
    public static String getCurrentShortDateTimeStr() {
        return LocalDateTime.now().format(SHORT_DATETIME_FORMATTER);
    }
    
    /**
     * HHmmss
     * @return
     */
    public static String getCurrentTimeStr() {
        return LocalTime.now().format(TIME_FORMATTER);
    }
    
    /**
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDateTimeSplitStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER_SPLIT);
    }
    
    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     * @return
     */
    public static String getCurrentDateTimeSplitZhStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER_SPLIT_ZH);
    }
    
    /**
     * 根据指定格式 返回当前的日期 yyyyMMdd
     * @return	
     */
    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据指定格式 返回当前时间 HHmmss
     * @return
     */
    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据指定格式 返回当前日期时间 yyMMddHHmmss
     * @return
     */
    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }
    
    
    /**
     * 根据指定 字符串 和  格式 返回 LocalDate
     * @return	
     */
    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据指定 字符串 和  格式 返回 LocalTime
     * @return	
     */
    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据指定 字符串 和  格式 返回 LocalDateTime
     * @return	
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据指定 LocalDate 和  格式 返回 String
     * @return	
     */
    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据指定 LocalTime 和  格式 返回 String
     * @return	
     */
    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据指定 LocalDateTime 和  格式 返回 String
     * @return	
     */
    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    /**
     * 根据 字符串 采用默认 yyyyMMdd 格式 获取 LocalDate
     * @return
     */
    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    /**
     * 根据 字符串 采用默认 yyyy-MM-dd 格式 获取 LocalDate
     * @return
     */
    public static LocalDate parseLocalDateSplit(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER_SPLIT);
    }
    
    /**
     * 根据 字符串 采用默认 yyyy-MM-dd HH:mm:ss 格式 获取 LocalDateTime
     * @return
     */
    public static LocalDateTime parseLocalDateTimeSplit(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER_SPLIT);
    }
    
    /**
     * 根据 字符串 采用默认 yyyyMMddHHmmss 格式 获取 LocalDateTime
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }
    
    /**
     * 根据 字符串 采用默认 yyyy年MM月dd日 HH时mm分ss秒 格式 获取 LocalDateTime
     * @return
     */
    public static LocalDateTime parseLocalDateTimeSplitZh(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER_SPLIT_ZH);
    }
    
    /**
     * 根据 字符串 采用默认 HHmmss 格式 获取 LocalTime
     * @return
     */
    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }
    
    /**
     * 根据 LocalDate 采用默认 yyyyMMdd 格式 获取 String
     * @return
     */
    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * 根据 LocalDate 采用默认 yyyyMM 格式 获取 String
     * @return
     */
    public static String formatLocalMonth(LocalDate date) {
        return date.format(MONTH_FORMATTER);
    }
    
    /**
     * 根据 LocalDate 采用默认 yyyy 格式 获取 String
     * @return
     */
    public static String formatLocalYear(LocalDate date) {
        return date.format(YEAR_FORMATTER);
    }
    
    
    /**
     * 根据 LocalTime 采用默认 HHmmss 格式 获取 String
     * @return
     */
    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }
    
    /**
     * 根据 LocalDateTime 采用默认 yyyyMMddHHmmss 格式 获取 String
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }
    
    /**
     * 根据 LocalDateTime 采用默认 yyyy-MM-dd HH:mm:ss 格式 获取 String
     * @return
     */
    public static String formatLocalDateTimeSplit(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER_SPLIT);
    }
    
    /**
     * 根据 LocalDateTime 采用默认 yyyy年MM月dd日 HH时mm分ss秒 格式 获取 String
     * @return
     */
    public static String formatLocalDateTimeSplitZh(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER_SPLIT_ZH);
    }
    
    /**
     * 根据 LocalDateTime 采用默认"yyyy-MM-dd 格式 获取 String
     * @return
     */
    public static String formatLocalDateSplit(LocalDateTime datetime) {
        return datetime.format(DATE_FORMATTER_SPLIT);
    }
    
    
    /**
     * 日期相隔天数
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static int periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return Period.between(startDateInclusive, endDateExclusive).getDays();
    }
    
    /**
     * 日期相隔小时
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationHours(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toHours();
    }
    
    /**
     * 日期相隔分钟
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMinutes(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMinutes();
    }
    
    /**
     * 日期相隔毫秒数
     * @param startInclusive
     * @param endExclusive
     * @return
     */
    public static long durationMillis(Temporal startInclusive, Temporal endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }
    
    /**
     * 是否当天
     * @param date
     * @return
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }
    
    /**
     * <p>
     * Title: toEpochMilli
     * </p>
     * <p>
     * Description: LocalDateTime转化long
     * 
     * 通过 LocalDateTime 获取当前时间戳 毫秒值
     * 
     * </p>
     * @param @param dateTime
     * @param @return 
     * @return Long
     * @throws
     * @author 周顺宇 
     * @date 2019年1月7日 下午5:20:45 
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    /**
     * <p>
     * Title: getDateTimeOfTimestamp
     * </p>
     * <p>
     * Description: long转化LocalDateTime
     * 
     * 通过 时间戳毫秒值 获取 LocalDateTime
     * 
     * </p>
     * @param @param timestamp
     * @param @return 
     * @return LocalDateTime
     * @throws
     * @author 周顺宇 
     * @date 2019年1月7日 下午5:22:27 
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }
    
    /**
       *  时间累加天数
       *  通过默认格式 yyyy-MM-dd HH:mm:ss 的字符串 添加 days 天数 返回LocalDateTime
	* @param time           "2019-01-16 14:30:00"
	* @param daysToAdd       天数
	* @return
	*/
    public static LocalDateTime plusDays(String time,long days) {   	
		return plusDays(LocalDateTime.parse(time, DATETIME_FORMATTER_SPLIT),days);
	}   

    /**
     * 时间累加天数
     * 通过 LocalDateTime 添加 days 的天数 返回 LocalDateTime
     */
	public static LocalDateTime plusDays(LocalDateTime localDateTime,long days) {   	
		return localDateTime.plusDays(days);
	} 
	
    /**
     * 时间累加秒
     * 通过 LocalDateTime 添加 seconds 秒值 返回 LocalDateTime
     */
	public static LocalDateTime plusSeconds(LocalDateTime localDateTime,long seconds) {   	
		return localDateTime.plusSeconds(seconds);
	} 
	
    /**
     * 时间累加分钟
     * 通过 LocalDateTime 添加 minutes 分钟值 返回 LocalDateTime
     */
	public static LocalDateTime plusMinutes(LocalDateTime localDateTime,long minutes) {   	
		return localDateTime.plusMinutes(minutes);
	} 

	/**
	 *  时间累减天数
	 *  通过默认格式 yyyy-MM-dd HH:mm:ss 的字符串 递减 days 天数 返回LocalDateTime
	* @param time           "2019-01-16 14:30:00"
	* @param daysToAdd       天数
	* @return
	*/
	public static LocalDateTime minusDays(String time,long days) {		
		return minusDays(LocalDateTime.parse(time, DATETIME_FORMATTER_SPLIT),days);   
	}
	
    /**
     * 时间累减天数
     * 通过 LocalDateTime 递减 days 天数  返回 LocalDateTime
     */
	public static LocalDateTime minusDays(LocalDateTime localDateTime,long days) {		
		return localDateTime.minusDays(days);
	}
	
    /**
     * 时间累减秒值
     * 通过 LocalDateTime 递减 seconds 秒值  返回 LocalDateTime
     */
	public static LocalDateTime minusSeconds(LocalDateTime localDateTime,long seconds) {   	
		return localDateTime.minusSeconds(seconds);
	} 
	
    /**
     * 时间累减分钟
     * 通过 LocalDateTime 递减 minutes 分钟  返回 LocalDateTime
     */
	public static LocalDateTime minusMinutes(LocalDateTime localDateTime,long minutes) {   	
		return localDateTime.minusMinutes(minutes);
	} 
	
    /**
     *  时间累减天数
     *  通过默认格式 yyyy-MM-dd 的字符串 递减 days 天数 返回LocalDateTime
	* @param time           "2019-01-16"
	* @param daysToAdd       天数
	* @return
	*/
  public static LocalDate minusLocalDays(String time,long days) {   	
		return minusLocalDays(LocalDate.parse(time, DATE_FORMATTER_SPLIT),days);
	}   

  /**
   * 时间累减天数
   * 通过 LocalDate 递减 days 的天数 返回 LocalDate
   */
	public static LocalDate minusLocalDays(LocalDate localDate,long days) {   	
		return localDate.minusDays(days);
	} 
	
    /**
     *  时间累加天数
     *  通过默认格式 yyyy-MM-dd  的字符串 添加 days 天数 返回LocalDateTime
	* @param time           "2019-01-16"
	* @param daysToAdd       天数
	* @return
	*/
  public static LocalDate plusLocalDays(String time,long days) {   	
		return plusLocalDays(LocalDate.parse(time, DATE_FORMATTER_SPLIT),days);
	}   

  /**
   * 时间累加天数
   * 通过 LocalDate 添加 days 的天数 返回 LocalDate
   */
	public static LocalDate plusLocalDays(LocalDate localDate,long days) {   	
		return localDate.plusDays(days);
	}
	
	
	/**
	 * <p>
	 * Title: getUdateToLocalDate
	 * </p>
	 * <p>
	 * Description: Date 转换 LocalDate
	 * </p>
	 * @param @param date
	 * @param @return 
	 * @return LocalDate
	 * @throws
	 * @author 周顺宇 
	 * @date 2019年2月27日 下午2:02:51 
	 */
	public static LocalDate getUdateToLocalDate(Date date) {
		 Instant instant = date.toInstant();
	     ZoneId zoneId = ZoneId.systemDefault();
	     // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
	     return instant.atZone(zoneId).toLocalDate();
	}
	
	/**
	 * <p>
	 * Title: getBetweenDate
	 * </p>
	 * <p>
	 * 
	 * 	获取两个日期间隔的所有日期
	 * 
	 * @param start 格式必须为'2018-01-25'
	 * @param end 格式必须为'2018-01-25'
	 * @return
	 * 
	 * </p>
	 * @param @param start
	 * @param @param end
	 * @param @return 
	 * @return Collection<String> yyyyMMdd 默认格式
	 * @throws
	 * @author 周顺宇 
	 * @date 2019年2月27日 下午5:23:28 
	 */
	public static Collection<String> getBetweenDate(LocalDate startDateInclusive,LocalDate endDateInclusive){
		
		if(startDateInclusive.isBefore(CRITICAL_DATE)) {startDateInclusive = CRITICAL_DATE;}
		
		long distance = endDateInclusive.toEpochDay() - startDateInclusive.toEpochDay();
//		int distance = Period.between(startDateInclusive, endDateInclusive).getDays();
		if(distance < 1) {
			return  Stream.of(startDateInclusive.format(DATE_FORMATTER)).collect(Collectors.toSet());
		}
		
		return Stream.iterate(startDateInclusive, d -> {
			return d.plusDays(1);
		}).limit(distance+1).map( d -> {
			return d.format(DATE_FORMATTER);
		}).collect(Collectors.toSet());
//				.sorted( (o1,o2) -> {
//			return o2.compareTo(o1);
//		}).collect(Collectors.toCollection(TreeSet::new));
	}
	
	/**
	 * <p>
	 * Title: getBetweenMonth
	 * </p>
	 * <p>
	 * 	获取两个日期间隔的所有月份
	 * 
	 * @param start 格式必须为'2018-01-24'
	 * @param end 格式必须为'2018-02-25'
	 * @return
	 * 
	 * </p>
	 * @param @param start
	 * @param @param end
	 * @param @return 
	 * @return Collection<String> yyyyMM 默认格式
	 * </p>
	 * @param @param startMonthInclusive
	 * @param @param endMonthInclusive
	 * @param @return 
	 * @return Collection<String>
	 * @throws
	 * @author 周顺宇 
	 * @date 2019年2月27日 下午6:40:27 
	 */
	public static Collection<String> getBetweenMonth(LocalDate startMonthInclusive,LocalDate endMonthInclusive){
		
		if(startMonthInclusive.isBefore(CRITICAL_DATE)) {startMonthInclusive = CRITICAL_DATE;}
		
		Collection<String> betweenMonth = Stream.of(startMonthInclusive.format(MONTH_FORMATTER),endMonthInclusive.format(MONTH_FORMATTER)).collect(Collectors.toSet());
		
//		int distance = Period.between(startMonthInclusive, endMonthInclusive).getMonths();
		long distance = startMonthInclusive.until(endMonthInclusive,ChronoUnit.MONTHS);
		
		if(distance > 0) {
			Stream.iterate(startMonthInclusive, d -> {
				return d.plusMonths(1);
			}).limit(distance+1).forEach( month -> {
				betweenMonth.add(month.format(MONTH_FORMATTER));
			});
		}
		
		return betweenMonth;
	}
	
}