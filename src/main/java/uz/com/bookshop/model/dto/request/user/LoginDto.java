package uz.com.bookshop.model.dto.request.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {

    private String password;

    private String username;
}
