package com.UACH.Mercadito_Online.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelTimer extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://camelTimer?period=10000")
                .routeId("CamelTimerRoute")
                .log("Mercadito_Online esta funcionando actualmente");
    }
}