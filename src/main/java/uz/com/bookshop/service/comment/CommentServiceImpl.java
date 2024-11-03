package uz.com.bookshop.service.comment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.exception.UserBadRequestException;
import uz.com.bookshop.mapper.CommentMapper;
import uz.com.bookshop.model.dto.request.comment.CommentDto;
import uz.com.bookshop.model.dto.response.comment.CommentResponseDto;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.comment.Comments;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.repository.BookRepository;
import uz.com.bookshop.repository.CommentRepository;
import uz.com.bookshop.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {



    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;






    @Override
    public StandardResponse<CommentResponseDto> save(CommentDto commentDto, Principal principal) {
        Optional<Book> book = bookRepository.findBookById(UUID.fromString(commentDto.getBookId()));
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        Comments comments = modelMapper.map(commentDto,Comments.class);
        comments.setRate(commentDto.getRate());
        comments.setMessage(commentDto.getMessage());

        if (book.isEmpty() || user.isEmpty()){
            throw new DataNotFoundException("User or Book not found!");
        }

        comments.setBook(book.get());
        comments.setUser(user.get());
        Comments save = commentRepository.save(comments);
        CommentResponseDto commentResponseDto = modelMapper.map(save, CommentResponseDto.class);

        return StandardResponse.ok("Comment added",commentResponseDto);

    }






    @Override
    public StandardResponse<String> delete(UUID id, Principal principal) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        Optional<Comments> comments = commentRepository.findCommentsById(id);
        if (comments.isEmpty()){
            throw new DataNotFoundException("Comment not found!");
        }
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        comments.get().setDeleted(true);
        comments.get().setDeletedBy(user.get().getId());
        comments.get().setDeletedTime(LocalDateTime.now());
        commentRepository.save(comments.get());

        return StandardResponse.ok("Comment deleted","DELETED");
    }






    @Override
    public StandardResponse<CommentResponseDto> getById(UUID id) {
        Optional<Comments> comments = commentRepository.findCommentsById(id);
        if (comments.isEmpty()){
            throw new DataNotFoundException("Comment not found!");
        }
        CommentResponseDto commentResponseDto = modelMapper.map(comments, CommentResponseDto.class);

        return StandardResponse.ok("This is comment",commentResponseDto);
    }






    @Override
    public StandardResponse<CommentResponseDto> update(CommentDto commentDto, UUID id, Principal principal) {
        Optional<UserEntity> user = userRepository.findUserEntityByUsername(principal.getName());
        Optional<Comments> comments = commentRepository.findCommentsById(id);
        if (comments.isEmpty()){
            throw new DataNotFoundException("Comment not found!");
        }
        if (user.isPresent() && user.get().equals(comments.get().getUser())){
        comments.get().setRate(commentDto.getRate());
        comments.get().setMessage(commentDto.getMessage());
        Comments save = commentRepository.save(comments.get());
        CommentResponseDto commentResponseDto = modelMapper.map(save, CommentResponseDto.class);

        return StandardResponse.ok("Comment updated",commentResponseDto);
        }
        else {
            throw new UserBadRequestException("Can not update this comment!");
        }
    }






    @Override
    public Page<CommentResponseDto> getAll(Pageable pageable) {
        Page<Comments> comments = commentRepository.findAllComments(pageable);

        return comments.map(commentMapper::toDto);
    }







    @Override
    public Page<CommentResponseDto> getUserComments(UUID id, Pageable pageable) {
        Optional<UserEntity> user = userRepository.findUserEntityById(id);
        if (user.isEmpty()){
            throw new DataNotFoundException("User not found!");
        }
        Page<Comments> comments =  commentRepository.findAllByUser(user.get(),pageable);
        return comments.map(commentMapper::toDto);
    }
}
