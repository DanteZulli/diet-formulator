package ar.dantezulli.diet_formulator.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

import ar.dantezulli.diet_formulator.model.entities.Diet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietDTO {

    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private UUID animalProfileId;

    public Diet toEntity() {
        Diet entity = new Diet();
        entity.setId(id);
        entity.setName(name);
        return entity;
    }
}
