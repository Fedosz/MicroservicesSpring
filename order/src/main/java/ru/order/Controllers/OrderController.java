package ru.order.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.order.models.Dish;
import ru.order.repositories.DishRepository;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final DishRepository dishRepository;

    @GetMapping("/menu")
    public ResponseEntity<StringBuilder> getMenu() {
        List<Dish> dishes = dishRepository.findAll();
        StringBuilder response = new StringBuilder();

        if (dishes.isEmpty()) {
            return new ResponseEntity<>(new StringBuilder("Menu is empty yet"), HttpStatus.OK);
        }

        for (Dish dish: dishes) {
            if (dish.getIs_available()) {
                response.append(dish.getName())
                        .append(":\n")
                        .append(dish.getDescription())
                        .append("\nPrice: ")
                        .append(dish.getPrice())
                        .append("\n\n");
            }
        }

        if (response.length() == 0) {
            return new ResponseEntity<>(new StringBuilder("All dishes are gone, come back later"), HttpStatus.OK);
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
