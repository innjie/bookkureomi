package com.inyoon.bookkureomi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity
public class UserConfig extends WebSecurityConfigurerAdapter {
	
/*	@Autowired
    PasswordEncoder passwordEncoder;
*/
	@Autowired
    AuthProvider authProvider;
    
	
    // DB를 사용한 사용자 인증을 처리 할때 설정.
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userService()).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authProvider);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", 
					        		"/js/**", 
					        		"/img/**", 
					        		"/lib/**", 
					        		"/templates/fragments/**", 
					        		"templates/layouts/**");
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
	                /*.antMatchers(/*"/css/**",
	                        "/js/**",
	                        "/img/**",
	                        "/templates/fragments/**",
	                        "templates/layouts/**",
	                        "/user/join",
	                        "/user/login").permitAll()*/
	                .antMatchers("/user/mypage/**").hasRole("USER")
	                .antMatchers("/**").permitAll()

                //security_login
                .and()
	                .formLogin()
	                .loginProcessingUrl("/book/user/login")
	                .loginPage("/book/user/login")
	                .defaultSuccessUrl("/book")
	                .successHandler(new LoginSuccessHandler())
	                .failureHandler(new LoginFailHandler())
	                .permitAll()

                //security_logout
                .and()
	                .logout()
	                .logoutUrl("/book/user/logout")
	                .logoutSuccessUrl("/")
	                .invalidateHttpSession(true)
	                .logoutSuccessHandler(new MyLogoutSuccessHandler())
	                .permitAll();
    }
    
    
 /*   
    private UserService userService() {
    	UserService userService = new UserService();
    	userService.setEnableGroups(false);
        return userService;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
   */ 
    // logout 후 login할 때 정상동작을 위함
 /*   @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }*/
}
