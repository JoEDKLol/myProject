package com.jdl.jdlhome.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping(value = {"/","/index.html", "/main", "/main/main"})
    public String mainPage(){

        return "/main/main";
    }

    @GetMapping("/main/board101")
    public String board101Page(){
        return "/main/board101";
    }

    @GetMapping("/main/board102")
    public String board102Page(){
        return "/main/board102";
    }

    @GetMapping("/navigator")
    public String pageNavigator(String page){
        return page;
    }





}
