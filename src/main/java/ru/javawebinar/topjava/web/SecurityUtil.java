package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserType;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static UserType currentUser = UserType.USER;

    public static void setCurrentUser(UserType currentUser) {
        SecurityUtil.currentUser = currentUser;
    }

    public static int authUserId() {
        return currentUser.getId();
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}