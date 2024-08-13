package com.codecool.solarwatch.integrationtest;

import com.codecool.solarwatch.model.payload.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Activates 'application-test.properties'
public class GetAllUsersTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void requestReturnsAllUsers() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/user",
                UserResponse.class)).isNotNull();
    }
}
