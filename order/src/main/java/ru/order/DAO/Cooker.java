package ru.order.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.order.models.Order;
import ru.order.repositories.OrderRepository;

import static java.lang.Thread.sleep;

/**
 * Class that manipulates orders states
 */
@Data
@AllArgsConstructor
public class Cooker implements Runnable{


    private final OrderRepository orderRepository;
    private Integer order_id;

    /**
     * Cooking method
     */
    @Override
    public void run() {
        Order cur = orderRepository.findById(order_id).orElseThrow();

        cur.setStatus("In working");

        orderRepository.save(cur);

        try {
            sleep(1000 * 60);
        } catch (InterruptedException e) {

            // Order is canceled in case of exceptions;
            cur.setStatus("Canceled");
            orderRepository.save(cur);

            throw new RuntimeException(e);
        }

        cur.setStatus("Done");

        orderRepository.save(cur);
    }
}
