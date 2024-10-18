package uz.com.bookshop.model.dto.response.author;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorResponse {

    private UUID id;

    private String fullName;

    private String address;

    private LocalDate dateOfBirth;

}
