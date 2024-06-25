package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final List<Meal> ADMIN_MEALS = Arrays.asList(
            new Meal(START_SEQ + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Админ обед", 500),
            new Meal(START_SEQ + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Админ ужин", 1410)
    );

    public static final List<Meal> USER_MEALS = Arrays.asList(
            new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 00, 0), "Еда на граничное значение", 100),
            new Meal(START_SEQ + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(START_SEQ + 10, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(START_SEQ + 11, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}