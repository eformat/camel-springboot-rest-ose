package org.example;

import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;

/**
 * Spring Boot application to start this application
 */
@SpringBootApplication
@CamelOpenTracing
@EnablePrometheusEndpoint
// load regular Spring XML file from the classpath that contains the Camel XML DSL
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application {

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}