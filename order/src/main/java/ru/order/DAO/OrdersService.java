package ru.order.DAO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.order.models.*;
import ru.order.repositories.DishRepository;
import ru.order.repositories.OrderRepository;
import ru.order.repositories.Order_dishRepository;
import ru.order.repositories.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Services that manipulates orders
 */
@Service
@RequiredArgsConstructor
public class OrdersService {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final Order_dishRepository orderDishRepository;
    private final UserRepo userRepository;

    /**
     * New order
     * @param dishes = dishes list
     * @param text = special request
     * @param email = email of user
     * @return = answer
     */
    public ResponseEntity<StringBuilder> makeOrder(List<DishInfo<String, Integer>> dishes, String text, String email) {

        StringBuilder answer = new StringBuilder();
        List<Order_dish> orderDishes = new ArrayList<>();
        Order current_order = new Order();

        boolean couldBeDone = true;

        //Start making order
        User user = userRepository.findByEmail(email).orElseThrow();
        current_order.setUserId(user.getId());
        current_order.setStatus("Waiting");
        current_order.setSpecial_requests(text);

        int cur = 0;
        Integer order_id = 0;

        //Checking if we have all dishes
        for (var pair : dishes) {

            String name = pair.name();
            Integer quantity = pair.quantity();

            if (!dishRepository.existsByName(name)) {
                answer.append("Dish ").append(name).append(" doesn't exist\n");
                couldBeDone = false;
            } else {
               Dish dish = dishRepository.findByName(name).orElseThrow();

               if (dish.getQuantity() < quantity) {
                   answer.append("Not enough ").append(dish.getName()).append(" in the stock\n");
                   couldBeDone = false;
               } else {
                   Order_dish orderDish = new Order_dish();
                   orderDish.setDish_id(dish.getId());
                   orderDish.setPrice(dish.getPrice() * quantity);
                   orderDish.setQuantity(quantity);

                   orderDishes.add(orderDish);
               }
            }
        }

        //If all good - start making order_dishes
        if (couldBeDone) {
            orderRepository.save(current_order);

            //Find current order id by user id and status "Waiting"
            List<Order> possibles = orderRepository.findByUserId(user.getId());
            for (Order order : possibles) {
                if (Objects.equals(order.getStatus(), "Waiting")) {
                    order_id = order.getId();

                    for (Order_dish orderDish : orderDishes) {
                        orderDish.setOrder_id(order_id);

                        orderDishRepository.save(orderDish);

                        Integer dish_id = orderDish.getDish_id();
                        Dish dish = dishRepository.findById(dish_id).orElseThrow();

                        //Decrease amount of dishes in stock
                        dish.setQuantity(dish.getQuantity() - orderDish.getQuantity());
                        if (dish.getQuantity() == 0) {
                            dish.setIs_available(false);
                        }

                        dishRepository.save(dish);
                    }

                    //Send order for cooking

                    Cooker cooker = new Cooker(orderRepository, order_id);
                    Thread cooking = new Thread(cooker);

                    cooking.start();
                }
            }
        } else {
            return new ResponseEntity<>(answer, HttpStatus.BAD_REQUEST);
        }

        StringBuilder response = new StringBuilder("Order successfully created\n");
        response.append("Order id:").append(order_id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Find order by id
     * @param id = id
     * @return order
     */
    public Order findOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            return null;
        } else {
            return orderRepository.findById(id).orElseThrow();
        }
    }


}
