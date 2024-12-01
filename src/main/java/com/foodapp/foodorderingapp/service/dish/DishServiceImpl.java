package com.foodapp.foodorderingapp.service.dish;

import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dish.DishResponse;
import com.foodapp.foodorderingapp.dto.dish.DishSearch;
import com.foodapp.foodorderingapp.dto.group_option.GroupOptionResponse;
import com.foodapp.foodorderingapp.entity.*;
import com.foodapp.foodorderingapp.enumeration.DishStatus;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final CategoryJpaRepository categoryJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final DishJpaRepository dishJpaRepository;
    private final DishTypeJpaRepository dishTypeJpaRepository;
    private final GroupOptionJpaRepository groupOptionJpaRepository;
    private final Dish_GroupOptionJpaRepository dish_groupOptionJpaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DishResponse getDishById(long dishId) throws DataNotFoundException {
        Optional<Dish> dish = dishJpaRepository.findById(dishId);
        if (dish.isPresent()) {
            List<String> imageUrls = fetchImageUrls(dish.get().getName());
            if (imageUrls.size() >= 3) {
                dish.get().setImageUrl(String.join(", ", imageUrls.subList(0, 3)));
            } else {
                dish.get().setImageUrl("https://ik.imagekit.io/munchery/blog/tr:w-768/the-10-dishes-that-define-moroccan-cuisine.jpeg, https://giavivietan.com/wp-content/uploads/2020/01/VIANCO-Hinh-CHUP-T%C3%94-CA-RI-1-scaled.jpg, https://cms-prod.s3-sgn09.fptcloud.com/cach_nau_ca_ri_ga_tai_nha_bao_ngon_va_chuan_vi_an_hoai_khong_chan_1_c47c7657bc.jpg");
            }
            List<Dish_GroupOption> dishGroupOptions = dish_groupOptionJpaRepository.findByDishId(dishId);
            List<GroupOptionResponse> groupOptions = dishGroupOptions.stream()
                    .map(dishGroupOption -> {
                        return modelMapper.map( dishGroupOption.getDish_groupOptionId().getGroupOption(), GroupOptionResponse.class);
                    })
                    .toList();
            DishResponse dishResponse = modelMapper.map(dish, DishResponse.class);
            dishResponse.setOptions(groupOptions);
            return dishResponse;
        } else
            throw new DataNotFoundException("Can't not find dish with id" + dishId);
    }
    @Override
    public List<String> fetchImageUrls(String query) {
        String searchUrl = "https://www.google.com/search?q=" + query + "&site=webhp&tbm=isch";
        String response = restTemplate.getForObject(searchUrl, String.class);
        List<String> imageUrls = new ArrayList<>();  
            Document doc = Jsoup.parse(response);
            for (Element img : doc.select("img")) {
                String src = img.attr("src");
                if (src.startsWith("http")) {
                    imageUrls.add(src);
                }
            }
        return imageUrls;
    }

    @Override
    @Transactional
    public Dish addDish(DishRequest dishRequest) {
        Restaurant existingRestaurant = restaurantJpaRepository
                .findById(dishRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find restaurant with id: " + dishRequest.getRestaurantId()));

        Category existingCategory = categoryJpaRepository
                .findById(dishRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find category with id: " + dishRequest.getCategoryId()));
        DishType dishType = dishTypeJpaRepository.findById(dishRequest.getDishTypeId()).orElseThrow(
                () -> new IllegalArgumentException("Can't not find dish type with id" + dishRequest.getDishTypeId()));
        Dish newDish = Dish.builder()
                .name(dishRequest.getName())
                .price(dishRequest.getPrice())
                .imageUrl(dishRequest.getImageUrl())
                .description(dishRequest.getDescription())
                .restaurant(existingRestaurant)
                .category(existingCategory)
                .dishType(dishType)
                .status(DishStatus.ACTIVE)
                .build();
        return dishJpaRepository.save(newDish);
    }

    @Override
    @Transactional
    public Dish updateDish(long id, DishRequest dishRequest) throws Exception {
        Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Can't find dish with id: " + id
        ));
        categoryJpaRepository
                .findById(dishRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Cannot find category with id: " + dishRequest.getCategoryId()));
        if (dishRequest.getName() != null && !dishRequest.getName().isEmpty()) {
            dish.setName(dishRequest.getName());
        }
        if (dishRequest.getDescription() != null && !dishRequest.getDescription().isEmpty()) {
            dish.setDescription(dishRequest.getDescription());
        }
        if (dishRequest.getImageUrl() != null && !dishRequest.getImageUrl().isEmpty()) {
            dish.setImageUrl(dishRequest.getImageUrl());
        }
        dish.setPrice(dishRequest.getPrice());
        return dish;
    }

    @Override
    @Transactional
    public Dish deleteDish(long id) throws Exception {
        Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Can't find dish with id: " + id
        ));
        dish.setStatus(DishStatus.DELETED);
        return dish;
    }

    @Override
    public Dish_GroupOption addGroupOptionToDish(long id, long groupOptionId) throws Exception {
        Dish dish = dishJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "Can't find dish with id: " + id
        ));
        GroupOption groupOption = groupOptionJpaRepository.findById(groupOptionId)
                .orElseThrow(() -> new DataNotFoundException("Not found group optin with id" + groupOptionId));
        Dish_GroupOptionId dish_groupOptionId = Dish_GroupOptionId.builder()
                .dish(dish)
                .groupOption(groupOption)
                .build();
        Dish_GroupOption dish_groupOption = Dish_GroupOption.builder()
                .dish_groupOptionId(dish_groupOptionId)
                .build();
        return dish_groupOptionJpaRepository.save(dish_groupOption);
    }

    @Override
    public List<DishResponse> getDishesByCategory(long restaurantId, long categoryId, int page, int limit) {
        Category existingCategory = categoryJpaRepository
                .findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find category with id: " + String.valueOf(categoryId)));
        Restaurant restaurant = restaurantJpaRepository
        .findById(restaurantId)
        .orElseThrow(() -> new IllegalArgumentException(
                "Cannot find restaurant with id: " + String.valueOf(restaurantId)));
        List<Dish> dishesResponse = dishJpaRepository.findDishesByRestaurantAndCategory(restaurantId, categoryId, PageRequest.of(page, limit));
        for (Dish dishResponse : dishesResponse) {
            List<String> imageUrls = fetchImageUrls(dishResponse.getName());
            if (imageUrls.size() >= 3) {
                dishResponse.setImageUrl(String.join(", ", imageUrls.subList(0, 3)));
            } else {
                dishResponse.setImageUrl("https://ik.imagekit.io/munchery/blog/tr:w-768/the-10-dishes-that-define-moroccan-cuisine.jpeg, https://giavivietan.com/wp-content/uploads/2020/01/VIANCO-Hinh-CHUP-T%C3%94-CA-RI-1-scaled.jpg, https://cms-prod.s3-sgn09.fptcloud.com/cach_nau_ca_ri_ga_tai_nha_bao_ngon_va_chuan_vi_an_hoai_khong_chan_1_c47c7657bc.jpg");
            }
        }
        return dishesResponse.stream()
                .map(item -> modelMapper.map(item, DishResponse.class))
                .collect(Collectors.toList());
        
    }

    @Override
    public List<DishSearch> search(String keyword) {
        Pageable pageable = PageRequest.of(0, 5);
        return dishJpaRepository.search(keyword, pageable);
    }

    @Override
    public List<Dish> findAll() {
        List<Dish> dishes = dishJpaRepository.findAll();
        Hibernate.initialize(dishes);
        return dishes;
    }
    @Override
    public List<DishResponse> findDishesByRestaurant(long restaurantId, int limit, int page) {
        Restaurant restaurant = restaurantJpaRepository
                .findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find restaurant with id: " + String.valueOf(restaurantId)));
        System.out.println(restaurant);
        
        Pageable request = PageRequest.of(page, limit);
        List<Dish> dishes = dishJpaRepository.findDishesByRestaurant(restaurant, request);

        for (Dish dishResponse : dishes) {
            List<String> imageUrls = fetchImageUrls(dishResponse.getName());
            if (imageUrls.size() >= 3) {
                dishResponse.setImageUrl(String.join(", ", imageUrls.subList(0, 3)));
            } else {
                dishResponse.setImageUrl("https://ik.imagekit.io/munchery/blog/tr:w-768/the-10-dishes-that-define-moroccan-cuisine.jpeg, https://giavivietan.com/wp-content/uploads/2020/01/VIANCO-Hinh-CHUP-T%C3%94-CA-RI-1-scaled.jpg, https://cms-prod.s3-sgn09.fptcloud.com/cach_nau_ca_ri_ga_tai_nha_bao_ngon_va_chuan_vi_an_hoai_khong_chan_1_c47c7657bc.jpg");
            }
        }
        System.out.println(dishes.size());
        return dishes.stream()
        .map(item -> modelMapper.map(item, DishResponse.class))
        .collect(Collectors.toList());
    }

    @Override
    public List<DishResponse> getDishesByDishType(long dishTypeId, int limit, int page) {
        DishType dishType = dishTypeJpaRepository
                .findById(dishTypeId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cannot find restaurant with id: " + String.valueOf(dishTypeId)));
        Pageable request = PageRequest.of(page, limit);
        List<Dish> dishes = dishJpaRepository.findDishesByDishType(dishType, request);
        return dishes.stream()
                .map(item -> modelMapper.map(item, DishResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DishResponse> getRecommendedDishes(List<Long> ids) {
        List<Dish> dishes = new ArrayList<>();
        ids.forEach(id -> {
            dishJpaRepository.findById(id).ifPresent(dishes::add);
        });
        for (Dish dishResponse : dishes) {
            List<String> imageUrls = fetchImageUrls(dishResponse.getName());
            if (imageUrls.size() >= 3) {
                dishResponse.setImageUrl(String.join(", ", imageUrls.subList(0, 3)));
            } else {
                dishResponse.setImageUrl("https://ik.imagekit.io/munchery/blog/tr:w-768/the-10-dishes-that-define-moroccan-cuisine.jpeg, https://giavivietan.com/wp-content/uploads/2020/01/VIANCO-Hinh-CHUP-T%C3%94-CA-RI-1-scaled.jpg, https://cms-prod.s3-sgn09.fptcloud.com/cach_nau_ca_ri_ga_tai_nha_bao_ngon_va_chuan_vi_an_hoai_khong_chan_1_c47c7657bc.jpg");
            }
        }
        return dishes.stream()
                .map(item -> modelMapper.map(item, DishResponse.class))
                .collect(Collectors.toList());
    }

}
