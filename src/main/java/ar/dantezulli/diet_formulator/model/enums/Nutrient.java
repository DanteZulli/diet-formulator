package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nutrient {

    // --- Energy and general ---
    ENERGY_KCAL("Energía kcal", "kcal"),
    PORTION_SIZE_G("Tamaño porción g", "g"),
    WATER_G("Agua g", "g"),
    DRY_MATTER_G("Materia seca g", "g"),
    ASH_G("Ceniza g", "g"),

    // --- Protein ---
    PROTEIN_G("Proteína g", "g"),
    TRYPTOPHAN_G("Triptófano g", "g"),
    THREONINE_G("Treonina g", "g"),
    ISOLEUCINE_G("Isoleucina g", "g"),
    LEUCINE_G("Leucina g", "g"),
    LYSINE_G("Lisina g", "g"),
    METHIONINE_G("Metionina g", "g"),
    METHIONINE_CYSTINE_G("Metionina y Cistina g", "g"),
    PHENYLALANINE_G("Fenilalanina g", "g"),
    PHENYLALANINE_TYROSINE_G("Fenilalanina y Tirosina g", "g"),
    VALINE_G("Valina g", "g"),
    ARGININE_G("Arginina g", "g"),
    HISTIDINE_G("Histidina g", "g"),
    TAURINE_MG("Taurina mg", "mg"),

    // --- Lipids ---
    TOTAL_LIPIDS_G("Lípidos totales g", "g"),
    LINOLEIC_ACID_G("Ácido linoleico g", "g"),
    ALPHA_LINOLENIC_ACID_G("Ácido α-linoleico g", "g"),
    FATTY_ACID_20_2_N6_G("20:2 n-6 c,c g", "g"),
    FATTY_ACID_20_3_N6_G("20:3 n-6 g", "g"),
    ARACHIDONIC_ACID_G("Ácido araquidónico g", "g"),
    EPA_G("20:5 n-3 (EPA) g", "g"),
    DHA_G("22:6 n-3 (DHA) g", "g"),
    EPA_DHA_UNDIFFERENTIATED_G("EPA + DHA sin diferenciar g", "g"),
    EPA_DHA_TOTAL_G("EPA + DHA Total g", "g"),
    OMEGA_6_G("Omega 6 sin especificar g", "g"),
    OMEGA_3_G("Omega 3 sin especificar g", "g"),
    OMEGA_6_3_RATIO("Omega 6 : Omega 3", "ratio"),

    // --- Carbohydrates ---
    CARBOHYDRATES_G("Carbohidratos g", "g"),
    TOTAL_FIBER_G("Fibra, total g", "g"),

    // --- Minerals ---
    CALCIUM_MG("Calcio, Ca mg", "mg"),
    IRON_MG("Hierro, Fe mg", "mg"),
    MAGNESIUM_MG("Magnesio, Mg mg", "mg"),
    PHOSPHORUS_MG("Fósforo, P mg", "mg"),
    POTASSIUM_MG("Potasio, K mg", "mg"),
    SODIUM_MG("Sodio, Na mg", "mg"),
    ZINC_MG("Zinc, Zn mg", "mg"),
    COPPER_MG("Cobre, Cu mg", "mg"),
    MANGANESE_MG("Manganeso, Mn mg", "mg"),
    SELENIUM_UG("Selenio, Se µg", "µg"),
    CHLORIDE_MG("Cloruro, Cl mg", "mg"),
    IODINE_UG("Yodo, I µg", "µg"),
    CA_P_RATIO("Ca:P", "ratio"),
    ZN_CU_RATIO("Zn:Cu", "ratio"),

    // --- Vitamins ---
    VITAMIN_C_MG("Vitamina C mg", "mg"),
    THIAMINE_MG("Tiamina mg", "mg"),
    RIBOFLAVIN_MG("Riboflavina mg", "mg"),
    NIACIN_MG("Niacina mg", "mg"),
    PANTOTHENIC_ACID_MG("Ácido pantoténico mg", "mg"),
    VITAMIN_B6_MG("Vitamina B-6 mg", "mg"),
    FOLATE_UG("Folato, total µg", "µg"),
    CHOLINE_MG("Colina, total mg", "mg"),
    BETAINE_MG("Betaina mg", "mg"),
    VITAMIN_B12_UG("Vitamina B-12 µg", "µg"),
    RETINOL_UG("Retinol µg", "µg"),
    VITAMIN_E_MG("Vitamina E mg", "mg"),
    VITAMIN_D3_UG("Vitamina D3 µg", "µg"),
    VITAMIN_K_UG("Vitamina K (filoquinona) µg", "µg");

    private final String displayName;
    private final String unit;
}
