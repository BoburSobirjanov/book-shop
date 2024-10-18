package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.bookshop.model.dto.request.user.LoginDto;
import uz.com.bookshop.model.dto.request.user.UserDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.user.JwtResponse;
import uz.com.bookshop.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;


    @PostMapping("/sign-up")
    public StandardResponse<JwtResponse> signUp(
            @RequestBody UserDto userDto
            ){
        return userService.signUp(userDto);
    }




    @PostMapping("/sign-in")
    public StandardResponse<JwtResponse> signIn(
            @RequestBody LoginDto loginDto
    ){
        return userService.signIn(loginDto);
    }
}
