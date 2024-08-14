package com.codecool.solarwatch.integrationtest;

import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest

public class UserRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        UserEntity alex = new UserEntity();
        alex.setUsername("alex");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        UserEntity found = userRepository.findByUsername(alex.getUsername()).orElse(null);

        // then
        assertThat(found.getUsername())
                .isEqualTo(alex.getUsername());
    }
}
