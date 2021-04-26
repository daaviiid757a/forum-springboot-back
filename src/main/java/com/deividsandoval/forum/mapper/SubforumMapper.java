package com.deividsandoval.forum.mapper;

import com.deividsandoval.forum.dto.SubforumDTO;
import com.deividsandoval.forum.models.Post;
import com.deividsandoval.forum.models.Subforum;
import com.deividsandoval.forum.models.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubforumMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subforum.getPosts()))")
    SubforumDTO toDto(Subforum subforum);

    default Integer mapPosts(List<Post> numberOfPosts) { return numberOfPosts.size(); }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Subforum toMap(SubforumDTO subforum, User user);
}
