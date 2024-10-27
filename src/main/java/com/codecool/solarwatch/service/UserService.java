package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.UserResponse;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserEntity findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No such user."));

    }

    public void addRoleFor(Long userId, String roleName) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new IllegalArgumentException("No such user.");

        Role role = roleRepository.findByName(roleName);
        if (role == null) throw new IllegalArgumentException("No such role.");

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void removeRoleFrom(Long userId, String roleName) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new IllegalArgumentException("No such user.");

        Role role = roleRepository.findByName(roleName);
        if (role == null) throw new IllegalArgumentException("No such role.");

        if (!user.getRoles().remove(role)) throw new IllegalArgumentException("No such role with user.");
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) userRepository.deleteById(id);
        else throw new IllegalArgumentException("No such user.");
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
          return new UserResponse(
                  user.getId(),
                  user.getUsername(),
                  user.getRoles().stream().map(Role::getName).toList()
          );
        }).toList();
    }

}
