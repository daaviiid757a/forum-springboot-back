package com.deividsandoval.forum.controller;

import com.deividsandoval.forum.dto.SubforumDTO;
import com.deividsandoval.forum.models.Subforum;
import com.deividsandoval.forum.service.SubforumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subforum")
@AllArgsConstructor
public class SubforumController {
    private final SubforumService subforumService;

    @GetMapping("index")
    public ResponseEntity<List<SubforumDTO>> index() {
        return ResponseEntity.status(HttpStatus.OK).body(subforumService.getAll());
    }

    @PostMapping("create")
    public ResponseEntity<SubforumDTO> create(@RequestBody SubforumDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subforumService.save(req));
    }

    @GetMapping("show/{id}")
    public ResponseEntity<SubforumDTO> show(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(subforumService.getOne(id));
    }
}
