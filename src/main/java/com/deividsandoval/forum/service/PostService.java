package com.deividsandoval.forum.service;

import com.deividsandoval.forum.requests.PostRequest;
import com.deividsandoval.forum.dto.PostResponse;
import com.deividsandoval.forum.exceptions.ForumException;
import com.deividsandoval.forum.mapper.PostMapper;
import com.deividsandoval.forum.models.Post;
import com.deividsandoval.forum.models.Subforum;
import com.deividsandoval.forum.models.User;
import com.deividsandoval.forum.repository.PostRepository;
import com.deividsandoval.forum.repository.SubforumRepository;
import com.deividsandoval.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final SubforumRepository subforumRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final UserRepository userRepository;


    @Transactional
    public PostResponse getOne(int id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ForumException("Post not found"));
        return postMapper.toDto(post);
    }

    @Transactional
    public List<PostResponse> getAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public void save(PostRequest req) {
        Subforum subforum = subforumRepository.findByName(req.getSubforumName())
                .orElseThrow(() -> new ForumException(req.getSubforumName()));
        postRepository.save(postMapper.toMap(req, subforum, authService.getCurrentUser()));
    }

    @Transactional
    public List<PostResponse> getPostsBySubforum(int id) {
        Subforum subforum = subforumRepository.findById(id)
                .orElseThrow(() -> new ForumException("Post not found"));
        List<Post> posts = postRepository.findAllBySubforum(subforum);
        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ForumException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }
}
