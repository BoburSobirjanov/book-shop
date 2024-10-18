package uz.com.bookshop.service.comment;

import org.springframework.stereotype.Service;
import uz.com.bookshop.model.dto.request.comment.CommentDto;
import uz.com.bookshop.model.dto.response.comment.CommentResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;

import java.security.Principal;

@Service
public interface CommentService {

    StandardResponse<CommentResponse> save(CommentDto commentDto, Principal principal);
}
