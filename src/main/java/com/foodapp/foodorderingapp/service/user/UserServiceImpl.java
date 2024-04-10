package com.foodapp.foodorderingapp.service.user;

import com.foodapp.foodorderingapp.dto.address.CreateAddress;
import com.foodapp.foodorderingapp.dto.auth.CreateUserRequest;
import com.foodapp.foodorderingapp.dto.auth.SignInRequest;
import com.foodapp.foodorderingapp.dto.auth.response.SignInResponse;
import com.foodapp.foodorderingapp.entity.Address;
import com.foodapp.foodorderingapp.entity.Role;
import com.foodapp.foodorderingapp.entity.User;
import com.foodapp.foodorderingapp.entity.UserRole;
import com.foodapp.foodorderingapp.exception.AuthException;
import com.foodapp.foodorderingapp.exception.DataNotFoundException;
import com.foodapp.foodorderingapp.exception.UserExistException;
import com.foodapp.foodorderingapp.repository.AddressJpaRepository;
import com.foodapp.foodorderingapp.repository.RoleJpaRepository;
import com.foodapp.foodorderingapp.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private final AddressJpaRepository addressJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createNewUser(CreateUserRequest createUserRequest) throws UserExistException {
        if(!userJpaRepository.existsUserByUsername(createUserRequest.getUsername())){
            User newUser = User.builder()
                    .fullname(createUserRequest.getFullname())
                    .password(passwordEncoder.encode( createUserRequest.getPassword()))
                    .username(createUserRequest.getUsername())
                    .build();
            UserRole userRole = UserRole.builder().role(roleJpaRepository.findByName("ROLE_USER")).user(newUser).build();
            List<UserRole> userRoleList = new ArrayList<>();
            userRoleList.add(userRole);
            newUser.setRoles(userRoleList);
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
            throw new DataNotFoundException("Not found user with "+ username);
        }
        return user.get();
    }

    @Override
    public User findById(Long userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(()-> new EntityNotFoundException("Not found user"));
        return user;
    }




}
