package com.sportalytics.controller;

import com.sportalytics.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Attributes {

    @GetMapping("/about")
    public String about(Model model) {
        AddAttributes(model);
        return "about";
    }

    @GetMapping
    public String index1() {
        return "redirect:/matches";
    }

    @GetMapping("/index")
    public String index2() {
        return "redirect:/matches";
    }
}
