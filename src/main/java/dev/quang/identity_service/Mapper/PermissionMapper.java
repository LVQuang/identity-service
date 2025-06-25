package dev.quang.identity_service.Mapper;

import org.mapstruct.Mapper;

import dev.quang.identity_service.Dto.Request.AddPermission;
import dev.quang.identity_service.Dto.Response.ResponsePermission;
import dev.quang.identity_service.Entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(AddPermission request);
    ResponsePermission toResponsePermission(Permission permission);
}
