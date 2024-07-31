package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(format("could not find user %s in the repository", username)));

    }

//    public void addRoleFor(UserEntity user, Role role) {
//        Set<Role> oldRoles = user.roles();
//        Set<Role> copiedRoles = new HashSet<>(oldRoles);
//        copiedRoles.add(role);
//
//        userRepository.updateUserEntity(new UserEntity(
//                user.id(), user.username(), user.password(), Set.copyOf(copiedRoles))
//        );
//    }
}
