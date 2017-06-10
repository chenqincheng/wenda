package com.wenda.service;

import com.wenda.pojo.User;

import java.util.Map;

/**
 * Created by chen on 2017/6/9.
 */
public interface IUserService {
    Map<String,String> register(User user);
    Map<String,String> login(String name,String password);
}
