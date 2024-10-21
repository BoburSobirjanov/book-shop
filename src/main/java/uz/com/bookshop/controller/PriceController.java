package uz.com.bookshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.com.bookshop.model.dto.request.price.PriceDto;
import uz.com.bookshop.model.dto.response.price.PriceResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.service.price.PriceService;

import java.security.Principal;
import java.util.UUID;

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



    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<String> delete(
            @PathVariable UUID id,
            Principal principal
            ){
        return priceService.delete(id, principal);
    }




    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<PriceResponse> getById(
            @PathVariable UUID id
    ){
        return priceService.getById(id);
    }




    @GetMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<PriceResponse> update(
            @PathVariable UUID id,
            @RequestBody PriceDto priceDto
    ){
        return priceService.update(priceDto,id);
    }



    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<PriceResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        return priceService.getAll(pageable);
    }

}
