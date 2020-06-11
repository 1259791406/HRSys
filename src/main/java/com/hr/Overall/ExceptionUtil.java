package com.hr.Overall;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.hr.Util.Util;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获
 */

public class ExceptionUtil implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> map = new HashMap<>();
        map.put("time", Util.GetTime());
        map.put("data", "程序发生异常情况！请查看 Mes 信息！");
        if (e instanceof NullPointerException) {
            map.put("code", 500);
            map.put("mes", "空指针异常，请检查数据是否存在！");
        } else if (e instanceof IndexOutOfBoundsException) {
            map.put("code", 500);
            map.put("mes", "数组下标越界异常！");
        } else if (e instanceof SQLException) {
            map.put("code", 500);
            map.put("mes", "SQL语句发生错误，请检查！");
        } else if (e instanceof IllegalArgumentException) {
            map.put("code", 500);
            map.put("mes", "方法中发现无效字符串，请检查！");
        } else if (e instanceof ServletException) {
            map.put("code", 500);
            map.put("mes", e.getMessage());
        } else if (e instanceof PageException) {
            map.put("code", 500);
            map.put("mes", e.getMessage());
        } else {
            map.put("code", 500);
            map.put("mes", e.getMessage());
        }
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        ErrorFileUtil.Ex(e);
        view.setAttributesMap(map);
        modelAndView.setView(view);
        return modelAndView;
    }
}