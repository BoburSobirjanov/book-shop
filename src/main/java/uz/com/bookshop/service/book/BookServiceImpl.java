package uz.com.bookshop.service.book;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.model.dto.request.book.BookDto;
import uz.com.bookshop.model.dto.response.book.BookResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.standard.Status;
import uz.com.bookshop.model.entity.author.Author;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.repository.AuthorRepository;
import uz.com.bookshop.repository.BookRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;


    @Override
    public StandardResponse<BookResponse> save(BookDto bookDto) {
        Book book = modelMapper.map(bookDto,Book.class);
        book.setName(bookDto.getName());
        book.setPages(bookDto.getPages());
        book.setWrittenYear(bookDto.getWrittenYear());
        book.setAmount(bookDto.getAmount());
        Optional<Author> author = authorRepository.findAuthorById(UUID.fromString(bookDto.getAuthor()));
        if (author.isEmpty()){
            throw new DataNotFoundException("Author not found!");
        }
        book.setAuthor(author.get());
        Book save = bookRepository.save(book);

        BookResponse bookResponse = modelMapper.map(save,BookResponse.class);

        return StandardResponse.<BookResponse>builder()
                .status(Status.SUCCESS)
                .message("Book added!")
                .data(bookResponse)
                .build();
    }
}
