package ar.dantezulli.diet_formulator.model.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ar.dantezulli.diet_formulator.model.entities.Food;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.PortionUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {

    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "El tipo es obligatorio")
    private FoodType type;

    private Double portion;

    private PortionUnit portionUnit;

    private Map<Nutrient, Double> nutrients = new HashMap<>();

    public Food toEntity() {
        Food entity = new Food();
        entity.setId(id);
        entity.setName(name);
        entity.setType(type);
        entity.setPortion(portion);
        entity.setPortionUnit(portionUnit);
        entity.setNutrients(nutrients);
        return entity;
    }

    public static FoodDTO from(Food entity) {
        FoodDTO dto = new FoodDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setPortion(entity.getPortion());
        dto.setPortionUnit(entity.getPortionUnit());
        dto.setNutrients(new HashMap<>(entity.getNutrients()));
        return dto;
    }
}
