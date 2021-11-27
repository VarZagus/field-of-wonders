package com.varzagus.fow.controllers;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.Role;
import com.varzagus.fow.repository.QuestionRepository;
import com.varzagus.fow.repository.UserRepository;
import com.varzagus.fow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AuthService authService;

    @GetMapping("/users")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", authService.getLoggedInUser());
        return "userList";
    }

    @GetMapping("/user/{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("current", authService.getLoggedInUser());
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/user")
    public String userSave(@RequestParam("userId") User user,
                           @RequestParam("login") String login,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role){
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(Role.valueOf(role));
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/new")
    public String userNew(Model model) {
        model.addAttribute("user", authService.getLoggedInUser());
        return "userNew";
    }

    @PostMapping("/user/new")
    public String userCreate(@RequestParam("login") String login,
                             @RequestParam("password") String password,
                             @RequestParam("role") String role){
        User user = new User();
        user.setActive(true);
        user.setLogin(login);
        user.setRole(Role.valueOf(role));
        user.setPassword(password);
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/user/delete/{user}")
    public String userDelete(@PathVariable User user){
        userRepository.delete(user);
        return "redirect:/admin/users";
    }

    @GetMapping("questions")
    public String questionList(Model model){
        model.addAttribute("questions", questionRepository.findAll());
        model.addAttribute("user", authService.getLoggedInUser());
        return "questionList";
    }

    @GetMapping("/question/{question}")
    public String questionEdit(@PathVariable Question question, Model model) {
        model.addAttribute("question", question);
        model.addAttribute("user", authService.getLoggedInUser());
        return "questionEdit";
    }

    @PostMapping("/question")
    public String questionSave(@RequestParam("questionId") Question question,
                           @RequestParam("question") String questionStr,
                           @RequestParam("answer") String answer){
        question.setQuestion(questionStr);
        question.setAnswer(answer);
        questionRepository.save(question);
        return "redirect:/admin/questions";
    }

    @GetMapping("/question/new")
    public String questionNew(Model model) {
        model.addAttribute("user", authService.getLoggedInUser());
        return "questionNew";
    }

    @PostMapping("/question/new")
    public String questionCreate(@RequestParam("question") String question,
                             @RequestParam("answer") String answer){
        Question question1 = new Question();
        question1.setQuestion(question);
        question1.setAnswer(answer);
        questionRepository.save(question1);

        return "redirect:/admin/questions";
    }

    @GetMapping("/question/delete/{question}")
    public String questionDelete(@PathVariable Question question){
        questionRepository.delete(question);
        return "redirect:/admin/questions";
    }

}
