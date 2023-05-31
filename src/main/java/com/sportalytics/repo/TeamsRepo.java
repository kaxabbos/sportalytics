package com.sportalytics.repo;

import com.sportalytics.model.Teams;
import com.sportalytics.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamsRepo extends JpaRepository<Teams, Long> {
    List<Teams> findAllByNameContainingAndCategory(String name, Category category);

    List<Teams> findAllByCategory(Category category);
}
