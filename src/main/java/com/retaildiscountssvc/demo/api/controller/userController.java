package com.retaildiscountssvc.demo.api.controller;

import com.retaildiscountssvc.demo.api.dao.UserRepository;
import com.retaildiscountssvc.demo.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserRepository repo;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody final User user) {
        user.setJointDate(LocalDate.now());
        return new ResponseEntity<>(repo.save(user), HttpStatus.OK);
    }

    @GetMapping("/login/{id}")
    public ResponseEntity<User> login(@PathVariable final long id) {
        User user = repo.findById(id).orElse(null);
        return new ResponseEntity<>(user, user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
