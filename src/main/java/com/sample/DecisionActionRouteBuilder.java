package com.sample;

import javax.ws.rs.HttpMethod;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class DecisionActionRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        from("direct:makedecision")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/snmptrap")
                .log("Response: ${body}")
                .to("direct:callAnsible");

        from("direct:callAnsible")
                .choice()
                .when().jsonpath("[?($.result == 'Reboot Network')]")
                    .to("direct:networkUp")
                .otherwise()
                    .to("direct:doNothing");

        from("direct:networkUp")
                .log("Action: Network Up");

        from("direct:doNothing")
                .log("Action: DoNothing");
    }
}
