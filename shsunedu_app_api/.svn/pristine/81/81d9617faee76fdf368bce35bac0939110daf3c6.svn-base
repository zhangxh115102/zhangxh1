package com.shsunedu.tool;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String filterEmoji(String source) {
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", "");
		} else {
			return source;
		}
	}

	/**
	 * 判断字符串对象是否是null或者空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(String obj) {
		return obj == null || "".equals(obj.toString().trim());
	}

	/**
	 * 如果是null返回空
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj == null)
			return "";
		return obj.toString();
	}

	/**
	 * 首字母转换为大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstCharUpper(String str) {
		if (isNullOrEmpty(str)) {
			return "";
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String addSeparator(List<String> list, String prefix, String suffix, String separator) {

		if (list == null || list.size() < 1)
			return "";

		StringBuffer sb = new StringBuffer();

		for (String str : list) {
			sb.append(prefix);
			sb.append(str);
			sb.append(suffix);
			sb.append(separator);
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

	public static String addSeparator(String[] list, String prefix, String suffix, String separator) {

		if (list == null || list.length < 1)
			return "";

		StringBuffer sb = new StringBuffer();

		for (String str : list) {
			sb.append(prefix);
			sb.append(str);
			sb.append(suffix);
			sb.append(separator);
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}

	/**
	 * 检查当前字符串编码格式
	 * 
	 * @param str
	 * @return 编码格式
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return null;
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
}
