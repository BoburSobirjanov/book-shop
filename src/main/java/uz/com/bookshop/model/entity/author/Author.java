package uz.com.bookshop.model.entity.author;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.com.bookshop.model.entity.BaseEntity;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "authors")
public class Author extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate dateOfBirth;
}
