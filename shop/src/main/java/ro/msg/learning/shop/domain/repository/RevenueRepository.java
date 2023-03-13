package ro.msg.learning.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.domain.entity.Revenue;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
}
