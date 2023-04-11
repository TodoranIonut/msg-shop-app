package ro.msg.learning.shop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.domain.entity.DailyRevenue;

import java.util.Date;
import java.util.List;

@Repository
public interface DailyRevenueRepository extends JpaRepository<DailyRevenue, Integer> {

    List<DailyRevenue> findAllByLocalDateBetween(Date startDate, Date endDate);
}
