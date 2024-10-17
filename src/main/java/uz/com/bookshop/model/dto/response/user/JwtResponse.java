package uz.com.bookshop.model.dto.response.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private UserForFront userForFront;
}
