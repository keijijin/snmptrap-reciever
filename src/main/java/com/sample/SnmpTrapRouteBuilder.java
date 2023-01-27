package com.sample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.snmp4j.mp.SnmpConstants;

public class SnmpTrapRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        JacksonXMLDataFormat jacksonXMLDataFormat = new JacksonXMLDataFormat();
        jacksonXMLDataFormat.setUseList(true);

        from("snmp:192.168.0.15:162?protocol=udp&type=TRAP&snmpVersion=" + SnmpConstants.version2c)
            // .process(new PduConverter())
            .convertBodyTo(String.class)
            .log("Original: ${body}")
            .unmarshal(jacksonXMLDataFormat)
            .marshal().json(JsonLibrary.Jackson)
            .log("Body: ${body}")
            // .process(new FactProcessor())
            // .log("Body: ${body}")
            // .to("direct:makedecision")
            ;
    }
}
