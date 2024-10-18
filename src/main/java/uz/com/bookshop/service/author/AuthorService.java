package uz.com.bookshop.service.author;

import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.author.AuthorDto;
import uz.com.bookshop.model.dto.response.author.AuthorResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

@Service
public interface AuthorService {

    StandardResponse<AuthorResponse> save(AuthorDto authorDto);

}
