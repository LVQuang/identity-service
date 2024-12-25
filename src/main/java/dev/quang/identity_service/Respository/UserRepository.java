package dev.quang.identity_service.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dev.quang.identity_service.Entity.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {}
