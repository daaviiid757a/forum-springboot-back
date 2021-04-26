package com.deividsandoval.forum.controller;

import com.deividsandoval.forum.dto.VoteDTO;
import com.deividsandoval.forum.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping()
    public ResponseEntity vote(@RequestBody VoteDTO vote) {
        voteService.vote(vote);

        return new ResponseEntity(HttpStatus.OK);
    }
}
