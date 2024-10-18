package uz.com.bookshop.model.dto.response.book;

import lombok.*;
import uz.com.bookshop.model.dto.response.author.AuthorResponse;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookResponse {

    private UUID id;

    private String name;

    private AuthorResponse authorResponse;

    private Integer pages;

    private String  writtenYear;

    private Integer amount;
}
