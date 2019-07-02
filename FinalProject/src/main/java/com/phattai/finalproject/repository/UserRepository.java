package com.phattai.finalproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.phattai.finalproject.model.User;

public interface UserRepository extends CrudRepository<User, String> {

}
