package com.retaildiscountssvc.demo.api.service;

import com.retaildiscountssvc.demo.api.dao.UserRepository;
import com.retaildiscountssvc.demo.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public User saveUser(@RequestBody final User user) {
        return repo.save(user);
    }

    public User findUserById(@PathVariable final long id) {
        return repo.findById(id).orElse(null);
    }
}
