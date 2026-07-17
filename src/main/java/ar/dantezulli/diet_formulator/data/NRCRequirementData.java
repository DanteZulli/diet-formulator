package ar.dantezulli.diet_formulator.data;

import java.util.List;

import ar.dantezulli.diet_formulator.model.NutrientRequirement;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.Species;

import static ar.dantezulli.diet_formulator.model.enums.Nutrient.*;

/**
 * NRC (2006) Nutrient Requirements of Dogs and Cats.
 *
 * All values are Recommended Allowance (RA) per kg BW^exp.
 * Dogs: exponent = 0.75. Cats: exponent = 0.67.
 *
 * Units match Nutrient enum: g for amino acids/macros, mg for minerals/water-soluble vitamins,
 * µg for selenium/iodine/fat-soluble vitamins/B12/folate.
 *
 * Vitamin K: NRC reports in mg (menadione). Stored here in µg (×1000) to match enum.
 *
 * Reference: National Research Council (2006). Nutrient Requirements of Dogs and Cats.
 * National Academies Press. Tables 15-3, 15-5, 15-8, 15-10, 15-12, 15-14.
 */
public final class NRCRequirementData {

    private NRCRequirementData() {}

    public static List<NutrientRequirement> getRequirements(Species species, LifeStage lifeStage) {
        return switch (species) {
            case DOG -> switch (lifeStage) {
                case PUPPY -> DOG_PUPPY_14W;
                case ADULT -> DOG_ADULT;
                case PREGNANT, LACTATING -> DOG_GESTATION_LACTATION;
            };
            case CAT -> switch (lifeStage) {
                case PUPPY -> CAT_KITTEN;
                case ADULT -> CAT_ADULT;
                case PREGNANT -> CAT_GESTATION;
                case LACTATING -> CAT_LACTATION;
            };
        };
    }

    // =========================================================================
    // Table 15-3: Growing Puppies 14 Weeks and Older (protein/amino acids)
    //           + After Weaning (fats/minerals/vitamins)
    // =========================================================================
    private static final List<NutrientRequirement> DOG_PUPPY_14W = List.of(
        // Protein & Amino Acids — Table 15-3, 14+ weeks
        req(PROTEIN_G,               12.2,  null),
        req(ARGININE_G,               0.46, null),
        req(HISTIDINE_G,              0.17, null),
        req(ISOLEUCINE_G,             0.35, null),
        req(METHIONINE_G,             0.18, null),
        req(METHIONINE_CYSTINE_G,     0.37, null),
        req(LEUCINE_G,                0.57, null),
        req(LYSINE_G,                 0.49, 1.39),
        req(PHENYLALANINE_G,          0.35, null),
        req(PHENYLALANINE_TYROSINE_G, 0.70, null),
        req(THREONINE_G,              0.44, null),
        req(TRYPTOPHAN_G,             0.13, null),
        req(VALINE_G,                 0.39, null),

        // Fats & Fatty Acids — Table 15-3, After Weaning
        req(TOTAL_LIPIDS_G,           5.9,  23.0),
        req(LINOLEIC_ACID_G,          0.8,  4.5),
        req(ALPHA_LINOLENIC_ACID_G,   0.05, null),
        req(ARACHIDONIC_ACID_G,       0.022, null),
        req(EPA_DHA_TOTAL_G,          0.036, 0.77),

        // Minerals — Table 15-3, After Weaning
        req(CALCIUM_MG,               680.0, 1250.0),
        req(PHOSPHORUS_MG,            680.0, null),
        req(MAGNESIUM_MG,             27.4,  null),
        req(SODIUM_MG,                100.0, null),
        req(POTASSIUM_MG,             300.0, null),
        req(CHLORIDE_MG,              200.0, null),
        req(IRON_MG,                  6.1,   null),
        req(COPPER_MG,                0.76,  null),
        req(ZINC_MG,                  6.84,  null),
        req(MANGANESE_MG,             0.38,  null),
        req(SELENIUM_UG,              25.1,  null),
        req(IODINE_UG,                61.0,  null),

        // Vitamins — Table 15-3, After Weaning
        req(RETINOL_UG,               105.0, 1044.0),
        req(VITAMIN_D3_UG,            0.96,  5.6),
        req(VITAMIN_E_MG,             2.1,   null),
        req(VITAMIN_K_UG,             110.0, null),
        req(THIAMINE_MG,              0.096, null),
        req(RIBOFLAVIN_MG,            0.37,  null),
        req(VITAMIN_B6_MG,            0.10,  null),
        req(NIACIN_MG,                1.18,  null),
        req(PANTOTHENIC_ACID_MG,      1.04,  null),
        req(VITAMIN_B12_UG,           2.4,   null),
        req(FOLATE_UG,                18.8,  null),
        req(CHOLINE_MG,               118.0, null)
    );

