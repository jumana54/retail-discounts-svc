package com.retaildiscountssvc.demo.api.dao;

import com.retaildiscountssvc.demo.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}
