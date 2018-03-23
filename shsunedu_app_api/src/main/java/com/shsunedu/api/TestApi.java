package com.shsunedu.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.util.EnumUtil.UserType;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.security.SecurityService;
import com.shsunedu.tool.JsonUtil;



@RestController
@RequestMapping("/test")
public class TestApi extends BaseApi{
	
	@Autowired
	private SecurityService securityService;
	
    public String test(){
    	JsonUtil jsonUtil = new JsonUtil();
        try {
        	jsonUtil.addToRoot("userInfo", "testUser");
            logger.info("------------------test isLogin");
          return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @AuthWhite
    @RequestMapping("/login")
    public String login(String userName,String password){
    	JsonUtil jsonUtil = new JsonUtil();
        try {
        	if("testUser".equals(userName)&&"testPass".equals(password)){
        		jsonUtil.addToRoot("token",securityService.createCredentials(String.valueOf(1L), UserType.STUDENT.getValue(),""));
        	}
            logger.info("------------------test login");
          return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
