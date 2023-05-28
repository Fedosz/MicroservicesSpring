package ru.order.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.order.DAO.OrdersService;
import ru.order.models.Dish;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
@RequiredArgsConstructor
public class DishController {

    private final OrdersService ordersService;

    @GetMapping("/getAll")
    public ResponseEntity<StringBuilder> allDishes() {
        List<Dish> dishes = ordersService.allDishes();
        StringBuilder response = new StringBuilder();

        if (dishes.isEmpty()) {
            return new ResponseEntity<>(new StringBuilder("No dishes yet"), HttpStatus.OK);
        }

        for (Dish dish : dishes) {
            response.append(dish.getId()).append(": ")
                    .append(dish.getName()).append("\n")
                    .append("Quantity: ").append(dish.getQuantity())
                    .append("\n\n");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
