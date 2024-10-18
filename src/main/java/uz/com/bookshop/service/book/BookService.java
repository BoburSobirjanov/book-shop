package uz.com.bookshop.service.book;

import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.book.BookDto;
import uz.com.bookshop.model.dto.response.book.BookResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

@Service
public interface BookService {

    StandardResponse<BookResponse> save(BookDto bookDto);
}
