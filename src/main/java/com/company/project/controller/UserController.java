package com.company.project.controller;

import com.company.project.core.ResultGenerator;
import com.company.project.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    UserServiceImpl userService;

    @RequestMapping(path = {"/login"},method = {RequestMethod.POST})
    public @ResponseBody String login(@RequestParam("username") String username){
        String token = userService.getUserByUsername(username).getToken();
        Map data =  new HashMap<String,String>();
        data.put("token",token);
        if (token!=null){
            return ResultGenerator.genSuccessResult(data).toString();
        }else {
            return ResultGenerator.genFailResult("用户名不存在").toString();
        }

    }
}
