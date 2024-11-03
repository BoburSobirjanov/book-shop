package uz.com.bookshop.mapper;

import org.mapstruct.Mapper;
import uz.com.bookshop.model.dto.response.price.PriceResponseDto;
import uz.com.bookshop.model.entity.price.Price;

@Mapper(componentModel = "spring", uses = {})
public interface PriceMapper extends EntityMapper <Price, PriceResponseDto>{

    PriceResponseDto toDto(Price price);
}
