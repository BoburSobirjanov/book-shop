package uz.com.bookshop.model.dto.response.userpurchases;


import lombok.*;
import uz.com.bookshop.model.dto.response.book.BookResponseDto;
import uz.com.bookshop.model.dto.response.user.UserForFrontDto;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPurchasesResponseDto {

    private UUID id;

    private UserForFrontDto user;

    private BookResponseDto book;

    private Double price;
}
