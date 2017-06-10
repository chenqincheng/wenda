package com.wenda.service.Impl;

import com.wenda.common.Const;
import com.wenda.dao.TokenMapper;
import com.wenda.dao.UserMapper;
import com.wenda.pojo.Token;
import com.wenda.pojo.User;
import com.wenda.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chen on 2017/6/9.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public Map<String, String> register(User user) {
        Map<String,String> map = new HashMap<String,String>();
        if (!this.checkValid(user.getName(),Const.USERNAME)){
            map.put("msg","用户名已存在！");
            return map;
        }
        if (!this.checkValid(user.getEmail(),Const.EMAIL)){
            map.put("msg","email已存在！");
            return map;
        }

        int resultCount = userMapper.insert(user);
        if (resultCount == 0){
            map.put("msg","注册失败~");
        }

        map.put("msg","注册成功！");
        return  map;

    }

    @Override
    public Map<String, String> login(String name, String password) {
        Map<String,String> map = new HashMap<String,String>();
        int resultCount = userMapper.checkUsername(name);
        if (resultCount == 0){
            map.put("msg","用户名不存在！");
            return map;
        }

        User user = userMapper.selectLogin(name,password);
        if (user == null){
            map.put("msg","密码错误");
            return map;
        }

        String token = addToken(user.getId());
        map.put("token",token);
        return map;
    }

    /*
    * 数据验证
    * data：数据
    * type：用户名、邮箱
    * */
    public boolean checkValid(String data, String type){
        if (StringUtils.isNotBlank(type)){
            //邮箱验证
            if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(data);
                return resultCount == 0;
            }

            //用户名验证
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(data);
                return resultCount == 0;
            }
        }
        //参数错误，直接return FALSE
        return false;
    }

    public String addToken(int userId){
        Token token = new Token();
        token.setUserId(userId);
        Date now = new  Date();
        now.setTime(3600*24*7 + now.getTime());//默认token有效期为7天
        token.setExpired(now);
        token.setStatus(0);
        token.setToken(UUID.randomUUID().toString().replaceAll("-",""));
        tokenMapper.insert(token);
        return token.getToken();
    }
}
