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
import lombok.Data;
import lombok.NoArgsConstructor;

import ar.dantezulli.diet_formulator.model.enums.UnidadCantidad;

/**
 * Individual item within a diet (an ingredient with its quantity).
 *
 * Each diet item references a food and specifies how much of it is used.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "diet_item")
public class DietItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diet_id", nullable = false)
    private Diet diet;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(nullable = false)
    private Double cantidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadCantidad unidad;

    private String tipoCoccion;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public DietItem(Diet diet, Food food, Double cantidad, UnidadCantidad unidad) {
        this.diet = diet;
        this.food = food;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }
}
