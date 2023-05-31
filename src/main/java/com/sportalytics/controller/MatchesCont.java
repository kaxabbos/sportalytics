package com.sportalytics.controller;

import com.sportalytics.controller.main.Attributes;
import com.sportalytics.model.Marks;
import com.sportalytics.model.MatchStats;
import com.sportalytics.model.Matches;
import com.sportalytics.model.Opinions;
import com.sportalytics.model.enums.Category;
import com.sportalytics.model.enums.MatchStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/matches")
public class MatchesCont extends Attributes {

    @GetMapping
    public String matchesWaiting(Model model) {
        AddAttributesMatches(model);
        return "matches";
    }

    @GetMapping("/start/{id}")
    public String matchStart(@PathVariable Long id) {
        Matches match = matchesRepo.getReferenceById(id);
        match.setStatus(MatchStatus.PROGRESS);
        matchesRepo.save(match);
        return "redirect:/matches/{id}";
    }

    @GetMapping("/win/teamA/{id}")
    public String matchWinTeamA(@PathVariable Long id) {
        Matches match = matchesRepo.getReferenceById(id);
        match.WinTeamA();
        matchesRepo.save(match);
        return "redirect:/matches/{id}";
    }

    @GetMapping("/win/teamB/{id}")
    public String matchWinTeamB(@PathVariable Long id) {
        Matches match = matchesRepo.getReferenceById(id);
        match.WinTeamB();
        matchesRepo.save(match);
        return "redirect:/matches/{id}";
    }

    @GetMapping("/assumption/teamA/{id}")
    public String matchAssumptionTeamA(@PathVariable Long id) {
        Matches match = matchesRepo.getReferenceById(id);
        MatchStats matchStat = match.getStats();
        matchStat.VoteTeamA();
        marksRepo.save(new Marks(getUser(), match, (byte) 1));
        return "redirect:/matches/{id}";
    }

    @GetMapping("/assumption/teamB/{id}")
    public String matchAssumptionTeamB(@PathVariable Long id) {
        Matches match = matchesRepo.getReferenceById(id);
        MatchStats matchStat = match.getStats();
        matchStat.VoteTeamB();
        marksRepo.save(new Marks(getUser(), match, (byte) 2));
        return "redirect:/matches/{id}";
    }

    @GetMapping("/{id}")
    public String match(Model model, @PathVariable Long id) {
        AddAttributesMatch(model, id);
        return "match";
    }

    @GetMapping("/opinion/{id}")
    public String matchOpinion(@RequestParam String text, @PathVariable Long id) {
        opinionsRepo.save(new Opinions(text, DateNow(), getUser(), matchesRepo.getReferenceById(id)));
        return "redirect:/matches/{id}";
    }

    @GetMapping("/search")
    public String matchesSearch(Model model, @RequestParam String name, @RequestParam MatchStatus status, @RequestParam Category category) {
        AddAttributesMatchesSearch(model, name, status, category);
        return "matches";
    }

    @GetMapping("/search/category/{category}")
    public String matchesSearchCategory(Model model, @PathVariable Category category) {
        AddAttributesMatchesSearchCategory(model, category);
        return "matches";
    }

    @GetMapping("/search/status/{status}")
    public String matchesSearchStatus(Model model, @PathVariable MatchStatus status) {
        AddAttributesMatchesSearchStatus(model, status);
        return "matches";
    }

    @GetMapping("/add")
    public String matchAdd(Model model, @RequestParam Category category) {
        AddAttributesMatchAdd(model, category);
        return "match_add";
    }

    @GetMapping("/edit/{id}")
    public String matchEdit(Model model, @PathVariable Long id) {
        AddAttributesMatchEdit(model, id);
        return "match_edit";
    }

    @GetMapping("/delete/{id}")
    public String matchDelete(@PathVariable Long id) {
        matchesRepo.deleteById(id);
        return "redirect:/matches";
    }

    @PostMapping("/add")
    public String matchAdd(Model model, @RequestParam Category category, @RequestParam String name, @RequestParam String date, @RequestParam String time, @RequestParam MultipartFile file, @RequestParam Long teamA, @RequestParam Long teamB) {
        if ((teamA == 0 || teamB == 0) || teamA.equals(teamB)) {
            model.addAttribute("message", "Некорректный выбор команд!");
            AddAttributesMatchAdd(model, category);
            return "match_add";
        }

        String res = "";
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "matches/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesMatchAdd(model, category);
                return "match_add";
            }
        }

        matchesRepo.save(new Matches(name, date, time, res, teamsRepo.getReferenceById(teamA), teamsRepo.getReferenceById(teamB)));

        return "redirect:/matches";
    }

    @PostMapping("/edit/{id}")
    public String matchEdit(Model model, @PathVariable Long id, @RequestParam Category category, @RequestParam String name, @RequestParam String date, @RequestParam String time, @RequestParam MultipartFile file, @RequestParam Long teamA, @RequestParam Long teamB) {
        if ((teamA == 0 || teamB == 0) || teamA.equals(teamB)) {
            model.addAttribute("message", "Некорректный выбор команд!");
            AddAttributesMatchEdit(model, id);
            return "match_edit";
        }

        Matches match = matchesRepo.getReferenceById(id);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String res = "";
            String uuidFile = UUID.randomUUID().toString();
            boolean createDir = true;
            try {
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) createDir = uploadDir.mkdir();
                if (createDir) {
                    res = "matches/" + uuidFile + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadImg + "/" + res));
                }
            } catch (Exception e) {
                model.addAttribute("message", "Некорректный данные!");
                AddAttributesMatchEdit(model, id);
                return "match_edit";
            }
            match.setPhoto(res);
        }

        match.setName(name);
        match.setDate(date);
        match.setTime(time);
        match.setTeamA(teamsRepo.getReferenceById(teamA));
        match.setTeamB(teamsRepo.getReferenceById(teamB));

        matchesRepo.save(match);

        return "redirect:/matches";
    }
}
