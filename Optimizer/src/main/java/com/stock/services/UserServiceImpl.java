package com.stock.services;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stock.model.UserData;
import com.stock.repositories.UserDataRepository;

@Service
public class UserServiceImpl implements UserService {

  private final UserDataRepository userDataRepository;

  @Autowired
  public UserServiceImpl(final UserDataRepository userDataRepository) {
    super();
    this.userDataRepository = userDataRepository;
  }

  @Override
  public UserData saveUserData(final UserData u) {
    UserData userData = new UserData();
    return userDataRepository.save(u);
  }

  /**
   * Update existing user data
   */
  @Override
  public UserData updateUserData(final UserData u) {
    UserData existingUserData = userDataRepository.findById(u.getUserId()).orElse(null);
    if (existingUserData == null) {
      return userDataRepository.save(u);
    }
    existingUserData.setInvestedAmount(u.getInvestedAmount());
    existingUserData.setAvailableCash(u.getAvailableCash());
    existingUserData.setUpdatedOn(LocalDateTime.now());
    return userDataRepository.save(existingUserData);
  }

}
