package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.auth.CreateUserRequest;
import com.foodapp.foodorderingapp.dto.auth.SignInRequest;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.exception.UserNotFoundException;
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
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> signUp(@RequestBody CreateUserRequest createUserRequest){
        System.out.println("Sign up controller is fired");
        User user = userService.createNewUser(createUserRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  ResponseEntity<Map<String, Object>> signIn(@RequestBody SignInRequest signInRequest) throws UserNotFoundException {
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
            throw new UserNotFoundException("invalid user request !");
        }
    }
}
