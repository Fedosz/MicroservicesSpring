package ru.order.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.order.DAO.DishService;
import ru.order.DAO.OrdersService;
import ru.order.models.Dish;
import ru.order.models.request.DishRequest;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
@RequiredArgsConstructor
public class DishController {

    private final OrdersService ordersService;
    private final DishService dishService;

    @GetMapping("/getAll")
    public ResponseEntity<StringBuilder> allDishes() {
        List<Dish> dishes = dishService.allDishes();
        StringBuilder response = new StringBuilder();

        if (dishes.isEmpty()) {
            return new ResponseEntity<>(new StringBuilder("No dishes yet"), HttpStatus.OK);
        }

        for (Dish dish : dishes) {
            response.append(dish.getId().toString()).append(": ")
                    .append(dish.getName()).append("\n")
                    .append("Quantity: ").append(dish.getQuantity().toString())
                    .append("\n\n");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createDish(@RequestBody @Valid DishRequest dishRequest,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            StringBuilder response = new StringBuilder();
            for (FieldError error : fieldErrorList) {
                response.append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
        }

        dishService.createDish(dishRequest);

        return new ResponseEntity<>("Dish successfully added", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDish(@RequestBody Integer id) {
        return dishService.deleteDish(id);
    }

}
