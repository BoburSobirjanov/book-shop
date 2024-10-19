package uz.com.bookshop.service.userpurchases;

import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.userpurchases.UserPurchasesDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponse;

import java.security.Principal;

@Service
public interface UserPurchasesService {

    StandardResponse<UserPurchasesResponse> save(UserPurchasesDto userPurchasesDto, Principal principal);
}
