package uz.com.bookshop.service.book;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.mapper.BookMapper;
import uz.com.bookshop.model.dto.request.book.BookDto;
import uz.com.bookshop.model.dto.response.author.AuthorResponseDto;
import uz.com.bookshop.model.dto.response.book.BookResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.entity.author.Author;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.repository.AuthorRepository;
import uz.com.bookshop.repository.BookRepository;
import uz.com.bookshop.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{


    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final BookMapper bookMapper;




    @Override
    public StandardResponse<BookResponseDto> save(BookDto bookDto) {
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

        BookResponseDto bookResponseDto = modelMapper.map(save, BookResponseDto.class);

        return StandardResponse.ok("Book added!",bookResponseDto);
    }






    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        Optional<Book> book = bookRepository.findBookById(id);
        if (book.isEmpty()){
            throw new DataNotFoundException("Book not found!");
        }
        book.get().setDeleted(true);
        book.get().setDeletedTime(LocalDateTime.now());
        book.get().setDeletedBy(user.get().getId());
        bookRepository.save(book.get());

        return StandardResponse.ok("Book deleted!","DELETED");
    }







    @Override
    public StandardResponse<BookResponseDto> getById(UUID id) {
        Optional<Book> book = bookRepository.findBookById(id);
        if (book.isEmpty()){
            throw new DataNotFoundException("Book not found!");
        }
        BookResponseDto bookResponseDto = modelMapper.map(book.get(), BookResponseDto.class);
        return StandardResponse.ok("This is book",bookResponseDto);
    }








    @Override
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        Page<Book> books = bookRepository.findAllBooks(pageable);

        if (books.isEmpty()){
            throw new DataNotFoundException("Books not found!");
        }

        return books.map(bookMapper::toDto);
    }







    @Override
    public StandardResponse<BookResponseDto> update(BookDto bookDto, UUID id) {
        Optional<Book> book = bookRepository.findBookById(id);
        if (book.isEmpty()){
            throw new DataNotFoundException("Book not found!");
        }
        book.get().setName(bookDto.getName());
        book.get().setPages(bookDto.getPages());
        book.get().setWrittenYear(bookDto.getWrittenYear());
        book.get().setAmount(bookDto.getAmount());
        Optional<Author> author = authorRepository.findAuthorById(UUID.fromString(bookDto.getAuthor()));
        if (author.isEmpty()){
            throw new DataNotFoundException("Author not found!");
        }
        book.get().setAuthor(author.get());
        Book save = bookRepository.save(book.get());

        BookResponseDto bookResponseDto = modelMapper.map(save, BookResponseDto.class);

        return StandardResponse.ok("Book updated!",bookResponseDto);
    }
}
