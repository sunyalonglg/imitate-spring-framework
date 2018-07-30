package com.iwork.core.web.view;

import com.alibaba.fastjson.JSON;
import com.iwork.core.annotation.ResponseBody;
import com.iwork.core.cons.SysCons;
import com.iwork.core.web.readcfg.SystemCfg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * jsp基础的视图渲染解析器
 *
 * @author sunyalong
 * @version 1.0, 2018-7-20 23:38:09
 */
public class BaseViewRenderer {

    /**
     * 渲染视图
     *
     * @param object
     */
    public static void executer(Map<Object, Method> object, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        for(Object o :object.keySet()){
            Method method = object.get(o);
            boolean annotationPresent = method.isAnnotationPresent(ResponseBody.class);
            // 如果该方法上表示有@ResponseBody的注解
            if(annotationPresent){
                String json = JSON.toJSONString(o);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("此次请求返回Json数据为: "+json);
                PrintWriter out = response.getWriter();
                out.write(json);
                out.flush();
                out.close();
            }
            // 判断返回的是不是String
            if(o instanceof String){
                String data  = (String) o;
                // 判断是不是转发
                String basePack = SystemCfg.getKey("context-path");
                if(data.startsWith(SysCons.FORWARD)){
                    request.getRequestDispatcher(basePack + "/" +data.substring(SysCons.FORWARD.length())).forward(request,response);
                    return;
                }
                // 判断是不是重定向
                if(data.startsWith(SysCons.REDIRECT)){
                    response.sendRedirect(basePack + "/" + data.substring(SysCons.REDIRECT.length()));
                    return;
                }

                // 确认此次请求为转发到jsp
                boolean isJsp = Boolean.parseBoolean(SystemCfg.getKey("web-jsp"));
                if(isJsp){
                    String profix = SystemCfg.getKey("web-profix");
                    String suffix = SystemCfg.getKey("web-suffix");
                    request.getRequestDispatcher(profix + "/" + data + suffix).forward(request,response);
                }
            }

        }
    }
}