    // =========================================================================
    // Table 15-5: Nutrient Requirements of Adult Dogs for Maintenance
    // =========================================================================
    private static final List<NutrientRequirement> DOG_ADULT = List.of(
        // Protein & Amino Acids
        req(PROTEIN_G,                3.28,  null),
        req(ARGININE_G,               0.11,  null),
        req(HISTIDINE_G,              0.062, null),
        req(ISOLEUCINE_G,             0.12,  null),
        req(METHIONINE_G,             0.11,  null),
        req(METHIONINE_CYSTINE_G,     0.21,  null),
        req(LEUCINE_G,                0.22,  null),
        req(LYSINE_G,                 0.11,  null),
        req(PHENYLALANINE_G,          0.15,  null),
        req(PHENYLALANINE_TYROSINE_G, 0.24,  null),
        req(THREONINE_G,              0.14,  null),
        req(TRYPTOPHAN_G,             0.046, null),
        req(VALINE_G,                 0.16,  null),

        // Fats & Fatty Acids
        req(TOTAL_LIPIDS_G,           1.8,   10.8),
        req(LINOLEIC_ACID_G,          0.36,  2.1),
        req(ALPHA_LINOLENIC_ACID_G,   0.014, null),
        req(EPA_DHA_TOTAL_G,          0.03,  0.37),

        // Minerals
        req(CALCIUM_MG,               130.0, null),
        req(PHOSPHORUS_MG,            100.0, null),
        req(MAGNESIUM_MG,             19.7,  null),
        req(SODIUM_MG,                26.2,  null),
        req(POTASSIUM_MG,             140.0, null),
        req(CHLORIDE_MG,              40.0,  null),
        req(IRON_MG,                  1.0,   null),
        req(COPPER_MG,                0.2,   null),
        req(ZINC_MG,                  2.0,   null),
        req(MANGANESE_MG,             0.16,  null),
        req(SELENIUM_UG,              11.8,  null),
        req(IODINE_UG,                29.6,  null),

        // Vitamins
        req(RETINOL_UG,               50.0,  2099.0),
        req(VITAMIN_D3_UG,            0.45,  2.6),
        req(VITAMIN_E_MG,             1.0,   null),
        req(VITAMIN_K_UG,             54.0,  null),
        req(THIAMINE_MG,              0.074, null),
        req(RIBOFLAVIN_MG,            0.171, null),
        req(VITAMIN_B6_MG,            0.049, null),
        req(NIACIN_MG,                0.57,  null),
        req(PANTOTHENIC_ACID_MG,      0.49,  null),
        req(VITAMIN_B12_UG,           1.15,  null),
        req(FOLATE_UG,                8.9,   null),
        req(CHOLINE_MG,               56.0,  null)
    );

