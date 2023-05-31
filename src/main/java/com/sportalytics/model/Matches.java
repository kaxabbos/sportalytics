package com.sportalytics.model;

import com.sportalytics.model.enums.MatchStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Matches {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String date;
    private String time;
    private String photo;
    private byte winner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Teams teamA;
    @ManyToOne(fetch = FetchType.LAZY)
    private Teams teamB;
    @Enumerated(EnumType.STRING)
    private MatchStatus status;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MatchStats stats;
    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Marks> mark;
    @OneToMany(mappedBy = "match", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Opinions> opinions;

    public Matches(String name, String date, String time, String photo, Teams teamA, Teams teamB) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.photo = photo;
        this.teamA = teamA;
        this.teamB = teamB;
        winner = 0;
        stats = new MatchStats(this);
        status = MatchStatus.WAITING;
    }

    public void WinTeamA() {
        status = MatchStatus.FINISH;
        winner = 1;
        TeamStats stats1 = teamA.getStats();
        stats1.Win();
        TeamStats stats2 = teamB.getStats();
        stats2.Loss();
    }

    public void WinTeamB() {
        status = MatchStatus.FINISH;
        winner = 2;
        TeamStats stats1 = teamA.getStats();
        stats1.Loss();
        TeamStats stats2 = teamB.getStats();
        stats2.Win();
    }

    public String Winner() {
        switch (winner) {
            case 1 -> {
                return "Победила команда " + teamA.getName();
            }
            case 2 -> {
                return "Победила команда " + teamB.getName();
            }
            default -> {
                return "Ничья";
            }
        }
    }
}
