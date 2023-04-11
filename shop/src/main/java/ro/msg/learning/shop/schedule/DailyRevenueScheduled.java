package ro.msg.learning.shop.schedule;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.service.revenue.RevenueService;

import java.util.Date;

@Component
@EnableScheduling
@AllArgsConstructor
@Transactional
public class DailyRevenueScheduled {

    private final RevenueService revenueService;

    @Scheduled(cron = "59 59 23 * * *")
    public void createRevenueByLocationAtTheEndOfTheDay(){
        revenueService.createDailyRevenueForAllLocations(new Date());
    }
}
