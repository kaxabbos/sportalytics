package com.sportalytics.repo;

import com.sportalytics.model.MatchStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchStatsRepo extends JpaRepository<MatchStats, Long> {
}
