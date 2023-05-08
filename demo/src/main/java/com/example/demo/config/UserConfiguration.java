package com.example.demo.config;

import com.example.demo.repository.RoleRepository;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class UserConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository) {

        return args -> {
            Role userRole = new Role("ROLE_USER");
            Role adminRole = new Role("ROLE_ADMIN");
            Set<Role> roleUser = new HashSet<>();
            roleUser.add(userRole);
            Set<Role> roleAdmin = new HashSet<>();
            roleAdmin.add(adminRole);
            roleAdmin.add(userRole);
            roleRepository.saveAll(List.of(userRole, adminRole));
            User user1 = new User("User", "Nikita@mail.ru", 22, "$2a$12$.lSzFvX0U8uyjQSCtBUYJu8ROF6Myb4PW2QJ37b/QxBduW3/kh1HK", roleUser);
            User admin = new User("Admin", "Alexander@mail.ru", 20, "$2a$12$fSq2n4zuypBjJd9l51Mor.2Z5RcTUH2ckgl/5RaYJ9/sB9lvQN1iC", roleAdmin);
            User user2 = new User("Maria", "SDVGSASHA@mail.ru", 20, "$2a$12$hHR9vyas0mo5qQANiCYiReCwQSlRATNgLyPdV2eqbA/upgKb2Ihc2", roleUser);
            userRepository.saveAll(List.of(user1, admin,user2));

        };
    }
}
