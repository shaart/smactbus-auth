package com.github.shaart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.endpoint.TokenKeyEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwksController {

  private final TokenKeyEndpoint tokenKeyEndpoint;

  @GetMapping("/.well-known/jwks.json")
  public Map<String, String> get(Principal principal) {
    return tokenKeyEndpoint.getKey(principal);
  }
}
