package com.iwork.core.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwork.core.web.create.CreateClass;
import com.iwork.core.web.parambinding.BastParamBinding;
import com.iwork.core.web.readcfg.SystemCfg;
import com.iwork.core.web.view.BaseViewRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class StartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public StartServlet() {
        super();
    }

    /**
     * 初始化方法初始化容器，创建所有的类
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        String requestURI = request.getRequestURI();
        String basePack = SystemCfg.getKey("context-path");
        if(basePack != null){
            boolean context = requestURI.startsWith(basePack);
            if(!context){
                response.setStatus(400);
                return;
            }
            requestURI = requestURI.substring(basePack.length());
        }
        if(requestURI == null || requestURI.length() < 1|| requestURI.trim().length() < 1){
            response.getOutputStream().println("没有对应的url： "+requestURI);
        }

        Map<Object,Method> object = null;
        try {
            // 执行目标方法
            object = executer(requestURI,request,response);
        }catch (NullPointerException e){
            e.printStackTrace();
            response.getOutputStream().println("没有对应的url： "+requestURI);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 渲染视图
        if(object == null) {
            response.getOutputStream().println("执行方法失败，或没有对应的方法");
        }

        BaseViewRenderer.executer(object,request,response);

    }



    /**
     * 执行方法
     *
     * @param url
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public Map<Object,Method> executer(String url,HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, InstantiationException, InvocationTargetException, NullPointerException, JsonProcessingException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException {
        Map<Class, Method> action = CreateClass.findAction(url);
        if(action == null){
            throw new NullPointerException();
        }
        for(Class c : action.keySet()){
            Object obj = c.newInstance();
            Method method = action.get(c);
            // 执行方法
            Object res = BastParamBinding.executer(method,request,response,obj,c);
            // 分装返回结果
            HashMap<Object, Method> result = new HashMap<Object, Method>();
            result.put(res,method);
            return result;
        }
        return null;
    }
}
