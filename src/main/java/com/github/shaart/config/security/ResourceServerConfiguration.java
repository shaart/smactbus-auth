package com.github.shaart.config.security;

import com.github.shaart.model.errors.CustomAccessDeniedHandler;
import com.github.shaart.model.errors.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId("api");
  }

  private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .cors().disable()
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers("/h2-console", "/h2-console/**").permitAll()
        .antMatchers("/.well-known/jwks.json").permitAll()
        .antMatchers("/oauth/**").permitAll()
        .antMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
        .antMatchers("/api/auth", "/api/auth/**").permitAll()
        .antMatchers("/api/signin", "/api/signin/**").permitAll()
        .antMatchers("/api/glee**").hasAnyAuthority("ADMIN", "USER")
        .antMatchers("/api/users**").hasAuthority("ADMIN")
        .antMatchers("/api/**").authenticated()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(customAuthenticationEntryPoint)
        .accessDeniedHandler(new CustomAccessDeniedHandler());
  }
}