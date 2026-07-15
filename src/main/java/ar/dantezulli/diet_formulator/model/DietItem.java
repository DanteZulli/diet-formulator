package ar.dantezulli.diet_formulator.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import ar.dantezulli.diet_formulator.model.enums.UnidadCantidad;

/**
 * Individual item within a diet (an ingredient with its quantity).
 * Índividual dentro de una dieta (un ingrediente con su cantidad).
 *
 * Each diet item references a food and specifies how much of it is used.
 * Cada ítem de dieta referencia un alimento y especifica cuánto se usa.
 */
@Entity
@Table(name = "diet_item")
public class DietItem {

    /** Unique identifier. / Identificador único. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The diet this item belongs to. / La dieta a la que pertenece este ítem. */
    @ManyToOne
    @JoinColumn(name = "diet_id", nullable = false)
    private Diet diet;

    /** The food used in this item. / El alimento usado en este ítem. */
    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    /** Quantity of the food item. / Cantidad del alimento. */
    @Column(nullable = false)
    private Double cantidad;

    /** Unit of measurement for the quantity. / Unidad de medida para la cantidad. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadCantidad unidad;

    /** Cooking/preparation type (e.g., "dorada en sartén", "cocido a fuego lento"). / Tipo de cocción/preparación. */
    private String tipoCoccion;

    /** Record creation timestamp. / Fecha y hora de creación del registro. */
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /** JPA callback: set timestamps before persist. / Callback JPA: establecer timestamps antes de persistir. */
    @jakarta.persistence.PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    // --- Constructors / Constructores ---

    public DietItem() {}

    public DietItem(Diet diet, Food food, Double cantidad, UnidadCantidad unidad) {
        this.diet = diet;
        this.food = food;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    // --- Getters and Setters / Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Diet getDiet() { return diet; }
    public void setDiet(Diet diet) { this.diet = diet; }

    public Food getFood() { return food; }
    public void setFood(Food food) { this.food = food; }

    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }

    public UnidadCantidad getUnidad() { return unidad; }
    public void setUnidad(UnidadCantidad unidad) { this.unidad = unidad; }

    public String getTipoCoccion() { return tipoCoccion; }
    public void setTipoCoccion(String tipoCoccion) { this.tipoCoccion = tipoCoccion; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
}
