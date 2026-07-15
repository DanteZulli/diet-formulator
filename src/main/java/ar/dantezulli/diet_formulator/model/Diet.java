package ar.dantezulli.diet_formulator.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

/**
 * Diet/recipe entity linked to an animal profile.
 * Entidad de dieta/receta vinculada a un perfil de animal.
 *
 * A diet contains multiple food items that together form a nutritional plan.
 * Una dieta contiene múltiples ítems de alimento que juntos forman un plan nutricional.
 *
 * One profile can have many diets; each diet belongs to one profile.
 * Un perfil puede tener muchas dietas; cada dieta pertenece a un perfil.
 */
@Entity
@Table(name = "diet")
public class Diet {

    /** Unique identifier. / Identificador único. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Diet name/description. / Nombre/descripción de la dieta. */
    @Column(nullable = false)
    private String nombre;

    /** The animal profile this diet is for. / El perfil de animal para el que es esta dieta. */
    @ManyToOne
    @JoinColumn(name = "animal_profile_id", nullable = false)
    private AnimalProfile animalProfile;

    /** List of food items in this diet. / Lista de ítems de alimento en esta dieta. */
    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("fechaCreacion ASC")
    private List<DietItem> items = new ArrayList<>();

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

    public Diet() {}

    // --- Helper methods / Métodos auxiliares ---

    /**
     * Adds a food item to this diet. / Agrega un ítem de alimento a esta dieta.
     */
    public void addItem(DietItem item) {
        items.add(item);
        item.setDiet(this);
    }

    /**
     * Removes a food item from this diet. / Elimina un ítem de alimento de esta dieta.
     */
    public void removeItem(DietItem item) {
        items.remove(item);
        item.setDiet(null);
    }

    // --- Getters and Setters / Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public AnimalProfile getAnimalProfile() { return animalProfile; }
    public void setAnimalProfile(AnimalProfile animalProfile) { this.animalProfile = animalProfile; }

    public List<DietItem> getItems() { return items; }
    public void setItems(List<DietItem> items) { this.items = items; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
}
