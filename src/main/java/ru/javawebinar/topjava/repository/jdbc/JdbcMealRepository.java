package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class JdbcMealRepository implements MealRepository {


    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertMeal;

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    @Autowired
    public JdbcMealRepository(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("dateTime", meal.getDateTime())
                .addValue("calories", meal.getCalories())
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE meals SET description=:description, dateTime=:dateTime, calories=:calories, " +
                        "user_id=:user_id WHERE id=:id AND user_id=:user_id", map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> users = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY dateTime DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? and datetime BETWEEN ? and ? ORDER BY dateTime DESC",
                ROW_MAPPER, userId, startDateTime, endDateTime);
    }
}