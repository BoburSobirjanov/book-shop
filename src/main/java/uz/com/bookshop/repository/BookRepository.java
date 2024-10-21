package uz.com.bookshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.book.Book;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("select u from books as u where u.isDeleted=false and u.id=?1")
    Optional<Book> findBookById(UUID id);

    @Query("select u from books as u where u.isDeleted=false")
    Page<Book> findAllBooks(Pageable pageable);
}
