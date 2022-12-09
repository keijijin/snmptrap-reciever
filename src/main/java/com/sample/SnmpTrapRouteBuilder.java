package com.sample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public class SnmpTrapRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        JacksonXMLDataFormat jacksonXMLDataFormat = new JacksonXMLDataFormat();
        jacksonXMLDataFormat.setUseList(true);

        from("snmp:127.0.0.1:162?protocol=udp&type=TRAP")
            .convertBodyTo(String.class)
            .log("Original: ${body}")
            .unmarshal(jacksonXMLDataFormat)
            .marshal().json(JsonLibrary.Jackson)
            .process(new FactProcessor())
            .log("Fact: ${body}")
            .to("direct:makedecision");
    }
}
