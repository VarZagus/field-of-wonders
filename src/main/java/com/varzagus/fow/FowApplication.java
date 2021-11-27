package com.varzagus.fow;

import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.Role;
import com.varzagus.fow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FowApplication {
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(FowApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		if(userRepository.findByLogin("admin") == null){
			User user = new User();
			user.setLogin("admin");
			user.setPassword("123");
			user.setRole(Role.ADMIN);
			userRepository.save(user);
		}
	}

}
