package com.shsunedu.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.tool.JsonUtil;

@RestController
@RequestMapping("/common")
public class CommonApi extends BaseApi{
	
	@RequestMapping("/testHeart")
	public String testHeart() {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonUtil.toErrorString(e.getMessage());
		}
	}
}
