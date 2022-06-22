package com.github.shaart.startup;

import com.github.shaart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

  private final UserService userService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    userService.createAdmin("admin@example.com", "admin");
    userService.createUser("user@example.com", "user");
  }
}
