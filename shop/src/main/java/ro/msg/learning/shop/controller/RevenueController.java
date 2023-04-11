package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.domain.entity.DailyRevenue;
import ro.msg.learning.shop.service.revenue.RevenueService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/revenue")
public class RevenueController {

    private final RevenueService revenueService;

    @GetMapping("/daily")
    public ResponseEntity<List<DailyRevenue>> getRevenueForToday(@RequestParam String dateFormat, @RequestParam String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Date formatedDate = simpleDateFormat.parse(date);
        List<DailyRevenue> dailyRevenues = revenueService.getDailyRevenueByDate(formatedDate);
        return ResponseEntity.ok().body(dailyRevenues);
    }
}
