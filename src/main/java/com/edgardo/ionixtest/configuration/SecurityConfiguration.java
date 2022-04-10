package com.edgardo.ionixtest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CORSFilter corsFilter() {
        CORSFilter filter = new CORSFilter();
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http

                .csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();*/
        (
                (HttpSecurity)
                        (
                                (HttpSecurity)
                                        (
                                                (ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)
                                                        http
                                                                .headers().addHeaderWriter(
                                                                        new StaticHeadersWriter("Access-Control-Allow-Origin", "*")).and()
                                                                .addFilterBefore(corsFilter(), SessionManagementFilter.class)
                                                                .csrf().disable()
                                                                .authorizeRequests()
                                                                .antMatchers(HttpMethod.OPTIONS,"/api/**").permitAll()
                                                                .antMatchers("/api/users/**")
                                                                .permitAll()
                                                                .anyRequest()

                                        ).authenticated().and()
                        ).formLogin().and()
        ).httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder().username("edgardo").password(passwordEncoder().encode("password"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}