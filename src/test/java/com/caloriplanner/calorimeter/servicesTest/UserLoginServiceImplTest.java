package com.caloriplanner.calorimeter.servicesTest;

import com.caloriplanner.calorimeter.clos.helpers.SlugHelper;
import com.caloriplanner.calorimeter.clos.models.User;
import com.caloriplanner.calorimeter.clos.repositories.UserRepository;
import com.caloriplanner.calorimeter.clos.service.impl.UserLoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserLoginServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SlugHelper slugHelper;

    @InjectMocks
    private UserLoginServiceImpl userLoginService;  // This will inject mocks into userLoginService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void registerUser_ShouldEncodePasswordAndSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword123");
        when(slugHelper.generateSlug(anyString())).thenReturn("testuser-slug");
        when(slugHelper.ensureSlugUnique(anyString())).thenReturn("testuser-slug");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userLoginService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("encodedPassword123", registeredUser.getPassword());
        assertEquals("testuser-slug", registeredUser.getSlug());
        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode("password123");
        verify(slugHelper, times(1)).generateSlug("testuser");
        verify(slugHelper, times(1)).ensureSlugUnique("testuser-slug");
    }

    @Test
    void getUserByUsername_ShouldReturnUser() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userLoginService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void authenticateUser_ShouldReturnTrueWhenCredentialsAreCorrect() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword123");

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password123", "encodedPassword123")).thenReturn(true);

        boolean isAuthenticated = userLoginService.authenticateUser("testuser", "password123");

        assertTrue(isAuthenticated);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword123");
    }

    @Test
    void authenticateUser_ShouldReturnFalseWhenUserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        boolean isAuthenticated = userLoginService.authenticateUser("testuser", "password123");

        assertFalse(isAuthenticated);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(0)).matches(anyString(), anyString());
    }

    @Test
    void authenticateUser_ShouldReturnFalseWhenPasswordDoesNotMatch() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword123");

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword123")).thenReturn(false);

        boolean isAuthenticated = userLoginService.authenticateUser("testuser", "wrongPassword");

        assertFalse(isAuthenticated);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("wrongPassword", "encodedPassword123");
    }
}


