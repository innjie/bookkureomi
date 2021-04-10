package com.inyoon.bookkureomi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class UserConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**",
                        "/js/**",
                        "/img/**",
                        "/templates/fragments/**",
                        "templates/layouts/**",
                        "/user/signup",
                        "/user/login"
                ).permitAll()
                //.antMatchers("").hasRole("USERS")
                ;
        System.out.println("configure in");
        http.formLogin()
                .loginProcessingUrl("authenticate")
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .passwordParameter("pw")
                .permitAll();

//        http.logout()
//                .logoutSuccessUrl("/")
//                .permitAll();


    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
