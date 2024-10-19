package uz.com.bookshop.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.exception.NotAcceptableException;
import uz.com.bookshop.exception.UserBadRequestException;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.standard.Status;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<StandardResponse<String>> dataNotFoundExceptionHandler(
            DataNotFoundException e){
        return ResponseEntity.status(404).body(StandardResponse.<String>builder().status(Status.ERROR).message(e.getMessage()).build());

    }

    @ExceptionHandler(value = {UserBadRequestException.class})
    public ResponseEntity<StandardResponse<String>> userBadRequestExceptionHandler(
            UserBadRequestException e){
        return ResponseEntity.status(400).body(StandardResponse.<String>builder().status(Status.ERROR).message(e.getMessage()).build());
    }


    @ExceptionHandler(value = {NotAcceptableException.class})
    public ResponseEntity<StandardResponse<String>> notAcceptableExceptionHandler(
            NotAcceptableException e){
        return ResponseEntity.status(406).body(StandardResponse.<String>builder().status(Status.ERROR).message(e.getMessage()).build());
    }
}
