package com.deividsandoval.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubforumDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
