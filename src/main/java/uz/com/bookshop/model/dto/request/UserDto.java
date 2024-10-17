package uz.com.bookshop.model.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String fullName;

    private String address;

    private String password;

    private String username;

    private String phoneNumber;

    private String dateOfBirth;

    private String gender;
}
