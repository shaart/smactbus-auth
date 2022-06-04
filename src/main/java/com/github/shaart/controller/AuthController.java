package com.github.shaart.controller;

import com.github.shaart.dto.AuthRequestDto;
import com.github.shaart.dto.LoginResponseDto;
import com.github.shaart.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid AuthRequestDto request) {
    try {
      Authentication authenticate = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );

      User user = (User) authenticate.getPrincipal();

      String accessToken = jwtTokenUtil.generateToken(user);
      return ResponseEntity.ok()
          .header(HttpHeaders.AUTHORIZATION, accessToken)
          .body(LoginResponseDto.builder()
              .accessToken(accessToken)
              .build()
          );
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

}