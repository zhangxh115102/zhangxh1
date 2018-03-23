package com.shsunedu.security;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shsunedu.base.api.util.EnumUtil;
import com.shsunedu.tool.EncryptUtil;
import com.shsunedu.tool.RSASignature;
import com.shsunedu.tool.EncryptUtil.KeyType;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    static final ExecutorService executor = Executors.newFixedThreadPool(4);
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证当前是否走白名单
        String redirectUrl = checkAuth(request, response, handler);
        String token = request.getHeader("token");
        if (redirectUrl == null && StringUtils.isEmpty(token)) { // 解决接口是白名单但需同时支持有token或者没token时调用的问题
            return true;
        }
        //验证签名信息
        if(redirectUrl != null && redirectUrl.indexOf("/v2/")>0){
            checkSign(request, response, handler);
        }
        String msg = null;
        try {
            if (null != token && !"".equals(token)) {
                String publicKey = securityService.getFromCache(token);
                // 判断公钥是否存在，不存在意味着过期或没有
                if (publicKey != null) {
                    String info = EncryptUtil.decrypt(KeyType.PUBLIC, publicKey, token);
                    String id = securityService.getIdFromSign(info);
                    String type = securityService.getTypeFromSign(info);
                    String tokenCache = securityService.getFromCache(id + "-" + type);
                    // 判断token是否一致
                    if (tokenCache != null && tokenCache.equals(token)) {
                        // 重置公钥过期时间
                        securityService.setToCacheTime(tokenCache, publicKey);
                        return true;
                    }
                    msg = "token error";
                } else
                    msg = "token error";
            } else
                msg = "token error";
            return onFailure(response, msg);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return onFailure(response, "token error");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    /** 签名验证 
     * @throws Exception **/
    private void checkSign(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkhwaXXud9Xi9lVPTE6xBmH6D1/mnUgBMrEVKW1FvofZY8Tf3hJJegw2Y5i2KfETxChLmWOd0gt51OoFb866vcqYafWSXeVixlVzkH01yR+N2trUosAM4UjECdbMi0xfJNIeIKGvJSJDEei/QpFVqYBSLS41mtq23/FEc91WnhtQIDAQAB";
        String sign = request.getHeader("sign");
        String signTime = request.getHeader("signTime");
        String signSource = request.getHeader("signSource");
        String content= "xueersen-"+ signTime; 
        try {
            //校验signSource
            if(!EnumUtil.SignSource.isSourceExits(signSource)){
                onCodeFailure(response, 5001, "签名验证来源不支持");
            }else if(!RSASignature.doCheck(content, sign, publicKey)){
                onCodeFailure(response, 5000, "签名验证失败");
            }
        } catch (Exception e) {
            onCodeFailure(response, 5000, "签名验证失败");
        }
    }
    
    /** 权限验证 **/
    private String checkAuth(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestUrl = request.getRequestURL().toString();
        // 记录日志
        doPrintLog(request);
        logger.info("loginInterceptor requestUrl " + requestUrl);
        // 白名单 json接口跳过验证
        if (method.isAnnotationPresent(AuthWhite.class) || method.isAnnotationPresent(ResponseBody.class)) {
            return null;
        }
        return requestUrl;
    }

    private boolean onFailure(HttpServletResponse response, String msg) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        root.put("success", 4000);
        root.put("msg", msg);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(objectMapper.writeValueAsString(root));
        pw.close();
        return false;
    }

    private boolean onCodeFailure(HttpServletResponse response,Integer code , String msg) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        root.put("success", code);
        root.put("msg", msg);

        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write(objectMapper.writeValueAsString(root));
        pw.close();
        return false;
    }
    
    private void doPrintLog(HttpServletRequest request) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String userType = request.getHeader("userType");
                    String token = request.getHeader("token");
                    String path = request.getServletPath();
                    if(StringUtils.isNotEmpty(path) && !"/test/1".endsWith(path) && !"/common/testHeart".endsWith(path)&& !"/error".endsWith(path)){
                        if (token == null) {
                            logger.info("statistics|" + path + "|" + userType + "|0|" + System.currentTimeMillis());
                        } else {
                            String publicKey = securityService.getFromCache(token);
                            String info = EncryptUtil.decrypt(KeyType.PUBLIC, publicKey, token);
                            String id = securityService.getIdFromSign(info);
                            logger.info("statistics|" + path + "|" + userType + "|" + id + "|" + System.currentTimeMillis());
                        }
                    }
                } catch (Exception e) {
                    logger.info("statistics|" + request.getServletPath() + "|0|0|" + System.currentTimeMillis());
                }
            }
        });
    }
}
