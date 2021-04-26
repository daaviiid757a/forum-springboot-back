package com.deividsandoval.forum.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private int postId;
    private String subforumName;
    private String title;
    private String url;
    private String description;
}
