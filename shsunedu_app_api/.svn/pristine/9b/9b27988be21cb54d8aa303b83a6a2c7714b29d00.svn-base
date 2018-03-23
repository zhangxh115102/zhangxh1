package com.shsunedu.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.service.ISmsService;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;

/**
 * 发送短信
 * @author Jash
 *
 */
@RestController
@RequestMapping("/sms")
public class SmsApi extends BaseApi {
	@Resource
	ISmsService smsService; 

	@AuthWhite
	@RequestMapping(value="/getVcode",method=RequestMethod.POST,produces = "application/json; charset=UTF-8")
	public String sendVCode(@RequestParam(value="mobilePhone") String mobilePhone,@RequestParam(value="vCodeType") String vCodeType){
		JsonUtil jsonUtil = new JsonUtil();
		try {
		String respon = smsService.getVcode(mobilePhone,vCodeType);
		if (respon==null||respon.length()!=4) {
				return jsonUtil.toSuccessString();
		}
			} catch (Exception e) {
				return jsonUtil.toErrorString(e.getMessage());
			}
		try {
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			return jsonUtil.toErrorString(e.getMessage());
		}
	}
	
	@AuthWhite
	@RequestMapping(value="/checkVcode",method=RequestMethod.POST,produces = "application/json; charset=UTF-8")
	public String checkVCode(@RequestParam String mobilePhone,@RequestParam String Vcode,@RequestParam String vCodeType){
		JsonUtil jsonUtil = new JsonUtil();
		try {
		if (smsService.checkVCode(Vcode,mobilePhone,vCodeType)) {
				return jsonUtil.toSuccessString();
			}
		} catch (Exception e) {
				return jsonUtil.toErrorString(e.getMessage());
			}
		return jsonUtil.toErrorString("验证码错误");
	}
	
	}
