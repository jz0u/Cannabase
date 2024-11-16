package com.cannabase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class AuditConfig {
    @Bean
    public Logger auditLogger() {
        return LoggerFactory.getLogger("AUDIT");
    }
}
