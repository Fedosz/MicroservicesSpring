package ru.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.order.models.Dish;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    Optional<Dish> findByName(String name);
}
