package com.shegoestech.student_register.controller;

import com.shegoestech.student_register.model.User;
import com.shegoestech.student_register.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getAll());
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(Model map, User user) {
        map.addAttribute("welcomeMessage", "Yoooooooo!!! Welcome ladies");

        return "welcome";
    }

    @GetMapping("/user-add")
    public String signUp(Model map, User user) {
        map.addAttribute("pageName", "Add New User");

        return "user-add";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, Model model) {
        userService.deleteById(id);
        return index(model);
    }

    @GetMapping("/edit/{id}")
    public String editById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit New User");

        User user = userService.getById(id);
        model.addAttribute("user", user);

        return "user-edit";
    }

    @PostMapping
    public String register(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user-add";
        }

        userService.register(user);

        return index(model);
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user-edit";
        }

        userService.update(id, user);

        return index(model);
    }
}
