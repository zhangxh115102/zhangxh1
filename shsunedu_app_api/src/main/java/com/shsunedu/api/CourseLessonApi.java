package com.shsunedu.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.model.Course;
import com.shsunedu.base.api.service.ICourseLessonLearnService;
import com.shsunedu.base.api.service.ICourseLessonService;
import com.shsunedu.base.api.service.ICourseService;
import com.shsunedu.base.api.service.ISystemConfigService;
import com.shsunedu.base.api.util.EnumUtil.CourseType;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;

@RestController
@RequestMapping("/courseLesson")
public class CourseLessonApi extends BaseApi {
    @Resource
    ICourseLessonService courseLessonService;
    @Resource
    ICourseService courseService;
    @Resource
    ICourseLessonLearnService courseLessonLearnService;
    @Resource
    ISystemConfigService systemConfigService;

    /**
     * 方法名: liveLesson<br>
     * 描述: 直播课程课时列表<br>
     * 时间: 2017年7月6日上午10:22:49
     * @author: star
     * @param courseId
     * @return String
     */
    @AuthWhite
    @RequestMapping("/liveLesson")
    public String liveLesson(Integer courseId) {
        JsonUtil jsonUtil = new JsonUtil();
        Course course = courseService.selectByPrimaryKey(courseId);
        try {
            Integer userId = getIdFromToken();
            if (course != null) {
                jsonUtil.addToRoot("type", course.getType());
                if (course.getType().equals(CourseType.LIVE.getValue())) {
                    jsonUtil.addToRoot("course", courseLessonService.listLiveLesson(courseId, userId));
                }
            }
            jsonUtil.addToRoot("nearCourseLesson",
                    courseLessonLearnService.SelectLessonByCourseid(courseId, getIdFromToken()));
            jsonUtil.addToRoot("freeCourseNologinView", systemConfigService.freeCourseNoLoginCanStudy());
            jsonUtil.addToRoot("isBuy", courseService.isBuy(courseId, getIdFromToken()));
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }

    /**
     * 方法名: normalLesson<br>
     * 描述: 录播课程课时列表<br>
     * 时间: 2017年7月6日上午10:23:02
     * @author: star
     * @param courseId
     * @return String
     */
    @AuthWhite
    @RequestMapping("/normalLesson")
    public String normalLesson(Integer courseId) {
        JsonUtil jsonUtil = new JsonUtil();
        Course course = courseService.selectByPrimaryKey(courseId);
        try {
            Integer userId = getIdFromToken();
            if (course != null) {
                jsonUtil.addToRoot("type", course.getType());
                if (course.getType().equals(CourseType.NORMAL.getValue())) {
                    jsonUtil.addToRoot("course", courseLessonService.listVideoLesson(courseId, userId));
                }
            }
            jsonUtil.addToRoot("nearCourseLesson",
                    courseLessonLearnService.SelectLessonByCourseid(courseId, getIdFromToken()));
            jsonUtil.addToRoot("freeCourseNologinView", systemConfigService.freeCourseNoLoginCanStudy());
            jsonUtil.addToRoot("isBuy", courseService.isBuy(courseId, getIdFromToken()));
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }
}
