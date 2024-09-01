package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.JwtResponse;
import com.codecool.solarwatch.model.payload.UserCredentials;
import com.codecool.solarwatch.model.payload.UserResponse;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.codecool.solarwatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody UserCredentials signUpRequest) {

        //TODO: check for existing username

        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.username());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@RequestBody UserCredentials loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return new JwtResponse(jwt, userDetails.getUsername(), roles);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> me() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return ResponseEntity.ok(new JwtResponse(user.getPassword(), user.getUsername(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        ));
    }

    @GetMapping("/context")
    public ResponseEntity<?> displayUserContext() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }

    @PatchMapping("/addrole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRoleToUser(@RequestParam(name = "user") Long userId
            , @RequestParam(name = "role") String roleName) {
        userService.addRoleFor(userId, roleName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/removerole")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeRoleFromUser(@RequestParam(name = "user") Long userId
            , @RequestParam(name = "role") String roleName) {
        userService.removeRoleFrom(userId, roleName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
