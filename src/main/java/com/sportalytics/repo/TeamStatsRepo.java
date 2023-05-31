package com.sportalytics.repo;

import com.sportalytics.model.TeamStats;
import com.sportalytics.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamStatsRepo extends JpaRepository<TeamStats, Long> {
    List<TeamStats> findAllByTeam_Category(Category category);
}
