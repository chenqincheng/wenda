package com.wenda.common;

import com.wenda.pojo.User;
import org.springframework.stereotype.Component;

/**
 * ThreadLocal
 * Created by chen on 2017/6/11.
 */
@Component
public class CurrentUser {
    private static ThreadLocal<User> currentUser = new ThreadLocal<User>();

    public User getUser(){
        return currentUser.get();
    }

    public void setUser(User user){
        currentUser.set(user);
    }

    public void clear(){
        currentUser.remove();
    }

}
