package com.foodapp.foodorderingapp.service.user;

import com.foodapp.foodorderingapp.dto.auth.CreateUserRequest;
import com.foodapp.foodorderingapp.dto.auth.SignInRequest;
import com.foodapp.foodorderingapp.dto.auth.response.SignInResponse;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.exception.AuthException;
import com.foodapp.foodorderingapp.exception.UserExistException;
import com.foodapp.foodorderingapp.exception.UserNotFoundException;
import com.foodapp.foodorderingapp.repository.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createNewUser(CreateUserRequest createUserRequest) throws UserExistException {
        if(!userJpaRepository.existsUserByPhoneNumber(createUserRequest.getPhoneNumber())){
            User newUser = User.builder()
                    .fullname(createUserRequest.getFullname())
                    .password(passwordEncoder.encode( createUserRequest.getPassword()))
                    .username(createUserRequest.getFullname())
                    .phoneNumber(createUserRequest.getPhoneNumber())
                    .build();
            return  userJpaRepository.save(newUser);
        }
        else throw new UserExistException("User exists!");
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) throws AuthException {
        try{
            User user = userJpaRepository.findUserByUsername(signInRequest.getUsername());
            if(user.getPassword().equals(signInRequest.getPassword())){
                String token = " ";
                return SignInResponse.builder().token(token).user(user).build();
            }
            else throw new AuthException("Password is not accurate");
        }
        catch (Exception e){
            throw  new AuthException("User is not exists");
        }
    }

    @Override
    public User getUserByUsername(String username){
        Optional<User> user = Optional.ofNullable(userJpaRepository.findUserByUsername(username));
        if(user.isEmpty()){
            throw new UserNotFoundException("Not found user with "+ username);
        }
        return user.get();
    }
}
