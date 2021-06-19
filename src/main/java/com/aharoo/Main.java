package com.aharoo;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.repository.ApplicationUserRepository;
import com.aharoo.security.ApplicationUserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		SpringApplication.run(Main.class,args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ApplicationUserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			ApplicationUser user1 = new ApplicationUser();
			user1.setUsername("Andrew");
			user1.setPassword(passwordEncoder.encode("password"));
			user1.setEmail("peacedead.nikitenko@gmail.com");
			user1.setRole(ApplicationUserRole.ADMIN);
			user1.setEnabled(true);
			userRepository.save(user1);
			log.info("User1 was saved");

			ApplicationUser user2 = new ApplicationUser();
			user2.setUsername("Tomas");
			user2.setPassword(passwordEncoder.encode("password"));
			user2.setEmail("Tom@gmail.com");
			user2.setRole(ApplicationUserRole.USER);
			user2.setEnabled(true);
			userRepository.save(user2);
			log.info("User2 was saved");

		};
	}



}
