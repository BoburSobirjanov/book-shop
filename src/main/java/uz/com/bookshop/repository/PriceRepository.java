package uz.com.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.com.bookshop.model.entity.price.Price;

import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Price, UUID> {
}
