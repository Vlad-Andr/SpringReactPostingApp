package org.example.edu.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long commentId;
    private String authorName;
    private Long date;
    private String content;
}
