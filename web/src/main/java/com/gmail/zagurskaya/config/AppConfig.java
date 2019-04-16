package com.gmail.zagurskaya.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.gmail.zagurskaya")
@PropertySource(value = "jdbc.properties")
public class AppConfig {
}
