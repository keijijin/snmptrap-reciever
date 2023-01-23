package com.sample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public class SnmpTrapRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        JacksonXMLDataFormat jacksonXMLDataFormat = new JacksonXMLDataFormat();
        jacksonXMLDataFormat.setUseList(true);

        String snmpVersion = "&snmpVersion=1";
        // String oids = "&oids=1.3.6.1.2.1.1.3.0,1.3.6.1.2.1.25.3.2.1.5.1,1.3.6.1.2.1.25.3.5.1.1.1,1.3.6.1.2.1.43.5.1.1.11.1.";

        from("snmp:192.168.0.15:162?protocol=udp&type=TRAP" + snmpVersion)
            .convertBodyTo(String.class)
            // .log("Original: ${body}")
            .unmarshal(jacksonXMLDataFormat)
            .marshal().json(JsonLibrary.Jackson)
            .log("Body: ${body}")
            // .process(new FactProcessor())
            // // .log("Fact: ${body}")
            // .to("direct:makedecision")
            ;
    }
}
