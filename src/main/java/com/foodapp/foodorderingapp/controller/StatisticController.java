package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.statistic.StatisticModelRes;
import com.foodapp.foodorderingapp.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/categories_percent/{restaurantId}")
    public List<Map<String, Object>> getCategoryPercentages(@PathVariable Long restaurantId){
        return statisticService.getCategoryPercentages(restaurantId);
    }
    @GetMapping("/order/{restaurantId}")
    public StatisticModelRes getOrderStatistic(@PathVariable Long restaurantId){
        return statisticService.getOrderStatistic(restaurantId);
    }
}
