package ro.msg.learning.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.domain.entity.Stock;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<Stock> findFirstStockByProductId(Integer productId);
    Set<Stock> findAllStockByProductId(Integer productId);
    List<Stock> findAllStockByLocationId(Integer locationId);
    boolean existsStockByProductId(Integer productId);
}
