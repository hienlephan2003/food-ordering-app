package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.address.CreateAddress;
import com.foodapp.foodorderingapp.dto.auth.CreateUserRequest;
import com.foodapp.foodorderingapp.dto.auth.SignInRequest;
import com.foodapp.foodorderingapp.dto.auth.SignUpResponse;
import com.foodapp.foodorderingapp.dto.auth.UserResponse;
import com.foodapp.foodorderingapp.entity.Address;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.security.JwtService;
import com.foodapp.foodorderingapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/profile")
    public ResponseEntity<User> getCurrentUser(){
        User user = userService.findCurrentUser();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getCurrentUser(@PathVariable Long id){
        User user = userService.findCurrentUser(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SignUpResponse> signUp(@RequestBody CreateUserRequest createUserRequest){
        System.out.println("Sign up controller is fired");
        UserResponse user = userService.createNewUser(createUserRequest);
        String token = jwtService.generateToken(createUserRequest.getUsername());
        SignUpResponse signUpResponse = new SignUpResponse(user.getUsername(), token);
        return ResponseEntity.ok(signUpResponse);
    }
    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody CreateUserRequest createUserRequest) throws DataNotFoundException {
        User result = userService.updateUser(createUserRequest);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  ResponseEntity<Map<String, Object>> signIn(@RequestBody SignInRequest signInRequest) throws DataNotFoundException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        System.out.println("sign in fire" + authentication);
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(signInRequest.getUsername());
            User user = userService.getUserByUsername(signInRequest.getUsername());
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", token);
            responseBody.put("user_info", user); // Assuming user is a serializable object

            return ResponseEntity.ok()
                    .body(responseBody);
        }
        else {
            throw new DataNotFoundException("invalid user request !");
        }
    }
}
