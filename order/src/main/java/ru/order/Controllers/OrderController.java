package ru.order.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.order.DAO.DishService;
import ru.order.DAO.OrdersService;
import ru.order.config.JwtService;
import ru.order.models.Dish;
import ru.order.models.Order;
import ru.order.models.request.OrderRequest;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;
    private final DishService dishService;
    private final JwtService jwtService;

    @GetMapping("/menu")
    public ResponseEntity<StringBuilder> getMenu() {
        List<Dish> dishes = dishService.allDishes();
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

    @PostMapping("/new")
    public ResponseEntity<StringBuilder> makeOrder(@RequestBody @Valid OrderRequest orderRequest,
                                                   BindingResult bindingResult,
                                                   @RequestHeader(name="Authorization") String token) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new StringBuilder("Your order can't be empty"), HttpStatus.BAD_REQUEST);
        }
        token = token.substring(7);

        return ordersService.makeOrder(orderRequest.getDishes(), orderRequest.getSpecial_request(), jwtService.extractUsername(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StringBuilder> orderInfo(@PathVariable Integer id) {
        Order current = ordersService.findOrder(id);
        if (current == null) {
            return new ResponseEntity<>(new StringBuilder("No order with current id"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new StringBuilder(current.getStatus()), HttpStatus.OK);
        }
    }
}
