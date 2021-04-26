package com.deividsandoval.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String url;
    private String description;
    private String username;
    private String subforumName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
}