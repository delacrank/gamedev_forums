package com.juan.gamedevforums.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.context.annotation.ComponentScan;

 
import java.io.IOException;
 
@Configuration
@ComponentScan(basePackages = { "com.juan.gamedevforums.web" })
public class MvcConfig implements WebMvcConfigurer {
    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     registry.addResourceHandler("/**")
    //             .addResourceLocations("classpath:/static/")
    //             .resourceChain(true)
    //             .addResolver(new PathResourceResolver() {
    //                 @Override
    //                 protected Resource getResource(String resourcePath, Resource location) throws IOException {
    //                     Resource requestedResource = location.createRelative(resourcePath);
 
    //                     return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
    //                             : new ClassPathResource("/static/successReg.html");
    //                 }
    //             });
    // }
}
