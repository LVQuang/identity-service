package dev.quang.identity_service.Dto.Request;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveUser {
    String email;
    String name;
    @Size(min = 5, message = "LENGTH_PASSWORD")
    String password;
    String firstname;
    String lastname;
    LocalDate dob;
    @Builder.Default
    Set<String> roles = new HashSet<String>();
}
