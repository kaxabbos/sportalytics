package com.sportalytics.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MatchStatus {
    WAITING("Предстоит"),
    PROGRESS("Проходит"),
    FINISH("Завершено"),
    ;

    private final String name;

}

