package ar.dantezulli.diet_formulator.model.enums;

/**
 * All nutrients tracked in the nutritional database.
 * Todos los nutrientes rastreados en la base de datos nutricional.
 *
 * Names match the Spanish labels from the original formulator.
 * Los nombres coinciden con las etiquetas en español del formulador original.
 */
public enum Nutrient {

    // --- Energy and general / Energía y general ---
    /** Energy in kcal. / Energía en kcal. */
    ENERGIA_KCAL("Energía kcal", "kcal"),
    /** Portion size in grams. / Tamaño de porción en gramos. */
    TAMANIO_PORCION_G("Tamaño porción g", "g"),
    /** Water in grams. / Agua en gramos. */
    AGUA_G("Agua g", "g"),
    /** Dry matter in grams. / Materia seca en gramos. */
    MATERIA_SECA_G("Materia seca g", "g"),
    /** Ash in grams. / Ceniza en gramos. */
    CENIZA_G("Ceniza g", "g"),

    // --- Protein / Proteína ---
    /** Total protein in grams. / Proteína total en gramos. */
    PROTEINA_G("Proteína g", "g"),
    /** Tryptophan in grams. / Triptófano en gramos. */
    TRIPTOFANO_G("Triptófano g", "g"),
    /** Threonine in grams. / Treonina en gramos. */
    TREONINA_G("Treonina g", "g"),
    /** Isoleucine in grams. / Isoleucina en gramos. */
    ISOLEUCINA_G("Isoleucina g", "g"),
    /** Leucine in grams. / Leucina en gramos. */
    LEUCINA_G("Leucina g", "g"),
    /** Lysine in grams. / Lisina en gramos. */
    LISINA_G("Lisina g", "g"),
    /** Methionine in grams. / Metionina en gramos. */
    METIONINA_G("Metionina g", "g"),
    /** Methionine + Cystine in grams. / Metionina y Cistina en gramos. */
    METIONINA_CISTINA_G("Metionina y Cistina g", "g"),
    /** Phenylalanine in grams. / Fenilalanina en gramos. */
    FENILALANINA_G("Fenilalanina g", "g"),
    /** Phenylalanine + Tyrosine in grams. / Fenilalanina y Tirosina en gramos. */
    FENILALANINA_TIROSINA_G("Fenilalanina y Tirosina g", "g"),
    /** Valine in grams. / Valina en gramos. */
    VALINA_G("Valina g", "g"),
    /** Arginine in grams. / Arginina en gramos. */
    ARGININA_G("Arginina g", "g"),
    /** Histidine in grams. / Histidina en gramos. */
    HISTIDINA_G("Histidina g", "g"),
    /** Taurine in milligrams. / Taurina en miligramos. */
    TAURINA_MG("Taurina mg", "mg"),

    // --- Lipids / Lípidos ---
    /** Total lipids in grams. / Lípidos totales en gramos. */
    LIPIDOS_TOTALES_G("Lípidos totales g", "g"),
    /** Linoleic acid in grams. / Ácido linoleico en gramos. */
    ACIDO_LINOLEICO_G("Ácido linoleico g", "g"),
    /** Alpha-linoleic acid in grams. / Ácido α-linoleico en gramos. */
    ACIDO_ALINOLEICO_G("Ácido α-linoleico g", "g"),
    /** 20:2 n-6 c,c in grams. / 20:2 n-6 c,c en gramos. */
    ACIDO_20_2_N6_G("20:2 n-6 c,c g", "g"),
    /** 20:3 n-6 in grams. / 20:3 n-6 en gramos. */
    ACIDO_20_3_N6_G("20:3 n-6 g", "g"),
    /** Arachidonic acid in grams. / Ácido araquidónico en gramos. */
    ACIDOARAQUIDONICO_G("Ácido araquidónico g", "g"),
    /** EPA (20:5 n-3) in grams. / EPA (20:5 n-3) en gramos. */
    EPA_G("20:5 n-3 (EPA) g", "g"),
    /** DHA (22:6 n-3) in grams. / DHA (22:6 n-3) en gramos. */
    DHA_G("22:6 n-3 (DHA) g", "g"),
    /** EPA + DHA undifferentiated in grams. / EPA + DHA sin diferenciar en gramos. */
    EPA_DHA_SIN_DIFERENCIAR_G("EPA + DHA sin diferenciar g", "g"),
    /** EPA + DHA total in grams. / EPA + DHA total en gramos. */
    EPA_DHA_TOTAL_G("EPA + DHA Total g", "g"),
    /** Omega 6 unspecified in grams. / Omega 6 sin especificar en gramos. */
    OMEGA_6_G("Omega 6 sin especificar g", "g"),
    /** Omega 3 unspecified in grams. / Omega 3 sin especificar en gramos. */
    OMEGA_3_G("Omega 3 sin especificar g", "g"),
    /** Omega 6 : Omega 3 ratio. / Relación Omega 6 : Omega 3. */
    OMEGA_6_3_RATIO("Omega 6 : Omega 3", "ratio"),

