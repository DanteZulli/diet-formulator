# AGENTS.md

Veterinary diet formulator — Spring Boot app for vets to create nutritional plans for dogs/cats based on NRC standards.

## Stack

- Java 25, Spring Boot 4.1.0, Gradle 9.5.1 (wrapper included)
- Single-module project, package: `ar.dantezulli.diet_formulator`
- License: GPL-3.0
- Early stage — only scaffold exists (app entrypoint + context-loads test)

## Commands

- Build: `./gradlew build`
- Test: `./gradlew test`
- Run: `./gradlew bootRun`
- Compile only: `./gradlew compileJava`

No lint, formatter, or typecheck tasks configured beyond standard Gradle/Java compilation.

## Project Structure

```
src/main/java/ar/dantezulli/diet_formulator/  — app code
src/test/java/ar/dantezulli/diet_formulator/  — tests (JUnit 5 via JUnit Platform)
src/main/resources/application.yaml            — Spring config
docs/                                          — reference docs (see below)
```

## docs/ contents

| File | Description |
|------|-------------|
| `notas-formulador-clase-overview.md` | Functional analysis notes from original formulator class (Spanish) |
| `formulador-original-descripcion.md` | Original formulator website description, cleaned to markdown (Spanish) |
| `dieta-ejemplo.md` | Example diet for Otto Mayo-Villarosa — ingredients, macros, full nutrient table (Spanish) |
| `dieta-ejemplo.pdf` | Original PDF of the example diet |
| `nrc-nutrient-requirements-dogs-cats.md` | NRC book reference — **in .gitignore, do NOT commit** |

## Conventions

- **README**: English (developer/LLM facing)
- **docs/**: Spanish (veterinarian end-user, Argentinean)
- **Code comments**: English
- No CI workflows exist yet.
- No database configured yet. When one is added, check `application.yaml` and `.gitignore` for env-specific profile patterns (`application-*.yml` gitignored).
- Nutritional data sourced from USDA FoodData Central (public domain).
- NRC book (2006) is copyrighted — reference only, never distribute.
- Author is NOT a veterinarian. All nutritional output is speculative/informational.
