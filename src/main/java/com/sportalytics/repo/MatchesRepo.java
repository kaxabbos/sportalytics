package com.sportalytics.repo;

import com.sportalytics.model.Matches;
import com.sportalytics.model.enums.Category;
import com.sportalytics.model.enums.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchesRepo extends JpaRepository<Matches, Long> {
    List<Matches> findAllByStatus(MatchStatus status);

    List<Matches> findAllByNameContainingAndTeamA_CategoryAndStatus(String name, Category category, MatchStatus status);

    List<Matches> findAllByTeamA_Category(Category category);
}
