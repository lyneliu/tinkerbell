package org.mauritius.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.sql.DataSource;

/**
 * Created by nn_liu on 2017/3/2.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/home","index").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
        .logout()
            .logoutSuccessUrl("/logout")
            .permitAll();
    }

    protected void configure(AuthenticationManagerBuilder auth){
        try
        {
            super.configure(auth);
            logger.info("<<<<<<<<<<<<<<< auth here >>>>>>>>>>>>>>>>>>");

            //auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery().authoritiesByUsernameQuery();
        }catch (Exception e){
            logger.error("auth error:",e);
        }

    }
}