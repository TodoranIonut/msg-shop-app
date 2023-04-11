package ro.msg.learning.shop.service.revenue;

import ro.msg.learning.shop.domain.entity.DailyRevenue;

import java.util.Date;
import java.util.List;

public interface RevenueService {

    List<DailyRevenue> createDailyRevenueForAllLocations(Date date);
    List<DailyRevenue> getDailyRevenueByDate(Date date);
}
