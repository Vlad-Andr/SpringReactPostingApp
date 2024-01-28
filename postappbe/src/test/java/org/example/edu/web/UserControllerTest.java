package org.example.edu.web;

import org.example.edu.model.User;
import org.example.edu.model.dto.UserDto;
import org.example.edu.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    static UserDto userToSave;
    static Long savedUserId;

    @BeforeAll
    public void setUp() {
        userToSave = UserDto.builder()
                .firstName("John")
                .lastName("Doe")
                .password("Pass").build();
    }

    @AfterAll
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Order(1)
    @Test
    public void saveUserPost_OK() {
        ResponseEntity<User> response = restTemplate.postForEntity("/user", userToSave, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        savedUserId = response.getBody().getId();
        assertEquals(userToSave.getFirstName(), response.getBody().getFirstName());
    }

    @Order(2)
    @Test
    public void findUserByIdGet_OK() {
        ResponseEntity<User> response = restTemplate.getForEntity("/user/{id}", User.class, savedUserId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userToSave.getFirstName(), response.getBody().getFirstName());
    }
}
