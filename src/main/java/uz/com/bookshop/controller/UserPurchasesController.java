package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.bookshop.model.dto.request.userpurchases.UserPurchasesDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponse;
import uz.com.bookshop.service.userpurchases.UserPurchasesService;

import java.security.Principal;

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

}
