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
        
        System.setProperty("JAEGER_SERVICE_NAME", (System.getenv("JAEGER_SERVICE_NAME") != null? System.getenv("JAEGER_SERVICE_NAME") : "helloservice"));
        System.setProperty("JAEGER_REPORTER_LOG_SPANS", (System.getenv("JAEGER_REPORTER_LOG_SPANS") != null? System.getenv("JAEGER_REPORTER_LOG_SPANS") : "true"));
        System.setProperty("JAEGER_SAMPLER_TYPE", (System.getenv("JAEGER_SAMPLER_TYPE") != null? System.getenv("JAEGER_SAMPLER_TYPE") : "const"));
        System.setProperty("JAEGER_SAMPLER_PARAM", (System.getenv("JAEGER_SAMPLER_PARAM") != null? System.getenv("JAEGER_SAMPLER_PARAM") : "1.0"));
        System.setProperty("JAEGER_ENDPOINT", (System.getenv("JAEGER_ENDPOINT") != null? System.getenv("JAEGER_ENDPOINT") : "http://localhost:14268/api/traces")); 
        
        try {
            return Configuration.fromEnv().getTracer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // NoopTracer
        return NoopTracerFactory.create();
    }

}
