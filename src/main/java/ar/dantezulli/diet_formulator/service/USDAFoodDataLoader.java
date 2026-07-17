package ar.dantezulli.diet_formulator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.PortionUnit;
import ar.dantezulli.diet_formulator.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class USDAFoodDataLoader implements CommandLineRunner {

    private final FoodRepository foodRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Map<Integer, Nutrient> NUTRIENT_MAP = new HashMap<>();

    static {
        NUTRIENT_MAP.put(1008, Nutrient.ENERGY_KCAL);
        NUTRIENT_MAP.put(1051, Nutrient.WATER_G);
        NUTRIENT_MAP.put(1007, Nutrient.ASH_G);
        NUTRIENT_MAP.put(1003, Nutrient.PROTEIN_G);
        NUTRIENT_MAP.put(1004, Nutrient.TOTAL_LIPIDS_G);
        NUTRIENT_MAP.put(1005, Nutrient.CARBOHYDRATES_G);
        NUTRIENT_MAP.put(1079, Nutrient.TOTAL_FIBER_G);

        NUTRIENT_MAP.put(1087, Nutrient.CALCIUM_MG);
        NUTRIENT_MAP.put(1089, Nutrient.IRON_MG);
        NUTRIENT_MAP.put(1090, Nutrient.MAGNESIUM_MG);
        NUTRIENT_MAP.put(1091, Nutrient.PHOSPHORUS_MG);
        NUTRIENT_MAP.put(1092, Nutrient.POTASSIUM_MG);
        NUTRIENT_MAP.put(1093, Nutrient.SODIUM_MG);
        NUTRIENT_MAP.put(1095, Nutrient.ZINC_MG);
        NUTRIENT_MAP.put(1098, Nutrient.COPPER_MG);
        NUTRIENT_MAP.put(1101, Nutrient.MANGANESE_MG);
        NUTRIENT_MAP.put(1103, Nutrient.SELENIUM_UG);
        NUTRIENT_MAP.put(1102, Nutrient.IODINE_UG);
        NUTRIENT_MAP.put(1097, Nutrient.CHLORIDE_MG);

        NUTRIENT_MAP.put(1162, Nutrient.VITAMIN_C_MG);
        NUTRIENT_MAP.put(1165, Nutrient.THIAMINE_MG);
        NUTRIENT_MAP.put(1166, Nutrient.RIBOFLAVIN_MG);
        NUTRIENT_MAP.put(1167, Nutrient.NIACIN_MG);
        NUTRIENT_MAP.put(1170, Nutrient.PANTOTHENIC_ACID_MG);
        NUTRIENT_MAP.put(1175, Nutrient.VITAMIN_B6_MG);
        NUTRIENT_MAP.put(1177, Nutrient.FOLATE_UG);
        NUTRIENT_MAP.put(1180, Nutrient.CHOLINE_MG);
        NUTRIENT_MAP.put(1198, Nutrient.BETAINE_MG);
        NUTRIENT_MAP.put(1178, Nutrient.VITAMIN_B12_UG);
        NUTRIENT_MAP.put(1106, Nutrient.RETINOL_UG);
        NUTRIENT_MAP.put(1109, Nutrient.VITAMIN_E_MG);
        NUTRIENT_MAP.put(1114, Nutrient.VITAMIN_D3_UG);
        NUTRIENT_MAP.put(1185, Nutrient.VITAMIN_K_UG);

        NUTRIENT_MAP.put(1210, Nutrient.TRYPTOPHAN_G);
        NUTRIENT_MAP.put(1212, Nutrient.THREONINE_G);
        NUTRIENT_MAP.put(1213, Nutrient.ISOLEUCINE_G);
        NUTRIENT_MAP.put(1214, Nutrient.LEUCINE_G);
        NUTRIENT_MAP.put(1215, Nutrient.LYSINE_G);
        NUTRIENT_MAP.put(1216, Nutrient.METHIONINE_G);
        NUTRIENT_MAP.put(1217, Nutrient.METHIONINE_CYSTINE_G);
        NUTRIENT_MAP.put(1218, Nutrient.PHENYLALANINE_G);
        NUTRIENT_MAP.put(1219, Nutrient.PHENYLALANINE_TYROSINE_G);
        NUTRIENT_MAP.put(1220, Nutrient.VALINE_G);
        NUTRIENT_MAP.put(1211, Nutrient.ARGININE_G);
        NUTRIENT_MAP.put(1221, Nutrient.HISTIDINE_G);
        NUTRIENT_MAP.put(1235, Nutrient.TAURINE_MG);

        NUTRIENT_MAP.put(1258, Nutrient.LINOLEIC_ACID_G);
        NUTRIENT_MAP.put(1280, Nutrient.ALPHA_LINOLENIC_ACID_G);
        NUTRIENT_MAP.put(1313, Nutrient.FATTY_ACID_20_2_N6_G);
        NUTRIENT_MAP.put(1406, Nutrient.FATTY_ACID_20_3_N6_G);
        NUTRIENT_MAP.put(1271, Nutrient.ARACHIDONIC_ACID_G);
        NUTRIENT_MAP.put(1278, Nutrient.EPA_G);
        NUTRIENT_MAP.put(1272, Nutrient.DHA_G);
        NUTRIENT_MAP.put(1257, Nutrient.OMEGA_6_G);
        NUTRIENT_MAP.put(1259, Nutrient.OMEGA_3_G);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        long count = foodRepository.count();
        if (count > 0) {
            log.info("Food database already has {} entries, skipping USDA import.", count);
            return;
        }

        log.info("Loading USDA FoodData Central foundation foods...");
        File jsonFile = new File("data/FoodData_Central_foundation_food_json_2026-04-30.json");

        if (!jsonFile.exists()) {
            log.warn("USDA JSON file not found at {}, skipping import.", jsonFile.getAbsolutePath());
            return;
        }

        try (InputStream is = new FileInputStream(jsonFile)) {
            JsonNode root = objectMapper.readTree(is);
            JsonNode foods = root.get("FoundationFoods");

            if (foods == null || !foods.isArray()) {
                log.warn("FoundationFoods array not found in USDA JSON.");
                return;
            }

            int loaded = 0;
            for (JsonNode foodNode : foods) {
                try {
                    Food food = parseFood(foodNode);
                    if (food != null) {
                        foodRepository.save(food);
                        loaded++;
                    }
                } catch (Exception e) {
                    log.debug("Skipping food entry: {}", e.getMessage());
                }
            }

            log.info("Loaded {} foods from USDA FoodData Central.", loaded);
        }
    }

    private Food parseFood(JsonNode foodNode) {
        String description = foodNode.has("description") ? foodNode.get("description").asText() : null;
        if (description == null || description.isBlank()) return null;

        FoodType type = inferFoodType(description);

        Food food = new Food();
        food.setName(description);
        food.setType(type);
        food.setPortion(100.0);
        food.setPortionUnit(PortionUnit.GRAMS);

        JsonNode nutrientsNode = foodNode.get("foodNutrients");
        if (nutrientsNode == null || !nutrientsNode.isArray()) return food;

        Map<Nutrient, Double> nutrients = new HashMap<>();
        for (JsonNode nutrientNode : nutrientsNode) {
            try {
                JsonNode nutrientInfo = nutrientNode.get("nutrient");
                if (nutrientInfo == null) continue;

                int nutrientId = nutrientInfo.get("id").asInt();
                Nutrient nutrient = NUTRIENT_MAP.get(nutrientId);

                if (nutrient != null && nutrientNode.has("amount")) {
                    double amount = nutrientNode.get("amount").asDouble();
                    if (amount > 0) {
                        nutrients.put(nutrient, amount);
                    }
                }
            } catch (Exception e) {
                // Skip malformed nutrient entries
            }
        }

        food.setNutrients(nutrients);
        return food;
    }

    private FoodType inferFoodType(String description) {
        String lower = description.toLowerCase();

        if (lower.contains("beef") || lower.contains("pork") || lower.contains("lamb") ||
            lower.contains("chicken") || lower.contains("turkey") || lower.contains("duck") ||
            lower.contains("fish") || lower.contains("salmon") || lower.contains("tuna") ||
            lower.contains("sardine") || lower.contains("mackerel") || lower.contains("anchov") ||
            lower.contains("egg") || lower.contains("liver") || lower.contains("heart") ||
            lower.contains("kidney") || lower.contains("brain") || lower.contains("gizzard") ||
            lower.contains("meat") || lower.contains("sausage") || lower.contains("ham") ||
            lower.contains("bacon") || lower.contains("frankfurter") || lower.contains("bologna") ||
            lower.contains("pastrami") || lower.contains("salami") || lower.contains("jerky") ||
            lower.contains("crab") || lower.contains("shrimp") || lower.contains("lobster") ||
            lower.contains("oyster") || lower.contains("clam") || lower.contains("mussel") ||
            lower.contains("squid") || lower.contains("octopus") || lower.contains("snail")) {
            return FoodType.ANIMAL;
        }

        if (lower.contains("supplement") || lower.contains("vitamin") || lower.contains("mineral") ||
            lower.contains("fish oil") || lower.contains("cod liver") || lower.contains("flaxseed oil") ||
            lower.contains("calcium carbonate") || lower.contains("iron supplement") ||
            lower.contains("multivitamin") || lower.contains("mineral oil")) {
            return FoodType.SUPPLEMENT;
        }

        if (lower.contains("rice") || lower.contains("pasta") || lower.contains("noodle") ||
            lower.contains("spaghetti") || lower.contains("macaroni") || lower.contains("bread") ||
            lower.contains("tortilla") || lower.contains("cereal") || lower.contains("oat") ||
            lower.contains("corn") || lower.contains("potato") || lower.contains("sweet potato") ||
            lower.contains("yam") || lower.contains("flour") || lower.contains("starch")) {
            return FoodType.COOKED_STARCH;
        }

        return FoodType.OTHER_PLANTS;
    }
}
