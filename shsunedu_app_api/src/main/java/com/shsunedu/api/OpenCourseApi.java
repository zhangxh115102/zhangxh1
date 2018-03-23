package com.shsunedu.api;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.service.IOpenCourseService;
import com.shsunedu.base.api.util.EnumUtil.OpenCourseStatus;
import com.shsunedu.base.api.util.EnumUtil.OpenCourseType;
import com.shsunedu.base.api.vo.OpenCourseVo;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;

/**
 * 类名: OpenCourseApi<br>
 * 描述: 公开课<br>
 * 时间: 2017年6月15日 下午3:32:18 
 * @author: star 
*/
@RestController
@RequestMapping("/openCourse")
public class OpenCourseApi extends BaseApi {
    @Resource
    IOpenCourseService openCourseService;

    /**
     * 方法名: listOpenCourse<br>
     * 描述: 公开课<br>
     * 时间: 2017年6月28日下午5:59:23
     * @author: star
     * @param openCoursevo
     * @return String
     */
    @AuthWhite
    @RequestMapping("/listOpenCourse")
    public String listOpenCourse(OpenCourseVo openCoursevo) {
        Date nowDate = new Date();
        JsonUtil jsonUtil = new JsonUtil();
        try {
            @SuppressWarnings("unchecked")
            List<OpenCourseVo> list = (List<OpenCourseVo>) openCourseService.listAppPage(openCoursevo).getResult();
            for (OpenCourseVo openCourseVo2 : list) {
                if (openCourseVo2 != null && StringUtils.isNotBlank(openCourseVo2.getType())) {
                    if (openCourseVo2.getType().equals(OpenCourseType.LIVEOPEN.getValue())) {
                        if (Long.valueOf(openCourseVo2.getEndTime()) * 1000 < nowDate.getTime()
                                && StringUtils.isNotBlank(openCourseVo2.getMedianame())) {
                            openCourseVo2.setOpenCourseStatus(OpenCourseStatus.PLAYBACK.getValue());
                        }
                        if (Long.valueOf(openCourseVo2.getEndTime()) * 1000 < nowDate.getTime()
                                && StringUtils.isNotBlank(openCourseVo2.getMediaUri())
                                && StringUtils.isBlank(openCourseVo2.getMedianame())) {
                            openCourseVo2.setOpenCourseStatus(OpenCourseStatus.ISEND.getValue());
                        }
                        if (Long.valueOf(openCourseVo2.getStartTime()) * 1000 - (30 * 60 * 1000) > nowDate.getTime()) {
                            openCourseVo2.setOpenCourseStatus(OpenCourseStatus.WILLBEGIN.getValue());
                        }
                        if (Long.valueOf(openCourseVo2.getStartTime()) * 1000 - (30 * 60 * 1000) < nowDate.getTime()
                                && Long.valueOf(openCourseVo2.getEndTime()) * 1000 > nowDate.getTime()) {
                            openCourseVo2.setOpenCourseStatus(OpenCourseStatus.INLESSON.getValue());
                        }
                    }
                }

            }
            jsonUtil.addToRoot("listCourses", list);
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }
}
