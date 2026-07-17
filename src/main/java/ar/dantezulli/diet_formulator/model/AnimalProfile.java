package ar.dantezulli.diet_formulator.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import ar.dantezulli.diet_formulator.model.enums.Especie;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.NivelActividad;
import lombok.Data;

/**
 * Animal patient profile.
 *
 * Stores all patient data needed for diet formulation:
 * - Basic identification (name, species, breed)
 * - Physical attributes (weight, age, body condition)
 * - Energy requirements (activity level, caloric intake)
 * - Macronutrient targets
 */
@Data
@Entity
@Table(name = "animal_profile")
public class AnimalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especie especie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LifeStage lifeStage;

    @Column(nullable = false)
    private Double pesoKg;

    private Double pesoIdealKg;

    @Column(nullable = false)
    private Boolean enPesoIdeal = true;

    @Column(nullable = false)
    private Integer edadMeses;

    private String raza;

    @Column(nullable = false)
    private Integer condicionCorporal;

    @Column(nullable = false)
    private Boolean peloNegro = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelActividad nivelActividad;

    private Double pesoAdultoCachorro;

    @Column(nullable = false)
    private Boolean sobrepeso = false;

    private Integer numCachorrosLactancia;

    private Integer semanasLactancia;

    private Double ingestaCaloricaCustom;

    @Column(nullable = false)
    private Boolean usarIngestaRecomendada = true;

    @Column(nullable = false)
    private Double ingestaCaloricaRecomendada;

    @Embedded
    private MacronutrientTargets macronutrientTargets;

    @Column(columnDefinition = "TEXT")
    private String notas;

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
}
