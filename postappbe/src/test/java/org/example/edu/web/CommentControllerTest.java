package org.example.edu.web;

import org.example.edu.model.Comment;
import org.example.edu.model.Post;
import org.example.edu.model.User;
import org.example.edu.model.dto.CommentDto;
import org.example.edu.model.dto.PostDto;
import org.example.edu.model.dto.UserDto;
import org.example.edu.service.PostService;
import org.example.edu.service.UserService;
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
public class CommentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    Post post;

    @Order(1)
    @Test
    public void saveNewCommentToPost_OK() {
        User user = userService.createUser(UserDto.builder().build());
        post = postService.createNewPost(PostDto.builder().username(user.getUsername()).content("Content").build());
        Comment response1 = restTemplate.postForObject("/comment/" + post.getPostId(), CommentDto.builder()
                        .date(System.currentTimeMillis())
                        .content("Content1")
                        .authorName(user.getUsername())
                        .build(),
                Comment.class);
        Comment response2 = restTemplate.postForObject("/comment/" + post.getPostId(), CommentDto.builder()
                        .date(System.currentTimeMillis())
                        .content("Content2")
                        .authorName(user.getUsername())
                        .build(),
                Comment.class);
        assertNotNull(response1);
        assertNotNull(response2);
        assertEquals("Content1", response1.getContent());
        assertEquals("Content2", response2.getContent());
    }

    @Order(2)
    @Test
    public void findCommentsByPostIdGet_OK() {
        ParameterizedTypeReference<List<CommentDto>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<CommentDto>> response = restTemplate.exchange(
                "/comment/{postId}",
                HttpMethod.GET,
                null,
                responseType,
                post.getPostId()
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Order(3)
    @Test
    public void deleteCommentById_OK() {
        restTemplate.delete("/comment/1");

        ParameterizedTypeReference<List<CommentDto>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<CommentDto>> response = restTemplate.exchange(
                "/comment/{postId}",
                HttpMethod.GET,
                null,
                responseType,
                post.getPostId()
        );
        assertEquals(1, response.getBody().size());
    }
}
