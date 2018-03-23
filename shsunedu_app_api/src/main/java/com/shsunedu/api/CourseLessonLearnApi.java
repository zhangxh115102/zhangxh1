package com.shsunedu.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.service.ICourseLessonLearnService;
import com.shsunedu.base.api.vo.CourseLessonVo;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;

@RestController
@RequestMapping("/courseLessonLearn")
public class CourseLessonLearnApi extends BaseApi {
	@Resource
	ICourseLessonLearnService CourseLessonLearnService;

	/**
	 * 类名: nearCourseLesson<br>
	 * 描述: 用户对应课程最近观看的课时<br>
	 * 时间: 2017年6月22日下午
	 * 
	 * @param courseid
	 * @return String
	 */
	@AuthWhite
//	@RequestMapping("/nearCourseLesson")
	public String nearCourseLesson(Integer courseid) {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			jsonUtil.addToRoot("nearCourseLesson",
					CourseLessonLearnService.SelectLessonByCourseid(courseid, getIdFromToken()));
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonUtil.toErrorString(e.getMessage());
		}

	}

	/**
	 * 更新用户课时学习时间
	 * @param userid 用户id
	 * @param courseId 课程ID
	 * @param lessonId 课时ID
	 * @param learnTime 学习时间
	 * @return
	 */
	@RequestMapping("/updCourseLessonLearn")
	public String updCourseLessonLearn(CourseLessonVo vo) {
		JsonUtil jsonUtil = new JsonUtil();
		try {
			vo.setUserid(getIdFromTokenForSun());
			jsonUtil.addToRoot("updCourseLessonLearn1",CourseLessonLearnService.updCourseLessonLearn(vo));
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonUtil.toErrorString(e.getMessage());
		}
	}
}
