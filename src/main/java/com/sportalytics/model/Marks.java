package com.sportalytics.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Marks {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Matches match;
    private byte assumption;

    public Marks(Users owner, Matches match, byte assumption) {
        this.owner = owner;
        this.match = match;
        this.assumption = assumption;
    }

    public String Assumption() {
        switch (assumption) {
            case 1 -> {
                return match.getTeamA().getName();
            }
            case 2 -> {
                return match.getTeamB().getName();
            }
            default -> {
                return "";
            }
        }
    }
}
