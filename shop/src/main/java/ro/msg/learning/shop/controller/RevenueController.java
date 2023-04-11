package ro.msg.learning.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.domain.entity.DailyRevenue;
import ro.msg.learning.shop.exception.ShopAppException;
import ro.msg.learning.shop.exception.date.IllegalDateFormatExceptions;
import ro.msg.learning.shop.exception.date.IllegalDateStringExceptions;
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

    @GetMapping("/date")
    public ResponseEntity<List<DailyRevenue>> getRevenueByDate(@RequestParam String dateFormat, @RequestParam String date) throws ShopAppException {
        SimpleDateFormat simpleDateFormat = null;
        Date formatedDate = null;
        try{
            simpleDateFormat = new SimpleDateFormat(dateFormat);
            formatedDate = simpleDateFormat.parse(date);
        } catch (IllegalArgumentException | ParseException e){
            if ( e instanceof IllegalArgumentException)
                throw new IllegalDateFormatExceptions();
            else
                throw new IllegalDateStringExceptions();
        }
        List<DailyRevenue> dailyRevenues = revenueService.getDailyRevenueByDate(formatedDate);
        return ResponseEntity.ok().body(dailyRevenues);
    }
}
