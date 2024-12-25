package dev.quang.identity_service.Dto.Request;

import java.time.LocalDate;

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
    @Size(min = 5, message = "At least 5 characters")
    String password;
    String firstname;
    String lastname;
    LocalDate dob;
}
