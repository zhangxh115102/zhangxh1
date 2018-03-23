package com.shsunedu.tool;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;

public class EncryptUtil {
	public static enum Algorithm {
		MD5, SHA1
	}

	public static enum KeyType {
		PUBLIC, PRIVATE
	}

	private static final String SECRET = "KUAKAO";
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	// /**
	// * Base64加密
	// *
	// * @param 字符串
	// * @return
	// * @throws Exception
	// */
	// public static String encryptByBase64(byte[] key) throws Exception {
	// byte[] bytes = Base64.encodeBase64(info.getBytes("UTF-8"));
	// return new String(bytes, "UTF-8");
	// }

	// /**
	// * Base64解密
	// *
	// * @param 加密字符串
	// * @return
	// * @throws Exception
	// */
	// public static String decode(String secretStr) throws Exception {
	// byte[] bytes = Base64.decodeBase64(secretStr);
	// return new String(bytes, "UTF-8");
	// }

	/**
	 * 加密
	 * 
	 * @param 加密类型
	 * @param 被加密数据组
	 * @return
	 */
	public static String encrypt(Algorithm algorithm, Map<String, Object> map) throws Exception {
		try {
			StringBuffer regionSign = new StringBuffer();
			for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
				String mapKey = iterator.next();
				regionSign.append("&").append(mapKey).append("=").append(map.get(mapKey));
			}

			MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toString());
			messageDigest.update(regionSign.substring(1).getBytes());
			return bytesToHexStr(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("加密失败");
		}
	}

	/**
	 * 创建私钥和公钥
	 * 
	 * @param 登录名
	 * @return
	 */
	public static KeyPair initKeyPair(String key) throws Exception {

		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(key.getBytes("UTF-8")); // 初始化随机产生器
			keygen.initialize(512, secrand);

			return keygen.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("生成密钥对失败");
		}
	}

	/**
	 * 加密
	 * 
	 * @param 加密数据
	 * @param 密钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(KeyType keyType, String key, String data) throws Exception {
		// 解密钥匙
		byte[] keyBytes = decryptByBase64(key);
		byte[] dataBytes = data.getBytes("UTF-8");
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		Key keyObject = null;
		switch (keyType) {
		case PUBLIC:
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
			keyObject = keyFactory.generatePublic(x509EncodedKeySpec);
			break;
		case PRIVATE:
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			keyObject = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			break;
		}

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, keyObject);

		return encryptByBase64(cipher.doFinal(dataBytes));
	}

	/**
	 * 解密
	 * 
	 * @param 加密数据
	 * @param 密钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(KeyType keyType, String key, String data) throws Exception {
		// 解密钥匙
		byte[] keyBytes = decryptByBase64(key);
		byte[] dataBytes = decryptByBase64(data);
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		Key keyObject = null;
		switch (keyType) {
		case PUBLIC:
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
			keyObject = keyFactory.generatePublic(x509EncodedKeySpec);
			break;
		case PRIVATE:
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			keyObject = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			break;
		}

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, keyObject);

		return new String(cipher.doFinal(dataBytes), "UTF-8");
	}

	/**
	 * 生成数字签名
	 * 
	 * @param 加密数据
	 * @param 私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(String privateKey) throws Exception {
		// 解密私钥
		byte[] privateKeyBytes = decryptByBase64(privateKey);
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		// 取私钥匙对象
		PrivateKey privateKeyObject = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKeyObject);
		signature.update(SECRET.getBytes("UTF-8"));

		return encryptByBase64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param 加密数据
	 * @param 公钥
	 * @param 数字签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySign(String publicKey, String sign) throws Exception {
		// 解密公钥
		byte[] keyBytes = decryptByBase64(publicKey);
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		// 指定加密算法
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		// 取公钥匙对象
		PublicKey publicKeyObjecy = keyFactory.generatePublic(x509EncodedKeySpec);

		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicKeyObjecy);
		signature.update(SECRET.getBytes("UTF-8"));
		// 验证签名是否正常
		return signature.verify(decryptByBase64(sign));
	}

	/**
	 * 从钥匙对里得到私钥
	 * 
	 * @param 钥匙对
	 * @return 私钥
	 * @throws Exception
	 */
	public static String getPrivateKey(KeyPair keyPair) throws Exception {
		PrivateKey privatekey = keyPair.getPrivate();
		return encryptByBase64(privatekey.getEncoded());
	}

	/**
	 * 从钥匙对里得到公钥
	 * 
	 * @param 钥匙对
	 * @return 公钥
	 * @throws Exception
	 */
	public static String getPublicKey(KeyPair keyPair) throws Exception {
		PublicKey publickey = keyPair.getPublic();
		return encryptByBase64(publickey.getEncoded());
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static String encryptByBase64(byte[] key) throws Exception {
		return new String(Base64.encodeBase64(key), "UTF-8");
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] decryptByBase64(String key) throws Exception {
		return Base64.decodeBase64(key);
	}

	/**
	 * 字节数组转换字符串
	 * 
	 * @param 字节数组
	 * @return
	 */
	private static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(HEX_DIGITS[(bcd[i] >>> 4) & 0x0f]);
			s.append(HEX_DIGITS[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	public static void main(String[] args) throws Exception {
		// Map<String, Object> params1 = new HashMap<String, Object>();
		// params1.put("dddd", "dddd");
		// params1.put("aaaa", "aaaa");
		//
		// Map<String, Object> params2 = new HashMap<String, Object>();
		// params2.put("aaaa", "aaaa");
		// params2.put("dddd", "dddd");
		//
		// System.out.println("111111 MD5 :" +
		// EncryptUtil.encrypt(Algorithm.MD5, params1));
		// System.out.println("111111 MD5 :" +
		// EncryptUtil.encrypt(Algorithm.MD5, params2));
		// System.out.println("111111 SHA1 :" +
		// EncryptUtil.encrypt(Algorithm.SHA1, params1));
		// System.out.println("111111 SHA1 :" +
		// EncryptUtil.encrypt(Algorithm.SHA1, params2));

		String data = "kuakao-123456789-" + new Date().getTime();
		KeyPair keyPaire = initKeyPair("123456789");

		String encryptData = encrypt(KeyType.PRIVATE, getPrivateKey(keyPaire), data);
		System.out.println(encryptData);
		String decryptData = decrypt(KeyType.PUBLIC, getPublicKey(keyPaire), encryptData);
		System.out.println(decryptData);
	}
}
