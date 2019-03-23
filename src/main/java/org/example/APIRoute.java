package org.example;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ContextName("rest-dsl")
public class APIRoute extends RouteBuilder {

    @Bean
    ServletRegistrationBean camelServlet() {
        ServletRegistrationBean mapping = new ServletRegistrationBean();
        mapping.setName("CamelServlet");
        mapping.setLoadOnStartup(1);
        // CamelHttpTransportServlet is the name of the Camel servlet to use
        mapping.setServlet(new CamelHttpTransportServlet());
        mapping.addUrlMappings("/api/*");
        return mapping;
    }


    @Override
    public void configure() throws Exception {
        // configure swagger doc
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
            // and output using pretty print
            .dataFormatProperty("prettyPrint", "true")
            // add swagger api-doc out of the box
            .apiContextPath("/api-doc")
            // add swagger api-doc out of the box
            .apiProperty("api.title", "Helloservice API").apiProperty("api.version", "1.0.0")
            // and enable CORS
            .apiProperty("cors", "true")
            // and return right api doco host
            .apiProperty("base.path", "/api")
            // set host on swagger doc
            .apiProperty("host", (System.getenv("SWAGGERUI_HOST") != null? System.getenv("SWAGGERUI_HOST") : "localhost:8080"));

        // hello rest service
        rest()
            .get("/hello/{personId}")
            .to("direct:getPersonId");

        // chaining rest service
        rest()
            .get("/chaining/{personId}")
            .to("direct:chaining");

        from("direct:chaining")
            .removeHeaders("*", "personId")
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader("Content-Type", constant("text/plain"))
            .setHeader("Accept", constant("text/plain"))
            .to("log:DEBUG?showBody=true&showHeaders=true")
            .setHeader(Exchange.HTTP_URI, simple("http4://localhost:8080/api/hello/${in.header.personId}"))
            .to("http4://fobar")
            .convertBodyTo(String.class);
    }
}