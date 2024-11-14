package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.UserCredentials;
import com.codecool.solarwatch.model.payload.UserResponse;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.codecool.solarwatch.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

public class UserControllerTest {
    private UserController underTest;

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
    private final JwtUtils jwtUtils = Mockito.mock(JwtUtils.class);
    private final UserService userService = Mockito.mock(UserService.class);

    private final UserResponse userResponse1 = new UserResponse(1L, "Admin", List.of("ROLE_USER", "ROLE_ADMIN"));
    private final UserResponse userResponse2 = new UserResponse(2L, "User", List.of("ROLE_USER"));
    private final List<UserResponse> userResponseList = List.of(userResponse1, userResponse2);

    @BeforeEach
    void beforeEachTest() {
        underTest = new UserController(userRepository, roleRepository,
                passwordEncoder, authenticationManager, jwtUtils, userService);
    }

    @Test
    void testGetAllUsers_shouldReturnListOfAllUsers() {
        //arrange
        Mockito.when(userService.getAllUsers()).thenReturn(userResponseList);
        //act
        List<UserResponse> actual = underTest.getAllUsers();
        //assert
        Assertions.assertEquals(userResponseList, actual);
        Mockito.verify(userService, Mockito.times(1)).getAllUsers();
    }

    @Test
    void testCreateUser_givenValidUserName_shouldCallFindAllMethod() {
        //arrange
        UserCredentials userCredentials = new UserCredentials("New User", "password");
        UserEntity user = new UserEntity();
        user.setUsername(userCredentials.username());
        user.setPassword("encodedPassword");
        Role roleUser = new Role("ROLE_USER");
        user.setRoles(Set.of(roleUser));

        Mockito.when(passwordEncoder.encode(userCredentials.password())).thenReturn("encodedPassword");
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(roleUser);
        //act
        underTest.createUser(userCredentials);
        //assert
        Mockito.verify(userRepository, Mockito.times(1)).existsByUsername(userCredentials.username());
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(userCredentials.password());
        Mockito.verify(roleRepository, Mockito.times(1)).findByName("ROLE_USER");
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    void testCreateUser_givenInvalidUserName_shouldThrowIllegalArgumentException() {
        //arrange
        UserCredentials userCredentials = new UserCredentials("User", "Password");
        Mockito.when(userRepository.existsByUsername(userCredentials.username()))
                .thenThrow(new IllegalArgumentException("Username not available."));
        //act and assert
        Assertions.assertThrows(IllegalArgumentException.class, ()-> underTest.createUser(userCredentials));
    }
}
