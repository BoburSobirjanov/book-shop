package uz.com.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.comment.Comments;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {
}
