package com.sportalytics.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryAll {
    ALL("Все"),
    FOOTBALL("Футбол"),
    HOCKEY("Хоккей"),
    VOLLEYBALL("Волейбол"),
    BASKETBALL("Баскетбол"),
    BASEBALL("Бейсбол"),
    TENNIS("Теннис"),
    ;

    private final String name;

}

