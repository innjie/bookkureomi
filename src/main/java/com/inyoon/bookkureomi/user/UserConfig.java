package com.inyoon.bookkureomi.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                        "/user/join",
                        "/user/login"
                ).permitAll()
                .antMatchers("/user/mypage/**").hasRole("USER")

                .and()
                .formLogin()
                .loginProcessingUrl("/book/user/login")
                .loginPage("/book/user/login")
                .defaultSuccessUrl("/book")
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(new LoginSuccessHandler())
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/book/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
//                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .permitAll();
    }

    //
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
