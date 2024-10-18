package uz.com.bookshop.model.dto.request.book;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookDto {

    private String name;

    private String author;

    private Integer pages;

    private String  writtenYear;

    private Integer amount;

}
