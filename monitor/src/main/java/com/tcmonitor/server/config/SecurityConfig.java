package com.tcmonitor.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/secure/**").access("hasRole('ROLE_USER')")
//                .antMatchers("/index/**").access("hasRole('ROLE_USER')")
//                .antMatchers("/v2/**").access("hasRole('ROLE_USER')")
//                .antMatchers("/api/**").access("hasRole('ROLE_USER')")
//                .antMatchers("/swagger-resources/**").access("hasRole('ROLE_USER')")
                .and().formLogin().loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login").defaultSuccessUrl("/").and().csrf().disable();
    }
}
