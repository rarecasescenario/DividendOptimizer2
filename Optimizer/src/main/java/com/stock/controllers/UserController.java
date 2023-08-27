package com.stock.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stock.model.UserData;
import com.stock.services.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {

  private static Logger logger = LogManager.getLogger(UserController.class);

  private final UserService userService;

  public UserController(final UserService userService) {
    super();
    this.userService = userService;
  }

  @PostMapping("/updateUserData")
  public UserData updateUserData(@RequestBody final UserData userData) {
    return userService.updateUserData(userData);
  }
}
