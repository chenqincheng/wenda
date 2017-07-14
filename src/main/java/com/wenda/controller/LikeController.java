package com.wenda.controller;

import com.wenda.common.CurrentUser;
import com.wenda.common.EntityType;
import com.wenda.common.JSONResponse;
import com.wenda.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by chen on 2017/6/26.
 */
@Controller
public class LikeController {
    @Autowired
    private ILikeService iLikeService;

    @Autowired
    private CurrentUser currentUser;


    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public JSONResponse like(@RequestParam(value = "commentId") int commentId){
        if (currentUser.getUser() == null){
            return JSONResponse.returnStatusAndMessage(999,"用户未登录");
        }
        long likeCount = iLikeService.like(currentUser.getUser().getId(), EntityType.COMMENT,commentId);
        return JSONResponse.returnStatusAndMessage(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = "/dislike",method = RequestMethod.POST)
    @ResponseBody
    public JSONResponse dislike(@RequestParam(value = "commentId") int commentId){
        if (currentUser.getUser() == null){
            return JSONResponse.returnStatusAndMessage(999,"用户未登录");
        }
        long likeCount = iLikeService.dislike(currentUser.getUser().getId(), EntityType.COMMENT,commentId);
        return JSONResponse.returnStatusAndMessage(0,String.valueOf(likeCount));
    }
}
