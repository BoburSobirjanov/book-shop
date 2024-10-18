package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.bookshop.model.dto.request.book.BookDto;
import uz.com.bookshop.model.dto.response.book.BookResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.service.book.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;


    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<BookResponse> save(
            @RequestBody BookDto bookDto
            ){
        return bookService.save(bookDto);
    }
}
