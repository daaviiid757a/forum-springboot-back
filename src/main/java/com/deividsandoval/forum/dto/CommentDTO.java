package com.deividsandoval.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private int id;
    private int postId;
    private Instant createdDate;
    private String text;
    private String username;
}
