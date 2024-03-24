package com.foodapp.foodorderingapp.controller;
import com.foodapp.foodorderingapp.dto.address.CreateAddress;
import com.foodapp.foodorderingapp.dto.dish.DishRequest;
import com.foodapp.foodorderingapp.dto.dishType.DishTypeCreate;
import com.foodapp.foodorderingapp.entity.Address;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.service.address.AddressService;
import com.foodapp.foodorderingapp.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.foodapp.foodorderingapp.entity.DishType;
import com.foodapp.foodorderingapp.service.dishType.DishTypeService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;
    public
    @PostMapping()
    ResponseEntity<Address> addAddress(@Valid @RequestBody CreateAddress createAddress){
        Address address = addressService.addAddress(createAddress);
        return ResponseEntity.ok(address);
    }
    public
    @GetMapping()
    ResponseEntity<List<Address>> getAddresses( @RequestParam Long userId){
        return ResponseEntity.ok(addressService.getAddresses(userId));
    }

}
