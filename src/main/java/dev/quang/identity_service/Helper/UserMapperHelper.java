package dev.quang.identity_service.Helper;

import java.util.Set;

import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.quang.identity_service.Enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserMapperHelper {
    PasswordEncoder passwordEncoder;

    @Named("transRoles")
    public Set<String> transRoles(Set<String> roles) {
        var response = roles;
        response.add(Role.USER.name());
        return response;
    }

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
