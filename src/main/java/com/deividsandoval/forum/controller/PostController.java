package com.deividsandoval.forum.controller;

import com.deividsandoval.forum.requests.PostRequest;
import com.deividsandoval.forum.dto.PostResponse;
import com.deividsandoval.forum.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("index")
    public ResponseEntity<List<PostResponse>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAll());
    }

    @PostMapping("create")
    public ResponseEntity create(@RequestBody PostRequest req) {
        postService.save(req);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("show/{id}")
    public PostResponse show(@PathVariable int id) {
        //return ResponseEntity.status(HttpStatus.OK).body(subforumService.getOne(id));
        return postService.getOne(id);
    }

    @GetMapping("subforum/{id}")
    public List<PostResponse> showBySubforum(@PathVariable int id) {
        //return ResponseEntity.status(HttpStatus.OK).body(subforumService.getOne(id));
        return postService.getPostsBySubforum(id);
    }

    @GetMapping("user/{name}")
    public List<PostResponse> showByUsername(@PathVariable String username) {
        //return ResponseEntity.status(HttpStatus.OK).body(subforumService.getOne(id));
        return postService.getPostsByUsername(username);
    }
}
