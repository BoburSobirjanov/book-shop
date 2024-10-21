package uz.com.bookshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.price.Price;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Price, UUID> {

    @Query("select u from prices as u where u.isDeleted=false and u.id=?1")
    Optional<Price> findPriceById(UUID id);


    @Query("select u from prices as u where u.isDeleted=false and u.book=?1")
    Optional<Price> findPriceByBookId(Book book);

    @Query("select u from prices as u where u.isDeleted=false")
    Page<Price> findAllPrice(Pageable pageable);
}
