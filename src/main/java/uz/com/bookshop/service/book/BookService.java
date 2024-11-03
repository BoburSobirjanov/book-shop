package uz.com.bookshop.service.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.book.BookDto;
import uz.com.bookshop.model.dto.response.book.BookResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

import java.security.Principal;
import java.util.UUID;

@Service
public interface BookService {

    StandardResponse<BookResponseDto> save(BookDto bookDto);

    StandardResponse<String> delete(UUID id, Principal principal);

    StandardResponse<BookResponseDto> getById(UUID id);

    Page<BookResponseDto> getAllBooks(Pageable pageable);

    StandardResponse<BookResponseDto> update(BookDto bookDto, UUID id);
}
