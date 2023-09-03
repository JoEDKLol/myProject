package com.jdl.jdlhome.controller;

import com.jdl.jdlhome.dto.UserDto;
import com.jdl.jdlhome.entity.User;
import com.jdl.jdlhome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("/")
//    public String mainPage(){
//
//        return "signIn";
//    }

    @GetMapping("/signIn")
    public String signInPage(){

        return "signIn";
    }

    @GetMapping("/signUp")
    public String signUpPage(){

        return "signUp";
    }

    @PostMapping("/signUpAction")
    public String signUpAction(Model model, UserDto userDto) {

        String success = userService.signUp(userDto);

        model.addAttribute("msg", success);
        return "signUp :: #resultDiv";
    }

    @GetMapping("/fail")
    public String failPage(){
        return "fail";
    }

    @GetMapping("/test")
    public String testPage(){
        return "test";
    }

    @GetMapping("/auth_test")
    public String authTestPage(){
        return "auth_test";
    }
    @GetMapping("/admin/admin")
    public @ResponseBody String adminPage(){
        return "admin";
    }

    @GetMapping("/manager/manager")
    public @ResponseBody String managerPage(){
        return "manager";
    }

    @GetMapping("/user/user")
    public @ResponseBody String userPage(){
        return "main";
    }
}
