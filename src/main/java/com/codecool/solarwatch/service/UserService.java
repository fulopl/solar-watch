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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

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
                .orElseThrow(() -> new IllegalArgumentException(format("could not find user %s in the repository", username)));

    }

    public void addRoleFor(String userName, String roleName) {
        UserEntity user = userRepository.findByUsername(userName).orElse(null);
        if (user == null) throw new IllegalArgumentException("No such user."); // TODO ex.handling

        Role role = roleRepository.findByName(roleName);
        if (role == null) throw new IllegalArgumentException("No such role."); // TODO ex.handling

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void removeRoleFrom(String userName, String roleName) {
        UserEntity user = userRepository.findByUsername(userName).orElse(null);
        if (user == null) throw new IllegalArgumentException("No such user."); // TODO ex.handling

        Role role = roleRepository.findByName(roleName);
        if (role == null) throw new IllegalArgumentException("No such role."); // TODO ex.handling

        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) userRepository.deleteById(id);
        else throw new IllegalArgumentException("No such user."); // TODO ex.handling
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
