package uz.com.bookshop.model.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.com.bookshop.model.BaseEntity;
import uz.com.bookshop.model.entity.Book;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "user_purchases")
public class UserPurchases extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private Book book;

    private Double price;
}
