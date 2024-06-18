package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal ->save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        meal.setUserId(userId);
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(),
                (id, oldMeal) -> oldMeal.getUserId().equals(userId) ? meal : oldMeal);
    }

    @Override
    public boolean delete(int id, Integer userId) {return repository.remove(id, get(id, userId));}

    //с примитивами запутался, хотел использовать тип int userId, но тогда невозможно сравнивать через equals, пришлось бы использовать ==. ВА любом случае оба варианта выглядят корявенько
    @Override
    public Meal get(int id, Integer userId) {
        Meal searchResultMeal = repository.getOrDefault(id, new Meal(null, null, 0));
        return userId.equals(searchResultMeal.getUserId()) ? searchResultMeal : null;
    }

    //Аналогично с предыдущим методом
    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values().stream().filter(meal -> userId.equals(meal.getUserId()))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

