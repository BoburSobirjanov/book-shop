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

    public static <T> StandardResponse<T> ok(String message, T data) {
        return StandardResponse.<T>builder()
                .status(Status.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }
}
