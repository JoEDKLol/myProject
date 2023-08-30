package com.jdl.jdlhome.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdl.jdlhome.dto.UserDto;
import com.jdl.jdlhome.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("TEST")
    void join() throws Exception {

        String userId = "test";
        String password = "test";
        String name = "test";
        String email = "test";

        mockMvc.perform(post("/signUpAction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserDto(userId,password,name,email))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Fail")
    void join_fail() throws Exception {

        String userId = "test";
        String password = "test";
        String name = "test";
        String email = "test";

        mockMvc.perform(post("/signUpAction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserDto(userId,password,name,email))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

}