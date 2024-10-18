package uz.com.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.user.UserPurchases;

import java.util.UUID;

@Repository
public interface UserPurchasesRepository extends JpaRepository<UserPurchases, UUID> {
}
