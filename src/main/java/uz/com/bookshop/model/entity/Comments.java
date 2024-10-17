package uz.com.bookshop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.com.bookshop.model.BaseEntity;
import uz.com.bookshop.model.entity.user.UserEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "comments")
public class Comments extends BaseEntity {

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Integer rate;

    @ManyToOne
    private Book book;

    @ManyToOne
    private UserEntity user;
}
