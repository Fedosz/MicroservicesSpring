package ru.order.DAO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.order.models.*;
import ru.order.models.request.DishRequest;
import ru.order.repositories.DishRepository;
import ru.order.repositories.OrderRepository;
import ru.order.repositories.Order_dishRepository;
import ru.order.repositories.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final Order_dishRepository orderDishRepository;
    private final UserRepo userRepository;

    public List<Dish> allDishes() {
        return dishRepository.findAll();
    }

    public void createDish(DishRequest dishRequest) {
        Dish newDish = new Dish();
        newDish.setQuantity(dishRequest.getQuantity());
        newDish.setPrice(dishRequest.getPrice());
        newDish.setName(dishRequest.getName());
        newDish.setDescription(dishRequest.getDescription());
        newDish.setIs_available(dishRequest.getQuantity() > 0);

        dishRepository.save(newDish);
    }

}
