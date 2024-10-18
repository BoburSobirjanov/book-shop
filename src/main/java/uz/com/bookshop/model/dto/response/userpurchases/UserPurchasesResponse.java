package uz.com.bookshop.model.dto.response.userpurchases;


import lombok.*;
import uz.com.bookshop.model.dto.response.book.BookResponse;
import uz.com.bookshop.model.dto.response.user.UserForFront;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPurchasesResponse {

    private UUID id;

    private UserForFront user;

    private BookResponse book;

    private Double price;
}
