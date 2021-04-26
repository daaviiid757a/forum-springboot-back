package com.deividsandoval.forum.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int postId;
    @NotBlank(message = "Post name cannot be empty or null")
    private String title;
    private String url;
    @Lob
    private String description;
    private int voteCount;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    // Corregir a "subforumId"
    // @JoinColumn(name = "subforumId", referencedColumnName = "subforumId")
    // Eliminar este y dejar el anterior
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subforum subforum;

}
