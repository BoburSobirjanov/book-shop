package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.bookshop.model.dto.request.comment.CommentDto;
import uz.com.bookshop.model.dto.response.comment.CommentResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.service.comment.CommentService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;



    @PostMapping("/save")
    public StandardResponse<CommentResponseDto> save(
            @RequestBody CommentDto commentDto,
            Principal principal
             ){
        return commentService.save(commentDto, principal);
    }





    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @PathVariable UUID id,
            Principal principal
    ){
        return commentService.delete(id, principal);
    }





    @GetMapping("/get-by-id/{id}")
    public StandardResponse<CommentResponseDto> getById(
            @PathVariable UUID id
    ){
        return commentService.getById(id);
    }





    @GetMapping("/get-all-comments")
    public Page<CommentResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return commentService.getAll(pageable);
    }





    @PutMapping("/{id}/update")
    public StandardResponse<CommentResponseDto> update(
            @RequestBody CommentDto commentDto,
            @PathVariable UUID id,
            Principal principal
    ){
        return commentService.update(commentDto, id, principal);
    }




    @GetMapping("/{id}/get-all-comments")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CommentResponseDto> getUserComments(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        return commentService.getUserComments(id,pageable);
    }
}
