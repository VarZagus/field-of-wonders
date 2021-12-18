package com.varzagus.fow.controllers;

import com.varzagus.fow.domain.GameResult;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.Role;
import com.varzagus.fow.repository.UserRepository;
import com.varzagus.fow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

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

    @GetMapping("/userinfo")
    public String userInfo(Map<String,Object> model) {
        User user = authService.getLoggedInUser();
        model.put("user",user);
        List<GameResult> results = new ArrayList<>();
        results.addAll(user.getResults());
        results.sort(Comparator.comparing(GameResult::getDate));
        model.put("results", results);

        return "user";
    }



}
