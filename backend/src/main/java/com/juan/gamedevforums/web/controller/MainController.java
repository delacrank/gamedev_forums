package com.juan.gamedevforums.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
    	return "forward:/";
    }
}
