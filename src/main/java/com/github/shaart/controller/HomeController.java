package com.github.shaart.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

  @GetMapping
  public String home() {
    return "hello";
  }

  @GetMapping("/api/test")
  @PreAuthorize("hasRole('USER')")
  public Object home(Authentication authentication) {
    return authentication.getDetails();
  }
}
