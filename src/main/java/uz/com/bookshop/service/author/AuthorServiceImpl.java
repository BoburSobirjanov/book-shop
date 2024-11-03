package uz.com.bookshop.service.author;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.mapper.AuthorMapper;
import uz.com.bookshop.model.dto.request.author.AuthorDto;
import uz.com.bookshop.model.dto.response.author.AuthorResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.entity.author.Author;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.repository.AuthorRepository;
import uz.com.bookshop.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {



    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthorMapper authorMapper;






    @Override
    public StandardResponse<AuthorResponseDto> save(AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto, Author.class);
        author.setFullName(authorDto.getFullName());
        author.setAddress(authorDto.getAddress());
        author.setDateOfBirth(LocalDate.parse(authorDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        Author save = authorRepository.save(author);
        AuthorResponseDto authorResponseDto = modelMapper.map(save, AuthorResponseDto.class);

        return StandardResponse.ok("Author saved",authorResponseDto);
    }






    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        Optional<Author> author = authorRepository.findAuthorById(id);
        if (author.isEmpty()){
            throw new DataNotFoundException("Author not found!");
        }
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        author.get().setDeleted(true);
        author.get().setDeletedBy(user.get().getId());
        author.get().setDeletedTime(LocalDateTime.now());
        authorRepository.save(author.get());

        return StandardResponse.ok("Author deleted!","DELETED");
    }






    @Override
    public StandardResponse<AuthorResponseDto> getById(UUID id) {
        Optional<Author> author = authorRepository.findAuthorById(id);
        if (author.isEmpty()){
            throw new DataNotFoundException("Author not found!");
        }
        AuthorResponseDto authorResponseDto = modelMapper.map(author, AuthorResponseDto.class);

        return StandardResponse.ok("This is author",authorResponseDto);
    }






    @Override
    public StandardResponse<AuthorResponseDto> update(AuthorDto authorDto, UUID id) {
        Optional<Author> author = authorRepository.findAuthorById(id);
        if (author.isEmpty()){
            throw new DataNotFoundException("Author not found!");
        }
        author.get().setFullName(authorDto.getFullName());
        author.get().setAddress(authorDto.getAddress());
        author.get().setDateOfBirth(LocalDate.parse(authorDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        Author save = authorRepository.save(author.get());
        AuthorResponseDto authorResponseDto = modelMapper.map(save, AuthorResponseDto.class);

        return StandardResponse.ok("Author updated!",authorResponseDto);
    }






    @Override
    public Page<AuthorResponseDto> getAll(Pageable pageable) {
        Page<Author> authors = authorRepository.findAllAuthors(pageable);

        return authors.map(authorMapper::toDto);
    }
}
