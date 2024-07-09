package com.workflowManager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //웹에 css, js 로드가 되지 않아 기본경로 재설정함
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/assets/js/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : null;
                    }
                });
    }
}
