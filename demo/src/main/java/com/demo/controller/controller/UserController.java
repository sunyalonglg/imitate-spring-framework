package com.demo.controller.controller;

import com.demo.controller.bean.User;
import com.demo.controller.service.UserService;
import com.iwork.core.annotation.Autowwired;
import com.iwork.core.annotation.Controller;
import com.iwork.core.annotation.RequestMapping;
import com.iwork.core.annotation.ResponseBody;
import com.iwork.core.web.base.ModelActionSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("user")
public class UserController extends ModelActionSupport<User> {



    @Autowwired
    private UserService userService;

    @RequestMapping(value = "info2")
    public String getUserInfo3() {
        userService.updateUser(model);
        return "test";
    }

    @RequestMapping(value = "info")
    public HashMap<Object, Object> getUserInfo(){
        userService.updateMap(model);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","返回userinfo2方法");
        return objectObjectHashMap;
    }

    @RequestMapping(value = "info3")
    public HashMap<Object, Object> getUserInfo2(){
       userService.updateString(model.getYear());
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","返回userinfo2方法");
        return objectObjectHashMap;
    }

    @RequestMapping(value = "info5")
    public String getUserInfo5() {
        userService.inert(model);
        return "test";
    }

    @RequestMapping(value = "info6")
    @ResponseBody
    public Object getUserInfo6() {
        List<User> all = userService.getAll();
        Map map = userService.selectMap(model.getTid());
        User user = userService.get(model);
        User userService1 = userService.get1(model);
        return all;
    }

    @RequestMapping(value = "forward")
    public String forward(){
        System.out.println("userInfo方法");
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","李四");
        return "forward:user/info";
    }

    @RequestMapping(value = "redirect")
    public String redirect(){
        System.out.println("userInfo方法");
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","李四");
        return "redirect:user/info";
    }


}
