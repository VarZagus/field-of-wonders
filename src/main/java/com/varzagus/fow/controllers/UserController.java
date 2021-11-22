package com.varzagus.fow.controllers;

import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.Role;
import com.varzagus.fow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Map<String, Object> model) {
        if(userRepository.findByLogin(user.getLogin()) != null) {
            model.put("message","Данный пользователь уже существует!");
            return "registration";
        }

        user.setRole(Role.USER);
        user.setActive(true);
        userRepository.save(user);

        return "redirect:/login";
    }

}
