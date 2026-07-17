package ar.dantezulli.diet_formulator.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;

import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.TipoAlimento;
import ar.dantezulli.diet_formulator.model.enums.UnidadPorcion;
import lombok.Data;

/**
 * Food entity with nutritional information.
 *
 * Nutritional data is stored per 100g of the food.
 *
 * Can be imported from USDA FoodData Central or created manually.
 */
@Data
@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlimento tipo;

    private Double porcion;

    @Enumerated(EnumType.STRING)
    private UnidadPorcion unidadPorcion;

    /**
     * Nutritional values per 100g of food.
     *
     * Key: Nutrient enum
     * Value: amount in the nutrient's specified unit
     */
    @ElementCollection
    @CollectionTable(name = "food_nutrient", joinColumns = @jakarta.persistence.JoinColumn(name = "food_id"))
    @MapKeyColumn(name = "nutrient")
    @Column(name = "nutrient_value")
    @Enumerated(EnumType.STRING)
    private Map<Nutrient, Double> nutrientes = new HashMap<>();

    private Long usdaId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }

    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

    /**
     * Gets a specific nutrient value.
     *
     * @param nutrient the nutrient to look for
     * @return the value, or 0.0 if not present
     */
    public Double getNutriente(Nutrient nutrient) {
        return nutrientes.getOrDefault(nutrient, 0.0);
    }
}
