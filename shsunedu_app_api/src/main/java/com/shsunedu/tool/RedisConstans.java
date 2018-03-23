package com.shsunedu.tool;

/**
 * 
 * @ClassName: RedisConstans
 * @Description: 存储redis中的key的方法
 * @author liyong
 * @date 2017年3月28日
 *
 */
public class RedisConstans {
	/** 常用缓存时间单位 **/
	public static final int LIFE = -1;
	public static final int MINUTE = 60;
	public static final int HARF_HOUR = MINUTE * 30;
	public static final int HOUR = MINUTE * 60;
	public static final int DAY = HOUR * 24;
	public static final int WEEK = DAY * 7;
	public static final int MONTH = DAY * 30;

	/**
	 * 
	 * @Title: studentRegeditKey
	 * @Description: 学生注册时存储的验证码的值
	 * @param mobile
	 * @return String 返回类型
	 * @throws
	 */
	public static String studentRegeditKey(String mobile) {
		return "student:regedit:" + mobile;
	}

	/**
	 * 
	 * @Title: fastLoginKey
	 * @Description: 快递登录时的验证码
	 * @param mobile
	 *            手机号
	 * @return String 返回类型
	 * @throws
	 */
	public static String fastLoginKey(String mobile) {
		return "fast:login:" + mobile;
	}

	/**
	 * 
	 * @Title: qrcodeWebLogin
	 * @Description:pc二维码登录
	 * @param uuid
	 * @return String 返回类型
	 * @throws
	 */
	public static String qrcodeWebLogin(int uuid) {
		return "qrcode:web:login:" + uuid;
	}

}
