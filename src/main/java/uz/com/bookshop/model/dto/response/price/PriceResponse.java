package uz.com.bookshop.model.dto.response.price;

import lombok.*;
import uz.com.bookshop.model.dto.response.book.BookResponse;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PriceResponse {

    private UUID id;

    private Double price;

    private BookResponse book;
}
