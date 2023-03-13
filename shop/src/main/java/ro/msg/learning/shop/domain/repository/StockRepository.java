package ro.msg.learning.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.domain.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
}
