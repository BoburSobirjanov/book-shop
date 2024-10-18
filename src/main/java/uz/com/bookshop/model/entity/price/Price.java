package uz.com.bookshop.model.entity.price;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.com.bookshop.model.entity.BaseEntity;
import uz.com.bookshop.model.entity.book.Book;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "prices")
public class Price extends BaseEntity {

    private Double price;

    @OneToOne
    private Book book;

    private UUID createdBy;
}
