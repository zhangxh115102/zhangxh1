package com.shsunedu.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.model.Category;
import com.shsunedu.base.api.model.ClassRoom;
import com.shsunedu.base.api.model.Course;
import com.shsunedu.base.api.service.ICourseService;
import com.shsunedu.base.api.vo.CourseLessonCountVo;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;

@RestController
@RequestMapping("/course")
public class CourseApi extends BaseApi {
    @Resource
    ICourseService courseService;

    /**
     * 方法名: recommend<br>
     * 描述: 推荐课程<br>
     * 时间: 2017年6月14日上午11:14:00
     * @author: star
     * @return String
     */
    @AuthWhite
    @RequestMapping("/recommend")
    public String recommend() {
        JsonUtil jsonUtil = new JsonUtil();
        try {
            Course course = new Course();
            course.setRecommended((byte) 1);
            jsonUtil.addToRoot("listCourses", courseService.listAppPage(course).getResult());
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }

    /**
     * 方法名: SelectCourseByCategory<br>
     * 描述: 通过分类id与是否购买查询模块信息<br>
     * 时间: 2017年6月21日
     * @return String
     */
    @AuthWhite
    @RequestMapping("/SelectCourseByCategory")
    public String SelectCourseByCategory(Category category, boolean isbuy) {
        JsonUtil jsonUtil = new JsonUtil();
        try {
            @SuppressWarnings("unchecked")
            List<CourseLessonCountVo> lists = (List<CourseLessonCountVo>) courseService
                    .listCoursePage(category, getIdFromToken(), isbuy).getResult();
            for (CourseLessonCountVo courseLessonCountVo : lists) {
                courseLessonCountVo.setHaveFreeLesson(courseService.isHaveFreeLesson(courseLessonCountVo.getId()));
            }
            jsonUtil.addToRoot("listCourses", lists);
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }

    /**
	 * 方法名: SelectCourseByClassroom<br>
	 * 描述: 单科id<br>
	 * 时间: 2017年6月21日
	 * @return String
	 */
	@AuthWhite
	@RequestMapping("SelectCourseByClassroom")
	public String SelectCourseByClassroom(ClassRoom classroom){
		JsonUtil jsonUtil = new JsonUtil();
		try {
		    @SuppressWarnings("unchecked")
            List<CourseLessonCountVo> lists = (List<CourseLessonCountVo>) courseService.listCourseByClassroom(classroom,getIdFromTokenForSun()).getResult();
			for (CourseLessonCountVo courseLessonCountVo : lists) {
			    if (courseLessonCountVo.getId()!=null) {
			        courseLessonCountVo.setHaveFreeLesson(courseService.isHaveFreeLesson(courseLessonCountVo.getId()));
                }
            }
		    jsonUtil.addToRoot("listCourses", lists);
			return jsonUtil.toSuccessString();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonUtil.toErrorString(e.getMessage());
		}
	}
}
