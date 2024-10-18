package uz.com.bookshop.model.dto.request.price;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PriceDto {

    private Double price;

    private String bookId;

}
