package uz.com.bookshop.model.dto.response.standard;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StandardResponse <T> {

    private String message;

    private Status status;

    private T data;
}
