package com.UACH.Mercadito_Online.controllers;

import com.UACH.Mercadito_Online.components.CamelRouteBuilder;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/tareaCamel")
public class camelHomeworkRestController {

    @Autowired
    private  final CamelRouteBuilder camelRouteBuilder;

    public camelHomeworkRestController(CamelRouteBuilder camelRouteBuilder) {
        this.camelRouteBuilder = camelRouteBuilder;
    }

    @GetMapping("/start")
    public String iniciarTarea() {
        camelRouteBuilder.activar();
        return "Tarea Camel iniciada. Se ejecutará cada 5 segundos (ver logs).";
    }

    @GetMapping("/stop")
    public String detenerTarea() {
        camelRouteBuilder.desactivar();
        return "Tarea Camel detenida.";
    }
}

