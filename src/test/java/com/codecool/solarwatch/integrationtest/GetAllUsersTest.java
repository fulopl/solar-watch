package com.codecool.solarwatch.integrationtest;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.UserResponse;
import com.codecool.solarwatch.repository.RoleRepository;
import com.codecool.solarwatch.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test") // Activates 'application-test.properties'
@TestPropertySource(locations = "classpath:application-test.properties")
public class GetAllUsersTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public GetAllUsersTest(TestRestTemplate restTemplate, UserRepository userRepository, RoleRepository roleRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @BeforeEach
    void setup() {
        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");
        roleRepository.save(role1);
        roleRepository.save(role2);
        UserEntity user1 = new UserEntity();
        user1.setUsername("intTest1");
        user1.setRoles(Set.of(roleRepository.findByName("ROLE_USER"), roleRepository.findByName("ROLE_ADMIN")));
        userRepository.save(user1);

        UserEntity user2 = new UserEntity();
        user2.setUsername("intTest2");
        user2.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user2);

        System.out.println(userRepository.findByUsername("intTest1").orElse(null).toString());
        System.out.println(userRepository.findByUsername("intTest2").orElse(null).toString());

    }

    @Test
    void requestReturnsAllUsers() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/user",
                UserResponse[].class)).isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/user",
                UserResponse[].class)).hasSize(2);
    }

    @Test
    void requestRemovesAdminRole() throws Exception {
        // TODO: I/O error on PATCH request for "http://localhost:53723/api/user/removerole": Invalid HTTP method: PATCH
        //act
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/user/removerole?user=1&role=ROLE_ADMIN",
                HttpMethod.PATCH,
                null,
                String.class);
        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
