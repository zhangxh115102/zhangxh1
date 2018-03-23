package com.shsunedu.tool;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日期相关操作
 */
public class TimeUtil {
	private static final Logger logger = LoggerFactory.getLogger(TimeUtil.class);

	private static final String PATTERN_10 = "yyyy-MM-dd";
	private static final String PATTERN_16 = "yyyy-MM-dd HH:mm";
	private static final String PATTERN_19 = "yyyy-MM-dd HH:mm:ss";
	private static final Map<Integer,String> DATE_WEEK_MAP = new HashMap<>();
	static{
		DATE_WEEK_MAP.put(2, "周一");
		DATE_WEEK_MAP.put(3, "周二");
		DATE_WEEK_MAP.put(4, "周三");
		DATE_WEEK_MAP.put(5, "周四");
		DATE_WEEK_MAP.put(6, "周五");
		DATE_WEEK_MAP.put(7, "周六");
		DATE_WEEK_MAP.put(1, "周日");
	}

	public static String getNow() {
		try {
			return DateFormatHolder.formatFor(PATTERN_10).format(new Date());
		} catch (Exception e) {
		}
		return "";
	}

	public static String getNow16() {
		return DateFormatHolder.formatFor(PATTERN_16).format(new Date());
	}

	public static String getNow19() {
		return DateFormatHolder.formatFor(PATTERN_19).format(new Date());
	}

