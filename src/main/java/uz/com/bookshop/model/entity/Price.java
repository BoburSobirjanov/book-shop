package uz.com.bookshop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.com.bookshop.model.BaseEntity;


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
}
