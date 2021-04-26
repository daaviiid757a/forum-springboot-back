package com.deividsandoval.forum.controller;

import com.deividsandoval.forum.dto.CommentDTO;
import com.deividsandoval.forum.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("by-post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForPost(@PathVariable("postId") int postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsForUser(@PathVariable("username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
    }

    @PostMapping("create")
    public ResponseEntity create(@RequestBody CommentDTO req) {
        commentService.save(req);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
