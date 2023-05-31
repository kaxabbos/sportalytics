package com.sportalytics.controller;

import com.sportalytics.controller.main.Attributes;
import com.sportalytics.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileCont extends Attributes {

    @GetMapping
    public String profile(Model model) {
        AddAttributesProfile(model);
        return "profile";
    }

    @PostMapping("/edit/fio")
    public String profileEditFio(@RequestParam String fio) {
        Users user = getUser();
        user.setFio(fio);
        usersRepo.save(user);
        return "redirect:/profile";
    }

    @PostMapping("/edit/password")
    public String profileEditPassword(@RequestParam String password) {
        Users user = getUser();
        user.setPassword(password);
        usersRepo.save(user);
        return "redirect:/profile";
    }
}
