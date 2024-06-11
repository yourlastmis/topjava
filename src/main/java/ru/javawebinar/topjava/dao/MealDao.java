package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void create(Meal meal);
    List<Meal> readAll();
    Meal read(Long id);
    void update(Meal meal);
    void delete(Long id);
}
