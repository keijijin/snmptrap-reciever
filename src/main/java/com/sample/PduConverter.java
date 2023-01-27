package com.sample;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.snmp4j.PDUv1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class PduConverter implements Processor{
    @Inject
    CamelContext context;

    @Override
    public void process(Exchange exchange) throws Exception {
        PDUv1 trap = exchange.getIn().getBody(PDUv1.class);

        log.debug("PDUv1: {}", trap.toString());
    }
}
