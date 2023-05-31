package com.sportalytics.controller.main;

import com.sportalytics.model.Matches;
import com.sportalytics.model.TeamStats;
import com.sportalytics.model.enums.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }

    protected void AddAttributesEnums(Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("matchStatuses", MatchStatus.values());
    }

    protected void AddAttributesUsers(Model model) {
        AddAttributes(model);
        model.addAttribute("users", usersRepo.findAll());
        model.addAttribute("roles", Role.values());
    }

    protected void AddAttributesTeams(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("teams", teamsRepo.findAll());
    }

    protected void AddAttributesTeamsSearch(Model model, String name, Category category) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("teams", teamsRepo.findAllByNameContainingAndCategory(name, category));
        model.addAttribute("name", name);
        model.addAttribute("category", category);
    }

    protected void AddAttributesTeamsCategory(Model model, Category category) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("teams", teamsRepo.findAllByCategory(category));
    }

    protected void AddAttributesTeamAdd(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
    }

    protected void AddAttributesTeamEdit(Model model, Long id) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("team", teamsRepo.getReferenceById(id));
    }

    protected void AddAttributesProfile(Model model) {
        AddAttributes(model);
    }

    protected void AddAttributesMatchAdd(Model model, Category category) {
        AddAttributes(model);
        model.addAttribute("teams", teamsRepo.findAllByCategory(category));
        model.addAttribute("category", category);
    }

    protected void AddAttributesMatchEdit(Model model, Long id) {
        AddAttributes(model);
        Matches match = matchesRepo.getReferenceById(id);
        model.addAttribute("match", match);
        model.addAttribute("teams", teamsRepo.findAllByCategory(match.getTeamA().getCategory()));
        model.addAttribute("category", match.getTeamA().getCategory());
    }

    protected void AddAttributesMatch(Model model, Long id) {
        AddAttributes(model);
        Matches match = matchesRepo.getReferenceById(id);
        model.addAttribute("mark", marksRepo.findByOwnerAndMatch(getUser(), match));
        model.addAttribute("match", match);
    }

    protected void AddAttributesMatches(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
        List<Matches> matches = matchesRepo.findAllByStatus(MatchStatus.WAITING);
        matches.addAll(matchesRepo.findAllByStatus(MatchStatus.PROGRESS));
        matches.addAll(matchesRepo.findAllByStatus(MatchStatus.FINISH));
        model.addAttribute("matches", matches);
    }

    protected void AddAttributesMatchesSearch(Model model, String name, MatchStatus status, Category category) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("matches", matchesRepo.findAllByNameContainingAndTeamA_CategoryAndStatus(name, category, status));
        model.addAttribute("name", name);
        model.addAttribute("status", status);
        model.addAttribute("category", category);
    }

    protected void AddAttributesMatchesSearchCategory(Model model, Category category) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("matches", matchesRepo.findAllByTeamA_Category(category));
        model.addAttribute("category", category);
    }

    protected void AddAttributesMatchesSearchStatus(Model model, MatchStatus status) {
        AddAttributes(model);
        AddAttributesEnums(model);
        model.addAttribute("matches", matchesRepo.findAllByStatus(status));
        model.addAttribute("status", status);
    }

    protected void AddAttributesStats(Model model, CategoryAll category, SortStats sort) {
        AddAttributes(model);
        model.addAttribute("categories", CategoryAll.values());
        model.addAttribute("sorts", SortStats.values());
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);
        List<TeamStats> stats = new ArrayList<>();

        if (category == CategoryAll.ALL) {
            stats = teamStatsRepo.findAll();
        } else {
            for (Category i : Category.values()) {
                if (i.name().equals(category.name())) {
                    stats = teamStatsRepo.findAllByTeam_Category(i);
                    break;
                }
            }
        }

        switch (sort) {
            case VOTE_DOWN -> {
                stats.sort(Comparator.comparing(TeamStats::AverageVote));
                Collections.reverse(stats);
            }
            case VOTE_UP -> {
                stats.sort(Comparator.comparing(TeamStats::AverageVote));
            }
            case WIN_DOWN -> {
                stats.sort(Comparator.comparing(TeamStats::AverageWin));
                Collections.reverse(stats);
            }
            case WIN_UP -> {
                stats.sort(Comparator.comparing(TeamStats::AverageWin));
            }
        }

        model.addAttribute("stats", stats);

        model.addAttribute("football", matchesRepo.findAllByTeamA_Category(Category.FOOTBALL).size());
        model.addAttribute("hockey", matchesRepo.findAllByTeamA_Category(Category.HOCKEY).size());
        model.addAttribute("volleyball", matchesRepo.findAllByTeamA_Category(Category.VOLLEYBALL).size());
        model.addAttribute("basketball", matchesRepo.findAllByTeamA_Category(Category.BASKETBALL).size());
        model.addAttribute("baseball", matchesRepo.findAllByTeamA_Category(Category.BASEBALL).size());
        model.addAttribute("tennis", matchesRepo.findAllByTeamA_Category(Category.TENNIS).size());

        int waiting = 0;
        int progress = 0;
        int finish = 0;

        for (TeamStats i : stats) {
            for (Matches j : i.getTeam().getMatchesA()) {
                switch (j.getStatus()) {
                    case WAITING -> waiting++;
                    case PROGRESS -> progress++;
                    case FINISH -> finish++;
                }
            }
        }

        model.addAttribute("waiting", waiting);
        model.addAttribute("progress", progress);
        model.addAttribute("finish", finish);

        int[] topNumber = new int[5];
        String[] topName = new String[5];
        for (int i = 0; i < stats.size(); i++) {
            if (i == 5) break;
            topName[i] = stats.get(i).getTeam().getName();
            if (sort == SortStats.VOTE_DOWN || sort == SortStats.VOTE_UP) {
                topNumber[i] = stats.get(i).AverageVote();
            }
            if (sort == SortStats.WIN_DOWN || sort == SortStats.WIN_UP) {
                topNumber[i] = stats.get(i).AverageWin();
            }
        }

        model.addAttribute("topName", topName);
        model.addAttribute("topNumber", topNumber);
    }
}
