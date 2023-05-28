package ru.order.DAO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.order.models.*;
import ru.order.models.request.DishRequest;
import ru.order.models.request.UpdateDishRequest;
import ru.order.repositories.DishRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Service that manipulates with dishes
 */
@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    /**
     * Get all dishes
     * @return = list of dishes
     */
    public List<Dish> allDishes() {
        return dishRepository.findAll();
    }

    /**
     * Create new dish
     * @param dishRequest = request body
     */
    public void createDish(DishRequest dishRequest) {
        Dish newDish = new Dish();
        newDish.setQuantity(dishRequest.getQuantity());
        newDish.setPrice(dishRequest.getPrice());
        newDish.setName(dishRequest.getName());
        newDish.setDescription(dishRequest.getDescription());
        newDish.setIs_available(dishRequest.getQuantity() > 0);

        dishRepository.save(newDish);
    }

    /**
     * Delete dish
     * @param id = id
     * @return = Answer
     */
    public ResponseEntity<String> deleteDish(Integer id) {
        if (!dishRepository.existsById(id)) {
            return new ResponseEntity<>("Dish doesn't exist", HttpStatus.BAD_REQUEST);
        } else {
            Dish cur = dishRepository.findById(id).orElseThrow();
            dishRepository.delete(cur);
            return new ResponseEntity<>("Dish successfully deleted", HttpStatus.OK);
        }
    }

    /**
     * Update dish quantity
     * @param updateDishRequest = request body
     * @return = answer
     */
    public ResponseEntity<String> setDishQuantity(UpdateDishRequest updateDishRequest) {
        if (!dishRepository.existsById(updateDishRequest.getId())) {
            return new ResponseEntity<>("Dish with current id doesn't exist", HttpStatus.BAD_REQUEST);
        } else {
            Dish dish = dishRepository.findById(updateDishRequest.getId()).orElseThrow();
            dish.setQuantity(updateDishRequest.getNew_quantity());

            if (updateDishRequest.getNew_quantity() == 0) {
                dish.setIs_available(false);
            } else {
                dish.setIs_available(true);
            }

            dish.setUpdated_at(new Timestamp(new Date().getTime()));

            dishRepository.save(dish);

            return new ResponseEntity<>("Dish quantity successfully updated", HttpStatus.OK);
        }

    }

}
