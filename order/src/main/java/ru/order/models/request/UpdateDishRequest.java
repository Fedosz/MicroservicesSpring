package ru.order.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishRequest {
    @NotNull(message = "id can't be null")
    private Integer id;
    @NotNull(message = "quantity can't be null")
    @Range(min = 0, message = "quantity can't be less than 0")
    private Integer new_quantity;
}
