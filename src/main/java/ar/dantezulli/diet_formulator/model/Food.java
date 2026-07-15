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

/**
 * Food entity with nutritional information.
 * Entidad de alimento con información nutricional.
 *
 * Nutritional data is stored per 100g of the food.
 * Los datos nutricionales se almacenan por cada 100g de alimento.
 *
 * Can be imported from USDA FoodData Central or created manually.
 * Puede importarse de USDA FoodData Central o crearse manualmente.
 */
@Entity
@Table(name = "food")
public class Food {

    /** Unique identifier. / Identificador único. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Food name. / Nombre del alimento. */
    @Column(nullable = false)
    private String nombre;

    /** Food type category. / Categoría de tipo de alimento. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlimento tipo;

    /** Portion size value. / Valor del tamaño de porción. */
    private Double porcion;

    /** Portion size unit. / Unidad del tamaño de porción. */
    @Enumerated(EnumType.STRING)
    private UnidadPorcion unidadPorcion;

    /**
     * Nutritional values per 100g of food.
     * Valores nutricionales por cada 100g de alimento.
     *
     * Key: Nutrient enum / Clave: enum Nutrient
     * Value: amount in the nutrient's specified unit / Valor: cantidad en la unidad especificada del nutriente
     */
    @ElementCollection
    @CollectionTable(name = "food_nutrient", joinColumns = @jakarta.persistence.JoinColumn(name = "food_id"))
    @MapKeyColumn(name = "nutrient")
    @Column(name = "nutrient_value")
    @Enumerated(EnumType.STRING)
    private Map<Nutrient, Double> nutrientes = new HashMap<>();

    /** USDA FoodData Central ID (if imported). / ID de USDA FoodData Central (si se importó). */
    private Long usdaId;

    /** Record creation timestamp. / Fecha y hora de creación del registro. */
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /** Record last modification timestamp. / Fecha y hora de última modificación del registro. */
    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    /** JPA callback: set timestamps before persist. / Callback JPA: establecer timestamps antes de persistir. */
    @jakarta.persistence.PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }

    /** JPA callback: update timestamp before update. / Callback JPA: actualizar timestamp antes de modificar. */
    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

    // --- Constructors / Constructores ---

    public Food() {}

    // --- Getters and Setters / Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoAlimento getTipo() { return tipo; }
    public void setTipo(TipoAlimento tipo) { this.tipo = tipo; }

    public Double getPorcion() { return porcion; }
    public void setPorcion(Double porcion) { this.porcion = porcion; }

    public UnidadPorcion getUnidadPorcion() { return unidadPorcion; }
    public void setUnidadPorcion(UnidadPorcion unidadPorcion) { this.unidadPorcion = unidadPorcion; }

    public Map<Nutrient, Double> getNutrientes() { return nutrientes; }
    public void setNutrientes(Map<Nutrient, Double> nutrientes) { this.nutrientes = nutrientes; }

    public Long getUsdaId() { return usdaId; }
    public void setUsdaId(Long usdaId) { this.usdaId = usdaId; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }

    /**
     * Gets a specific nutrient value.
     * Obtiene el valor de un nutriente específico.
     *
     * @param nutrient the nutrient to look for / el nutriente a buscar
     * @return the value, or 0.0 if not present / el valor, o 0.0 si no está presente
     */
    public Double getNutriente(Nutrient nutrient) {
        return nutrientes.getOrDefault(nutrient, 0.0);
    }
}
