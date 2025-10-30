package com.UACH.Mercadito_Online.components;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class CamelProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("Ejecutando tarea Camel cada 5 segundos...");
    }
}
