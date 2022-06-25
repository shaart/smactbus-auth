package com.github.shaart.config.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableAuthorizationServer
@Import(OAuth2AuthorizationServerConfiguration.class)
public class OAuthConfiguration extends OAuth2AuthorizationServerConfiguration {

  private final ApplicationContext context;
  private final JwtAccessTokenConverter jwtAccessTokenConverter;
  private final AccessJwtProperties accessJwtProperties;
  private final UserDetailsService userDetailsService;

  public OAuthConfiguration(
      BaseClientDetails details,
      AuthenticationConfiguration authenticationConfiguration,
      ObjectProvider<TokenStore> tokenStore,
      ObjectProvider<AccessTokenConverter> tokenConverter,
      AuthorizationServerProperties properties,
      ApplicationContext context,
      JwtAccessTokenConverter jwtAccessTokenConverter,
      AccessJwtProperties accessJwtProperties,
      @Qualifier("defaultUserDetailsService") UserDetailsService userDetailsService) throws Exception {
    super(details, authenticationConfiguration, tokenStore, tokenConverter, properties);
    this.context = context;
    this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    this.accessJwtProperties = accessJwtProperties;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    super.configure(endpoints);
    endpoints.userDetailsService(userDetailsService);

    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(List.of(tokenEnhancer(), jwtAccessTokenConverter));
    endpoints.tokenEnhancer(tokenEnhancerChain);
  }

  @Bean
  public KeyPair keyPair(AuthorizationServerProperties authorization) {
    Resource keyStore = this.context.getResource(authorization.getJwt().getKeyStore());
    char[] keyStorePassword = authorization.getJwt().getKeyStorePassword().toCharArray();
    var keyStoreKeyFactory = new KeyStoreKeyFactory(keyStore, keyStorePassword);

    String keyAlias = authorization.getJwt().getKeyAlias();
    char[] keyPassword = Optional.ofNullable(authorization.getJwt().getKeyPassword())
        .map(String::toCharArray)
        .orElse(keyStorePassword);

    return keyStoreKeyFactory.getKeyPair(keyAlias, keyPassword);
  }

  @Bean
  public JWKSet jwkSet(KeyPair keyPair) {
    RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
        .keyUse(KeyUse.SIGNATURE)
        .algorithm(JWSAlgorithm.RS256);
    return new JWKSet(builder.build());
  }

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new CustomTokenEnhancer(accessJwtProperties);
  }

}
