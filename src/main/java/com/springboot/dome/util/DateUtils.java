package com.springboot.dome.util;

import cn.hutool.core.date.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	
	/******************************************************************
	 * 时间周期定义
	 */
	/**
	 * 今天
	 */
	public static final String TODAY = "today";
	/**
	 * 昨天
	 */
	public static final String YESTERDAY = "yesterday";
	
	/**
	 * 明天
	 */
	public static final String TOMORROW = "tomorrow";
	/**
	 * 本周
	 */
	public static final String THIS_WEEK = "this_week";
	/**
	 * 上周
	 */
	public static final String LAST_WEEK = "last_week";
	
	/**
	 * 下周
	 */
	public static final String NEXT_WEEK = "next_week";
	
	/**
	 * 当月
	 */
	public static final String THIS_MONTH = "this_month";
	/**
	 * 上月
	 */
	public static final String LAST_MONTH = "last_month";
	
	/**
	 * 下月
	 */
	public static final String NEXT_MONTH = "next_month";
	/**
	 * 今年
	 */
	public static final String THIS_YEAR = "this_year";
	/**
	 * 去年
	 */
	public static final String LAST_YEAR = "last_year";
	/**
	 * 今天以前(不包括今天）
	 */
	public static final String BEFORE_TODAY = "before_today";
	/**
	 * 今天以后(包括今天)
	 */
	public static final String AFTER_TODAY = "after_today";
	
	
	

    private DateUtils() {
    }

    public static String getDateStr(Date date){
    	return getString(date, "yyyy-MM-dd");
    }
    
    public static String getDateString(Date date){
    	return getString(date, "yyyyMMdd");
    }
    
    public static String getDateTimeStr(Date date) {
    	if(null==date){
    		return "";
    	}
        return getString(date, "yyyy-MM-dd HH:mm");
    }


    public static String getString(Date date, String pattern) {
        if (date == null)
            return "";
        SimpleDateFormat df3 = new SimpleDateFormat();
        df3.applyPattern(pattern);
        return df3.format(date);
    }


    public static Date getDate(String str, String pattern){
        if (null == str || str.trim().equals("")) {
            return null;
        }
        SimpleDateFormat df3 = new SimpleDateFormat();
        df3.applyPattern(pattern);
        try {
			return df3.parse(str);
		} catch (ParseException e) {
			// Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
    }

    public static Date getDate(String str, String pattern,Date defaultDate){
        if (null == str || str.trim().equals("")) {
            return defaultDate;
        }
        SimpleDateFormat df3 = new SimpleDateFormat();
        df3.applyPattern(pattern);
        try {
			return df3.parse(str);
		} catch (ParseException e) {
			// Auto-generated catch block
			e.printStackTrace();
			return defaultDate;
		}
    }

    public static Date getDate(String str){
        String strResult;
        if (str == null || "".equals(str)){
        	return null;
        }
        strResult = str.trim();
        if (strResult.length() <= 10) {
            return getDate(strResult, "yyyy-MM-dd");
        } else {
            return getDate(strResult, "yyyy-MM-dd HH:mm:ss");
        }
    }


    public static Long subtract(Long arg1, Long arg2) {
        if (arg1 != null && arg2 != null) {
            return new Long(arg1.longValue() - arg2.longValue());
        } else if (arg1 == null) {
            if (arg2 != null) {
                return new Long(-arg2.longValue());
            } else {
                return Long.valueOf(0);
            }
        } else {
            return arg1;
        }
    }


    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        String currentTime = sdf.format(date);
        return currentTime;
    }


    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
    }


    /**
     * 比较2个日期的时间部分
     * @param d1
     * @param d2
     * @return 结果小于0，d1<d2
     */
    public static int compareTime(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	c1.set(2000, 1, 1);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	c2.set(2000, 1, 1);
    	return c1.compareTo(c2);
    }
    
    /**
     * 比较2日期
     * @param d1
     * @param d2
     * @return
     */
    public static int compare(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	return c1.compareTo(c2);
    }  
    

    /**
     * 判断2个日期是否为同一年，年相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameYear(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	int year1 = c1.get(Calendar.YEAR);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	int year2 = c2.get(Calendar.YEAR);
    	return (year1==year2);
    }    
    
    /**
     * 判断2个日期是否为同一月，年、月都相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameMonth(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	int year1 = c1.get(Calendar.YEAR);
    	int month1 = c1.get(Calendar.MONTH);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	int year2 = c2.get(Calendar.YEAR);
    	int month2 = c2.get(Calendar.MONTH);
    	return (year1==year2 && month1==month2);
    }
    
    /**
     * 判断2个日期是否为同一天，年、月、日都相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameDay(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	int year1 = c1.get(Calendar.YEAR);
    	int month1 = c1.get(Calendar.MONTH);
    	int day1 = c1.get(Calendar.DAY_OF_MONTH);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	int year2 = c2.get(Calendar.YEAR);
    	int month2 = c2.get(Calendar.MONTH);
    	int day2 = c2.get(Calendar.DAY_OF_MONTH);
    	return (year1==year2 && month1==month2 && day1==day2);
    }
   
    /**
     * 判断2个日期是否为同一小时，年、月、日，小时都相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameHour(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	int year1 = c1.get(Calendar.YEAR);
    	int month1 = c1.get(Calendar.MONTH);
    	int day1 = c1.get(Calendar.DAY_OF_MONTH);
    	int hour1 = c1.get(Calendar.HOUR_OF_DAY);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	int year2 = c2.get(Calendar.YEAR);
    	int month2 = c2.get(Calendar.MONTH);
    	int day2 = c2.get(Calendar.DAY_OF_MONTH);
    	int hour2 = c2.get(Calendar.HOUR_OF_DAY);
    	return (year1==year2 && month1==month2 && day1==day2 && hour1==hour2);
    }    

    /**
     * 判断2个日期是否为同一分钟，年、月、日、小时、分钟都相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameMinute(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	int year1 = c1.get(Calendar.YEAR);
    	int month1 = c1.get(Calendar.MONTH);
    	int day1 = c1.get(Calendar.DAY_OF_MONTH);
    	int hour1 = c1.get(Calendar.HOUR_OF_DAY);
    	int minute1 = c1.get(Calendar.MINUTE);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	int year2 = c2.get(Calendar.YEAR);
    	int month2 = c2.get(Calendar.MONTH);
    	int day2 = c2.get(Calendar.DAY_OF_MONTH);
    	int hour2 = c2.get(Calendar.HOUR_OF_DAY);
    	int minute2 = c2.get(Calendar.MINUTE);
    	return (year1==year2 && month1==month2 && day1==day2 && hour1==hour2 && minute1==minute2);
    }    
    
    /**
     * 判断2个日期是否为同一秒，年、月、日、小时、分钟、秒都相等
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameSecond(Date d1,Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	int year1 = c1.get(Calendar.YEAR);
    	int month1 = c1.get(Calendar.MONTH);
    	int day1 = c1.get(Calendar.DAY_OF_MONTH);
    	int hour1 = c1.get(Calendar.HOUR_OF_DAY);
    	int minute1 = c1.get(Calendar.MINUTE);
    	int second1 = c1.get(Calendar.SECOND);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	int year2 = c2.get(Calendar.YEAR);
    	int month2 = c2.get(Calendar.MONTH);
    	int day2 = c2.get(Calendar.DAY_OF_MONTH);
    	int hour2 = c2.get(Calendar.HOUR_OF_DAY);
    	int minute2 = c2.get(Calendar.MINUTE);
    	int second2 = c2.get(Calendar.SECOND);
    	return (year1==year2 && month1==month2 && day1==day2 && hour1==hour2 && minute1==minute2 && second1==second2);
    }        
    
    /**
     * 获取指定日期所在月的天数
     * 
     * @param date
     * @return
     */
    public static int getDayNumInMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.DATE, -1);
        return c.get(Calendar.DATE);
    }
    

    /**
     * 提取时间的日期部分（将时间部分都置为0）
     * @param date
     * @return
     */
    public static Date extractDate(Date date){
    	 Calendar c = Calendar.getInstance();
         c.setTime(date);
         c.set(Calendar.AM_PM,Calendar.AM);
         c.set(Calendar.HOUR, 0);
         c.set(Calendar.MINUTE, 0);
         c.set(Calendar.SECOND, 0);
         c.set(Calendar.MILLISECOND, 0);
         return c.getTime();
    }


    /**
     * 获取指定日期所在月的开始日期
     * @param date
     * @param firstDayInMonth 一个月的起始号数
     * @return
     */
    public static Date getFirstDayInMonth(Date date,Integer firstDayInMonth) {
    	if(null==firstDayInMonth || firstDayInMonth<1 || firstDayInMonth>28){
    		firstDayInMonth = 1;
    	}
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        if(dayOfMonth<firstDayInMonth){
        	c.add(Calendar.MONTH, -1);
        }
    	c.set(Calendar.DAY_OF_MONTH,firstDayInMonth);
        return c.getTime();
    }

    /**
     * 获取指定日期所在月的结束日期
     * @param date
     * @param firstDayInMonth 一个月的起始号数
     * @return
     */
    public static Date getLastDayInMonth(Date date,Integer firstDayInMonth) {
    	if(null==firstDayInMonth || firstDayInMonth<1 || firstDayInMonth>28){
    		firstDayInMonth = 1;
    	}
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        if(dayOfMonth>=firstDayInMonth){
        	c.add(Calendar.MONTH, 1);
        }
    	c.set(Calendar.DAY_OF_MONTH,firstDayInMonth);
    	c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    /**
     * 获取指定日期所在年的开始日期
     * @param date
     * @param firstDayInMonth
     * @return
     */
    public static Date getFirstDayInYear(Date date,Integer firstDayInMonth){
    	if(null==firstDayInMonth || firstDayInMonth<1 || firstDayInMonth>28){
    		firstDayInMonth = 1;
    	}
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        if(dayOfMonth<firstDayInMonth && currentMonth==Calendar.JANUARY){
        	c.add(Calendar.YEAR, -1);
        }
    	c.set(Calendar.DAY_OF_MONTH,firstDayInMonth);
        return c.getTime();
    }
    
    /**
     * 获取指定日期所在年的结束日期
     * @param date
     * @param firstDayInMonth 一个月的起始号数
     * @return
     */
    public static Date getLastDayInYear(Date date,Integer firstDayInMonth) {
    	return addDate(addYear(getFirstDayInYear(date, firstDayInMonth),1),-1);
    }
    
    /**
     * 获取日期所在周的第一天（周一）的日期
     * @param date
     * @return
     */
    public static Date getFirstDayInWeek(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.setFirstDayOfWeek(Calendar.MONDAY);
    	c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	return c.getTime();
    }
    
    
    /**
     * 获取日期所在周的最后一天（周日）的日期
     * @param date
     * @return
     */
    public static Date getLastDayInWeek(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.setFirstDayOfWeek(Calendar.MONDAY);
    	c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    	return c.getTime();
    }
        
    
    
    /**
     * 返回指定日期的前几分钟或后几分钟的日期
     * @param date
     * @param numOfDays
     * @return
     */
    public static Date addMinute(Date date,int numOfMinutes){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, numOfMinutes);
		return c.getTime();
    }
    
    /**
     * 返回指定日期的前几天或后几天的日期
     * @param date
     * @param numOfDays
     * @return
     */
    public static Date addDate(Date date,int numOfDays){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numOfDays);
		return c.getTime();
    }
    
    /**
     * 返回指定日期的前几周或后几周的日期
     * @param date
     * @param numOfDays
     * @return
     */
    public static Date addWeek(Date date,int numOfWeeks){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_YEAR, numOfWeeks);
		return c.getTime();
    }
    
    /**
     * 返回指定日期的前几月或后几月的日期
     * @param date
     * @param numOfDays
     * @return
     */
    public static Date addMonth(Date date,int numOfMonths){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, numOfMonths);
		return c.getTime();
    }

    /**
     * 返回指定日期的前几年或后几年的日期
     * @param date
     * @param numOfDays
     * @return
     */
    public static Date addYear(Date date,int numOfYears){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, numOfYears);
		return c.getTime();
    }

    
    /**
     * 根据年，月，日，返回一个日期（时间为00:00:00:000）
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(int year,int month,int day){
    	Calendar c = Calendar.getInstance();
    	c.set(year, month, day);
    	return extractDate(c.getTime());
    }
    
    /**
     * 计算2个日期之间相差的天数（计算时不包括时间部分）
     * @param date1
     * @param date2
     * @return
     */
	public static int getDaysBetween(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(extractDate(date1));
		c1.clear(Calendar.HOUR);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(extractDate(date2));
		return (int) ((c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24));
	}
	
	/**
	 * 获取2个日期之间相差的天数（计算时包括时间部分）
	 * @Title: getDaysBetweenWithTime
	 * @author: 顾敬峰
	 * @Description:
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDaysBetweenWithTime(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		return (int) ((c2.getTimeInMillis()-c1.getTimeInMillis())/(1000*60*60*24));
	}
	
	/**
	 * 根据指定的时间周期，返回该周期的起始日期和终止日期
	 * @param period
	 * @param firstDayInMonth 每月的第一天号数
	 * @return 长度为2的日期数组，数组第一个存储的是起始日期，第二个存储的是结束日期，返回的日期数据不包含时间
	 */
    public static Date[] getStartAndEndDateByPeriod(String period,int firstDayInMonth){
    	Date[] result = new Date[2];
    	Date now = new Date();
    	if(TODAY.equals(period)){ 
    		//今天
    		result[0] = extractDate(now); 
    		result[1] = result[0];
    	}else if(YESTERDAY.equals(period)){
    		//昨天
    		result[0] = extractDate(addDate(now,-1)); 
    		result[1] = result[0];
    	}else if(TOMORROW.equals(period)){
    		//明天
    		result[0] = extractDate(addDate(now,1)); 
    		result[1] = result[0];
    	}else if(THIS_WEEK.equals(period)){ 
    		//本周
    		result[0] = extractDate(getFirstDayInWeek(now)); 
    		result[1] = addDate(addWeek(result[0], 1),-1);
    	}else if(LAST_WEEK.equals(period)){ 
    		//上周
    		result[0] = addWeek(extractDate(getFirstDayInWeek(now)),-1); 
    		result[1] = addDate(addWeek(result[0], 1),-1);
    	}else if(NEXT_WEEK.equals(period)){ 
    		//下周
    		result[0] = addWeek(extractDate(getFirstDayInWeek(now)),1); 
    		result[1] = addDate(addWeek(result[0], 1),-1);
    	}else if(THIS_MONTH.equals(period)){
    		//本月
    		result[0] = extractDate(getFirstDayInMonth(now,firstDayInMonth)); 
    		result[1] = addDate(addMonth(result[0], 1),-1);
    	}else if(LAST_MONTH.equals(period)){
    		//上月
    		result[0] = addMonth(extractDate(getFirstDayInMonth(now,firstDayInMonth)),-1); 
    		result[1] = addDate(addMonth(result[0], 1),-1);
    	}else if(NEXT_MONTH.equals(period)){
    		//下月
    		result[0] = addMonth(extractDate(getFirstDayInMonth(now,firstDayInMonth)),1); 
    		result[1] = addDate(addMonth(result[0], 1),-1);
    	}else if(THIS_YEAR.equals(period)){
    		//今年
    		result[0] = extractDate(getFirstDayInYear(now, firstDayInMonth)); 
    		result[1] = addDate(addYear(result[0], 1),-1);
    	}else if(LAST_YEAR.equals(period)){
    		//去年
    		result[0] = addYear(extractDate(getFirstDayInYear(now,firstDayInMonth)),-1); 
    		result[1] = addDate(addYear(result[0], 1),-1);
    	}else if(BEFORE_TODAY.equals(period)){
    		//今天以前
    		result[0] = null;
    		result[1] = addDate(extractDate(now),-1);
    	}else if(AFTER_TODAY.equals(period)){
    		result[0] = extractDate(now);
    		result[1] = null;
    	}
    	return result;
    }
    /**
     * 根据生日计算年龄
     * @param birthday
     * @return
     */
    public static int calculateAgeByBirthday(Date birthday) {
        int age = 0;
        Calendar birthdayC = Calendar.getInstance();
        birthdayC.setTime(birthday);

        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.MONTH) > birthdayC.get(Calendar.MONTH)) {
            age = c.get(Calendar.YEAR) - birthdayC.get(Calendar.YEAR);
        } else {
            age = c.get(Calendar.YEAR) - birthdayC.get(Calendar.YEAR) - 1;
        }

        return age;
    }

    /**
     * 根据生日计算星座
     * @param birthday
     * @return
     */
    public static int calculateConstellationByBirthday(Date birthday) {
        Calendar birthdayC = Calendar.getInstance();
        birthdayC.setTime(birthday);
        int month = birthdayC.get(Calendar.MONTH);
        int day = birthdayC.get(Calendar.DAY_OF_MONTH);
        int constellation = 0;
        int[] mArr = { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };
        if (day >= mArr[month]) {
            constellation = month;
        } else {
            if (month == 0) {
                constellation = 11;
            } else {
                constellation = month - 1;
            }
        }
        return constellation;
    }
    
    /**
     * 获取日期的年份
     * @param date
     * @return
     */
    public static int getYear(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.YEAR);
    }
    
    /**
     * 获取日期的月份
     * @param date
     * @return
     */
    public static int getMonth(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.MONTH);
    }
    
    
	/**
	 * 获取某天的结束时间
	 * 
	 * @param calendar 日期 {@link Calendar}
	 * @return {@link Calendar}
	 */
	public static Calendar endOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}

	/**
	 * 获取某天的结束时间
	 * 
	 * @param date 日期
	 * @return {@link DateTime}
	 */
	public static Date endOfDay(Date date) {
		return endOfDay(calendar(date)).getTime();
	}
	
	/**
	 * 转换为Calendar对象
	 * 
	 * @param date 日期对象
	 * @return Calendar对象
	 */
	public static Calendar calendar(Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * 获取当前还剩余的毫秒数
	 * @Title: getTodayRemainMillis
	 * @author: 顾敬峰
	 * @Description:
	 * @return
	 */
	public static long getTodayRemainMillis(){
		Date now = new Date();
		long current = now.getTime();
		long dayEnd = endOfDay(now).getTime();
		return dayEnd - current;
	}
	
	/**
	 * 组合日期和时间
	 * @Title: mergeDateAndTime
	 * @author: 顾敬峰
	 * @Description:
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date mergeDateAndTime(Date date,Date time){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		c.set(Calendar.YEAR, c1.get(Calendar.YEAR));
		c.set(Calendar.MONTH, c1.get(Calendar.MONTH));
		c.set(Calendar.DATE, c1.get(Calendar.DATE));
		return c.getTime();
	}
	
	/**
	 * 判断套餐到期时间
	 * @Title: isTheMonth
	 * @author: 邱习艺
	 * @Description:
	 * @date: 2020年3月23日 下午3:59:41
	 * @param expTime
	 * @return
	 */
	public static int isTheMonth(Date expTime) {
		Date now = new Date();
		int nowYear = DateUtils.getYear(now);
		int nowMonth = DateUtils.getMonth(now);
		int expYear = DateUtils.getYear(expTime);//到期年
		int expMonth = DateUtils.getMonth(expTime);//到期月
		// 当前年月等于到期年月，说明是当月到期
		if(nowYear == expYear && nowMonth == expMonth){
			return 0;
		}
		// 判断是否已到期
//		if(DateUtils.compare(now, expTime)>0){
//			return true;
//		}
		return DateUtils.compare(now, expTime);
	}
	
}
