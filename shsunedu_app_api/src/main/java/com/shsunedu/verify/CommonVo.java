package com.shsunedu.verify;


import com.shsunedu.base.api.exception.UserException;
import com.shsunedu.tool.RegularUtil;

public class CommonVo {
	public static void checkIncreaseId(Integer schoolId, boolean isRequired) throws UserException {
		if (isRequired) {
			if (schoolId == null)
				throw new UserException("ID不能为空");
			if (schoolId == 0)
				throw new UserException("ID非法");
		} else {
			if (schoolId != null && schoolId == 0)
				throw new UserException("ID非法");
		}
	}

	public static void checkMobilePhone(String phone, boolean isRequired) throws UserException {
		if (isRequired) {
			if (phone == null || "".equals(phone))
				throw new UserException("手机号不能为空");
			try {
				if (!RegularUtil.checkPhone(phone))
					throw new UserException("手机号非法");
			} catch (Exception e) {
				throw new UserException("手机号非法");
			}
		} else {
			try {
				if (phone != null && !RegularUtil.checkPhone(phone))
					throw new UserException("手机号非法");
			} catch (Exception e) {
				throw new UserException("手机号非法");
			}
		}
	}

	public static void checkVCode(String vCode, boolean isRequired) throws UserException  {
		if (isRequired) {
			if (vCode == null || "".equals(vCode))
				throw new UserException("验证码不能为空");
		} else {
			try {
				if (vCode != null && !RegularUtil.checkVCode(vCode))
					throw new UserException("验证码非法");
			} catch (Exception e) {
				throw new UserException("验证码非法");
			}
		}
	}
	
	public static void checkUserId(Long userId, boolean isRequired) throws UserException {
		if (isRequired) {
			if (userId == null)
				throw new UserException("推荐人ID不能为空");
			if (userId == 0)
				throw new UserException("推荐人ID非法");
		} else {
			if (userId != null && userId == 0)
				throw new UserException("推荐人ID非法");
		}
	}
	
	public static void checkOpenId(String openId, boolean isRequired) throws Exception {
		if (isRequired) {
			if (openId == null || "".equals(openId))
				throw new Exception("appId不能为空");
		}
	}
	
	public static void checkAccessToken(String accessToken, boolean isRequired) throws Exception {
		if (isRequired) {
			if (accessToken == null || "".equals(accessToken))
				throw new Exception("accessToken不能为空");
		}
	}
	
	public static void checkRefreshToken(String refreshToken, boolean isRequired) throws Exception {
		if (isRequired) {
			if (refreshToken == null || "".equals(refreshToken))
				throw new Exception("refresh不能为空");
		}
	}
	
	public static void checkCurrentPage(Integer currentPage, boolean isRequired) throws Exception {
		if (isRequired) {
			if (currentPage == null){
				throw new Exception("查询页码不能为空");
			}
			if(currentPage<=0){
				throw new Exception("查询页码不能小于0");
			}
		}
	}
	
	public static void checkPageCount(Integer pageCount, boolean isRequired) throws Exception {
		if (isRequired) {
			if (pageCount == null){
				throw new Exception("每页显示数量不能为空");
			}
			if(pageCount<=0){
				throw new Exception("每页显示数量不能小于0");
			}
		}
	}
}
