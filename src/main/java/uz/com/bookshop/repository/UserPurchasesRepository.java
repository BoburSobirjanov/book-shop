package uz.com.bookshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.book.Book;
import uz.com.bookshop.model.entity.user.UserEntity;
import uz.com.bookshop.model.entity.user.UserPurchases;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPurchasesRepository extends JpaRepository<UserPurchases, UUID> {


    @Query("select u from user_purchases as u where u.isDeleted=false and u.id=?1")
    Optional<UserPurchases> findUserPurchasesById(UUID id);


    @Query("select u from user_purchases as u where u.isDeleted=false")
    Page<UserPurchases> findAllUserPurchases(Pageable pageable);



    @Query("select u from user_purchases as u where u.isDeleted=false and u.user=?1")
    Page<UserPurchases> findUserPurchasesByUser(UserEntity user,Pageable pageable);


    @Query("select u from user_purchases as u where u.isDeleted=false and u.book=?1")
    Page<UserPurchases> findUserPurchasesByBook(Book book,Pageable pageable);
}
