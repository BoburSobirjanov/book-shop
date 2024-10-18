package uz.com.bookshop.model.dto.request.userpurchases;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPurchasesDto {

    private String user;

    private String book;

    private Double price;

}
