package uz.com.bookshop.model.dto.response.book;

import lombok.*;
import uz.com.bookshop.model.entity.Author;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BookResponse {

    private UUID id;

    private String name;

    private Author author;

    private Integer pages;

    private String  writtenYear;

    private Integer amount;
}
