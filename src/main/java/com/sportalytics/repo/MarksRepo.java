package com.sportalytics.repo;

import com.sportalytics.model.Marks;
import com.sportalytics.model.Matches;
import com.sportalytics.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksRepo extends JpaRepository<Marks, Long> {
    Marks findByOwnerAndMatch(Users owner, Matches match);

}
