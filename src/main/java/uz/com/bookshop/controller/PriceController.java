package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.com.bookshop.model.dto.request.price.PriceDto;
import uz.com.bookshop.model.dto.response.price.PriceResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.service.price.PriceService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/price")
public class PriceController {

    private final PriceService priceService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<PriceResponse> save(
            @RequestBody PriceDto priceDto,
            Principal principal
            ){
       return priceService.save(priceDto, principal);
    }

}
