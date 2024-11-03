package uz.com.bookshop.model.dto.response.comment;

import lombok.*;
import uz.com.bookshop.model.dto.response.book.BookResponseDto;
import uz.com.bookshop.model.dto.response.user.UserForFrontDto;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponseDto {

    private UUID id;

    private String message;

    private Integer rate;

    private BookResponseDto book;

    private UserForFrontDto user;
}
