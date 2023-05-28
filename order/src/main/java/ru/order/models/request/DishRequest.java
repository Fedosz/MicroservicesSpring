package ru.order.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishRequest {
    @NotNull (message = "name can't be null")
    private String name;
    @NotNull (message = "description can't be null")
    private String Description;
    @NotNull (message = "price can't be null")
    @DecimalMin(value = "0.00", message = "Enter higher price")
    @DecimalMax(value = "100000.00", message = "Enter lower price")
    private Double price;
    @NotNull (message = "quantity can't be null")
    @Range(min = 0, message = "Quantity can't be less than 0")
    private Integer quantity;
}
