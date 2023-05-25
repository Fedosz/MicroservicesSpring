package ru.order.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "order_dish")
@AllArgsConstructor
@NoArgsConstructor
public class Order_dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer order_id;
    private Integer dish_id;
    private Integer quantity;
    private Double price;
}
