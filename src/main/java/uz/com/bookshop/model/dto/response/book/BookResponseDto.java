package uz.com.bookshop.model.dto.response.book;

import lombok.*;
import uz.com.bookshop.model.dto.response.author.AuthorResponseDto;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookResponseDto {

    private UUID id;

    private String name;

    private AuthorResponseDto author;

    private Integer pages;

    private String  writtenYear;

    private Integer amount;
}
