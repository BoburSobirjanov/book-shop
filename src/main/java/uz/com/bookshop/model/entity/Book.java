package uz.com.bookshop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.com.bookshop.model.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "books")
public class Book extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne
    private Author author;

    private Integer pages;

    private String  writtenYear;

    private Integer amount;
}
