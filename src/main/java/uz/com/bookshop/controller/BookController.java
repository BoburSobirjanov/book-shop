package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.bookshop.model.dto.request.book.BookDto;
import uz.com.bookshop.model.dto.response.book.BookResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.service.book.BookService;

import java.security.Principal;
import java.util.UUID;

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






    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @PathVariable UUID id,
            Principal principal
            ){
       return bookService.delete(id, principal);
    }





    @GetMapping("/get-by-id/{id}")
    public StandardResponse<BookResponse> getById(
            @PathVariable UUID id
    ){
        return bookService.getById(id);
    }





    @GetMapping("/get-all-books")
    public Page<BookResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getAllBooks(pageable);
    }





    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<BookResponse> update(
            @RequestBody BookDto bookDto,
            @PathVariable UUID id
    ){
        return bookService.update(bookDto, id);
    }
}
