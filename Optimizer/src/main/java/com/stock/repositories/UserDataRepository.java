package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.stock.model.UserData;

@Repository
public interface UserDataRepository extends CrudRepository<UserData, Long> {

}
