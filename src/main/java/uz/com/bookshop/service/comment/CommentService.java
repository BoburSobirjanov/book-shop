package uz.com.bookshop.service.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.comment.CommentDto;
import uz.com.bookshop.model.dto.response.comment.CommentResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

import java.security.Principal;
import java.util.UUID;

@Service
public interface CommentService {

    StandardResponse<CommentResponseDto> save(CommentDto commentDto, Principal principal);

    StandardResponse<String> delete(UUID id, Principal principal);

    StandardResponse<CommentResponseDto> getById(UUID id);

    StandardResponse<CommentResponseDto> update(CommentDto commentDto, UUID id, Principal principal);

    Page<CommentResponseDto> getAll(Pageable pageable);


    Page<CommentResponseDto> getUserComments(UUID id, Pageable pageable);
}
