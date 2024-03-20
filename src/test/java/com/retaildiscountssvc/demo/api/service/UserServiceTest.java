package com.retaildiscountssvc.demo.api.service;

import com.retaildiscountssvc.demo.api.dao.UserRepository;
import com.retaildiscountssvc.demo.api.model.User;
import com.retaildiscountssvc.demo.api.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repo;
    @InjectMocks
    private UserService userService;
    private User user;

    @BeforeEach
    public void init() {
        user = User.builder().id(123456789).name("test-user").type(UserType.EMPLOYEE).build();
    }

    @Test
    public void UserService_SaveUser_ReturnSavedUser() {
        when(repo.save(any())).thenReturn(user);
        assertEquals(user, userService.saveUser(user));
    }

    @Test
    public void UserService_FindUserById_ReturnUserForId() {
        when(repo.findById(anyInt())).thenReturn(Optional.of(user));
        assertEquals(user, userService.findUserById(user.getId()));
    }
}
