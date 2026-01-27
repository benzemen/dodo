package com.example.dodo.Repositories;

import com.example.dodo.Entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByuseremail(String useremail);
    Optional<User> findByuseremail( String useremail);
}
