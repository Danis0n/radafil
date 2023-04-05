package com.danis0n;

import com.danis0n.repository.UserRepository;
import com.danis0n.entity.AUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner fillDB(UserRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {

            AUser AUser = new AUser();

            AUser.setEmail("email");
            AUser.setPassword(passwordEncoder.encode("1234"));
            AUser.setName("name");

            repository.save(AUser);
        };
    }

}