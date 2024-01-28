package org.example.edu.web;

import lombok.AllArgsConstructor;
import org.example.edu.model.Comment;
import org.example.edu.model.dto.CommentDto;
import org.example.edu.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentDto> dto = commentService.findPostCommentsById(postId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(path = "/{postId}")
    public ResponseEntity<Comment> saveNewCommentToPost(@PathVariable Long postId, @RequestBody CommentDto dto) {
        Comment comment = commentService.saveNewComment(postId, dto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long commentId) {
        commentService.removeComment(commentId);
        return ResponseEntity.ok().build();
    }
}
