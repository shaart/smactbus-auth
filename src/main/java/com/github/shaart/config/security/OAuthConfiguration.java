package com.github.shaart.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userService;

  @Value("${jwt.clientId}")
  private String clientId;

  @Value("${jwt.client-secret}")
  private String clientSecret;

  @Value("${jwt.access-token-validity-seconds}")
  private int accessTokenValiditySeconds;

  @Value("${jwt.authorized-grant-types}")
  private String[] authorizedGrantTypes;

  @Value("${jwt.refresh-token-validity-seconds}")
  private int refreshTokenValiditySeconds;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient(clientId)
        .secret(passwordEncoder.encode(clientSecret))
        .accessTokenValiditySeconds(accessTokenValiditySeconds)
        .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
        .authorizedGrantTypes(authorizedGrantTypes)
        .scopes("read", "write")
        .resourceIds("api");
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
        .accessTokenConverter(accessTokenConverter())
        .userDetailsService(userService)
        .authenticationManager(authenticationManager);
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    return new JwtAccessTokenConverter();
  }
}
