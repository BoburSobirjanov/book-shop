package uz.com.bookshop.service.comment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.com.bookshop.exception.DataNotFoundException;
import uz.com.bookshop.model.dto.request.comment.CommentDto;
import uz.com.bookshop.model.dto.response.comment.CommentResponse;
import uz.com.bookshop.model.dto.response.standard.StandardResponse;
import uz.com.bookshop.model.dto.response.standard.Status;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.comment.Comments;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.repository.BookRepository;
import uz.com.bookshop.repository.CommentRepository;
import uz.com.bookshop.repository.UserRepository;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    @Override
    public StandardResponse<CommentResponse> save(CommentDto commentDto, Principal principal) {
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
        CommentResponse commentResponse = modelMapper.map(save,CommentResponse.class);

        return StandardResponse.<CommentResponse>builder()
                .status(Status.SUCCESS)
                .message("Comment added!")
                .data(commentResponse)
                .build();

    }
}
