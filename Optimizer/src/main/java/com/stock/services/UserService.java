package com.stock.services;

import com.stock.model.UserData;

public interface UserService {

  UserData saveUserData(UserData u);

  UserData updateUserData(UserData u);
}