	public static int getYear() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.YEAR);
	}

	public static int getDay() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.DATE);
	}

	public static int getHour() {
		Calendar ca = Calendar.getInstance();
		return ca.get(Calendar.HOUR);
	}

	/**
	 * 获取日期年份
	 *
	 * @param dateString
	 * @return
	 */
	public static int getDateYear(String dateString) {
		if (dateString == null || dateString.length() == 0) {
			return 0;
		}
		try {
			Date date = parse(dateString);
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			return ca.get(Calendar.YEAR);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 获取日期月份
	 *
	 * @param dateString
	 * @return
	 */
	public static int getDateMonth(String dateString) {
		if (dateString == null || dateString.length() == 0) {
			return 0;
		}
		try {
			Date date = parse(dateString);
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			return ca.get(Calendar.MONTH) + 1;
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 获取日期天数
	 *
	 * @param dateString
	 * @return
	 */
	public static int getDateDay(String dateString) {
		if (dateString == null || dateString.length() == 0) {
			return 0;
		}

		try {
			Date date = parse(dateString);
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			return ca.get(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 中文年月日
	 *
	 * @param date
	 * @return
	 */
	public static String getChineseDate(String date) {
		if (date == null || date.trim().length() == 0) {
			return null;
		}
		return getDateYear(date) + "年" + getDateMonth(date) + "月" + getDateDay(date) + "日";
	}

	public static String getBirthday(int age, int month, int day) {
		Calendar cal = Calendar.getInstance();
		String y = (cal.get(Calendar.YEAR) - age) + "";
		String m = "01";
		String d = "01";
		if (month < 10)
			m = "0" + month;
		else
			m = "" + month;
		if (day < 10)
			d = "0" + day;
		else
			d = "" + day;
		String ymd = y + "-" + m + "-" + d;
		java.sql.Date birthday = java.sql.Date.valueOf(ymd);

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		return ymd;

	}

	public static String format10(Date time) {
		if (time == null)
			return "";
		return DateFormatHolder.formatFor(PATTERN_10).format(time);
	}

	public static String format16(Date time) {
		if (time == null)
			return "";
		return DateFormatHolder.formatFor(PATTERN_16).format(time);
	}

	public static String format19(Date time) {
		if (time == null)
			return "";
		return DateFormatHolder.formatFor(PATTERN_19).format(time);
	}
	
	public static String format(Date time,String pattern) {
		if (time == null || pattern == null)
			return "";
		return DateFormatHolder.formatFor(pattern).format(time);
	}

	
	/**
	 * @param after
	 * @param before
	 * @return 比较时间戳 默认精确到分
	 */
	public static boolean after(Date after, Date before) {
		String beforeStr = null;
		String afterStr = null;
		if (before != null) {
			beforeStr = format16(before);
		}
		if (after != null) {
			afterStr = format16(after);
		}
		return after(afterStr, beforeStr);
	}

	/**
	 * @param after
	 * @param before
	 * @return 比较时间戳 默认精确到分
	 */
	public static boolean after(String after, Date before) {
		String beforeStr = null;
		if (before != null) {
			beforeStr = format16(before);
		}
		return after(after, beforeStr);
	}

	/**
	 * 如果before<after 则返回true *
	 */
	public static boolean after(String after, String before) {
		if (after == null)
			return false;
		if (before == null)
			return true;
		return after.compareTo(before) > 0;
	}

	public static Date parse(String dateStr) {
		Date date = null;
		try {
			if (dateStr.length() == 10) {
				date = DateFormatHolder.formatFor(PATTERN_10).parse(dateStr);
			}
			if (dateStr.length() == 16) {
				date = DateFormatHolder.formatFor(PATTERN_16).parse(dateStr);
			}
			if (dateStr.length() > 16) {
				date = DateFormatHolder.formatFor(PATTERN_19).parse(dateStr);
			}
			return date;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 按指定格式格式化日期 *
	 */
	public static String parseDate(Date date, String pattern) {
		if (date == null || pattern == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * util时间转为sql时间*
	 */
	public static java.sql.Date toSqlDate(Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	/**
	 * util时间转为Timestamp时间*
	 */
	public static Timestamp toTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 日期按秒添加
	 */
	public static Date dateSecond(Date date, int second) {
		if (date == null) {
			return null;
		}
		date.setTime(date.getTime() + (second * 1000l));
		return date;
	}

	/**
	 * 日期按小时增加 *
	 */
	public static Date hourAdd(Date date, int hour) {
		if (date == null) {
			return null;
		}
		date.setTime(date.getTime() + (hour * 60 * 60 * 1000l));
		return date;
	}

	/**
	 * 日期按天增加 *
	 */
	public static Date dayAdd(Date date, int day) {
		if (date == null) {
			return null;
		}
		date.setTime(date.getTime() + (day * 24 * 60 * 60 * 1000L));
		return date;
	}

	/**
	 * 判断两个时间戳是否在相近的seconds 秒内（包括前后） *
	 */
	public static boolean isNearSeconds(String descTime, String nowTime, long seconds) {
		if (seconds < 0) {
			seconds = -seconds;
		}
		if (descTime == null || descTime.trim().length() == 0) {
			return false;
		}
		if (nowTime == null || nowTime.trim().length() == 0) {
			return false;
		}
		Date descDate = parse(descTime);
		Date nowDate = parse(nowTime);
		if (descDate == null || nowDate == null) {
			return false;
		}
		long timeDifference = descDate.getTime() - nowDate.getTime();
		if (timeDifference == 0) {
			return true;
		}
		if (timeDifference > 0 && timeDifference < seconds * 1000) {
			return true;
		}
		if (timeDifference < 0 && -timeDifference < seconds * 1000) {
			return true;
		}
		return false;
	}

	/**
	 * 计算两天之间的秒数
	 *
	 * @param beforeString
	 * @param afterString
	 * @return
	 */
	public static long differSecond(String beforeString, String afterString) {
		if (beforeString == null || beforeString.trim().length() == 0) {
			return 0;
		}
		if (afterString == null || afterString.trim().length() == 0) {
			return 0;
		}
		Date beforeDate = parse(beforeString);
		Date afterDate = parse(afterString);
		if (null == beforeDate || null == afterDate) {
			return 0;
		}
		long differMillis = beforeDate.getTime() - afterDate.getTime();
		if (differMillis <= 0) {
			return 0;
		}
		return differMillis / 1000;
	}

	/**
	 * 
	 * @Title: getTimeToString
	 * @Description: 按规划计算时间差
	 * @param @throws ParseException 设定文件
	 * @return String 返回类型 (规则1~24小时：xx：xx 1~7天：n天前 7天之外：7天前)
	 * @throws
	 */
	public static String getTimeToString(String beginTime, String endTime) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date begin = dfs.parse(beginTime);
			java.util.Date end = dfs.parse(endTime);
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			long day = between / (24 * 3600);
			if (day < 1) {
				String bgt = beginTime.substring(0, 10);
				String edt = endTime.substring(0, 10);
				if (!bgt.equals(edt)) {
					return "昨天 " + parseDate(begin, "HH:mm");
				}
				return parseDate(begin, "HH:mm");
			}
			if (day >= 1 && day <= 7) {
				return day + "天前";
			}
			return 7 + "天前";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static long differSecond(String timeString) {
		return differSecond(timeString, getNow19());
	}

	/**
	 * 计算两个时间相差天数
	 *
	 * @param beforeString
	 * @param afterString
	 * @return
	 */
	public static long differDay(String beforeString, String afterString) {
		if (beforeString == null || beforeString.trim().length() == 0) {
			return -1;
		}
		if (afterString == null || afterString.trim().length() == 0) {
			return -1;
		}
		Date beforeDate = parse(beforeString);
		Date afterDate = parse(afterString);
		if (beforeDate == null || afterDate == null) {
			return -1;
		}
		long differMillis = beforeDate.getTime() - afterDate.getTime();
		if (differMillis <= 0) {
			return -1;
		}
		long dayMillis = 1000 * 60 * 60 * 24;
		return differMillis / dayMillis;
	}

	/**
	 * 计算与当前相差天数
	 *
	 * @param timeString
	 * @return
	 */
	public static long differDay(String timeString) {
		return differDay(timeString, getNow19());
	}

	/**
	 * 信箱页时间展示
	 *
	 * @param timeString
	 * @return
	 */
	public static String period(String timeString) {
		if (StringUtils.isBlank(timeString))
			return null;
		try {
			Date date = parse(timeString);
			long day = differDay(getNow19(), timeString);
			DateFormat dateFormat;
			if (day <= 1) {
				dateFormat = new SimpleDateFormat("HH:mm");
			} else {
				dateFormat = new SimpleDateFormat("MM-dd");
			}
			if (date != null) {
				return dateFormat.format(date);
			}
		} catch (Exception e) {
			logger.error("TimeUtil.period error", e);
		}

		return null;
	}

	public static boolean between(String begin, String end) {
		String now = getNow19();
		if (now.compareTo(begin) >= 0 && now.compareTo(end) <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: getDayByInt
	 * @Description: 获取与当前天数相隔几天的天数
	 * @param @param days
	 * @return int 返回类型
	 * @throws
	 */
	public static int getDayByInt(int days) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - days);
		int dayBefore = c.get(Calendar.DATE);
		return dayBefore;
	}

	/**
	 * 当前时间到今天结束还有多少毫秒
	 * 
	 * @return
	 */
	public static long timeRemainingOfToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime().getTime() - System.currentTimeMillis();
	}
	
	/**
	 * 返回周几
	 * @param date
	 * @return
	 */
	public static String dayForWeek(String date){  
		 Calendar c = Calendar.getInstance();  
		 c.setTime(parse(date));
		 return DATE_WEEK_MAP.get(c.get(Calendar.DAY_OF_WEEK));
	}
}
