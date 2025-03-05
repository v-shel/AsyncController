package com.vshel.asynccontroller.configuration;

import java.util.SplittableRandom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfiguration {

    @Bean
    public SplittableRandom splittableRandom() {
        return new SplittableRandom();
    }
}
