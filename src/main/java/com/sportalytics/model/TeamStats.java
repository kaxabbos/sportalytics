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
public class TeamStats {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private int match;
    private int win;
    private int loss;
    @OneToOne(fetch = FetchType.LAZY)
    private Teams team;

    public TeamStats(Teams team) {
        match = 0;
        win = 0;
        loss = 0;
        this.team = team;
    }

    public void Win() {
        match++;
        win++;
    }

    public void Loss() {
        match++;
        loss++;
    }

    public int AverageVote() {
        double res = 0;
        int count = 0;

        for (Matches i : team.getMatchesA()) {
            if (i.getWinner() == 1) {
                if (i.getStats().getTeamA() != 0) {
                    res += (double) i.getStats().getTeamA() / i.getStats().getVotes();
                }
                count++;
            }
        }
        for (Matches i : team.getMatchesB()) {
            if (i.getWinner() == 2) {
                if (i.getStats().getTeamB() != 0) {
                    res += (double) i.getStats().getTeamB() / i.getStats().getVotes();
                }
                count++;
            }
        }

        if (res == 0) return 0;
        res = res / count;
        return (int) (res * 100);
    }

    public int AverageWin() {
        if (win == 0) return 0;
        double temp1 = (double) win / match;
        return (int) (temp1 * 100);
    }
}
