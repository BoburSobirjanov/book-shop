package uz.com.bookshop.mapper;

import org.mapstruct.Mapper;
import uz.com.bookshop.model.dto.response.user.UserForFrontDto;
import uz.com.bookshop.model.entity.user.UserEntity;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserEntity, UserForFrontDto>{
    UserForFrontDto toDto(UserEntity userEntity);
}
