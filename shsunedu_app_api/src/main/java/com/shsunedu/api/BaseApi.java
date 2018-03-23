package com.shsunedu.api;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shsunedu.security.SecurityService;
import com.shsunedu.tool.EncryptUtil;
import com.shsunedu.tool.EncryptUtil.KeyType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

public class BaseApi {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecurityService securityService;

    public Integer getIdFromToken() throws Exception {
        String token = getToken();
        if (StringUtils.isBlank(token)) {
            return 0;
        }
        String publicKey = securityService.getFromCache(token);
        String info = EncryptUtil.decrypt(KeyType.PUBLIC, publicKey, token);
        String id = securityService.getIdFromSign(info);
        Integer userId = Integer.valueOf(id);
        if (null == userId || 0 == userId) {
            return 0;
        }
        return userId;
    }

    public Integer getIdFromTokenForSun() throws Exception {
        String token = getToken();
        if (StringUtils.isBlank(token)) {
            return 0;
        }
        String publicKey = securityService.getFromCache(token);
        String info = EncryptUtil.decrypt(KeyType.PUBLIC, publicKey, token);
        String id = securityService.getIdFromSign(info);
        Integer userId = Integer.valueOf(id);
        if (null == userId || 0 == userId) {
            return 0;
        }
        return userId;
    }

    public String getTypeFromToken() throws Exception {
        String token = getToken();
        if (token == null) {
            throw new Exception("token null");
        }
        String publicKey = securityService.getFromCache(token);
        String info = EncryptUtil.decrypt(KeyType.PUBLIC, publicKey, token);
        return securityService.getTypeFromSign(info);
    }

    public String getToken() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request.getHeader("token");
    }

    /**
     * 方法名: getDeviceId<br>
     * 描述: 获取用户机型标识<br>
     * 时间: 2017年6月23日上午10:45:32
     * @author: star
     * @return
     * @throws Exception String
     */
    public String getDeviceId() throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request.getHeader("deviceId");
    }

    public String getCommonConfig(String key) {
        ResourceBundle res = ResourceBundle.getBundle("config/common");
        return res.getString(key);

    }

    /**
     * 方法名: getPhoneType<br>
     * 描述: 获取用户操作系统<br>
     * 时间: 2017年6月23日上午10:44:26
     * @author: star
     * @return String
     */
    public String getPhoneType() {
        UserAgent userAgent = UserAgent.parseUserAgentString(getRequest().getHeader("User-Agent"));
        OperatingSystem oSystem = userAgent.getOperatingSystem();
        String getPhoneSysteName = oSystem.getName();
        return getPhoneSysteName;

    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return request;
    }
}
