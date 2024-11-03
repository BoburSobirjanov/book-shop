package uz.com.bookshop.service.price;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.price.PriceDto;
import uz.com.bookshop.model.dto.response.price.PriceResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

import java.security.Principal;
import java.util.UUID;

@Service
public interface PriceService {


    StandardResponse<PriceResponseDto> save(PriceDto priceDto, Principal principal);

    StandardResponse<String> delete(UUID id, Principal principal);


    StandardResponse<PriceResponseDto> getById(UUID id);


    StandardResponse<PriceResponseDto> update(PriceDto priceDto, UUID id);

    Page<PriceResponseDto> getAll(Pageable pageable);
}
