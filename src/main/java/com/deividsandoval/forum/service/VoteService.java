package com.deividsandoval.forum.service;

import com.deividsandoval.forum.dto.VoteDTO;
import com.deividsandoval.forum.exceptions.ForumException;
import com.deividsandoval.forum.models.Post;
import com.deividsandoval.forum.models.Vote;
import com.deividsandoval.forum.repository.PostRepository;
import com.deividsandoval.forum.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.deividsandoval.forum.models.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDTO voteDTO) {
        Post post = postRepository.findById(voteDTO.getPostId()).orElseThrow(() -> new ForumException("Post Not Found with ID - " + voteDTO.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDTO.getVoteType())) {
            throw new ForumException("You have already " + voteDTO.getVoteType() + "'d for this post");
        }

        if (UPVOTE.equals(voteDTO.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(toMap(voteDTO, post));
        postRepository.save(post);
    }

    private Vote toMap(VoteDTO voteDTO, Post post) {
        return Vote.builder()
                .voteType(voteDTO.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
