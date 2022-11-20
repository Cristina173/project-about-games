package com.todolistrest.configuration;

import com.todolistrest.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                // user details service -> se ocupa cu incarcarea utilizatorului
                // din baza de date bazat pe username
                .userDetailsService(userAuthenticationService)
                // avem nevoie de password encoder
                // pentru ca spring-ul sa verifice password-ul introdus de catre utilizator
                .passwordEncoder(bCryptPasswordEncoder);
    }


    // Configurare ca request-urile sa NU fie securizate
//    @Override
//    protected void configure(HttpSecurity security) throws Exception {
//        security
//                .csrf().disable()  // -> csrf da disable la metodele de POST si PUT la servicii REST
//                .cors().disable() // -> cors -> restrictioneaza accesul de unde se fac request-urile
//                .authorizeRequests()
//                .anyRequest()
//                .permitAll(); // -> permite accesul la orice request care vine in aplicatie
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/tasks")
                .authenticated()
                .antMatchers("/users")
                .permitAll()
                .antMatchers(HttpMethod.DELETE, "/tasks/{id}")
                .hasAnyAuthority("ADMIN")
                .and()
                .httpBasic();
    }
}
