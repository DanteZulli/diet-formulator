package ar.dantezulli.diet_formulator.model.enums;

/**
 * Activity level of the animal, ordered from highest to lowest.
 *
 * The activity factor is used to calculate the daily caloric intake
 * (MER - Maintenance Energy Requirement) based on NRC standards.
 */
public enum NivelActividad {

    /** Great Dane / Active Greyhound. */
    GRAN_DANES,

    /** Terrier. */
    TERRIER,

    /** Very active. */
    MUY_ACTIVO,

    /** Active. */
    ACTIVO,

    /** Young adult. */
    ADULTO_JOVEN,

    /** Inactive, senior. */
    INACTIVO_SENIOR,

    /** Kennel dog, very inactive. */
    CANIL_MUY_INACTIVO,

    /** Sedentary. */
    SEDENTARIO
}
