package com.shsunedu.api;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shsunedu.base.api.model.ClassRoom;
import com.shsunedu.base.api.model.ClassroomBagClassroom;
import com.shsunedu.base.api.service.ICategoryService;
import com.shsunedu.base.api.service.IClassRoomService;
import com.shsunedu.base.api.vo.ClassRoomVo;
import com.shsunedu.security.AuthWhite;
import com.shsunedu.tool.JsonUtil;

/**
 * 类名: ClassRoomApi<br>
 * 描述: 单科全科<br>
 * 时间: 2017年6月15日 下午3:32:34 
 * @author: star 
*/
@RestController
@RequestMapping("/classRoom")
public class ClassRoomApi extends BaseApi {
    @Resource
    IClassRoomService classRoomService;
    @Resource
    ICategoryService categoryService;

    /**
     * 方法名: recommend<br>
     * 描述: 推荐课程<br>
     * 时间: 2017年6月14日上午11:14:00
     * @author: star
     * @return String
     */
    @AuthWhite
    @RequestMapping("/recommend")
    public String recommend(ClassRoomVo classRoomVo) {
        JsonUtil jsonUtil = new JsonUtil();
        try {
            jsonUtil.addToRoot("listCourses", classRoomService.listAppPageByRecommend(classRoomVo).getResult());
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }

    /**
     * 方法名: courseDetail<br>
     * 描述: 课程详情<br>
     * 时间: 2017年6月15日下午3:37:34
     * @author: star
     * @param classRoomVo
     * @return String
     */
    @AuthWhite
    @RequestMapping("/classRoomDetail")
    public String courseDetail(ClassRoomVo classRoomVo) {
        JsonUtil jsonUtil = new JsonUtil();
        try {
            jsonUtil.addToRoot("listCourses", classRoomService.listAppPageByRecommend(classRoomVo).getResult());
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }

    /**
     * 方法名: classroomByclassroomBag<br>
     * 描述: 通过全科ID查询所有分类<br>
     * @param ClassroomBagClassroom
     * @return String
     */
    @AuthWhite
    @RequestMapping("/categoryByclassroomBag")
    public String categoryByclassroomBag(ClassroomBagClassroom ClassroomBagClassroom) {
        JsonUtil jsonUtil = new JsonUtil();
        try {
            jsonUtil.addToRoot("listCourses",
                    categoryService.listAppPageByClassroomBag(ClassroomBagClassroom, getIdFromToken()));
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            e.printStackTrace();
            return jsonUtil.toErrorString(e.getMessage());
        }
    }

    /**
     * 获取单科\全科介绍
     * @param order
     * @return
     */
    @AuthWhite
    @RequestMapping("classRoomIntroduce")
    public String classRoomIntroduce(ClassRoomVo classRoomVo) {

        JsonUtil jsonUtil = new JsonUtil();
        try {
            classRoomVo.setUserid(getIdFromTokenForSun());
            jsonUtil.addToRoot("introduce", classRoomService.classRoomIntroduce(classRoomVo));
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return jsonUtil.toErrorString(e.getMessage());
        }

    }

    /**
     * 获取单科\全科 分享地址
     * @param order
     * @return
     */
    @AuthWhite
    @RequestMapping("shareURL")
    public String shareURL(ClassRoomVo classRoomVo) {
        // 读取配置文件
        String envTarget = System.getProperty("envTarget");
        String config = "config/system-" + envTarget + ".properties";
        Properties properties = new Properties();
        InputStream in = ClassRoomApi.class.getClassLoader().getResourceAsStream(config);
        JsonUtil jsonUtil = new JsonUtil();
        try {
            properties.load(in);
            // 获取配置文件中配置的 分享地址前缀， 拼接 单|全科类型 拼接 id
            String prefix = properties.getProperty("shareURL_prefix") + classRoomVo.getTargetType() + "/"
                    + classRoomVo.getId();
            classRoomVo.setUserid(getIdFromToken());
            // 获取 对象 ， 得到 标题
            ClassRoom vo = classRoomService.selectByPrimaryKey(classRoomVo.getId());
            jsonUtil.addToRoot("url", prefix);
            jsonUtil.addToRoot("title", vo != null ? vo.getTitle() : "");
            jsonUtil.addToRoot("content", "我正在学习「" + (vo != null ? vo.getTitle() : "") + "」，收获巨大哦，一起来学习吧！");
            System.out.println(jsonUtil.toSuccessString());
            return jsonUtil.toSuccessString();
        } catch (Exception e) {
            return jsonUtil.toErrorString(e.getMessage());
        }

    }
}
