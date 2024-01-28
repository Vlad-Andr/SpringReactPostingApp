package org.example.edu.web;

import lombok.AllArgsConstructor;
import org.example.edu.model.Post;
import org.example.edu.model.dto.CommentDto;
import org.example.edu.model.dto.PostDto;
import org.example.edu.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postService.findAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @PostMapping
    public ResponseEntity<Post> createNewPost(@RequestBody PostDto postDto) {
        Post newPost = postService.createNewPost(postDto);
        return ResponseEntity.ok(newPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.ok().build();
    }
}
