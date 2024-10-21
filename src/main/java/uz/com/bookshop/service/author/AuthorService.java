package uz.com.bookshop.service.author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.author.AuthorDto;
import uz.com.bookshop.model.dto.response.author.AuthorResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

import java.security.Principal;
import java.util.UUID;

@Service
public interface AuthorService {

    StandardResponse<AuthorResponse> save(AuthorDto authorDto);

    StandardResponse<String> delete(UUID id, Principal principal);

    StandardResponse<AuthorResponse> getById(UUID id);

    StandardResponse<AuthorResponse> update(AuthorDto authorDto, UUID id);

    Page<AuthorResponse> getAll(Pageable pageable);

}
