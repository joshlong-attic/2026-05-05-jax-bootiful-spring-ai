package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

import static org.springaicommunity.mcp.security.authorizationserver.config.McpAuthorizationServerConfigurer.mcpAuthorizationServer;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }


    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    Customizer<HttpSecurity> httpSecurityCustomizer() {
        return (http) -> http
                .oauth2AuthorizationServer(a -> a.oidc(Customizer.withDefaults()))
                .with(mcpAuthorizationServer(), w -> w
                        .authorizationServer(as -> as.oidc(Customizer.withDefaults())));
    }
}
