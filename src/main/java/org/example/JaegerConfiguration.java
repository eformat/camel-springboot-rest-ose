package org.example;

import com.uber.jaeger.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class JaegerConfiguration {

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        Configuration config = Configuration.fromEnv();
        return config.getTracer();
    }

}
