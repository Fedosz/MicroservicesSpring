package ru.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.order.models.Order_dish;

@Repository
public interface Order_dishRepository extends JpaRepository<Order_dish, Integer> {
}
