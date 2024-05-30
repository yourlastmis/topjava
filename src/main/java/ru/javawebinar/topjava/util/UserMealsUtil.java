package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        List<UserMealWithExcess> mealsTo1 = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("Cicle filtered");
        if (mealsTo != null) {
            mealsTo.forEach(System.out::println);
        }
        System.out.println("Stream filtered");
        if (mealsTo1 != null) {
            mealsTo1.forEach(System.out::println);
        }

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        boolean excess = false;
        Collections.sort(meals, new Comparator<UserMeal>() {
            @Override
            public int compare(final UserMeal object1, final UserMeal object2) {
                return object1.getDateTime().compareTo(object2.getDateTime());
            }
        });
        int sumCaloriesPerDay = 0;
        int date = meals.get(0).getDateTime().getDayOfMonth();
        List<UserMealWithExcess> resultList = new ArrayList<>();
        List<UserMeal> intermediateList = new ArrayList<>();
//        meals.stream().sorted((date1,date2) ->date1.getDateTime().compareTo(date2.getDateTime()));
        for( UserMeal userMeal : meals) {
            if (date != userMeal.getDateTime().getDayOfMonth()){
                date = userMeal.getDateTime().getDayOfMonth();
                if (sumCaloriesPerDay > caloriesPerDay){
                    intermediateList.forEach(userMealIntermediateList -> {
                        resultList.add(new UserMealWithExcess(userMealIntermediateList.getDateTime(), userMealIntermediateList.getDescription(), userMealIntermediateList.getCalories(), true));
                    });
                }
//                else {
//                    intermediateList.forEach(userMealIntermediateList -> {
//                        resultList.add(new UserMealWithExcess(userMealIntermediateList.getDateTime(), userMealIntermediateList.getDescription(), userMealIntermediateList.getCalories(), false);
//                    });
//                }
                sumCaloriesPerDay = 0;
                intermediateList.clear();
            }
            sumCaloriesPerDay = sumCaloriesPerDay + userMeal.getCalories();
            intermediateList.add(userMeal);
        }
        if (sumCaloriesPerDay > caloriesPerDay){
            intermediateList.forEach(userMealIntermediateList -> {
                resultList.add(new UserMealWithExcess(userMealIntermediateList.getDateTime(), userMealIntermediateList.getDescription(), userMealIntermediateList.getCalories(), true));
            });
        }
//        else {
//            intermediateList.forEach(userMealIntermediateList -> {
//                resultList.add(new UserMealWithExcess(userMealIntermediateList.getDateTime(), userMealIntermediateList.getDescription(), userMealIntermediateList.getCalories(), false));
//            });
//        }


        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        //List<List<UserMeal>> groupedMeals = meals.stream().collect(Collectors.groupingBy(element -> element.getDateTime().getDayOfMonth())).values().stream().collect(Collectors.toList());

        //List<List<UserMeal>> filteredMeals = groupedMeals.stream().filter(listItem -> listItem.stream().mapToInt(i -> i.getCalories()).sum() > caloriesPerDay).collect(Collectors.toList());

        //List<UserMeal> flatMap = filteredMeals.stream().flatMap(List::stream).collect(Collectors.toList());


        //List<UserMealWithExcess> mapList = flatMap.stream().map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true)).collect(Collectors.toList());

        //List<UserMealWithExcess> filteredMeals0 = meals.stream().collect(Collectors.groupingBy(element -> element.getDateTime().getDayOfMonth())).values().stream().map(userMeal -> new UserMealWithExcess(userMeal.get(0).getDateTime(), userMeal.get(0).getDescription(), userMeal.get(0).getCalories(),true)).collect(Collectors.toList());

        List<UserMealWithExcess> resultMeals = meals.stream()
                .collect(Collectors.groupingBy(element -> element.getDateTime().getDayOfMonth()))
                .values().stream().filter(listItem -> listItem.stream().mapToInt(i -> i.getCalories()).sum() > caloriesPerDay)
                .flatMap(List::stream).map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true)).collect(Collectors.toList());



        return resultMeals;
    }
//    boolean isMealsCaloriesExeeded () {
//        return get
//    }
}
