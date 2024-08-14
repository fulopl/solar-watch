package com.codecool.solarwatch.integrationtest;

import com.codecool.solarwatch.controller.SolarWatchController;
import com.codecool.solarwatch.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@ActiveProfiles("test") // Activates 'application-test.properties'
@TestPropertySource(locations = "classpath:application-test.properties")
public class WebApplicationIT {

    @Autowired
    private SolarWatchController solarWatchController;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() throws Exception {
        assertThat(solarWatchController).isNotNull();
        assertThat(userController).isNotNull();
    }
}


