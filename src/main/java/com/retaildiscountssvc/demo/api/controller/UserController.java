package com.retaildiscountssvc.demo.api.controller;

import com.retaildiscountssvc.demo.api.model.User;
import com.retaildiscountssvc.demo.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody final User user) {
        user.setJoinDate(LocalDate.now());
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("/login/{id}")
    public ResponseEntity<User> login(@PathVariable final int id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
