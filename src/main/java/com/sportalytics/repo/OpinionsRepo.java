package com.sportalytics.repo;

import com.sportalytics.model.Opinions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionsRepo extends JpaRepository<Opinions, Long> {

}
