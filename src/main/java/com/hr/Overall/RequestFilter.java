package com.hr.Overall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.hr.dao.LogDao;
import com.hr.dao.UtilDao;
import com.hr.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;

import java.io.PrintWriter;
import java.util.*;

public class RequestFilter extends HandlerInterceptorAdapter {

    @Autowired
    private LogDao logDao;

    @Autowired
    private UtilDao utilDao;

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map<String, String[]> ss = request.getParameterMap();
        Map<String, Object> map = new HashMap<>();
        for (String name : ss.keySet()) {
            String[] values = ss.get(name);
            map.put(name, Arrays.toString(values));
        }
        logDao.Add(new Log(request.getRemoteHost(), url, map.toString(), request.getMethod(), response.getStatus() + ""));
        if (Global.isLogin) {
            List<String> list = utilDao.UrlList();
            if (list.contains(url)) {
                return true;
            }
            String token = request.getHeader("token");
            if (token == null || token == "") {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer = response.getWriter();
                JSONObject json = new JSONObject();
                json.put("code", 302);
                json.put("url", Global.Url);
                json.put("mes", "请携带Token进行数据请求！");
                writer.write(json.toJSONString());
                return false;
            } else {
                if (TokenIsTrue.isTrue(token)) {
                    System.out.println(response.getStatus());
                    return true;
                } else {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter writer = response.getWriter();
                    JSONObject json = new JSONObject();
                    json.put("code", 302);
                    json.put("mes", "请携带正确得Token，否则不允许请求！");
                    json.put("url", Global.Url);
                    writer.write(json.toJSONString());
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。
     *   postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之 后， 也就是在Controller的方法调用之后执行。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle方法  " + "开始执行了！");
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 这个方法的主要作用是用于清理资源的，
     *    
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        System.out.println("afterCompletion()" + "方法开始执行了！");
    }
}