package com.couponservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig  {

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic(Customizer.withDefaults());
        http.authorizeHttpRequests(autorize->
                autorize.requestMatchers(HttpMethod.GET,"/couponapi/coupons/**")
                        .hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/couponapi/coupon")
                        .hasAnyRole("ADMIN"));

        http.csrf(csrf->csrf.disable());

        return http.build();
    }
}
