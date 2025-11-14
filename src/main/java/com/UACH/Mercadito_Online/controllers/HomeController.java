package com.UACH.Mercadito_Online.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/")
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "forward:/index2.html";
    }
    
}
