package com.deividsandoval.forum.service;

import com.deividsandoval.forum.dto.CommentDTO;
import com.deividsandoval.forum.exceptions.ForumException;
import com.deividsandoval.forum.mapper.CommentMapper;
import com.deividsandoval.forum.models.Comment;
import com.deividsandoval.forum.models.NotificationEmail;
import com.deividsandoval.forum.models.Post;
import com.deividsandoval.forum.models.User;
import com.deividsandoval.forum.repository.CommentRepository;
import com.deividsandoval.forum.repository.PostRepository;
import com.deividsandoval.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    //TODO: Construct POST URL
    private static final String POST_URL = "";

    @Transactional
    public void save(CommentDTO req) {
        Post post = postRepository.findById(req.getPostId()).orElseThrow(() -> new ForumException("Post not found"));

        Comment comment = commentMapper.toMap(req, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.send(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentDTO> getAllCommentsForPost(int postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ForumException("Post not found"));

        return commentRepository.findByPost(post).stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    public List<CommentDTO> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ForumException(username));

        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}
