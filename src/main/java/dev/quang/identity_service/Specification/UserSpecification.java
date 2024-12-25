package dev.quang.identity_service.Specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import dev.quang.identity_service.Entity.User;

@Component
public class UserSpecification {
    public Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("email"), email);
    }
}
