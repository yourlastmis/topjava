package ru.javawebinar.topjava.model;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Meal {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Long id;

    public Meal(LocalDateTime dateTime, String description, int calories, Long id) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = id;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

}
