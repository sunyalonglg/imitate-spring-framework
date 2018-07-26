package com.iwork.core.web.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 每一次請求的上下文對象
 *
 * @author sunyalong
 * @version 1.0, 2018-7-20 23:28:33
 */
public class RequestContext {

    private HttpServletRequest request;

    private HttpServletResponse response;

    public RequestContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession(){
        return request.getSession();
    }

    /**
     * 将当前属性添加到request中
     *
     * @param name
     * @param value
     */
    public void add(String name, Object value){
        request.setAttribute(name,value);
    }
}
