package com.foodapp.foodorderingapp.service.statistic;

import com.foodapp.foodorderingapp.dto.statistic.StatisticModelRes;
import com.foodapp.foodorderingapp.entity.Category;
import com.foodapp.foodorderingapp.entity.Order;
import com.foodapp.foodorderingapp.entity.Restaurant;
import com.foodapp.foodorderingapp.enumeration.OrderStatus;
import com.foodapp.foodorderingapp.repository.OrderJpaRepository;
import com.foodapp.foodorderingapp.repository.RestaurantJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    public List<Map<String, Object>> getCategoryPercentages(Long restaurantId) {
        Restaurant restaurant = restaurantJpaRepository.findById(restaurantId).orElseThrow(() -> new IllegalArgumentException("Not found restaurant with id "));
        List<Category> categories = restaurant.getCategories();
        Map<String, Double> categoryTotals = new HashMap<>();
        double totalQuantity = 0;
        // Calculate total quantity and quantity per category
        for (Category category: categories) {
            double currentCategoryQuantity = categoryTotals.getOrDefault(category.getName(), 0.0);
            categoryTotals.put(category.getName(), currentCategoryQuantity + category.getDishes().size());
            totalQuantity += category.getDishes().size(); // Accumulate the total quantity
        }

        // Prepare pieData format
        List<Map<String, Object>> pieData = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            Map<String, Object> categoryData = new HashMap<>();
            categoryData.put("name", entry.getKey());
            categoryData.put("value", (entry.getValue() * 100.0) / totalQuantity);
            pieData.add(categoryData);
        }

        return pieData;
    }
    public StatisticModelRes getOrderStatistic(Long restaurantId){
        StatisticModelRes statisticModelRes = new StatisticModelRes();
        Restaurant restaurant = restaurantJpaRepository.findById(restaurantId).orElseThrow(() -> new IllegalArgumentException("Not found restaurant with id "));
        List<Order> thisMonthOrders = getThisMonthOrderList(restaurant);
        List<Order> lastMonthOrders = getLastMonthOrderList(restaurant);
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> totalPrice1 = new AtomicReference<>(BigDecimal.ZERO);
        AtomicInteger totalQuantity = new AtomicInteger(0);
        AtomicInteger cancelQuantity = new AtomicInteger(0);
        AtomicInteger totalQuantity1 = new AtomicInteger(0);
        AtomicInteger cancelQuantity1 = new AtomicInteger(0);

        thisMonthOrders.forEach(order -> {
            totalPrice.updateAndGet(v -> v.add(order.getPrice()));
            totalQuantity.incrementAndGet();
            if (order.getOrderStatus() == OrderStatus.CANCELED) {
                cancelQuantity.incrementAndGet();
            }
        });
        lastMonthOrders.forEach(order -> {
            totalPrice1.updateAndGet(v -> v.add(order.getPrice()));
            totalQuantity1.incrementAndGet();
            if (order.getOrderStatus() == OrderStatus.CANCELED) {
                cancelQuantity1.incrementAndGet();

            }
        });
        totalQuantity.addAndGet(thisMonthOrders.size());
        totalQuantity1.addAndGet(lastMonthOrders.size());

        double lastTotalPrice = Math.max(totalPrice1.get().doubleValue(), 1);
        int lastTotalQuantity = Math.max(totalQuantity.get(), 1);
        int lastCancelQuantity = Math.max(cancelQuantity1.get(), 1);

        double totalPricePercent = (totalPrice.get().doubleValue() * 100.0) / lastTotalPrice;
        double totalQuantityPercent = (totalQuantity.get() * 100.0) / lastTotalQuantity;
        double cancelQuantityPercent = (cancelQuantity.get() * 100.0) / lastCancelQuantity;

        String totalPricePercentChange = calculatePercentageChange(totalPricePercent);
        String totalQuantityPercentPercentChange = calculatePercentageChange(totalQuantityPercent);
        String cancelQuantityPercentChange = calculatePercentageChange(cancelQuantityPercent);

        statisticModelRes.setTotalPrice(totalPrice.get().doubleValue());
        statisticModelRes.setCancelQuantity(cancelQuantity.get());
        statisticModelRes.setTotalQuantity(totalQuantity.get());

        statisticModelRes.setTotalPrice1(totalPrice1.get().doubleValue());
        statisticModelRes.setCancelQuantity1(cancelQuantity1.get());
        statisticModelRes.setTotalQuantity1(totalQuantity1.get());


        statisticModelRes.setTotalQuantityPercentChange(totalQuantityPercentPercentChange);
        statisticModelRes.setCancelQuantityPercentChange(cancelQuantityPercentChange);
        statisticModelRes.setTotalPricePercentChange(totalPricePercentChange);

        return statisticModelRes;
    }

    private List<Order> getThisMonthOrderList(Restaurant restaurant){
        LocalDateTime currentDate = LocalDateTime.now();

        // Get the first and last date of the current month
        LocalDateTime firstDayOfThisMonth = currentDate.withDayOfMonth(1);
        LocalDateTime lastDayOfThisMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth());

        return orderJpaRepository.findByRestaurantAndCreatedAtBetween(
                restaurant,
                firstDayOfThisMonth,
                lastDayOfThisMonth
        );

    }
    private List<Order> getLastMonthOrderList(Restaurant restaurant){
        LocalDateTime currentDate = LocalDateTime.now();

        // Get the first and last date of the current month
        LocalDateTime firstDayOfThisMonth = currentDate.minusMonths(1).withDayOfMonth(1);
        LocalDateTime lastDayOfThisMonth = currentDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        return orderJpaRepository.findByRestaurantAndCreatedAtBetween(
                restaurant,
                firstDayOfThisMonth,
                lastDayOfThisMonth
        );

    }
    private String calculatePercentageChange(double percentage) {
        if (percentage > 100) {
            // Calculate the increase
            double increase = percentage - 100;
            return "Increase " + String.format("%.2f", increase) + "%";
        } else if (percentage < 100) {
            // Calculate the decrease
            double decrease = 100 - percentage;
            return "Decrease " + String.format("%.2f", decrease) + "%";
        } else {
            // No change
            return "Not change";
        }
    }


}


