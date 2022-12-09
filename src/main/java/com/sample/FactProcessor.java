package com.sample;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class FactProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String str = exchange.getIn().getBody(String.class);
        exchange.getIn().setBody("{\"oids\": " + str + "}");
        // log.info(exchange.getIn().getBody(String.class));
    }
}
