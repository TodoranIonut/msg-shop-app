package ro.msg.learning.shop.service.revenue;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.domain.entity.DailyRevenue;
import ro.msg.learning.shop.domain.entity.Location;
import ro.msg.learning.shop.domain.entity.Revenue;
import ro.msg.learning.shop.domain.repository.DailyRevenueRepository;
import ro.msg.learning.shop.domain.repository.RevenueRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RevenueServiceImpl implements RevenueService {

    private final RevenueRepository revenueRepository;
    private final DailyRevenueRepository dailyRevenueRepository;

    @Override
    public List<DailyRevenue> createDailyRevenueForAllLocations(Date date) {
        List<DailyRevenue> dailyRevenueByLocation = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();
        Set<Revenue> revenueFromDate = revenueRepository.findAllByLocalDateBetween(startDate, endDate);
        Set<Location> revenueUniqueLocations = revenueFromDate.stream()
                .map(Revenue::getLocation)
                .collect(Collectors.toSet());

        for (Location location : revenueUniqueLocations) {
            DailyRevenue dailyRevenue = new DailyRevenue();
            dailyRevenue.setLocation(location);
            dailyRevenue.setSum(BigDecimal.valueOf(0));
            dailyRevenue.setLocalDate(startDate);
            for (Revenue revenue : revenueFromDate) {
                if (revenue.getLocation().equals(location)){
                    BigDecimal sum = dailyRevenue.getSum().add(revenue.getSum());
                    dailyRevenue.setSum(sum);
                }
            }
            dailyRevenueByLocation.add(dailyRevenue);
        }

        return dailyRevenueRepository.saveAll(dailyRevenueByLocation);
    }

    @Override
    public List<DailyRevenue> getDailyRevenueByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();
        return dailyRevenueRepository.findAllByLocalDateBetween(startDate,endDate);
    }
}
