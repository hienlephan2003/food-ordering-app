package com.foodapp.foodorderingapp;

import com.foodapp.foodorderingapp.entity.Role;
import com.foodapp.foodorderingapp.repository.RoleJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FoodOrderingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderingAppApplication.class, args);
		System.out.println("Xin chao cac ban");
	}
	@Bean
	public CommandLineRunner initialData(RoleJpaRepository roleJpaRepository){
		return args ->{
			if(roleJpaRepository.findAll().isEmpty()) {
				Role role = new Role();
				role.setName("ROLE_USER");
				roleJpaRepository.save(role);

				Role adminRole = new Role();
				adminRole.setName("ROLE_ADMIN");
				roleJpaRepository.save(adminRole);

				Role sellerRole = new Role();
				sellerRole.setName("ROLE_SELLER");
				roleJpaRepository.save(sellerRole);
			}
		};
	}
}
