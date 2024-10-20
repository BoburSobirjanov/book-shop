package uz.com.bookshop.model.dto.response.user;

import lombok.*;
import uz.com.bookshop.model.entity.user.Gender;
import uz.com.bookshop.model.entity.user.UserRole;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserForFront {

    private UUID id;

    private String fullName;

    private String username;

    private String phoneNumber;

    private String address;

    private UserRole role;

    private LocalDate dateOfBirth;

    private Gender gender;

    private Integer age;
}
