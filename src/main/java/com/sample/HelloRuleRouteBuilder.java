package com.sample;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.ws.rs.HttpMethod;

public class HelloRuleRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        /*
        from("timer:foo?repeatCount=1")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setBody(simple("{\n" +
                        "  \"messages\": [\n" +
                        "    {\n" +
                        "      \"status\": 0,\n" +
                        "      \"message\": \"Hello World\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"))
                .to("http://localhost:8081/helloworld")
                .log("Response: ${body}");

         */
    }
}
