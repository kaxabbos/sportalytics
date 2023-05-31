package com.sportalytics.controller;

import com.sportalytics.controller.main.Attributes;
import com.sportalytics.model.enums.CategoryAll;
import com.sportalytics.model.enums.SortStats;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/stats")
public class StatsCont extends Attributes {
    @GetMapping
    public String stats(Model model) {
        AddAttributesStats(model, CategoryAll.ALL, SortStats.VOTE_UP);
        return "stats";
    }

    @GetMapping("/sort")
    public String statsSort(Model model, @RequestParam CategoryAll category, @RequestParam SortStats sort) {
        AddAttributesStats(model, category, sort);
        return "stats";
    }
}
