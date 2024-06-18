package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.UserType;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static final UserType currentUser = UserType.USER;

    public static int authUserId() {
        return currentUser.getId();
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}