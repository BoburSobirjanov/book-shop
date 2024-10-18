package uz.com.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.author.Author;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID>{

    @Query("select u from authors as u where u.isDeleted=false and u.id=?1")
    Optional<Author> findAuthorById(UUID id);

}