    // =========================================================================
    // Table 15-8: Bitches in Late Gestation and Peak Lactation
    // =========================================================================
    private static final List<NutrientRequirement> DOG_GESTATION_LACTATION = List.of(
        // Protein & Amino Acids
        req(PROTEIN_G,                24.6,  null),
        req(ARGININE_G,               1.23,  null),
        req(HISTIDINE_G,              0.54,  null),
        req(ISOLEUCINE_G,             0.87,  null),
        req(METHIONINE_G,             0.38,  null),
        req(METHIONINE_CYSTINE_G,     0.76,  null),
        req(LEUCINE_G,                2.46,  null),
        req(LYSINE_G,                 1.11,  null),
        req(PHENYLALANINE_G,          1.02,  null),
        req(PHENYLALANINE_TYROSINE_G, 1.51,  null),
        req(THREONINE_G,              1.28,  null),
        req(TRYPTOPHAN_G,             0.15,  null),
        req(VALINE_G,                 1.60,  null),

        // Fats & Fatty Acids
        req(TOTAL_LIPIDS_G,           10.5,  40.6),
        req(LINOLEIC_ACID_G,          1.6,   8.0),
        req(ALPHA_LINOLENIC_ACID_G,   0.10,  null),
        req(EPA_DHA_TOTAL_G,          0.06,  1.4),

        // Minerals
        req(CALCIUM_MG,               820.0, null),
        req(PHOSPHORUS_MG,            580.0, null),
        req(MAGNESIUM_MG,             69.0,  null),
        req(SODIUM_MG,                238.0, null),
        req(POTASSIUM_MG,             430.0, null),
        req(CHLORIDE_MG,              358.0, null),
        req(IRON_MG,                  8.67,  null),
        req(COPPER_MG,                1.52,  null),
        req(ZINC_MG,                  11.7,  null),
        req(MANGANESE_MG,             0.87,  null),
        req(SELENIUM_UG,              43.0,  null),
        req(IODINE_UG,                108.0, null),

        // Vitamins
        req(RETINOL_UG,               186.0, 1846.0),
        req(VITAMIN_D3_UG,            1.70,  9.8),
        req(VITAMIN_E_MG,             3.7,   null),
        req(VITAMIN_K_UG,             200.0, null),
        req(THIAMINE_MG,              0.28,  null),
        req(RIBOFLAVIN_MG,            0.64,  null),
        req(VITAMIN_B6_MG,            0.185, null),
        req(NIACIN_MG,                2.09,  null),
        req(PANTOTHENIC_ACID_MG,      1.84,  null),
        req(VITAMIN_B12_UG,           4.3,   null),
        req(FOLATE_UG,                33.2,  null),
        req(CHOLINE_MG,               209.0, null)
    );

    // =========================================================================
    // Table 15-10: Nutrient Requirements for Growth of Kittens
    // =========================================================================
    private static final List<NutrientRequirement> CAT_KITTEN = List.of(
        // Protein & Amino Acids
        req(PROTEIN_G,                11.8,  null),
        req(ARGININE_G,               0.50,  1.83),
        req(HISTIDINE_G,              0.17,  1.15),
        req(ISOLEUCINE_G,             0.29,  4.54),
        req(METHIONINE_G,             0.23,  0.68),
        req(METHIONINE_CYSTINE_G,     0.46,  null),
        req(LEUCINE_G,                0.67,  4.54),
        req(LYSINE_G,                 0.44,  3.03),
        req(PHENYLALANINE_G,          0.27,  1.51),
        req(PHENYLALANINE_TYROSINE_G, 1.00,  3.55),
        req(THREONINE_G,              0.33,  2.66),
        req(TRYPTOPHAN_G,             0.084, 0.89),
        req(VALINE_G,                 0.33,  4.54),
        req(TAURINE_MG,               21.0,  460.0),

        // Fats & Fatty Acids
        req(TOTAL_LIPIDS_G,           4.7,   17.2),
        req(LINOLEIC_ACID_G,          0.29,  2.9),
        req(ALPHA_LINOLENIC_ACID_G,   0.010, null),
        req(ARACHIDONIC_ACID_G,       0.001, null),
        req(EPA_DHA_TOTAL_G,          0.005, null),

        // Minerals
        req(CALCIUM_MG,               410.0, null),
        req(PHOSPHORUS_MG,            372.0, null),
        req(MAGNESIUM_MG,             20.0,  null),
        req(SODIUM_MG,                74.0,  null),
        req(POTASSIUM_MG,             209.0, null),
        req(CHLORIDE_MG,              46.5,  null),
        req(IRON_MG,                  4.2,   null),
        req(COPPER_MG,                0.44,  null),
        req(ZINC_MG,                  3.9,   null),
        req(MANGANESE_MG,             0.25,  null),
        req(SELENIUM_UG,              15.8,  null),
        req(IODINE_UG,                93.0,  null),

        // Vitamins
        req(RETINOL_UG,               52.0,  4180.0),
        req(VITAMIN_D3_UG,            0.29,  39.0),
        req(VITAMIN_E_MG,             2.0,   null),
        req(VITAMIN_K_UG,             50.0,  null),
        req(THIAMINE_MG,              0.29,  null),
        req(RIBOFLAVIN_MG,            0.21,  null),
        req(VITAMIN_B6_MG,            0.13,  null),
        req(NIACIN_MG,                2.1,   null),
        req(PANTOTHENIC_ACID_MG,      0.30,  null),
        req(VITAMIN_B12_UG,           1.18,  null),
        req(FOLATE_UG,                39.0,  null),
        req(CHOLINE_MG,               133.0, null)
    );

