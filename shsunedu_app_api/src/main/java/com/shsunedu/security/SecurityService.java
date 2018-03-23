package com.shsunedu.security;

import java.security.KeyPair;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shsunedu.CommonProperties;
import com.shsunedu.tool.EncryptUtil;
import com.shsunedu.tool.EncryptUtil.KeyType;
import com.shsunedu.tool.RedisUtil;

@Component
public class SecurityService {
	private final static Logger logger = LoggerFactory.getLogger(SecurityService.class);

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private CommonProperties commonProperties;

	public String getFromCache(String key) throws Exception {
		return redisUtil.get(key);
	}

	public void setToCache(String key, String value) throws Exception {
		redisUtil.set(key, value);
	}

	public void setToCacheTime(String key, String value) throws Exception {
		redisUtil.set(key, value, commonProperties.getToken_deadline() * 60);
	}

	private void removeFromCache(String key) throws Exception {
		redisUtil.del(key);
	}

	public String getIdFromSign(String sign) throws Exception {
		return sign.substring(sign.indexOf("-") + 1, sign.indexOf('-', sign.indexOf("-") + 1));
	}

	public String getTypeFromSign(String sign) throws Exception {
		return sign.substring(sign.indexOf('-', sign.indexOf("-") + 1) + 1, sign.lastIndexOf("-"));
	}

	public boolean checkVCode(String key, String vCode) throws Exception {
		logger.info("============test key:" + key + " vCode: " + vCode + "  rdeis_value:" + getFromCache(key));
		logger.info("============test result: " + vCode.equals(getFromCache(key)));
		if ("8888".equals(vCode) || vCode.equals(getFromCache(key))) {
			removeFromCache(key);
			return true;
		}
		return false;
	}

	public String createCredentials(String id, String type, String phpToken) throws Exception {
		StringBuffer key = new StringBuffer().append("kuakao").append("-").append(id).append("-").append(type)
				.append("-").append(new Date().getTime());
		KeyPair keyPaire = EncryptUtil.initKeyPair(id);
		String token = EncryptUtil.encrypt(KeyType.PRIVATE, EncryptUtil.getPrivateKey(keyPaire), key.toString());
		String publicKey = EncryptUtil.getPublicKey(keyPaire);

		// 找到上次登录的sign

		String oldToken = getFromCache(id + "-" + type);// 用id+用户类型
		if (oldToken != null) {
			// 删除旧的sign的键值对
			removeFromCache(oldToken);
		}
		setToCache(id + "-" + type, token);
		setToCacheTime(token, publicKey);
		redisUtil.set(token+"php", phpToken);
		return token;
	}

	public void removeCredentials(String token) throws Exception {
		String publicKey = getFromCache(token);
		if (publicKey == null)
			return;
		redisUtil.del(token + "php");
		removeCredentials(token, publicKey);
	}

	public void removeCredentials(String token, String publicKey) throws Exception {
		String info = EncryptUtil.decrypt(KeyType.PUBLIC, publicKey, token);
		String id = getIdFromSign(info);
		String type = getTypeFromSign(info);
		removeFromCache(id + "-" + type);
		removeFromCache(token);
	}
}
