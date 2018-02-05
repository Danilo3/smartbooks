package com.koryakin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@EnableZuulProxy
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter{


    @SuppressWarnings("unused")
    private static final transient Logger LOG = LoggerFactory.getLogger(Application.class.getName());

    public static void main(String[] args) {
        LOG.info("Application started");
        SpringApplication.run(Application.class, args);
    }

}
