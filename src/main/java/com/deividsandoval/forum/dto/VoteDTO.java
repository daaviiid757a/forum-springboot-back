package com.deividsandoval.forum.dto;

import com.deividsandoval.forum.models.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private VoteType voteType;
    private int postId;
}
