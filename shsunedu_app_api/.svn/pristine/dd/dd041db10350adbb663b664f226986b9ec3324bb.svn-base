package com.shsunedu.api;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.model.CourseFavorite;
import com.shsunedu.base.api.service.ICourseFavoriteService;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;


/**
 * 类名: CourseFavoriteApi<br>
 * 描述: 用户的收藏<br>
 * 时间: 2017年6月21日下午12:30:16
 * @author: zhangxh
 * @return String
 */
@RestController
@RequestMapping("myHouse")
public class CourseFavoriteApi extends BaseApi {
	@Resource
	ICourseFavoriteService courseFavoriteService;
	
	/**
	 * 方法名: myHouse<br>
	 * 描述: 用户收藏商品，收藏成功后，并把此商品被收藏的次数查询出来返回去<br>
	 * 时间: 2017年6月21日上午12:30:16
	 * @author: zhangxh
	 * @return String
	 */
	@AuthWhite
	@RequestMapping("/house")
	public String myHouse(int key,String type){
		JsonUtil jsonUtil = new JsonUtil();
		try {
			CourseFavorite c = new CourseFavorite();
			c.setUserid(getIdFromToken());
			c.setCourseid(key);
			// int i=System.currentTimeMillis()/3600;
			int date = (int)(new Date().getTime()/1000);
			c.setCreatedtime(date);
			c.setType(type);
			 jsonUtil.addToRoot("key",courseFavoriteService.myHouse(c) );
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			 e.printStackTrace();
	          return jsonUtil.toErrorString(e.getMessage());
		}
		
	}
	
	/**
	 * 方法名: deleteHouse<br>
	 * 描述: 取消收藏的商品<br>
	 * 时间: 2017年6月21日下午13:01:16
	 * @author: zhangxh
	 * @return String
	 */
	@AuthWhite
	@RequestMapping("/deleteHouse")
	public String deleteHouse(int key){
		JsonUtil jsonUtil = new JsonUtil();
		try {
			 jsonUtil.addToRoot("key",courseFavoriteService.deleteByPrimaryKey(key) );
			 return jsonUtil.toSuccessString();
		} catch (Exception e) {
			 e.printStackTrace();
	          return jsonUtil.toErrorString(e.getMessage());
		}
		
	}
	
	/**
	 * 方法名: listCourseHouse<br>
	 * 描述: 收藏的商品列表<br>
	 * 时间: 2017年6月22日上午08:56:16
	 * @author: zhangxh
	 * @return String
	 */
	@AuthWhite
	@RequestMapping("/myCourseHouse")
	public String listCourseHouse(){
		JsonUtil jsonUtil = new JsonUtil();
		try {
			CourseFavorite c = new CourseFavorite();
			c.setUserid(getIdFromToken());
			 jsonUtil.addToRoot("key", courseFavoriteService.listCourseHouse(c).getResult());
			 return jsonUtil.toSuccessString();
		} catch (Exception e) {
			 e.printStackTrace();
	          return jsonUtil.toErrorString(e.getMessage());
		}
		
	}
	
}
