package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;
import com.stock.model.UserData;

public interface UserDataRepository extends CrudRepository<UserData, Long> {

}
