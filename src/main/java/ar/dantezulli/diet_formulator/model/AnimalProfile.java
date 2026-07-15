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

/**
 * Animal patient profile.
 * Perfil del paciente animal.
 *
 * Stores all patient data needed for diet formulation:
 * Almacena todos los datos del paciente necesarios para la formulación de dietas:
 * - Basic identification (name, species, breed) / Identificación básica (nombre, especie, raza)
 * - Physical attributes (weight, age, body condition) / Atributos físicos (peso, edad, condición corporal)
 * - Energy requirements (activity level, caloric intake) / Requisitos energéticos (nivel de actividad, ingesta calórica)
 * - Macronutrient targets / Objetivos de macronutrientes
 */
@Entity
@Table(name = "animal_profile")
public class AnimalProfile {

    /** Unique identifier. / Identificador único. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Animal name. / Nombre del animal. */
    @Column(nullable = false)
    private String nombre;

    /** Species (dog or cat). / Especie (perro o gato). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especie especie;

    /** Life stage (adult, puppy, pregnant, lactating). / Etapa de vida (adulto, cachorro, preñada, lactancia). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LifeStage lifeStage;

    /** Current weight in kilograms. / Peso actual en kilogramos. */
    @Column(nullable = false)
    private Double pesoKg;

    /** Ideal weight in kilograms. Null if the animal is at its ideal weight. / Peso ideal en kilogramos. Null si el animal está en su peso ideal. */
    private Double pesoIdealKg;

    /** Whether the animal is at its ideal weight. / Si el animal está en su peso ideal. */
    @Column(nullable = false)
    private Boolean enPesoIdeal = true;

    /** Age in months (always stored in months regardless of input). / Edad en meses (siempre almacenada en meses sin importar el input). */
    @Column(nullable = false)
    private Integer edadMeses;

    /** Breed. / Raza. */
    private String raza;

    /** Body condition score (1-5). / Puntuación de condición corporal (1-5). */
    @Column(nullable = false)
    private Integer condicionCorporal;

    /** Whether the animal has black fur (affects some nutrient requirements). / Si el animal tiene pelo negro (afecta algunos requisitos nutricionales). */
    @Column(nullable = false)
    private Boolean peloNegro = false;

    /** Activity level for caloric calculation. / Nivel de actividad para cálculo calórico. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelActividad nivelActividad;

    /** Adult weight for puppies (in kg). Null for non-puppies. / Peso de adulto para cachorros (en kg). Null para no cachorros. */
    private Double pesoAdultoCachorro;

    /** Whether the cat is overweight. Only applicable to cats. / Si el gato tiene sobrepeso. Solo aplicable a gatos. */
    @Column(nullable = false)
    private Boolean sobrepeso = false;

    /** Number of puppies/kittens during lactation. Null if not lactating. / Número de cachorros durante lactancia. Null si no está en lactancia. */
    private Integer numCachorrosLactancia;

    /** Weeks of lactation (1-4). Null if not lactating. / Semanas de lactancia (1-4). Null si no está en lactancia. */
    private Integer semanasLactancia;

    /** Custom caloric intake override. Null if using recommended value. / Ingesta calórica personalizada. Null si se usa el valor recomendado. */
    private Double ingestaCaloricaCustom;

    /** Whether to use the recommended caloric intake. / Si se usa la ingesta calórica recomendada. */
    @Column(nullable = false)
    private Boolean usarIngestaRecomendada = true;

    /** Calculated recommended caloric intake (kcal/day). / Ingesta calórica recomendada calculada (kcal/día). */
    @Column(nullable = false)
    private Double ingestaCaloricaRecomendada;

    /** Macronutrient percentage targets. / Porcentajes objetivo de macronutrientes. */
    @Embedded
    private MacronutrientTargets macronutrientTargets;

    /** Free-form consultation notes. / Notas libres de la consulta. */
    @Column(columnDefinition = "TEXT")
    private String notas;

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

    public AnimalProfile() {}

    // --- Getters and Setters / Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }

    public LifeStage getLifeStage() { return lifeStage; }
    public void setLifeStage(LifeStage lifeStage) { this.lifeStage = lifeStage; }

    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }

    public Double getPesoIdealKg() { return pesoIdealKg; }
    public void setPesoIdealKg(Double pesoIdealKg) { this.pesoIdealKg = pesoIdealKg; }

    public Boolean getEnPesoIdeal() { return enPesoIdeal; }
    public void setEnPesoIdeal(Boolean enPesoIdeal) { this.enPesoIdeal = enPesoIdeal; }

    public Integer getEdadMeses() { return edadMeses; }
    public void setEdadMeses(Integer edadMeses) { this.edadMeses = edadMeses; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public Integer getCondicionCorporal() { return condicionCorporal; }
    public void setCondicionCorporal(Integer condicionCorporal) { this.condicionCorporal = condicionCorporal; }

    public Boolean getPeloNegro() { return peloNegro; }
    public void setPeloNegro(Boolean peloNegro) { this.peloNegro = peloNegro; }

    public NivelActividad getNivelActividad() { return nivelActividad; }
    public void setNivelActividad(NivelActividad nivelActividad) { this.nivelActividad = nivelActividad; }

    public Double getPesoAdultoCachorro() { return pesoAdultoCachorro; }
    public void setPesoAdultoCachorro(Double pesoAdultoCachorro) { this.pesoAdultoCachorro = pesoAdultoCachorro; }

    public Boolean getSobrepeso() { return sobrepeso; }
    public void setSobrepeso(Boolean sobrepeso) { this.sobrepeso = sobrepeso; }

    public Integer getNumCachorrosLactancia() { return numCachorrosLactancia; }
    public void setNumCachorrosLactancia(Integer numCachorrosLactancia) { this.numCachorrosLactancia = numCachorrosLactancia; }

    public Integer getSemanasLactancia() { return semanasLactancia; }
    public void setSemanasLactancia(Integer semanasLactancia) { this.semanasLactancia = semanasLactancia; }

    public Double getIngestaCaloricaCustom() { return ingestaCaloricaCustom; }
    public void setIngestaCaloricaCustom(Double ingestaCaloricaCustom) { this.ingestaCaloricaCustom = ingestaCaloricaCustom; }

    public Boolean getUsarIngestaRecomendada() { return usarIngestaRecomendada; }
    public void setUsarIngestaRecomendada(Boolean usarIngestaRecomendada) { this.usarIngestaRecomendada = usarIngestaRecomendada; }

    public Double getIngestaCaloricaRecomendada() { return ingestaCaloricaRecomendada; }
    public void setIngestaCaloricaRecomendada(Double ingestaCaloricaRecomendada) { this.ingestaCaloricaRecomendada = ingestaCaloricaRecomendada; }

    public MacronutrientTargets getMacronutrientTargets() { return macronutrientTargets; }
    public void setMacronutrientTargets(MacronutrientTargets macronutrientTargets) { this.macronutrientTargets = macronutrientTargets; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
}
