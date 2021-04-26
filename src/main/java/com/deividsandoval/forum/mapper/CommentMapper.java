package com.deividsandoval.forum.mapper;

import com.deividsandoval.forum.dto.CommentDTO;
import com.deividsandoval.forum.models.Comment;
import com.deividsandoval.forum.models.Post;
import com.deividsandoval.forum.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDTO.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment toMap(CommentDTO commentDTO, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentDTO toDto(Comment comment);
}