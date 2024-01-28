package org.example.edu.web;

import org.example.edu.model.Post;
import org.example.edu.model.User;
import org.example.edu.model.dto.CommentDto;
import org.example.edu.model.dto.PostDto;
import org.example.edu.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    static PostDto postToSave;
    Long savedPostId;

    @BeforeAll
    public void init() {
        User mergedUser = userRepository.save(User.builder().firstName("1").lastName("1").password("123").build());
        postToSave = PostDto.builder().username(mergedUser.getUsername()).date(System.currentTimeMillis()).content("Some content").build();
    }

    @AfterAll
    public void destroy() {
        userRepository.deleteAll();
    }

    @Order(1)
    @Test
    public void savePostPost_OK() {
        ResponseEntity<Post> response = restTemplate.postForEntity("/post", postToSave, Post.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        savedPostId = response.getBody().getPostId();
        assertEquals(postToSave.getContent(), response.getBody().getContent());
    }

    @Order(2)
    @Test
    public void findPostByIdGet_OK() {
        ParameterizedTypeReference<List<PostDto>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<PostDto>> response = restTemplate.exchange(
                "/post/all",
                HttpMethod.GET,
                null,
                responseType
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Order(3)
    @Test
    public void deletePostById_OK() {
        restTemplate.delete("/post/" + savedPostId);
        ParameterizedTypeReference<List<PostDto>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<PostDto>> response = restTemplate.exchange(
                "/post/all",
                HttpMethod.GET,
                null,
                responseType
        );
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
}
