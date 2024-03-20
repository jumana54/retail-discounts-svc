package com.retaildiscountssvc.demo.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retaildiscountssvc.demo.api.model.User;
import com.retaildiscountssvc.demo.api.model.UserType;
import com.retaildiscountssvc.demo.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    public void init() {
        user = User.builder().id(123456789).name("test-user").type(UserType.EMPLOYEE).build();
    }

    @Test
    public void UserController_Signup_ReturnOk() throws Exception {
        given(userService.saveUser(any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(user.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(user.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", is(user.getType().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.joinDate", is(LocalDate.now().toString())));
    }

    @Test
    public void UserController_Login_ReturnResponseDto() throws Exception {
        when(userService.findUserById(anyInt())).thenReturn(user);

        ResultActions response = mockMvc.perform(get("/user/login/123456789"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(user.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(user.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", is(user.getType().toString())));
    }

    @Test
    public void UserController_Login_ReturnNotFound() throws Exception {
        when(userService.findUserById(anyInt())).thenReturn(null);

        ResultActions response = mockMvc.perform(get("/user/login/123456789"));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
