package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.bookshop.model.dto.request.user.UserDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.user.UserForFrontDto;
import uz.com.bookshop.service.user.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {


    private final UserService userService;




    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<UserForFrontDto> getById(
            @PathVariable UUID id
            ){
        return userService.getById(id);
    }





    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @PathVariable UUID id,
            Principal principal
    ){
        return userService.delete(id, principal);
    }





    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserForFrontDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }




    @PutMapping("/{id}/assign-to-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<UserForFrontDto> assignToAdmin(
            @PathVariable UUID id
    ){
        return userService.assignToAdmin(id);
    }





    @PutMapping("/{id}/update")
    public StandardResponse<UserForFrontDto> update(
            @RequestBody UserDto userDto,
            @PathVariable UUID id
            ){
        return userService.update(userDto, id);
    }
}
