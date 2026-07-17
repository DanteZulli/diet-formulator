package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nutrient {

    // --- Energy and general ---
    ENERGY_KCAL("Energía kcal", "kcal", NutrientCategory.ENERGY),
    PORTION_SIZE_G("Tamaño porción g", "g", null),
    WATER_G("Agua g", "g", null),
    DRY_MATTER_G("Materia seca g", "g", null),
    ASH_G("Ceniza g", "g", null),

    // --- Protein / Amino Acids ---
    PROTEIN_G("Proteína g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    TRYPTOPHAN_G("Triptófano g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    THREONINE_G("Treonina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    ISOLEUCINE_G("Isoleucina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    LEUCINE_G("Leucina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    LYSINE_G("Lisina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    METHIONINE_G("Metionina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    METHIONINE_CYSTINE_G("Metionina y Cistina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    PHENYLALANINE_G("Fenilalanina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    PHENYLALANINE_TYROSINE_G("Fenilalanina y Tirosina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    VALINE_G("Valina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    ARGININE_G("Arginina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    HISTIDINE_G("Histidina g", "g", NutrientCategory.PROTEIN_AMINO_ACIDS),
    TAURINE_MG("Taurina mg", "mg", NutrientCategory.PROTEIN_AMINO_ACIDS),

    // --- Lipids / Fatty Acids ---
    TOTAL_LIPIDS_G("Lípidos totales g", "g", NutrientCategory.LIPIDS_FATTY_ACIDS),
    LINOLEIC_ACID_G("Ácido linoleico g", "g", NutrientCategory.LIPIDS_FATTY_ACIDS),
    ALPHA_LINOLENIC_ACID_G("Ácido α-linoleico g", "g", NutrientCategory.LIPIDS_FATTY_ACIDS),
    FATTY_ACID_20_2_N6_G("20:2 n-6 c,c g", "g", null),
    FATTY_ACID_20_3_N6_G("20:3 n-6 g", "g", null),
    ARACHIDONIC_ACID_G("Ácido araquidónico g", "g", NutrientCategory.LIPIDS_FATTY_ACIDS),
    EPA_G("20:5 n-3 (EPA) g", "g", NutrientCategory.LIPIDS_FATTY_ACIDS),
    DHA_G("22:6 n-3 (DHA) g", "g", NutrientCategory.LIPIDS_FATTY_ACIDS),
    EPA_DHA_UNDIFFERENTIATED_G("EPA + DHA sin diferenciar g", "g", null),
    EPA_DHA_TOTAL_G("EPA + DHA Total g", "g", NutrientCategory.LIPIDS_FATTY_ACIDS),
    OMEGA_6_G("Omega 6 sin especificar g", "g", null),
    OMEGA_3_G("Omega 3 sin especificar g", "g", null),
    OMEGA_6_3_RATIO("Omega 6 : Omega 3", "ratio", null),

    // --- Carbohydrates ---
    CARBOHYDRATES_G("Carbohidratos g", "g", NutrientCategory.CARBOHYDRATES),
    TOTAL_FIBER_G("Fibra, total g", "g", NutrientCategory.CARBOHYDRATES),

    // --- Minerals ---
    CALCIUM_MG("Calcio, Ca mg", "mg", NutrientCategory.MINERALS),
    IRON_MG("Hierro, Fe mg", "mg", NutrientCategory.MINERALS),
    MAGNESIUM_MG("Magnesio, Mg mg", "mg", NutrientCategory.MINERALS),
    PHOSPHORUS_MG("Fósforo, P mg", "mg", NutrientCategory.MINERALS),
    POTASSIUM_MG("Potasio, K mg", "mg", NutrientCategory.MINERALS),
    SODIUM_MG("Sodio, Na mg", "mg", NutrientCategory.MINERALS),
    ZINC_MG("Zinc, Zn mg", "mg", NutrientCategory.MINERALS),
    COPPER_MG("Cobre, Cu mg", "mg", NutrientCategory.MINERALS),
    MANGANESE_MG("Manganeso, Mn mg", "mg", NutrientCategory.MINERALS),
    SELENIUM_UG("Selenio, Se µg", "µg", NutrientCategory.MINERALS),
    CHLORIDE_MG("Cloruro, Cl mg", "mg", NutrientCategory.MINERALS),
    IODINE_UG("Yodo, I µg", "µg", NutrientCategory.MINERALS),
    CA_P_RATIO("Ca:P", "ratio", null),
    ZN_CU_RATIO("Zn:Cu", "ratio", null),

    // --- Vitamins ---
    VITAMIN_C_MG("Vitamina C mg", "mg", NutrientCategory.VITAMINS),
    THIAMINE_MG("Tiamina mg", "mg", NutrientCategory.VITAMINS),
    RIBOFLAVIN_MG("Riboflavina mg", "mg", NutrientCategory.VITAMINS),
    NIACIN_MG("Niacina mg", "mg", NutrientCategory.VITAMINS),
    PANTOTHENIC_ACID_MG("Ácido pantoténico mg", "mg", NutrientCategory.VITAMINS),
    VITAMIN_B6_MG("Vitamina B-6 mg", "mg", NutrientCategory.VITAMINS),
    FOLATE_UG("Folato, total µg", "µg", NutrientCategory.VITAMINS),
    CHOLINE_MG("Colina, total mg", "mg", NutrientCategory.VITAMINS),
    BETAINE_MG("Betaina mg", "mg", null),
    VITAMIN_B12_UG("Vitamina B-12 µg", "µg", NutrientCategory.VITAMINS),
    RETINOL_UG("Retinol µg", "µg", NutrientCategory.VITAMINS),
    VITAMIN_E_MG("Vitamina E mg", "mg", NutrientCategory.VITAMINS),
    VITAMIN_D3_UG("Vitamina D3 µg", "µg", NutrientCategory.VITAMINS),
    VITAMIN_K_UG("Vitamina K (filoquinona) µg", "µg", NutrientCategory.VITAMINS);

    private final String displayName;
    private final String unit;
    private final NutrientCategory category;
}
