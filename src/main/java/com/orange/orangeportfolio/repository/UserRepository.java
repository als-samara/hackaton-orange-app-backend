package com.orange.orangeportfolio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.orange.orangeportfolio.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
}
