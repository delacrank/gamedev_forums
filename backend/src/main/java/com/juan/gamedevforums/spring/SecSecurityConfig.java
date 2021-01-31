package com.juan.gamedevforums.spring;

import java.io.File;
import java.io.IOException;

import com.juan.gamedevforums.persistence.dao.UserRepository;
import com.juan.gamedevforums.security.CustomRememberMeServices;
import com.juan.gamedevforums.security.CustomAuthenticationProvider;
import com.juan.gamedevforums.security.MyBasicAuthenticationEntryPoint;

import com.juan.gamedevforums.filter.CustomFilter;
import com.juan.gamedevforums.filter.AccessDeniedExceptionFilter;  
import com.juan.gamedevforums.security.CustomAccessDeniedHandler;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

@Configuration
@Order(2)
@ComponentScan(basePackages = { "com.juan.gamedevforums.security" })
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    public SecSecurityConfig() {
        super();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
	return new CustomAccessDeniedHandler();
    }	

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }    

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()	    

	    .antMatchers("/resources/**/","/","/registrationConfirm*", "/resetPassword*",
	    		 "/home*","/successfulRegistrarion*", "/badUser*",
	    		 "/pageNotFound*","index.html","/login","/user",
	    		 "/logout", "/h2-console/**").permitAll()
	    .antMatchers("/api/forum/{catName}/addTopic/").hasAuthority("WRITE_PRIVILEGE")
	    
 	    .and()
	    .formLogin()
	    .loginPage("/login")
	    .permitAll()
	    
	    .and()
	    .httpBasic()      
	    .authenticationEntryPoint(authenticationEntryPoint)
            // 
	    
	    .and()  
	    .headers()
	    .frameOptions()
            .sameOrigin();

	// http.addFilterBefore(BasicAuthenticationFilter.class, AccessDeniedExceptionFilter.class);
	http.addFilterAfter(new CustomFilter(),
			    BasicAuthenticationFilter.class);
	


	     // .and()
             //   .rememberMe().rememberMeServices(rememberMeServices()).key("theKey");

    // @formatter:on
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
        return rememberMeServices;
    }

}