    // =========================================================================
    // Table 15-12: Nutrient Requirements of Adult Cats for Maintenance
    // =========================================================================
    private static final List<NutrientRequirement> CAT_ADULT = List.of(
        // Protein & Amino Acids
        req(PROTEIN_G,                4.96,  null),
        req(ARGININE_G,               0.19,  null),
        req(HISTIDINE_G,              0.064, null),
        req(ISOLEUCINE_G,             0.11,  null),
        req(METHIONINE_G,             0.042, null),
        req(METHIONINE_CYSTINE_G,     0.084, null),
        req(LEUCINE_G,                0.25,  null),
        req(LYSINE_G,                 0.084, null),
        req(PHENYLALANINE_G,          0.099, null),
        req(PHENYLALANINE_TYROSINE_G, 0.38,  null),
        req(THREONINE_G,              0.13,  null),
        req(TRYPTOPHAN_G,             0.032, null),
        req(VALINE_G,                 0.13,  null),
        req(TAURINE_MG,               9.9,   null),

        // Fats & Fatty Acids
        req(TOTAL_LIPIDS_G,           2.2,   8.2),
        req(LINOLEIC_ACID_G,          0.14,  1.4),
        req(ARACHIDONIC_ACID_G,       0.0015, 0.049),
        req(EPA_DHA_TOTAL_G,          0.0025, null),

        // Minerals
        req(CALCIUM_MG,               71.0,  null),
        req(PHOSPHORUS_MG,            63.0,  null),
        req(MAGNESIUM_MG,             9.5,   null),
        req(SODIUM_MG,                16.7,  null),
        req(POTASSIUM_MG,             130.0, null),
        req(CHLORIDE_MG,              23.7,  null),
        req(IRON_MG,                  1.98,  null),
        req(COPPER_MG,                0.119, null),
        req(ZINC_MG,                  1.9,   600.0),
        req(MANGANESE_MG,             0.119, null),
        req(SELENIUM_UG,              6.95,  null),
        req(IODINE_UG,                35.0,  null),

        // Vitamins
        req(RETINOL_UG,               24.7,  2469.0),
        req(VITAMIN_D3_UG,            0.17,  19.0),
        req(VITAMIN_E_MG,             0.94,  null),
        req(VITAMIN_K_UG,             25.0,  null),
        req(THIAMINE_MG,              0.14,  null),
        req(RIBOFLAVIN_MG,            0.099, null),
        req(VITAMIN_B6_MG,            0.06,  null),
        req(NIACIN_MG,                0.99,  null),
        req(PANTOTHENIC_ACID_MG,      0.14,  null),
        req(VITAMIN_B12_UG,           0.56,  null),
        req(FOLATE_UG,                19.0,  null),
        req(CHOLINE_MG,               63.0,  null)
    );

    // =========================================================================
    // Table 15-14: Queens in Late Gestation — gestating values
    // =========================================================================
    private static final List<NutrientRequirement> CAT_GESTATION = List.of(
        // Protein & Amino Acids — gestating
        req(PROTEIN_G,                7.40,  null),
        req(ARGININE_G,               0.52,  null),
        req(HISTIDINE_G,              0.15,  null),
        req(ISOLEUCINE_G,             0.27,  null),
        req(METHIONINE_G,             0.170, null),
        req(METHIONINE_CYSTINE_G,     0.31,  null),
        req(LEUCINE_G,                0.63,  null),
        req(LYSINE_G,                 0.38,  null),
        req(PHENYLALANINE_TYROSINE_G, 0.66,  null),
        req(THREONINE_G,              0.31,  null),
        req(TRYPTOPHAN_G,             0.066, null),
        req(VALINE_G,                 0.35,  null),
        req(TAURINE_MG,               18.0,  null),

        // Fats & Fatty Acids — shared with lactating
        req(TOTAL_LIPIDS_G,           4.8,   17.6),
        req(LINOLEIC_ACID_G,          0.3,   2.93),
        req(ALPHA_LINOLENIC_ACID_G,   0.011, null),
        req(ARACHIDONIC_ACID_G,       0.011, null),
        req(EPA_DHA_TOTAL_G,          0.0044, null),

        // Minerals — shared with lactating
        req(CALCIUM_MG,               565.0, null),
        req(PHOSPHORUS_MG,            411.0, null),
        req(MAGNESIUM_MG,             32.0,  null),
        req(SODIUM_MG,                142.0, null),
        req(POTASSIUM_MG,             277.0, null),
        req(CHLORIDE_MG,              213.0, null),
        req(IRON_MG,                  4.3,   null),
        req(COPPER_MG,                0.47,  null),
        req(ZINC_MG,                  3.2,   null),
        req(MANGANESE_MG,             0.38,  null),
        req(SELENIUM_UG,              16.0,  null),
        req(IODINE_UG,                96.0,  null),

        // Vitamins — shared with lactating
        req(RETINOL_UG,               107.0, 5333.0),
        req(VITAMIN_D3_UG,            0.37,  40.0),
        req(VITAMIN_E_MG,             1.67,  null),
        req(VITAMIN_K_UG,             180.0, null),
        req(THIAMINE_MG,              0.32,  null),
        req(RIBOFLAVIN_MG,            0.21,  null),
        req(VITAMIN_B6_MG,            0.11,  null),
        req(NIACIN_MG,                2.10,  null),
        req(PANTOTHENIC_ACID_MG,      0.31,  null),
        req(VITAMIN_B12_UG,           1.0,   null),
        req(FOLATE_UG,                33.0,  null),
        req(CHOLINE_MG,               113.0, null)
    );

