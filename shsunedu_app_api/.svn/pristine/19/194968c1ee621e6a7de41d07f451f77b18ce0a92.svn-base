package com.shsunedu.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.service.ICourseLessonService;
import com.shsunedu.base.api.service.IOrderService;
import com.shsunedu.base.api.vo.ClassRoomMemberVo;
import com.shsunedu.tool.JsonUtil;

/**
 * 类名: OrderApi<br>
 * 描述: 我的课程订单<br>
 * 时间: 2017年6月15日 下午3:36:33 
 * @author: star 
*/
@RestController
@RequestMapping("orders")
public class OrderApi extends BaseApi {
	@Resource
	IOrderService orderService;
	@Resource
	ICourseLessonService courseLessonService;

	/**
	 * 方法名: myOrders<br>
	 * 描述: 我的课程<br>
	 * 时间: 2017年7月3日上午10:00:43
	 * @author: star
	 * @param classRoomMemberVo
	 * @return String
	 */
	@RequestMapping("myorders")
	public String myOrders(ClassRoomMemberVo classRoomMemberVo) {
		JsonUtil jsonUtil = new JsonUtil();
		try {
		    classRoomMemberVo.setUserid(getIdFromToken());
			jsonUtil.addToRoot("listCourses", orderService.listAppPage(classRoomMemberVo).getResult());
			jsonUtil.addToRoot("live", courseLessonService.onceCourseLessonLive(getIdFromToken()));
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
		    logger.error(e.getMessage());
			return jsonUtil.toErrorString(e.getMessage());
		}
		
	}
	 
}
