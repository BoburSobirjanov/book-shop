package uz.com.bookshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.comment.Comments;
import uz.com.bookshop.model.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {

    @Query("select u from comments as u where u.isDeleted=false and u.id=?1")
    Optional<Comments> findCommentsById(UUID id);


    @Query("select u from comments as u where u.isDeleted=false")
    Page<Comments> findAllComments(Pageable pageable);


    @Query("select u from comments as u where u.isDeleted=false and u.user=?1")
    Page<Comments> findAllByUser(UserEntity user,Pageable pageable);
}
