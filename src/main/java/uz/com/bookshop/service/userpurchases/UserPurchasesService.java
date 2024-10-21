package uz.com.bookshop.service.userpurchases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.userpurchases.UserPurchasesDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponse;
import uz.com.bookshop.model.entity.user.UserEntity;

import java.security.Principal;
import java.util.UUID;

@Service
public interface UserPurchasesService {

    StandardResponse<UserPurchasesResponse> save(UserPurchasesDto userPurchasesDto, Principal principal);

    StandardResponse<String> delete(UUID id, Principal principal);

    StandardResponse<UserPurchasesResponse> getById(UUID id);

    StandardResponse<UserPurchasesResponse> update(UUID id,UserPurchasesDto userPurchasesDto);

    Page<UserPurchasesResponse> getAllUserPurchases(Pageable pageable);

    Page<UserPurchasesResponse> getUserIdPurchases(Pageable pageable,UUID id);

    Page<UserPurchasesResponse> getBookIdPurchases(Pageable pageable,UUID id);
}
