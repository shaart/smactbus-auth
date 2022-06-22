package com.github.shaart.service;

import com.github.shaart.model.entity.User;

public interface UserService {

  User createUser(String email, String password);

  User createAdmin(String email, String password);

  boolean existsByEmail(String email);
}
