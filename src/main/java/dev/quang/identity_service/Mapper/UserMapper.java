package dev.quang.identity_service.Mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import dev.quang.identity_service.Dto.Request.SaveUser;
import dev.quang.identity_service.Dto.Response.DetailUser;
import dev.quang.identity_service.Entity.User;
import dev.quang.identity_service.Helper.UserMapperHelper;


@Mapper(componentModel = "spring", uses = UserMapperHelper.class)
public interface UserMapper {
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "hide", constant = "false")
    @Mapping(target = "id", ignore = true)
    User toUser(SaveUser request);

    DetailUser toDetailUser(User request);

    @InheritConfiguration(name = "toUser")
    void updateUser(SaveUser request, @MappingTarget User user);
}
