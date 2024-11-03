package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.bookshop.model.dto.request.author.AuthorDto;
import uz.com.bookshop.model.dto.response.author.AuthorResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.service.author.AuthorService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;




    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<AuthorResponseDto> save(
            @RequestBody AuthorDto authorDto
            ){
        return  authorService.save(authorDto);
    }





    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @PathVariable UUID id,
            Principal principal
            ){
        return authorService.delete(id, principal);
    }





    @GetMapping("/get-by-id/{id}")
    public StandardResponse<AuthorResponseDto> getById(
            @PathVariable UUID id
    ){
        return authorService.getById(id);
    }





    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<AuthorResponseDto> update(
            @RequestBody AuthorDto authorDto,
            @PathVariable UUID id
    ){
      return   authorService.update(authorDto, id);
    }






    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AuthorResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return authorService.getAll(pageable);
    }
}
