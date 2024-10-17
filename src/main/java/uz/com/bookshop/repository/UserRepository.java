package uz.com.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("select u from users as u where u.isDeleted=false and u.username=?1")
    Optional<UserEntity> findUserEntityByUsername(String username);

    @Query("select u from users as u where u.isDeleted=false and u.phoneNumber=?1")

    Optional<UserEntity> findUserEntityByPhoneNumber(String phoneNumber);
}
