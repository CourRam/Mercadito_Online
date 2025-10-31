package com.UACH.Mercadito_Online.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("nombre", "Joseph");
        return "home"; // busca home.html en /templates
    }

    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("insulto", "pendejo");
        return "productos"; // busca productos.html
    }
}
