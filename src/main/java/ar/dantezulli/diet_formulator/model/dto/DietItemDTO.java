package ar.dantezulli.diet_formulator.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

import ar.dantezulli.diet_formulator.model.enums.QuantityUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietItemDTO {

    private UUID id;

    @NotNull(message = "El alimento es obligatorio")
    private UUID foodId;

    @NotNull(message = "La cantidad es obligatoria")
    private Double quantity;

    @NotNull(message = "La unidad es obligatoria")
    private QuantityUnit unit;

    private String cookingMethod;
}
