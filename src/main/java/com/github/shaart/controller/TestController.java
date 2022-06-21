package com.github.shaart.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

  @GetMapping
  public String home() {
    return "hello";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER')")
  public Object testUserAccess(Authentication authentication) {
    return authentication.getPrincipal();
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public Object testAdminAccess(Authentication authentication) {
    return authentication.getPrincipal();
  }
}
