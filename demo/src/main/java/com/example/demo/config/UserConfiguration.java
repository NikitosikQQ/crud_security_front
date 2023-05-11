package com.example.demo.config;

import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class UserConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(UserService userService, RoleRepository roleRepository) {

        return args -> {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            Set<Role> roleUser = new HashSet<>();
            roleUser.add(userRole);
            Set<Role> roleAdmin = new HashSet<>();
            roleAdmin.add(adminRole);
            roleAdmin.add(userRole);
            roleRepository.saveAll(List.of(userRole, adminRole));
            User user1 = new User("User", "Nikita@mail.ru", 22, 103641307L, "user", roleUser);
            User admin = new User("Admin", "Alexander@mail.ru", 20, 229569263L, "admin", roleAdmin);
            User user2 = new User("Maria", "SDVGSASHA@mail.ru", 20, 436552949L, "123", roleUser);
            userService.saveUser(user1);
            userService.saveUser(admin);
            userService.saveUser(user2);
        };
    }
}
