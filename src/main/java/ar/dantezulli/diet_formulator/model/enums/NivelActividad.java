package ar.dantezulli.diet_formulator.model.enums;

/**
 * Activity level of the animal, ordered from highest to lowest.
 * Nivel de actividad del animal, ordenado de mayor a menor.
 *
 * The activity factor is used to calculate the daily caloric intake
 * (MER - Maintenance Energy Requirement) based on NRC standards.
 * El factor de actividad se usa para calcular la ingesta calórica diaria
 * (MER - Requisito Energético de Mantenimiento) según estándares NRC.
 */
public enum NivelActividad {

    /** Great Dane / Active Greyhound. / Gran Danés / Galgo activo. */
    GRAN_DANES,

    /** Terrier. / Terrier. */
    TERRIER,

    /** Very active. / Muy activo. */
    MUY_ACTIVO,

    /** Active. / Activo. */
    ACTIVO,

    /** Young adult. / Adulto joven. */
    ADULTO_JOVEN,

    /** Inactive, senior. / Inactivo, senior. */
    INACTIVO_SENIOR,

    /** Kennel dog, very inactive. / Perro canil, muy inactivo. */
    CANIL_MUY_INACTIVO,

    /** Sedentary. / Sedentario. */
    SEDENTARIO
}
