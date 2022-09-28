package com.sample;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jacksonxml.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import javax.ws.rs.HttpMethod;

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
                .log("${body}")
            .to("direct:makedecision");

        from("direct:makedecision")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .process(new FactProcessor())
                .to("http://localhost:8081/snmptrap")
                .log("Response: ${body}")
                .to("direct:callAnsible");

        from("direct:callAnsible")
                .log("${body}")
                .choice()
                .when().jsonpath("[?($.result == 'Reboot Network')]")
                    .to("direct:networkUp")
                .otherwise()
                    .to("direct:doNothing");

        from("direct:networkUp")
                .log("Network Up");

        from("direct:doNothing")
                .log("DoNothing");
    }
}
