package uz.com.bookshop.mapper;

import org.mapstruct.Mapper;
import uz.com.bookshop.model.dto.response.book.BookResponseDto;
import uz.com.bookshop.model.entity.book.Book;

@Mapper(componentModel = "spring", uses = {})
public interface BookMapper extends EntityMapper<Book, BookResponseDto>{

    BookResponseDto toDto(Book book);
}
