package uz.com.bookshop.mapper;

import org.mapstruct.Mapper;
import uz.com.bookshop.model.dto.response.comment.CommentResponseDto;
import uz.com.bookshop.model.entity.comment.Comments;

@Mapper(componentModel = "spring" , uses = {})
public interface CommentMapper extends EntityMapper<Comments, CommentResponseDto>{

    CommentResponseDto toDto(Comments comments);
}
