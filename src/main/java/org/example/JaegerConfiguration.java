package org.example;

import com.uber.jaeger.Configuration;
import io.opentracing.NoopTracerFactory;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class JaegerConfiguration {

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        Configuration config;
        try {
            config = Configuration.fromEnv();
            return config.getTracer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // NoopTracer
        return NoopTracerFactory.create();
    }

}
