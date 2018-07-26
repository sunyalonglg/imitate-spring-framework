package com.demo.controller.service;

import com.demo.controller.bean.User;
import com.demo.controller.dao.UserDao;
import com.iwork.core.annotation.Autowwired;
import com.iwork.core.annotation.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowwired
    private UserDao userDao;

    public void updateUser(User user){
        userDao.updateUser(user);
    }

    public void updateMap(User user){
        userDao.updateMap(user);
    }

    public void updateString(Integer user){
        userDao.updateString(user);
    }

    public void inert(User user){
        userDao.insert(user);
    }

    public User get(User user){
        return userDao.findByPrimaryKey(user.getTid());
    }

    public User get1(User user){
        return userDao.getUserTid(user);
    }

    public Map selectMap(Integer tid) {
        return userDao.getUserMap(tid);
    }

    public List<User> getAll(){
        return userDao.getAll();
    }
}
