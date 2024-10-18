package uz.com.bookshop.service.price;

import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.price.PriceDto;
import uz.com.bookshop.model.dto.response.price.PriceResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

import java.security.Principal;

@Service
public interface PriceService {

    StandardResponse<PriceResponse> save(PriceDto priceDto, Principal principal);
}
