package com.mtbs.user;

import org.springframework.data.repository.CrudRepository;

import com.mtbs.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {


}
