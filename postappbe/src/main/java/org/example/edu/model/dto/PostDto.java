package org.example.edu.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostDto {
    private Long postId;
    private String username;
    private Long date;
    private String title;
    private String content;
    private List<String> comments;
}
