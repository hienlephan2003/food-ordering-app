package com.foodapp.foodorderingapp.dto.dish_type;
import java.util.List;
import com.foodapp.foodorderingapp.dto.dish.FeaturedDish;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DishTypeOverview {
    private long id;
    private String name;
    private List<FeaturedDish> dishes;

}
