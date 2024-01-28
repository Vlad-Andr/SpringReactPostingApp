package org.example.edu.service.impl;

import lombok.AllArgsConstructor;
import org.example.edu.model.Comment;
import org.example.edu.model.Post;
import org.example.edu.model.User;
import org.example.edu.model.dto.PostDto;
import org.example.edu.repository.CommentRepository;
import org.example.edu.repository.PostRepository;
import org.example.edu.repository.UserRepository;
import org.example.edu.service.PostService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> all = postRepository.findAll();
        return all.stream().map(this::convertFromPostToPostDto).toList();
    }

    @Override
    public Post createNewPost(PostDto postDto) {
        return postRepository.save(convertFromPostDtoToPost(postDto));
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    private PostDto convertFromPostToPostDto(Post post) {
        if (post.getUser() == null) {
            return PostDto.builder().postId(post.getPostId())
                    .date(post.getDate())
                    .title(post.getTitle())
                    .content(post.getContent()).build();
        }
        return PostDto.builder().postId(post.getPostId())
                .username(post.getUser().getUsername())
                .date(post.getDate())
                .title(post.getTitle())
                .content(post.getContent()).build();
    }

    private Post convertFromPostDtoToPost(PostDto dto) {
        return Post.builder()
                .user(userRepository.findByUsername(dto.getUsername()))
                .date(System.currentTimeMillis())
                .title(dto.getTitle())
                .content(dto.getContent()).build();
    }
}
