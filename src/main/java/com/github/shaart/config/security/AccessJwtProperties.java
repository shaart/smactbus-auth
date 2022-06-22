package com.github.shaart.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.oauth2.authorization.jwt.access")
public class AccessJwtProperties {

  private String issuer;
}
