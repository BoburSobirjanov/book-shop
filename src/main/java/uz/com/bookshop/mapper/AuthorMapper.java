package uz.com.bookshop.mapper;

import org.mapstruct.Mapper;
import uz.com.bookshop.model.dto.response.author.AuthorResponseDto;
import uz.com.bookshop.model.entity.author.Author;

@Mapper(componentModel = "spring",uses = {})
public interface AuthorMapper extends EntityMapper<Author, AuthorResponseDto>{

    AuthorResponseDto toDto(Author author);
}
