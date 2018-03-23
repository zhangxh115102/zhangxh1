package com.shsunedu.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class NumUtil {

	/**
	 * 计算bmi
	 * 
	 * @param weight单位kg
	 * @param height单位m
	 * @return
	 */
	public static double bmi(double weight, double height) {
		BigDecimal bigDecimal = new BigDecimal(weight);
		return bigDecimal.divide(new BigDecimal(height * height), 2, RoundingMode.HALF_UP).doubleValue();
	}

	/** 判断手机号格式 **/
	public static boolean isMobile(String mobile) {
		if (mobile == null || mobile.length() != 11) {
			return false;
		}
		return mobile.matches("^1[3|4|5|7|8]\\d{9}$");
	}

	/**
	 * 强制转换为int类型
	 *
	 * @param o
	 * @return
	 */
	public static final int toInt(Object o) {
		int num = 0;
		try {
			num = Integer.parseInt(o.toString());
		} catch (Exception e) {
		}
		return num;
	}

	/**
	 * 强制转换为int类型，不能转时返回默认值
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static final int toInt(Object o, int defaultValue) {
		int num = defaultValue;
		try {
			num = Integer.parseInt(o.toString());
		} catch (Exception e) {
		}
		return num;
	}

	/**
	 * 强制转换为long类型
	 *
	 * @param o
	 * @return
	 */
	public static final long toLong(Object o) {
		long num = 0l;
		try {
			num = Long.parseLong(o.toString());
		} catch (Exception e) {
		}
		return num;
	}

	/**
	 * 强制转换为long类型，不能转时返回默认值
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static final long toLong(Object o, long defaultValue) {
		long num = defaultValue;
		try {
			num = Long.parseLong(o.toString());
		} catch (Exception e) {
		}
		return num;
	}

	/**
	 * 强制转换为double类型
	 *
	 * @param o
	 * @return
	 */
	public static final double toDouble(Object o) {
		double num = 0.0;
		try {
			num = Double.parseDouble(o.toString());
		} catch (Exception e) {
		}
		return num;
	}

	/**
	 * 强制转换为float类型
	 *
	 * @param o
	 * @return
	 */
	public static final float toFloat(Object o) {
		float num = 0.0f;
		try {
			num = Float.parseFloat(o.toString());
		} catch (Exception e) {
		}
		return num;
	}

	/**
	 * 返回字符串值 null值返回空串
	 *
	 * @param o
	 * @return
	 */
	public static final String toString(Object o) {
		try {
			if (o == null)
				return "";
			return o.toString();
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * 强制转换为String类型，不能转时返回默认值
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static final String toString(Object o, String defaultValue) {
		try {
			if (o == null)
				return defaultValue;
			String s = o.toString();
			if (s == null || s.trim().length() == 0) {
				return defaultValue;
			}
			return s;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String getOrderId(String head) {
		return head + (new Date()).getTime();
	}

	public static void main(String[] args) {
		System.out.println(isMobile("15710068657"));
	}
}
