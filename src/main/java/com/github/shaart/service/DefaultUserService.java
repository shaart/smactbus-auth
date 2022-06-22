package com.github.shaart.service;

import com.github.shaart.model.entity.Role;
import com.github.shaart.model.entity.User;
import com.github.shaart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User createUser(String email, String password) {
    var user = new User(
        null,
        email,
        passwordEncoder.encode(password),
        Role.USER,
        Collections.emptyList()
    );
    return repository.save(user);
  }

  @Override
  public User createAdmin(String email, String password) {
    var admin = new User(
        null,
        email,
        passwordEncoder.encode(password),
        Role.ADMIN,
        Collections.emptyList()
    );
    return repository.save(admin);
  }

  @Override
  public boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }
}
