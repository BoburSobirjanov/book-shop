package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.bookshop.model.dto.request.userpurchases.UserPurchasesDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponse;
import uz.com.bookshop.service.userpurchases.UserPurchasesService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-purchases")
public class UserPurchasesController {

    private final UserPurchasesService userPurchasesService;




    @PostMapping("/save")
    public StandardResponse<UserPurchasesResponse> save(
            @RequestBody UserPurchasesDto userPurchasesDto,
            Principal principal
            ){
        return userPurchasesService.save(userPurchasesDto, principal);
    }





    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @PathVariable UUID id,
            Principal principal
            ){
       return userPurchasesService.delete(id, principal);
    }






    @GetMapping("/get-by-id/{id}")
    public StandardResponse<UserPurchasesResponse> getById(
            @PathVariable UUID id
    ){
        return userPurchasesService.getById(id);
    }






    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserPurchasesResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return userPurchasesService.getAllUserPurchases(pageable);
    }






    @GetMapping("/{id}/get-all-userid")
    public Page<UserPurchasesResponse> getUserIdPurchases(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return userPurchasesService.getUserIdPurchases(pageable,id);
    }





    @GetMapping("/{id}/get-all-bookid")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserPurchasesResponse> getBookIdPurchases(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return userPurchasesService.getBookIdPurchases(pageable,id);
    }





    @PutMapping("/{id}/update")
    public StandardResponse<UserPurchasesResponse> update(
            @RequestBody UserPurchasesDto userPurchasesDto,
            @PathVariable UUID id
    ){
        return userPurchasesService.update(id, userPurchasesDto);
    }

}
