package dev.quang.identity_service.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.quang.identity_service.Dto.Request.AddPermission;
import dev.quang.identity_service.Dto.Response.ResponsePermission;
import dev.quang.identity_service.Mapper.PermissionMapper;
import dev.quang.identity_service.Respository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public ResponsePermission addPermission(AddPermission request) {
        var permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        var response = permissionMapper.toResponsePermission(permission);   
        return response;
    }

    public List<ResponsePermission> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions
                .stream()
                .map(permission -> permissionMapper.toResponsePermission(permission))
                .toList();
    }
}
