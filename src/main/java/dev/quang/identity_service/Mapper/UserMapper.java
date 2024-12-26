package dev.quang.identity_service.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.quang.identity_service.Dto.Request.SaveUser;
import dev.quang.identity_service.Dto.Response.DetailUser;
import dev.quang.identity_service.Entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "hide", constant = "false")
    @Mapping(target = "id", ignore = true)
    User toUser(SaveUser request);

    DetailUser toDetailUser(User request);

    @Mapping(target = "hide", constant = "false")
    @Mapping(target = "id", ignore = true)
    void updateUser(SaveUser request, @MappingTarget User user);
}
