package dev.quang.identity_service.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.quang.identity_service.Entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {} 
