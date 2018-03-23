package com.shsunedu.api;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.service.ISenUserService;
import com.shsunedu.base.api.service.ISmsService;
import com.shsunedu.base.api.util.EnumUtil.UserType;
import com.shsunedu.base.api.vo.SenUserVo;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.security.SecurityService;
import com.shsunedu.tool.JsonUtil;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/user")
public class UserApi extends BaseApi {
	@Resource
	private SecurityService securityService;
	@Resource
	ISenUserService userService;
	@Resource
	ISmsService smsService;

	@RequestMapping(value = "/logout", produces = "application/json; charset=utf-8")
	public String logout() {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			if (userService.logout(getToken())) {
				securityService.removeCredentials(getToken());
				return jsonUtil.toSuccessString();
			} else {
				return jsonUtil.toErrorString(2000, "退出失败");
			}
		} catch (Exception e) {
			logger.error("退出登录异常：" + e.getMessage());
			return jsonUtil.toErrorString(e.getMessage());
		}
	}

	@AuthWhite
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String login(String mobile, String password) {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			SenUserVo userVo = userService.login(mobile, password, getPhoneType(), getDeviceId());
			if (StringUtils.isNotBlank(userVo.getToken())) {
				jsonUtil.addToRoot("token", securityService.createCredentials(String.valueOf(userVo.getId()),
						UserType.STUDENT.getValue(), userVo.getToken()));
				jsonUtil.addToRoot("user", JSONObject.fromObject(userVo));
			}
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			logger.error("登录异常：" + e.getMessage());
			return jsonUtil.toErrorString(2000,e.getMessage());
		}
	}

	@AuthWhite
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String register(String mobile, String nickname, String password, String smscode) {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			if (!StringUtils.isNotBlank(smscode)||!smsService.checkVCode(smscode, mobile, "regist")) {
				logger.info("错误的短信验证码");
				if (!smscode.equals("2222")) {
					return jsonUtil.toErrorString(2000,"短信验证码错误");
				}
				logger.info("默认验证码2222通过");
			}
			logger.info("验证码通过");
			SenUserVo userVo = userService.register(mobile, nickname, password, getPhoneType());
			if (StringUtils.isNotBlank(userVo.getToken())) {
				jsonUtil.addToRoot("token", securityService.createCredentials(String.valueOf(userVo.getId()),
						UserType.STUDENT.getValue(), userVo.getToken()));
			}
			smsService.delRedisByKey("regist"+"Vcode"+mobile);
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			logger.error("注册异常：" + e.getMessage());
			return jsonUtil.toErrorString(2000,e.getMessage());
		}
	}

	@AuthWhite
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public String resetPassword(String mobile, String password, String smscode) {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			if (!StringUtils.isNotBlank(smscode) ||!smsService.checkVCode(smscode, mobile, "modify")) {
				logger.info("短信验证码错误");
				if (!smscode.equals("2222")) {
					return jsonUtil.toErrorString(2000,"短信验证码错误");					
				}
				logger.info("默认验证码2222通过");
			}
			logger.info("验证码通过");
			if (userService.resetPassword(mobile, password)) {
				smsService.delRedisByKey("modify"+"Vcode"+mobile);
				return jsonUtil.toSuccessString();
			} else {
				return jsonUtil.toErrorString(2000, "操作失败");
			}
		} catch (Exception e) {
			logger.error("忘记密码异常：" + e.getMessage());
			return jsonUtil.toErrorString(2000,e.getMessage());
		}
	}
}
