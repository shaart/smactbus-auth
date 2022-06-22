package com.github.shaart.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

@RequiredArgsConstructor
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

  private final AccessJwtProperties accessJwtProperties;

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    OAuth2AccessToken enhanced = super.enhance(accessToken, authentication);
    Map<String, Object> additionalInformation = enhanced.getAdditionalInformation();
    additionalInformation.put("iss", accessJwtProperties.getIssuer());
    return enhanced;
  }
}
