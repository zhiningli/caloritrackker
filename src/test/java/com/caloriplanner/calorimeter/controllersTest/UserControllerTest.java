package com.caloriplanner.calorimeter.controllersTest;

import com.caloriplanner.calorimeter.clos.controllers.UserController;
import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.service.UserLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLoginService userLoginService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerUser_ShouldReturnCreatedUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userLoginService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getUserByUsername_ShouldReturnUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        when(userLoginService.getUserByUsername("testuser")).thenReturn(user);

        mockMvc.perform(get("/api/users/testuser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void loginUser_ShouldReturnSuccessMessageWhenCredentialsAreCorrect() throws Exception {
        when(userLoginService.authenticateUser("testuser", "password123")).thenReturn(true);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"identifier\":\"testuser\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    void loginUser_ShouldReturnUnauthorizedWhenCredentialsAreIncorrect() throws Exception {
        when(userLoginService.authenticateUser("testuser", "wrongpassword")).thenReturn(false);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"identifier\":\"testuser\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void checkSlugExists_ShouldReturnTrueWhenSlugExists() throws Exception {
        when(userLoginService.checkSlugExists("existing-slug")).thenReturn(true);

        mockMvc.perform(get("/api/users/check-slug/existing-slug")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void checkSlugExists_ShouldReturnFalseWhenSlugDoesNotExist() throws Exception {
        when(userLoginService.checkSlugExists("non-existing-slug")).thenReturn(false);

        mockMvc.perform(get("/api/users/check-slug/non-existing-slug")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}


