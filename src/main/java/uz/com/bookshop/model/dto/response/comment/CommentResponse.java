package uz.com.bookshop.model.dto.response.comment;

import lombok.*;
import uz.com.bookshop.model.dto.response.book.BookResponse;
import uz.com.bookshop.model.dto.response.user.UserForFront;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponse {

    private UUID id;

    private String message;

    private Integer rate;

    private BookResponse book;

    private UserForFront user;
}
