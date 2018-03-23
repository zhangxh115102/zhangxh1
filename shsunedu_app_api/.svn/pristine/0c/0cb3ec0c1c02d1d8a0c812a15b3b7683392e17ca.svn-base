package com.shsunedu.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.exception.UserException;
import com.shsunedu.base.api.service.ISystemConfigService;
import com.shsunedu.base.api.vo.SystemConfigVo;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;
import com.shsunedu.tool.RedisUtil;
import com.shsunedu.verify.SystemConfigShVo;


@RestController
@RequestMapping("/config")
public class ConfigApi extends BaseApi {
	private final static Logger logger = LoggerFactory.getLogger(ConfigApi.class);

	@Autowired
	private ISystemConfigService systemConfigService;
	@Autowired
	RedisUtil baseRedisUtil;

	/**
	 * 系统版本检查更新
	 * 
	 * @param token
	 * @return
	 */
	@AuthWhite
	@RequestMapping(value = "/checkUpdate", method = RequestMethod.GET)
	public String checkUpdate() {
		JsonUtil jsonUtil = new JsonUtil();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = systemConfigService.getSysCoifigList(SystemConfigShVo.getConfigKeys());

			jsonUtil.addToRoot("studentTitle", map.get("studentTitle")==null?"":map.get("studentTitle"));
			jsonUtil.addToRoot("studentContent", map.get("studentContent")==null?"":map.get("studentContent"));
			jsonUtil.addToRoot("teacherTitle", map.get("teacherTitle")==null?"":map.get("teacherTitle"));
			jsonUtil.addToRoot("teacherContent", map.get("teacherContent")==null?"":map.get("teacherContent"));
			
			jsonUtil.addToRoot("studentTitle_android", map.get("studentTitle_android")==null?"":map.get("studentTitle_android"));
			jsonUtil.addToRoot("studentContent_android", map.get("studentContent_android")==null?"":map.get("studentContent_android"));
			jsonUtil.addToRoot("studentTitle_ios", map.get("studentTitle_ios")==null?"":map.get("studentTitle_ios"));
			jsonUtil.addToRoot("studentContent_ios", map.get("studentContent_ios")==null?"":map.get("studentContent_ios"));
			jsonUtil.addToRoot("teacherTitle_android", map.get("teacherTitle_android")==null?"":map.get("teacherTitle_android"));
			jsonUtil.addToRoot("teacherContent_android", map.get("teacherContent_android")==null?"":map.get("teacherContent_android"));
			jsonUtil.addToRoot("teacherTitle_ios", map.get("teacherTitle_ios")==null?"":map.get("teacherTitle_ios"));
			jsonUtil.addToRoot("teacherContent_ios", map.get("teacherContent_ios")==null?"":map.get("teacherContent_ios"));
			
			jsonUtil.addToRoot("studentUpdateVersion_android", map.get("student_android_update_version")==null?"":map.get("student_android_update_version"));
			jsonUtil.addToRoot("studentUpdateVersion_ios", map.get("student_ios_update_version")==null?"":map.get("student_ios_update_version"));
			jsonUtil.addToRoot("teacherUpdateVersion_android", map.get("teacher_android_update_version")==null?"":map.get("teacher_android_update_version"));
			jsonUtil.addToRoot("teacherUpdateVersion_ios", map.get("teacher_ios_update_version")==null?"":map.get("teacher_ios_update_version"));
			
			jsonUtil.addToRoot("student_ios", SystemConfigShVo.toJsonForListToStudent_ios(jsonUtil, map));
			jsonUtil.addToRoot("student_android", SystemConfigShVo.toJsonForListToStudent_android(jsonUtil, map));
			jsonUtil.addToRoot("teacher_ios", SystemConfigShVo.toJsonForListToTeacher_ios(jsonUtil, map));
			jsonUtil.addToRoot("teacher_android", SystemConfigShVo.toJsonForListToTeacher_android(jsonUtil, map));

			return jsonUtil.toSuccessString();
		}catch (UserException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return jsonUtil.toUserErrorString(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return jsonUtil.toErrorString(e.getMessage());
		}
	}

	
	/**
	 * 系统版本-强制更新检查
	 * @param appType  App类型
	 * @param userType  使用app的用户类型
	 * @param version  待检测的版本
	 * @return
	 */
	@AuthWhite
	@RequestMapping(value = "/checkForceUpdate", method = RequestMethod.POST)
	public String checkForceUpdate(@RequestParam String appType, @RequestParam String userType, @RequestParam String version) {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			if(appType != null && !SystemConfigShVo.isValueExits("appType",appType)){
				return jsonUtil.toErrorString("appType类型不支持!");
			}
			if(userType != null && !SystemConfigShVo.isValueExits("userType",userType)){
				return jsonUtil.toErrorString("userType类型不支持!");
			}
			if(version != null){
				int vLength = version.split("\\.").length;
				if(vLength != 3){
					return jsonUtil.toErrorString("version类型不支持!");
				}
			}
			List<String> list = new ArrayList<String>();
			list.add("student_android_version_new");
			list.add("student_ios_version_new");
			list.add("teacher_android_version_new");
			list.add("teacher_ios_version_new");
			
			list.add("student_android_update_version");
			list.add("student_ios_update_version");
			list.add("teacher_android_update_version");
			list.add("teacher_ios_update_version");

			list.add("studentTitle_android");
			list.add("studentContent_android");
			list.add("studentTitle_ios");
			list.add("studentContent_ios");
			list.add("teacherTitle_android");
			list.add("teacherContent_android");
			list.add("teacherTitle_ios");
			list.add("teacherContent_ios");
			
			list.add("student_ios_url");
			list.add("student_android_url");
			list.add("teacher_ios_url");
			list.add("teacher_android_url");
			
			SystemConfigVo systemConfigVo = systemConfigService.checkForceUpdate(userType, appType, version, list);
			jsonUtil.addToRoot("forceFlag", null != systemConfigVo ? systemConfigVo.getForceFlag() : "");
			jsonUtil.addToRoot("lastVersion", null != systemConfigVo ? systemConfigVo.getLastVersion() : "");
			jsonUtil.addToRoot("lastVersionTitle", null != systemConfigVo ? systemConfigVo.getLastVersionTitle() : "");
			jsonUtil.addToRoot("lastVersionContent", null != systemConfigVo ? systemConfigVo.getLastVersionContent() : "");
			jsonUtil.addToRoot("downLoadUrl", null != systemConfigVo ? systemConfigVo.getDownLoadUrl() : "");
			return jsonUtil.toSuccessString();
		}catch (UserException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return jsonUtil.toUserErrorString(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return jsonUtil.toErrorString(e.getMessage());
		}
	}

}
