package com.lexy.persistentapi.configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexy.persistentapi.util.DTOModelMapper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final EntityManager entityManager;

    @Autowired
    public WebMvcConfiguration(ApplicationContext applicationContext, EntityManager entityManager) {
        this.applicationContext = applicationContext;
        this.entityManager = entityManager;
    }
//
//    public WebMvcConfiguration(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
        argumentResolvers.add(new DTOModelMapper(objectMapper, entityManager));
    }
}
