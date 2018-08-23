package com.iwork.core.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * servlet的基本工具类,也就是关于request,和response的工具类
 *
 * @author sunyalong
 * @version 1.0, 2018-7-23 21:03:21
 */
public class ServletUtils {

    /**
     * 获得请求的json字符串
     *
     * @param request
     * @param response
     * @return
     */
    public static String getParamJson(HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        String json;
        if (contentType != null && contentType.equals("application/json")) {
            json = ServletUtils.readJson(request, response);
        } else {
            Map<String, Object> parameterMap = request.getParameterMap();
            json = JSONObject.toJSONString(parameterMap).replace("[", "").replace("]", "");
        }
        return json;
    }

    /**
     * 读取application/json请求格式个json字符串
     *
     * @param request
     * @param response
     * @return
     */
    public static String readJson(HttpServletRequest request, HttpServletResponse response){
        String param = null;
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
            param = jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }



}
