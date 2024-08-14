package com.codecool.solarwatch.integrationtest;

import com.codecool.solarwatch.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(UserController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RoleAddAndRemoveIT {

    //TODO: Parameter 0 of constructor in com.codecool.solarwatch.controller.UserController required a bean of type 'com.codecool.solarwatch.repository.UserRepository' that could not be found.

    @Autowired
    private MockMvc mockMvc;

//    @BeforeEach
//    void setup() {
//        Role role1 = new Role("ROLE_USER");
//        Role role2 = new Role("ROLE_ADMIN");
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//        UserEntity user1 = new UserEntity();
//        user1.setUsername("intTest1");
//        user1.setRoles(Set.of(roleRepository.findByName("ROLE_USER"), roleRepository.findByName("ROLE_ADMIN")));
//        userRepository.save(user1);
//
//        UserEntity user2 = new UserEntity();
//        user2.setUsername("intTest2");
//        user2.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
//        userRepository.save(user2);
//
//        System.out.println(userRepository.findByUsername("intTest1").orElse(null).toString());
//        System.out.println(userRepository.findByUsername("intTest2").orElse(null).toString());
//
//    }

    @Test
    void requestRemovesAdminRole() throws Exception {

//        mockMvc.perform(patch("/api/user/removerole?user=1&role=ROLE_ADMIN"))
//                .andExpect(status().isOk());

    }
}
