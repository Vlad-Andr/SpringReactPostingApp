package org.example.edu.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.edu.model.Comment;
import org.example.edu.model.Post;
import org.example.edu.model.dto.CommentDto;
import org.example.edu.repository.CommentRepository;
import org.example.edu.repository.PostRepository;
import org.example.edu.repository.UserRepository;
import org.example.edu.service.CommentService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentDto> findPostCommentsById(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostPostId(postId);
        return comments.stream().filter(comment -> comment.getUser() != null).map(this::convertFromCommentToCommentDto).toList();
    }

    @Override
    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public Comment saveNewComment(Long postId, CommentDto dto) {
        Comment comment = convertFromCommentDtoToComment(dto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);
        comment.setUser(userRepository.findByUsername(dto.getAuthorName()));
        comment.setDate(System.currentTimeMillis());
        comment.setContent(dto.getContent());
        commentRepository.save(comment);
        return comment;
    }

    private CommentDto convertFromCommentToCommentDto(Comment comment) {
        return CommentDto.builder().commentId(comment.getId()).content(comment.getContent()).authorName(comment.getUser().getUsername()).date(comment.getDate()).build();
    }

    private Comment convertFromCommentDtoToComment(CommentDto dto) {
        return Comment.builder().content(dto.getContent()).date(dto.getDate()).build();
    }
}
