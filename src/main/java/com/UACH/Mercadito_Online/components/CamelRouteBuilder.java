package com.UACH.Mercadito_Online.components;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteBuilder extends RouteBuilder {
    private boolean activo = false;
    /*
    @Autowired
    private CamelProcessor camelProcessor;
    */
    @Override
    public void configure() {
        // Ruta periódica: solo corre si activo == true
        from("timer://tareaTimer?period=5000") // cada 5 segundos
                .routeId("tarea-route")
                .filter(exchange -> activo) // solo ejecuta si está activo
                .process("camelProcessor");
    }

    // Permitir activar la ruta desde el controller
    public void activar() {
        activo = true;
        log.info("Se inicio el proceso de camel");
    }

    public void desactivar() {
        activo = false;
        log.info("Se detuvo el proceso");
        System.out.println("Se detuvo");
    }
    public CamelRouteBuilder(){

    }

}
