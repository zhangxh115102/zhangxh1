package com.shsunedu.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shsunedu.tool.JsonUtil;

public class SystemConfigShVo {


	public static ObjectNode toJsonForListToStudent_ios(JsonUtil jsonUtil, Map<String, String> map) throws Exception {
		ObjectNode on = jsonUtil.initObjectNode();
		on.put("version", map.get("student_ios_version") == null ? "" : map.get("student_ios_version"));
		on.put("url", map.get("student_ios_url") == null ? "" : map.get("student_ios_url"));
		return on;
	}

	public static ObjectNode toJsonForListToStudent_android(JsonUtil jsonUtil, Map<String, String> map) throws Exception {
		ObjectNode on = jsonUtil.initObjectNode();
		on.put("version", map.get("student_android_version") == null ? "" : map.get("student_android_version"));
		on.put("url", map.get("student_android_url") == null ? "" : map.get("student_android_url"));
		return on;
	}

	public static ObjectNode toJsonForListToTeacher_ios(JsonUtil jsonUtil, Map<String, String> map) throws Exception {
		ObjectNode on = jsonUtil.initObjectNode();
		on.put("version", map.get("teacher_ios_version") == null ? "" : map.get("teacher_ios_version"));
		on.put("url", map.get("teacher_ios_url") == null ? "" : map.get("teacher_ios_url"));
		return on;
	}

	public static ObjectNode toJsonForListToTeacher_android(JsonUtil jsonUtil, Map<String, String> map) throws Exception {
		ObjectNode on = jsonUtil.initObjectNode();
		on.put("version", map.get("teacher_android_version") == null ? "" : map.get("teacher_android_version"));
		on.put("url", map.get("teacher_android_url") == null ? "" : map.get("teacher_android_url"));
		return on;
	}

	/**
	 * 配置中所需信息的key集合
	 */
	public static List<String> getConfigKeys() {
		List<String> list = new ArrayList<String>();

		list.add("qrcode_url");
		list.add("qrcode_url_student");
		list.add("classDesc_url");
		list.add("teacherRecruitment_url");
		list.add("contactUs_url");
		list.add("approvalPhone");
		list.add("cashPhone");
		list.add("cashDesc_url");
		list.add("help_url");
		list.add("about_url");
		list.add("service_url");
		list.add("score_url");
		list.add("hotLine");
		list.add("student_hotLine");
		list.add("success_case");
		list.add("teacherRecruitment_url");
		list.add("teacher_share_url");
		list.add("teacher_share_url_new");//学生端二期老师分享
		list.add("course_share_url");
		list.add("buy_class_url");
		list.add("student_help_url");
		list.add("class_teacher_url");
		list.add("student_contactUs_url");
		list.add("classDese_student_url");
		//系统版本更新  start
		list.add("student_android_url");
		list.add("student_android_version");
		list.add("student_ios_url");
		list.add("student_ios_version");
		list.add("teacher_android_url");
		list.add("teacher_android_version");
		list.add("teacher_ios_url");
		list.add("teacher_ios_version");
		//v1 版本
		list.add("studentTitle");
		list.add("studentContent");
		list.add("teacherTitle");
		list.add("teacherContent");
		//v2版本
		list.add("studentTitle_android");
		list.add("studentContent_android");
		list.add("studentTitle_ios");
		list.add("studentContent_ios");
		list.add("teacherTitle_android");
		list.add("teacherContent_android");
		list.add("teacherTitle_ios");
		list.add("teacherContent_ios");
		//v2强制版本更新  end
		list.add("student_android_update_version");
		list.add("student_ios_update_version");
		list.add("teacher_android_update_version");
		list.add("teacher_ios_update_version");
		return list;
	}

	
	public static boolean isValueExits(String type,String value) {
		String[] appType = { "ios", "android"};
		String[] userType = { "teacher", "student"};
		if("appType".equals(type)){
			if (appType != null && appType.length > 0) {
				for (int i = 0; i < appType.length; i++) {
					if (appType[i].equals(value)) {
						return true;
					}
				}
			}
		}
		if("userType".equals(type)){
			if (userType != null && userType.length > 0) {
				for (int i = 0; i < userType.length; i++) {
					if (userType[i].equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
