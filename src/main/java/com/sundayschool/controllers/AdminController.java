package com.sundayschool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class AdminController {
    @RequestMapping(value = "/admin" , method = RequestMethod.GET)
    public String setUp(Model model){
        return "admin-home";
    }
}