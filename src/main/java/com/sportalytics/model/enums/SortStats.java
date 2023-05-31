package com.sportalytics.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortStats {
    VOTE_UP("Процент предложений по возрастанию"),
    VOTE_DOWN("Процент предложений по убыванию"),
    WIN_UP("Процент побед по возрастанию"),
    WIN_DOWN("Процент побед по убыванию"),
    ;

    private final String name;

}