    // --- Carbohydrates / Carbohidratos ---
    /** Total carbohydrates in grams. / Carbohidratos totales en gramos. */
    CARBOHIDRATOS_G("Carbohidratos g", "g"),
    /** Total fiber in grams. / Fibra total en gramos. */
    FIBRA_TOTAL_G("Fibra, total g", "g"),

    // --- Minerals / Minerales ---
    /** Calcium in milligrams. / Calcio en miligramos. */
    CALCIO_MG("Calcio, Ca mg", "mg"),
    /** Iron in milligrams. / Hierro en miligramos. */
    HIERRO_MG("Hierro, Fe mg", "mg"),
    /** Magnesium in milligrams. / Magnesio en miligramos. */
    MAGNESIO_MG("Magnesio, Mg mg", "mg"),
    /** Phosphorus in milligrams. / Fósforo en miligramos. */
    FOSFORO_MG("Fósforo, P mg", "mg"),
    /** Potassium in milligrams. / Potasio en miligramos. */
    POTASIO_MG("Potasio, K mg", "mg"),
    /** Sodium in milligrams. / Sodio en miligramos. */
    SODIO_MG("Sodio, Na mg", "mg"),
    /** Zinc in milligrams. / Zinc en miligramos. */
    ZINC_MG("Zinc, Zn mg", "mg"),
    /** Copper in milligrams. / Cobre en miligramos. */
    COBRE_MG("Cobre, Cu mg", "mg"),
    /** Manganese in milligrams. / Manganeso en miligramos. */
    MANGANESO_MG("Manganeso, Mn mg", "mg"),
    /** Selenium in micrograms. / Selenio en microgramos. */
    SELENIO_UG("Selenio, Se µg", "µg"),
    /** Chloride in milligrams. / Cloruro en miligramos. */
    CLORURO_MG("Cloruro, Cl mg", "mg"),
    /** Iodine in micrograms. / Yodo en microgramos. */
    YODO_UG("Yodo, I µg", "µg"),
    /** Ca:P ratio. / Relación Ca:P. */
    CA_P_RATIO("Ca:P", "ratio"),
    /** Zn:Cu ratio. / Relación Zn:Cu. */
    ZN_CU_RATIO("Zn:Cu", "ratio"),

    // --- Vitaminas ---
    /** Vitamin C in milligrams. / Vitamina C en miligramos. */
    VITAMINA_C_MG("Vitamina C mg", "mg"),
    /** Thiamine (B1) in milligrams. / Tiamina en miligramos. */
    TIAMINA_MG("Tiamina mg", "mg"),
    /** Riboflavin (B2) in milligrams. / Riboflavina en miligramos. */
    RIBOFLAVINA_MG("Riboflavina mg", "mg"),
    /** Niacin (B3) in milligrams. / Niacina en miligramos. */
    NIACINA_MG("Niacina mg", "mg"),
    /** Pantothenic acid (B5) in milligrams. / Ácido pantoténico en miligramos. */
    ACIDO_PANTOTENICO_MG("Ácido pantoténico mg", "mg"),
    /** Vitamin B6 in milligrams. / Vitamina B-6 en miligramos. */
    VITAMINA_B6_MG("Vitamina B-6 mg", "mg"),
    /** Total folate in micrograms. / Folato total en microgramos. */
    FOLATO_UG("Folato, total µg", "µg"),
    /** Total choline in milligrams. / Colina total en miligramos. */
    COLINA_MG("Colina, total mg", "mg"),
    /** Betaine in milligrams. / Betaina en miligramos. */
    BETAINA_MG("Betaina mg", "mg"),
    /** Vitamin B12 in micrograms. / Vitamina B-12 en microgramos. */
    VITAMINA_B12_UG("Vitamina B-12 µg", "µg"),
    /** Retinol (Vitamin A) in micrograms. / Retinol (Vitamina A) en microgramos. */
    RETINOL_UG("Retinol µg", "µg"),
    /** Vitamin E in milligrams. / Vitamina E en miligramos. */
    VITAMINA_E_MG("Vitamina E mg", "mg"),
    /** Vitamin D3 in micrograms. / Vitamina D3 en microgramos. */
    VITAMINA_D3_UG("Vitamina D3 µg", "µg"),
    /** Vitamin K (phylloquinone) in micrograms. / Vitamina K (filoquinona) en microgramos. */
    VITAMINA_K_UG("Vitamina K (filoquinona) µg", "µg");

    private final String displayName;
    private final String unit;

    Nutrient(String displayName, String unit) {
        this.displayName = displayName;
        this.unit = unit;
    }

    /** Display name in Spanish. / Nombre para mostrar en español. */
    public String getDisplayName() { return displayName; }

    /** Measurement unit. / Unidad de medida. */
    public String getUnit() { return unit; }
}