    // =========================================================================
    // Table 15-14: Queens in Peak Lactation — lactating values
    // =========================================================================
    private static final List<NutrientRequirement> CAT_LACTATION = List.of(
        // Protein & Amino Acids — lactating
        req(PROTEIN_G,                16.10, null),
        req(ARGININE_G,               0.81,  null),
        req(HISTIDINE_G,              0.39,  null),
        req(ISOLEUCINE_G,             0.64,  null),
        req(METHIONINE_G,             0.32,  null),
        req(METHIONINE_CYSTINE_G,     0.56,  null),
        req(LEUCINE_G,                1.07,  null),
        req(LYSINE_G,                 0.75,  null),
        req(PHENYLALANINE_TYROSINE_G, 1.03,  null),
        req(THREONINE_G,              0.58,  null),
        req(TRYPTOPHAN_G,             0.100, null),
        req(VALINE_G,                 0.64,  null),
        req(TAURINE_MG,               18.0,  null),

        // Fats & Fatty Acids — shared with gestating
        req(TOTAL_LIPIDS_G,           4.8,   17.6),
        req(LINOLEIC_ACID_G,          0.3,   2.93),
        req(ALPHA_LINOLENIC_ACID_G,   0.011, null),
        req(ARACHIDONIC_ACID_G,       0.011, null),
        req(EPA_DHA_TOTAL_G,          0.0044, null),

        // Minerals — shared with gestating
        req(CALCIUM_MG,               565.0, null),
        req(PHOSPHORUS_MG,            411.0, null),
        req(MAGNESIUM_MG,             32.0,  null),
        req(SODIUM_MG,                142.0, null),
        req(POTASSIUM_MG,             277.0, null),
        req(CHLORIDE_MG,              213.0, null),
        req(IRON_MG,                  4.3,   null),
        req(COPPER_MG,                0.47,  null),
        req(ZINC_MG,                  3.2,   null),
        req(MANGANESE_MG,             0.38,  null),
        req(SELENIUM_UG,              16.0,  null),
        req(IODINE_UG,                96.0,  null),

        // Vitamins — shared with gestating
        req(RETINOL_UG,               107.0, 5333.0),
        req(VITAMIN_D3_UG,            0.37,  40.0),
        req(VITAMIN_E_MG,             1.67,  null),
        req(VITAMIN_K_UG,             180.0, null),
        req(THIAMINE_MG,              0.32,  null),
        req(RIBOFLAVIN_MG,            0.21,  null),
        req(VITAMIN_B6_MG,            0.11,  null),
        req(NIACIN_MG,                2.10,  null),
        req(PANTOTHENIC_ACID_MG,      0.31,  null),
        req(VITAMIN_B12_UG,           1.0,   null),
        req(FOLATE_UG,                33.0,  null),
        req(CHOLINE_MG,               113.0, null)
    );

    private static NutrientRequirement req(Nutrient nutrient, double ra, Double sul) {
        return new NutrientRequirement(nutrient, ra, sul);
    }
}
