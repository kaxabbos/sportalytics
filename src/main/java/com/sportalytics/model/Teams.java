package com.sportalytics.model;

import com.sportalytics.model.enums.Category;
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
public class Teams {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String country;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String photo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TeamStats stats;
    @OneToMany(mappedBy = "teamA", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Matches> matchesA;
    @OneToMany(mappedBy = "teamB", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Matches> matchesB;

    public Teams(String name, String country, Category category, String photo) {
        this.name = name;
        this.country = country;
        this.category = category;
        this.photo = photo;
        stats = new TeamStats(this);
    }
}
