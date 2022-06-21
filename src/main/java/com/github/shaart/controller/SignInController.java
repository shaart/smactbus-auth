package com.github.shaart.controller;

import com.github.shaart.model.dto.RegisterRequestDto;
import com.github.shaart.model.entity.Role;
import com.github.shaart.model.entity.User;
import com.github.shaart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/signin")
@RequiredArgsConstructor
public class SignInController {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping
  public User signIn(@RequestBody RegisterRequestDto registerRequest) {
    var user = new User(
        null,
        registerRequest.getEmail(),
        passwordEncoder.encode(registerRequest.getPassword()),
        Role.USER,
        Collections.emptyList());
    return repository.save(user);
  }

  @PostMapping("/admin")
  public User createAdmin(@RequestBody RegisterRequestDto registerRequest) {
    var user = new User(
        null,
        registerRequest.getEmail(),
        passwordEncoder.encode(registerRequest.getPassword()),
        Role.ADMIN,
        Collections.emptyList());
    return repository.save(user);
  }

  @PostMapping("/validate-email")
  public Boolean emailExists(@RequestParam String email) {
    return repository.existsByEmail(email);
  }
}