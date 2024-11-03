package uz.com.bookshop.service.userpurchases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.userpurchases.UserPurchasesDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponseDto;

import java.security.Principal;
import java.util.UUID;

@Service
public interface UserPurchasesService {

    StandardResponse<UserPurchasesResponseDto> save(UserPurchasesDto userPurchasesDto, Principal principal);

    StandardResponse<String> delete(UUID id, Principal principal);

    StandardResponse<UserPurchasesResponseDto> getById(UUID id);

    StandardResponse<UserPurchasesResponseDto> update(UUID id, UserPurchasesDto userPurchasesDto);

    Page<UserPurchasesResponseDto> getAllUserPurchases(Pageable pageable);

    Page<UserPurchasesResponseDto> getUserIdPurchases(Pageable pageable, UUID id);

    Page<UserPurchasesResponseDto> getBookIdPurchases(Pageable pageable, UUID id);
}
