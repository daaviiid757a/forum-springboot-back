package com.deividsandoval.forum.repository;

import com.deividsandoval.forum.models.Post;
import com.deividsandoval.forum.models.Subforum;
import com.deividsandoval.forum.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllBySubforum(Subforum subforum);

    List<Post> findByUser(User user);
}
