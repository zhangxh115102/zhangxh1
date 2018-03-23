package com.shsunedu.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: RegularUtil
 * @Description: 注册校验类
 * @author liyong
 * @date 2017年3月28日
 *
 */
public class RegularUtil {
	public static boolean checkPhone(String phone) throws Exception {
		Pattern pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	public static boolean checkVCode(String vCode) throws Exception {
		Pattern pattern = Pattern.compile("^\\d{4}$");
		Matcher matcher = pattern.matcher(vCode);
		return matcher.matches();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(checkPhone("13912345678"));
	}
}
