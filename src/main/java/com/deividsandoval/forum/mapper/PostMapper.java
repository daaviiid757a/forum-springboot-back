package com.deividsandoval.forum.mapper;

import com.deividsandoval.forum.models.Comment;
import com.deividsandoval.forum.repository.CommentRepository;
import com.deividsandoval.forum.repository.VoteRepository;
import com.deividsandoval.forum.requests.PostRequest;
import com.deividsandoval.forum.dto.PostResponse;
import com.deividsandoval.forum.models.Post;
import com.deividsandoval.forum.models.Subforum;
import com.deividsandoval.forum.models.User;
import com.deividsandoval.forum.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    private VoteRepository voteRepository;
    private AuthService authService;


    // Se pueden simplificar, eliminando las dos lineas siguientes, ya que los nombres en las clases "target" y "source" es la misma
    // @Mapping(target = "subforum", source = "subforum")
    // @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "title", source = "postRequest.title")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post toMap(PostRequest postRequest, Subforum subforum, User user);


    // Se pueden simplificar, eliminando las dos lineas siguientes, ya que los nombres en las clases "target" y "source" es la misma
    // @Mapping(target = "postName", source = "postName")
    // @Mapping(target = "description", source = "description")
    // @Mapping(target = "url", source = "url")
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subforumName", source = "subforum.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse toDto(Post post);

    String getDuration(Post post) { return TimeAgo.using(post.getCreatedDate().toEpochMilli()); }

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }
}
