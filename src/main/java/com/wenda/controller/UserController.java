package com.wenda.controller;

import com.wenda.pojo.User;
import com.wenda.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2017/6/9.
 */
@Controller
public class UserController {
    @Autowired
    private IUserService iUserService;


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String showLoginPage(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String handleLogin(Model model, @RequestParam("name") String name,
                              @RequestParam("password") String password,
                              @RequestParam(value = "remember_me",defaultValue = "false") boolean rememberme,
                              HttpServletResponse response){
        Map<String,String> map = iUserService.login(name,password);
        if (map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }

        if (map.containsKey("token")){
            Cookie cookie = new Cookie("token",map.get("token"));
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String showRegisterPage(){
        return "register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String handleRegister(Model model,User user) {
        Map<String,String> map = iUserService.register(user);
        if (map.containsKey("msg")){
            model.addAttribute("msg",map.get("msg"));
            return "register";
        }
        return "index";
    }





}
