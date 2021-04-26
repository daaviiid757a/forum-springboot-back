package com.deividsandoval.forum.repository;

import com.deividsandoval.forum.models.Subforum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Repository
public interface SubforumRepository extends JpaRepository<Subforum, Integer> {
    Optional<Subforum> findByName(String subforumName);
}
