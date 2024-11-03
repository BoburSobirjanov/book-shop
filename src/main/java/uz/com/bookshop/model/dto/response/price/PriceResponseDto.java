package uz.com.bookshop.model.dto.response.price;

import lombok.*;
import uz.com.bookshop.model.dto.response.book.BookResponseDto;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PriceResponseDto {

    private UUID id;

    private Double price;

    private BookResponseDto book;
}
