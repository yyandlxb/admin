package com.ebupt.admin.controller;

import com.ebupt.admin.pojo.WebUser;
import com.ebupt.admin.session.Authenticated;
import com.ebupt.admin.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private SessionManager sessionManager;


    @RequestMapping("/login1")
    public String login(@RequestBody WebUser webUser, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(86400);//24小时过期
        session.setAttribute("user",webUser);

        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }



    @RequestMapping("/{index}")
    public String index(@PathVariable String index){
        System.out.println(index);
        return index;
    }

    @RequestMapping("/ceshi")
    @ResponseBody
    public String ceshi(String index){
        System.out.println(index);
        index = "1111";
        System.out.println(index);
        return index;
    }



}
