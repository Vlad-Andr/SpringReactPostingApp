package org.example.edu.service;

import org.example.edu.model.Post;
import org.example.edu.model.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();
    Post createNewPost(PostDto postDto);
    void deletePostById(Long postId);
}
