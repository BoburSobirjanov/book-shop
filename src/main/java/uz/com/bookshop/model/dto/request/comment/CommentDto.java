package uz.com.bookshop.model.dto.request.comment;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {

    private String message;

    private Integer rate;

    private String bookId;

    private String userId;

}
