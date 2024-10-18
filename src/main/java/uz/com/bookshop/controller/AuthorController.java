package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.bookshop.model.dto.request.author.AuthorDto;
import uz.com.bookshop.model.dto.response.author.AuthorResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.service.author.AuthorService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;


    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<AuthorResponse> save(
            @RequestBody AuthorDto authorDto
            ){
        return  authorService.save(authorDto);
    }
}
