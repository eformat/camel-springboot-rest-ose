package org.example;

import org.springframework.context.annotation.Bean;
import io.jaegertracing.Configuration;
import io.opentracing.noop.NoopTracerFactory;

@org.springframework.context.annotation.Configuration
public class TracingConfiguration {

    /*
     * See https://github.com/jaegertracing/jaeger-client-java/blob/master/jaeger-core/README.md#
     * configuration-via-environment
     */
    @Bean
    public io.opentracing.Tracer jaegerTracer() {

        System.setProperty("JAEGER_SERVICE_NAME", "helloservice");
        System.setProperty("JAEGER_REPORTER_LOG_SPANS", "true");
        System.setProperty("JAEGER_SAMPLER_TYPE", "const");
        System.setProperty("JAEGER_SAMPLER_PARAM", "1.0");
        System.setProperty("JAEGER_ENDPOINT", "http://localhost:14268/api/traces"); 
        
        try {
            return Configuration.fromEnv().getTracer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // NoopTracer
        return NoopTracerFactory.create();
    }

}
