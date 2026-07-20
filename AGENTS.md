# AGENTS.md

Veterinary diet formulator — Spring Boot app for vets to create nutritional plans for dogs/cats based on NRC standards.

## Stack

- Java 25, Spring Boot 4.1.0, Gradle 9.5.1 (wrapper included)
- Single-module project, package: `ar.dantezulli.diet_formulator`
- License: GPL-3.0

## Commands

- Build: `./gradlew build`
- Test: `./gradlew test`
- Run: `./gradlew bootRun`
- Compile only: `./gradlew compileJava`

No lint, formatter, or typecheck tasks configured beyond standard Gradle/Java compilation.

## Architecture

Server-rendered MVC — no REST API, no auth, no external services, no Docker/CI.

**Domain model** (JPA, SQLite, Lombok everywhere):
- `AnimalProfile` → `Diet` → `DietItem` (UUID PKs, `@OneToMany` cascade, audit timestamps via `BaseEntity`)
- `Food` — nutrients stored as `Map<Nutrient, Double>` via `@ElementCollection`
- `Nutrient` enum — central abstraction, every tracked nutrient must exist here
- `NRCRequirementService` — target nutrient values by species/life stage/weight
- `EnergyCalculator` — MER and recommended caloric intake (separate dog/cat coefficients and exponents)

**Key gotchas:**
- `ddl-auto: create-drop` — **DB schema rebuilt on every restart**. Data re-imported from USDA JSON (`data/FoodData_Central_foundation_food_json_2026-04-30.json`). Do not assume persistence across runs.
- USDA loader (`@Profile("!test")`) — only runs in non-test profiles, skips if DB already has entries.
- Tests use in-memory SQLite (`jdbc:sqlite::memory:`), separate from dev DB.
- Lombok required for new entity/service classes (`@Data`, `@RequiredArgsConstructor`, `@Slf4j`).
- IDs are `UUID` with `GenerationType.UUID`, not auto-increment.
- `Nutrient` enum maps to USDA nutrient IDs in `USDAFoodDataLoader.NUTRIENT_MAP`.

## Project Structure

```
src/main/java/ar/dantezulli/diet_formulator/
  model/entities/    — JPA entities (Food, AnimalProfile, Diet, DietItem, BaseEntity)
  model/dto/         — DTOs (FoodDTO, AnimalProfileDTO, DietDTO, DietItemDTO)
  model/enums/       — Enums (Species, LifeStage, ActivityLevel, Nutrient, FoodType, etc.)
  model/records/     — Records (NutrientRequirement)
  service/           — Business logic (EnergyCalculator, FoodService, DietService, etc.)
  controller/        — Thymeleaf controllers (Home, Profile, Diet, Food + GlobalExceptionHandler)
  repository/        — Spring Data JPA repos
  config/            — JPA converters
  data/              — NRC requirement data
src/test/java/       — JUnit 5 tests (service-layer tests exist)
src/main/resources/templates/ — Thymeleaf HTML views (foods, profiles, diets, error)
src/main/resources/application.yaml — Spring config (tracked, not gitignored)
data/                — SQLite DB + USDA JSON (gitignored except .gitkeep)
docs/                — Reference docs (see below)
```

## docs/ contents

| File | Description |
|------|-------------|
| `notas-formulador-clase-overview.md` | Functional analysis notes from original formulator class (Spanish) |
| `formulador-original-descripcion.md` | Original formulator website description, cleaned to markdown (Spanish) |
| `dieta-ejemplo.md` | Example diet for Otto Mayo-Villarosa — ingredients, macros, full nutrient table (Spanish) |
| `dieta-ejemplo.pdf` | Original PDF of the example diet |
| `energy-calculation-formulas.md` | Energy calculation formulas reference |
| `own-formulas.pdf` | Custom formulas reference |

## Conventions

- **README**: English (developer/LLM facing)
- **docs/**: Spanish (veterinarian end-user, Argentinean)
- **Code comments**: English
- No CI workflows.
- `.gitignore` tracks `application.yaml` but ignores `application-*.yml` / `application-*.properties` — env-specific profiles stay local.
- Nutritional data sourced from USDA FoodData Central (public domain).
- NRC book (2006) is copyrighted — reference only, never distribute.
- Author is NOT a veterinarian. All nutritional output is speculative/informational.
