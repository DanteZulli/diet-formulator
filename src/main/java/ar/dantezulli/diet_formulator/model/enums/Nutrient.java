package ar.dantezulli.diet_formulator.model.enums;

/**
 * All nutrients tracked in the nutritional database.
 *
 * Names match the Spanish labels from the original formulator.
 */
public enum Nutrient {

    // --- Energy and general ---
    /** Energy in kcal. */
    ENERGIA_KCAL("Energía kcal", "kcal"),
    /** Portion size in grams. */
    TAMANIO_PORCION_G("Tamaño porción g", "g"),
    /** Water in grams. */
    AGUA_G("Agua g", "g"),
    /** Dry matter in grams. */
    MATERIA_SECA_G("Materia seca g", "g"),
    /** Ash in grams. */
    CENIZA_G("Ceniza g", "g"),

    // --- Protein ---
    /** Total protein in grams. */
    PROTEINA_G("Proteína g", "g"),
    /** Tryptophan in grams. */
    TRIPTOFANO_G("Triptófano g", "g"),
    /** Threonine in grams. */
    TREONINA_G("Treonina g", "g"),
    /** Isoleucine in grams. */
    ISOLEUCINA_G("Isoleucina g", "g"),
    /** Leucine in grams. */
    LEUCINA_G("Leucina g", "g"),
    /** Lysine in grams. */
    LISINA_G("Lisina g", "g"),
    /** Methionine in grams. */
    METIONINA_G("Metionina g", "g"),
    /** Methionine + Cystine in grams. */
    METIONINA_CISTINA_G("Metionina y Cistina g", "g"),
    /** Phenylalanine in grams. */
    FENILALANINA_G("Fenilalanina g", "g"),
    /** Phenylalanine + Tyrosine in grams. */
    FENILALANINA_TIROSINA_G("Fenilalanina y Tirosina g", "g"),
    /** Valine in grams. */
    VALINA_G("Valina g", "g"),
    /** Arginine in grams. */
    ARGININA_G("Arginina g", "g"),
    /** Histidine in grams. */
    HISTIDINA_G("Histidina g", "g"),
    /** Taurine in milligrams. */
    TAURINA_MG("Taurina mg", "mg"),

    // --- Lipids ---
    /** Total lipids in grams. */
    LIPIDOS_TOTALES_G("Lípidos totales g", "g"),
    /** Linoleic acid in grams. */
    ACIDO_LINOLEICO_G("Ácido linoleico g", "g"),
    /** Alpha-linoleic acid in grams. */
    ACIDO_ALINOLEICO_G("Ácido α-linoleico g", "g"),
    /** 20:2 n-6 c,c in grams. */
    ACIDO_20_2_N6_G("20:2 n-6 c,c g", "g"),
    /** 20:3 n-6 in grams. */
    ACIDO_20_3_N6_G("20:3 n-6 g", "g"),
    /** Arachidonic acid in grams. */
    ACIDOARAQUIDONICO_G("Ácido araquidónico g", "g"),
    /** EPA (20:5 n-3) in grams. */
    EPA_G("20:5 n-3 (EPA) g", "g"),
    /** DHA (22:6 n-3) in grams. */
    DHA_G("22:6 n-3 (DHA) g", "g"),
    /** EPA + DHA undifferentiated in grams. */
    EPA_DHA_SIN_DIFERENCIAR_G("EPA + DHA sin diferenciar g", "g"),
    /** EPA + DHA total in grams. */
    EPA_DHA_TOTAL_G("EPA + DHA Total g", "g"),
    /** Omega 6 unspecified in grams. */
    OMEGA_6_G("Omega 6 sin especificar g", "g"),
    /** Omega 3 unspecified in grams. */
    OMEGA_3_G("Omega 3 sin especificar g", "g"),
    /** Omega 6 : Omega 3 ratio. */
    OMEGA_6_3_RATIO("Omega 6 : Omega 3", "ratio"),

    // --- Carbohydrates ---
    /** Total carbohydrates in grams. */
    CARBOHIDRATOS_G("Carbohidratos g", "g"),
    /** Total fiber in grams. */
    FIBRA_TOTAL_G("Fibra, total g", "g"),

    // --- Minerals ---
    /** Calcium in milligrams. */
    CALCIO_MG("Calcio, Ca mg", "mg"),
    /** Iron in milligrams. */
    HIERRO_MG("Hierro, Fe mg", "mg"),
    /** Magnesium in milligrams. */
    MAGNESIO_MG("Magnesio, Mg mg", "mg"),
    /** Phosphorus in milligrams. */
    FOSFORO_MG("Fósforo, P mg", "mg"),
    /** Potassium in milligrams. */
    POTASIO_MG("Potasio, K mg", "mg"),
    /** Sodium in milligrams. */
    SODIO_MG("Sodio, Na mg", "mg"),
    /** Zinc in milligrams. */
    ZINC_MG("Zinc, Zn mg", "mg"),
    /** Copper in milligrams. */
    COBRE_MG("Cobre, Cu mg", "mg"),
    /** Manganese in milligrams. */
    MANGANESO_MG("Manganeso, Mn mg", "mg"),
    /** Selenium in micrograms. */
    SELENIO_UG("Selenio, Se µg", "µg"),
    /** Chloride in milligrams. */
    CLORURO_MG("Cloruro, Cl mg", "mg"),
    /** Iodine in micrograms. */
    YODO_UG("Yodo, I µg", "µg"),
    /** Ca:P ratio. */
    CA_P_RATIO("Ca:P", "ratio"),
    /** Zn:Cu ratio. */
    ZN_CU_RATIO("Zn:Cu", "ratio"),

    // --- Vitamins ---
    /** Vitamin C in milligrams. */
    VITAMINA_C_MG("Vitamina C mg", "mg"),
    /** Thiamine (B1) in milligrams. */
    TIAMINA_MG("Tiamina mg", "mg"),
    /** Riboflavin (B2) in milligrams. */
    RIBOFLAVINA_MG("Riboflavina mg", "mg"),
    /** Niacin (B3) in milligrams. */
    NIACINA_MG("Niacina mg", "mg"),
    /** Pantothenic acid (B5) in milligrams. */
    ACIDO_PANTOTENICO_MG("Ácido pantoténico mg", "mg"),
    /** Vitamin B6 in milligrams. */
    VITAMINA_B6_MG("Vitamina B-6 mg", "mg"),
    /** Total folate in micrograms. */
    FOLATO_UG("Folato, total µg", "µg"),
    /** Total choline in milligrams. */
    COLINA_MG("Colina, total mg", "mg"),
    /** Betaine in milligrams. */
    BETAINA_MG("Betaina mg", "mg"),
    /** Vitamin B12 in micrograms. */
    VITAMINA_B12_UG("Vitamina B-12 µg", "µg"),
    /** Retinol (Vitamin A) in micrograms. */
    RETINOL_UG("Retinol µg", "µg"),
    /** Vitamin E in milligrams. */
    VITAMINA_E_MG("Vitamina E mg", "mg"),
    /** Vitamin D3 in micrograms. */
    VITAMINA_D3_UG("Vitamina D3 µg", "µg"),
    /** Vitamin K (phylloquinone) in micrograms. */
    VITAMINA_K_UG("Vitamina K (filoquinona) µg", "µg");

    private final String displayName;
    private final String unit;

    Nutrient(String displayName, String unit) {
        this.displayName = displayName;
        this.unit = unit;
    }

    /** Display name in Spanish. */
    public String getDisplayName() { return displayName; }

    /** Measurement unit. */
    public String getUnit() { return unit; }
}
