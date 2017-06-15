package com.wenda.config.interceptor;

import com.wenda.common.CurrentUser;
import com.wenda.dao.TokenMapper;
import com.wenda.dao.UserMapper;
import com.wenda.pojo.Token;
import com.wenda.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by chen on 2017/6/11.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    TokenMapper tokenMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CurrentUser currentUser;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = null;
        if (httpServletRequest.getCookies() != null){
            for (Cookie cookie : httpServletRequest.getCookies()){
                if (cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null){
            Token currentToken = tokenMapper.selectByToken(token);
            if (currentToken == null || currentToken.getExpired().before(new Date()) || currentToken.getStatus() != 1){
                User user = userMapper.selectByPrimaryKey(currentToken.getUserId());
                System.out.println(user.getName());
                currentUser.setUser(user);
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            modelAndView.addObject("user",currentUser.getUser());

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        currentUser.clear();
    }
}
