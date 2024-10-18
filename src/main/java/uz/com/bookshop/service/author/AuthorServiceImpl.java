package uz.com.bookshop.service.author;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.author.AuthorDto;
import uz.com.bookshop.model.dto.response.author.AuthorResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.standard.Status;
import uz.com.bookshop.model.entity.author.Author;
import uz.com.bookshop.repository.AuthorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;


    @Override
    public StandardResponse<AuthorResponse> save(AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto, Author.class);
        author.setFullName(authorDto.getFullName());
        author.setAddress(authorDto.getAddress());
        author.setDateOfBirth(LocalDate.parse(authorDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        Author save = authorRepository.save(author);
        AuthorResponse authorResponse = modelMapper.map(save, AuthorResponse.class);

        return StandardResponse.<AuthorResponse>builder()
                .status(Status.SUCCESS)
                .message("Author saved!")
                .data(authorResponse)
                .build();
    }
}
