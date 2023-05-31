package com.sportalytics.controller;

import com.sportalytics.controller.main.Attributes;
import com.sportalytics.model.Teams;
import com.sportalytics.model.enums.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/teams")
public class TeamsCont extends Attributes {

    @GetMapping
    public String teams(Model model) {
        AddAttributesTeams(model);
        return "teams";
    }

    @GetMapping("/{category}")
    public String teamsCategory(Model model, @PathVariable Category category) {
        AddAttributesTeamsCategory(model, category);
        return "teams";
    }

    @GetMapping("/search")
    public String teamsSearch(Model model, @RequestParam String name, @RequestParam Category category) {
        AddAttributesTeamsSearch(model, name,category);
        return "teams";
    }

    @GetMapping("/add")
    public String teamAdd(Model model) {
        AddAttributesTeamAdd(model);
        return "team_add";
    }

    @GetMapping("/edit/{id}")
    public String teamEdit(Model model, @PathVariable Long id) {
        AddAttributesTeamEdit(model, id);
        return "team_edit";
    }

    @GetMapping("/delete/{id}")
    public String teamDelete(@PathVariable Long id) {
        teamsRepo.deleteById(id);
        return "redirect:/teams";
    }

    @PostMapping("/add")
    public String teamAdd(Model model, @RequestParam String name, @RequestParam String country, @RequestParam MultipartFile file, @RequestParam Category category) {
        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "teams/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesTeamAdd(model);
                return "team_add";
            }
        }

        teamsRepo.save(new Teams(name, country, category, res));

        return "redirect:/teams";
    }

    @PostMapping("/edit/{id}")
    public String teamEdit(Model model, @RequestParam String name, @RequestParam String country, @RequestParam MultipartFile file, @RequestParam Category category, @PathVariable Long id) {
        Teams team = teamsRepo.getReferenceById(id);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "teams/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesTeamAdd(model);
                return "team_add";
            }
            team.setPhoto(res);
        }

        team.setName(name);
        team.setCountry(country);
        team.setCategory(category);
        teamsRepo.save(team);

        return "redirect:/teams";
    }


}
