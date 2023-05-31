package com.sportalytics.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MatchStats {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private int votes;
    private int teamA;
    private int teamB;
    @OneToOne(fetch = FetchType.LAZY)
    private Matches match;

    public MatchStats(Matches match) {
        this.match = match;
        votes = 0;
        teamA = 0;
        teamB = 0;
    }

    public void VoteTeamA() {
        votes++;
        teamA++;
    }

    public void VoteTeamB() {
        votes++;
        teamB++;
    }

    public int TeamA() {
        if (teamA == 0) return 0;
        double res = (double) teamA / votes;
        return (int) (res * 100);
    }

    public int TeamB() {
        if (teamA == 0) return 0;
        double res = (double) teamB / votes;
        return (int) (res * 100);
    }
}
