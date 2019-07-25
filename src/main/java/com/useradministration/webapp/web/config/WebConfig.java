package com.useradministration.webapp.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.useradministration.webapp.web.controller"})
@Import(value = {SecurityConfig.class, ThymeleafConfig.class})
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

   /* @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }*/
}
