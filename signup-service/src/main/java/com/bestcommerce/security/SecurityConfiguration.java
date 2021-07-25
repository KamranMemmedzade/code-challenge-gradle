package com.bestcommerce.security;

import com.bestcommerce.security.jwt.JwtAuthenticationEntryPoint;
import com.bestcommerce.security.jwt.JwtRequestFilter;
import com.bestcommerce.service.MerchantDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  private final MerchantDetailService merchantDetailService;

  private final JwtRequestFilter jwtRequestFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/login", "/register", "/home")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .logout()
        .clearAuthentication(true)
        .permitAll()
        .logoutSuccessUrl("/home")
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint);

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(merchantDetailService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
