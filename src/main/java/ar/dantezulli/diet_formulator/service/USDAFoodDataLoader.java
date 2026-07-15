package ar.dantezulli.diet_formulator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.TipoAlimento;
import ar.dantezulli.diet_formulator.model.enums.UnidadPorcion;
import ar.dantezulli.diet_formulator.repository.FoodRepository;

/**
 * Loads USDA FoodData Central foundation foods from the JSON file into the database.
 * Carga alimentos foundation de USDA FoodData Central desde el archivo JSON a la base de datos.
 *
 * Runs once on startup if the food table is empty.
 * Se ejecuta una vez al iniciar si la tabla de alimentos está vacía.
 *
 * USDA nutrient IDs mapped to our Nutrient enum:
 * IDs de nutrientes USDA mapeados a nuestro enum Nutrient:
 * Source: https://fdc.nal.usda.gov/
 */
@Service
public class USDAFoodDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(USDAFoodDataLoader.class);

    private final FoodRepository foodRepository;
    private final ObjectMapper objectMapper;

    /**
     * Mapping from USDA nutrient ID to our Nutrient enum.
     * Mapeo del ID de nutriente USDA a nuestro enum Nutrient.
     *
     * USDA IDs: https://fdc.nal.usda.gov/faq.html#12
     */
    private static final Map<Integer, Nutrient> NUTRIENT_MAP = new HashMap<>();

    static {
        // Energy / Energía
        NUTRIENT_MAP.put(1008, Nutrient.ENERGIA_KCAL);
        // Water / Agua
        NUTRIENT_MAP.put(1051, Nutrient.AGUA_G);
        // Ash / Ceniza
        NUTRIENT_MAP.put(1007, Nutrient.CENIZA_G);
        // Protein / Proteína
        NUTRIENT_MAP.put(1003, Nutrient.PROTEINA_G);
        // Total lipid (fat) / Lípidos totales
        NUTRIENT_MAP.put(1004, Nutrient.LIPIDOS_TOTALES_G);
        // Carbohydrate, by difference / Carbohidratos
        NUTRIENT_MAP.put(1005, Nutrient.CARBOHIDRATOS_G);
        // Fiber, total dietary / Fibra total
        NUTRIENT_MAP.put(1079, Nutrient.FIBRA_TOTAL_G);

        // Minerals / Minerales
        NUTRIENT_MAP.put(1087, Nutrient.CALCIO_MG);       // Calcium, Ca
        NUTRIENT_MAP.put(1089, Nutrient.HIERRO_MG);       // Iron, Fe
        NUTRIENT_MAP.put(1090, Nutrient.MAGNESIO_MG);     // Magnesium, Mg
        NUTRIENT_MAP.put(1091, Nutrient.FOSFORO_MG);      // Phosphorus, P
        NUTRIENT_MAP.put(1092, Nutrient.POTASIO_MG);      // Potassium, K
        NUTRIENT_MAP.put(1093, Nutrient.SODIO_MG);        // Sodium, Na
        NUTRIENT_MAP.put(1095, Nutrient.ZINC_MG);         // Zinc, Zn
        NUTRIENT_MAP.put(1098, Nutrient.COBRE_MG);        // Copper, Cu
        NUTRIENT_MAP.put(1101, Nutrient.MANGANESO_MG);    // Manganese, Mn
        NUTRIENT_MAP.put(1103, Nutrient.SELENIO_UG);      // Selenium, Se
        NUTRIENT_MAP.put(1102, Nutrient.YODO_UG);         // Iodine, I (not always in foundation, but included)
        // Note: Chloride (1097) is sometimes available
        NUTRIENT_MAP.put(1097, Nutrient.CLORURO_MG);      // Chloride, Cl

        // Vitamins / Vitaminas
        NUTRIENT_MAP.put(1162, Nutrient.VITAMINA_C_MG);   // Vitamin C
        NUTRIENT_MAP.put(1165, Nutrient.TIAMINA_MG);      // Thiamin (B1)
        NUTRIENT_MAP.put(1166, Nutrient.RIBOFLAVINA_MG);  // Riboflavin (B2)
        NUTRIENT_MAP.put(1167, Nutrient.NIACINA_MG);      // Niacin (B3)
        NUTRIENT_MAP.put(1170, Nutrient.ACIDO_PANTOTENICO_MG); // Pantothenic acid (B5)
        NUTRIENT_MAP.put(1175, Nutrient.VITAMINA_B6_MG);  // Vitamin B-6
        NUTRIENT_MAP.put(1177, Nutrient.FOLATO_UG);       // Folate, total
        NUTRIENT_MAP.put(1180, Nutrient.COLINA_MG);       // Choline, total
        NUTRIENT_MAP.put(1198, Nutrient.BETAINA_MG);      // Betaine
        NUTRIENT_MAP.put(1178, Nutrient.VITAMINA_B12_UG); // Vitamin B-12
        NUTRIENT_MAP.put(1106, Nutrient.RETINOL_UG);      // Vitamin A, RAE (Retinol)
        NUTRIENT_MAP.put(1109, Nutrient.VITAMINA_E_MG);   // Vitamin E (alpha-tocopherol)
        NUTRIENT_MAP.put(1114, Nutrient.VITAMINA_D3_UG);  // Vitamin D3 (cholecalciferol)
        // Vitamin K (phylloquinone) is nutrient ID 1185
        NUTRIENT_MAP.put(1185, Nutrient.VITAMINA_K_UG);   // Vitamin K (phylloquinone)

        // Amino acids / Aminoácidos
        NUTRIENT_MAP.put(1210, Nutrient.TRIPTOFANO_G);    // Tryptophan
        NUTRIENT_MAP.put(1212, Nutrient.TREONINA_G);      // Threonine
        NUTRIENT_MAP.put(1213, Nutrient.ISOLEUCINA_G);    // Isoleucine
        NUTRIENT_MAP.put(1214, Nutrient.LEUCINA_G);       // Leucine
        NUTRIENT_MAP.put(1215, Nutrient.LISINA_G);        // Lysine
        NUTRIENT_MAP.put(1216, Nutrient.METIONINA_G);     // Methionine
        NUTRIENT_MAP.put(1217, Nutrient.METIONINA_CISTINA_G); // Methionine + Cystine (Total sulfur AA sometimes)
        NUTRIENT_MAP.put(1218, Nutrient.FENILALANINA_G);  // Phenylalanine
        NUTRIENT_MAP.put(1219, Nutrient.FENILALANINA_TIROSINA_G); // Phenylalanine + Tyrosine
        NUTRIENT_MAP.put(1220, Nutrient.VALINA_G);        // Valine
        NUTRIENT_MAP.put(1211, Nutrient.ARGININA_G);      // Arginine
        NUTRIENT_MAP.put(1221, Nutrient.HISTIDINA_G);     // Histidine
        NUTRIENT_MAP.put(1235, Nutrient.TAURINA_MG);      // Taurine (if present)

        // Fatty acids / Ácidos grasos
        NUTRIENT_MAP.put(1258, Nutrient.ACIDO_LINOLEICO_G);    // FA 18:2 n-6 c,c (Linoleic)
        NUTRIENT_MAP.put(1280, Nutrient.ACIDO_ALINOLEICO_G);   // PUFA 18:3 n-3 (Alpha-linolenic)
        NUTRIENT_MAP.put(1313, Nutrient.ACIDO_20_2_N6_G);      // PUFA 20:2 n-6 c,c
        NUTRIENT_MAP.put(1406, Nutrient.ACIDO_20_3_N6_G);      // PUFA 20:3 n-6
        NUTRIENT_MAP.put(1271, Nutrient.ACIDOARAQUIDONICO_G);  // PUFA 20:4 (Arachidonic)
        NUTRIENT_MAP.put(1278, Nutrient.EPA_G);                // PUFA 20:5 n-3 (EPA)
        NUTRIENT_MAP.put(1272, Nutrient.DHA_G);                // PUFA 22:6 n-3 (DHA)
        NUTRIENT_MAP.put(1257, Nutrient.OMEGA_6_G);            // FA 18:2 undifferentiated (Omega 6)
        NUTRIENT_MAP.put(1259, Nutrient.OMEGA_3_G);            // FA 18:3 undifferentiated (Omega 3)
    }

    public USDAFoodDataLoader(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Loads foods on startup if the database is empty.
     * Carga alimentos al iniciar si la base de datos está vacía.
     */
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

    /**
     * Parses a single food entry from the USDA JSON.
     * Parsea una entrada individual de alimento del JSON USDA.
     */
    private Food parseFood(JsonNode foodNode) {
        String description = foodNode.has("description") ? foodNode.get("description").asText() : null;
        if (description == null || description.isBlank()) return null;

        // Determine food type based on description keywords
        // Determinar tipo de alimento según palabras clave en la descripción
        TipoAlimento tipo = inferFoodType(description);

        Food food = new Food();
        food.setNombre(description);
        food.setTipo(tipo);
        food.setPorcion(100.0);
        food.setUnidadPorcion(UnidadPorcion.GRAMOS);

        // Parse nutrients / Parsear nutrientes
        JsonNode nutrientsNode = foodNode.get("foodNutrients");
        if (nutrientsNode == null || !nutrientsNode.isArray()) return food;

        Map<Nutrient, Double> nutrientes = new HashMap<>();
        for (JsonNode nutrientNode : nutrientsNode) {
            try {
                JsonNode nutrientInfo = nutrientNode.get("nutrient");
                if (nutrientInfo == null) continue;

                int nutrientId = nutrientInfo.get("id").asInt();
                Nutrient nutrient = NUTRIENT_MAP.get(nutrientId);

                if (nutrient != null && nutrientNode.has("amount")) {
                    double amount = nutrientNode.get("amount").asDouble();
                    if (amount > 0) {
                        nutrientes.put(nutrient, amount);
                    }
                }
            } catch (Exception e) {
                // Skip malformed nutrient entries / Saltar entradas de nutriente mal formadas
            }
        }

        food.setNutrientes(nutrientes);
        return food;
    }

    /**
     * Infers food type from the description text.
     * Infiere el tipo de alimento desde el texto de la descripción.
     */
    private TipoAlimento inferFoodType(String description) {
        String lower = description.toLowerCase();

        // Animal sources / Fuentes animales
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
            return TipoAlimento.ANIMAL;
        }

        // Supplements / Suplementos
        if (lower.contains("supplement") || lower.contains("vitamin") || lower.contains("mineral") ||
            lower.contains("fish oil") || lower.contains("cod liver") || lower.contains("flaxseed oil") ||
            lower.contains("calcium carbonate") || lower.contains("iron supplement") ||
            lower.contains("multivitamin") || lower.contains("mineral oil")) {
            return TipoAlimento.SUPLEMENTO;
        }

        // Cooked starches / Almidones cocidos
        if (lower.contains("rice") || lower.contains("pasta") || lower.contains("noodle") ||
            lower.contains("spaghetti") || lower.contains("macaroni") || lower.contains("bread") ||
            lower.contains("tortilla") || lower.contains("cereal") || lower.contains("oat") ||
            lower.contains("corn") || lower.contains("potato") || lower.contains("sweet potato") ||
            lower.contains("yam") || lower.contains("flour") || lower.contains("starch")) {
            return TipoAlimento.ALMIDON_COCIDO;
        }

        // Other plants / Otras plantas
        return TipoAlimento.OTRAS_PLANTAS;
    }
}
