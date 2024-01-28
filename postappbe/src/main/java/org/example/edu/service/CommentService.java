package org.example.edu.service;

import org.example.edu.model.Comment;
import org.example.edu.model.dto.CommentDto;
import java.util.List;

public interface CommentService {
    List<CommentDto> findPostCommentsById(Long postId);
    void removeComment(Long commentId);
    Comment saveNewComment(Long postId, CommentDto dto);
}
