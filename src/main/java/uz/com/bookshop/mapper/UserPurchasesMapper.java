package uz.com.bookshop.mapper;

import org.mapstruct.Mapper;
import uz.com.bookshop.model.dto.response.userpurchases.UserPurchasesResponseDto;
import uz.com.bookshop.model.entity.user.UserPurchases;

@Mapper(componentModel = "spring", uses = {})
public interface UserPurchasesMapper extends EntityMapper<UserPurchases, UserPurchasesResponseDto>{

    UserPurchasesResponseDto toDto(UserPurchases userPurchases);
}
