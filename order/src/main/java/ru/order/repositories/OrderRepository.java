package ru.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.order.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
