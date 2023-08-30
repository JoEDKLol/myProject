package com.jdl.jdlhome.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


public class AdminController {

    public String adminPage(){
        return "admin";
    }

}
