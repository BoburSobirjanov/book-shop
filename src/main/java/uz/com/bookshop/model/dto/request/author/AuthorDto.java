package uz.com.bookshop.model.dto.request.author;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthorDto {

    private String fullName;

    private String address;

    private String dateOfBirth;

}
