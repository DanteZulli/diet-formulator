# Fórmulas de Cálculo — Diet Formulator

Referencia técnica de los cálculos nutricionales del sistema.
Basado en NRC (2006) *Nutrient Requirements of Dogs and Cats*.

---

## Parte 1: Ingesta Calórica Recomendada

Determina cuántas kcal/día necesita el animal.

### Flujo

```
Peso efectivo → Peso metabólico → MER base → Ajuste etapa de vida → Factor de actividad
```

### 1.1 Peso Efectivo

| Condición | Peso usado |
|-----------|------------|
| En peso ideal o sin peso ideal definido | Peso actual |
| No en peso ideal + peso ideal definido | Peso ideal |

Razón: las ecuaciones calculan energía para el peso que el animal debería tener.

### 1.2 Peso Metabólico

```
peso_metabólico = peso_efectivo ^ exponente
```

| Especie | Exponente | Ref. |
|---------|-----------|------|
| Perro   | 0.75      | NRC (2006) Tabla 3-4 |
| Gato    | 0.67      | NRC (2006) Tabla 3-6 |

Ejemplo: perro 21 kg → 21^0.75 = 9.81

### 1.3 MER Base (Mantenimiento)

```
MER_base = coeficiente × peso_metabólico
```

| Especie | Coeficiente | Ref. |
|---------|-------------|------|
| Perro   | 130.0       | NRC (2006) Tabla 15-4 |
| Gato    | 100.0       | NRC (2006) Tabla 15-11 |

Ejemplo: perro 21 kg → 130 × 9.81 = 1,275.3 kcal/día

### 1.4 Ajustes por Etapa de Vida

#### Adulto
Sin ajuste. MER_base se usa tal cual.

#### Cachorro (después del destete)
Se multiplica MER_base por un factor según edad:

| Edad | Factor | Ref. |
|------|--------|------|
| ≤ 4 meses | ×2.5 | NRC (2006) Tabla 15-2 |
| 5–8 meses | ×2.0 | NRC (2006) Tabla 15-2 |
| > 8 meses | ×1.6 | NRC (2006) Tabla 15-2 |

Estos factores incluyen energía para crecimiento.

#### Embarazada
Se suma energía fetal:

```
MER = MER_base + (26 × peso_kg)
```

Ref: NRC (2006) Tabla 15-6.

Ejemplo: perra 15 kg → 990.6 + (26 × 15) = 1,380.6 kcal/día

#### Lactancia
Mayor demanda energética. Tres componentes:

**Producción de leche por cachorro:**

| Camada | Leche/cachorro (% peso materno/día) |
|--------|--------------------------------------|
| 1–4    | 1.0%                                 |
| 5–8    | 0.5%                                 |
| > 8    | 0.3%                                 |

**Factor de semana:**

| Semana | Factor |
|--------|--------|
| 1      | 0.75   |
| 2      | 0.95   |
| 3      | 1.10   |
| 4+     | 1.20   |

**Cálculo:**

```
leche_diaria_g = peso_kg × 1000 × %leche × num_cachorros
energía_lactancia = leche_diaria_g × 1.45 × factor_semana
MER = MER_base + energía_lactancia
```

1.45 kcal/g = energía metabolizable de la leche canina/felina.

Ref: NRC (2006) Tabla 15-7 (perros), Tabla 15-13 (gatos).

Ejemplo: perra 21 kg, 4 cachorros, semana 3:
- Leche: 21,000 × 0.01 × 4 = 840 g
- Energía: 840 × 1.45 × 1.10 = 1,337.7 kcal
- MER = 1,275.3 + 1,337.7 = 2,613.0 kcal/día

### 1.5 Factor de Actividad

```
ingesta_recomendada = MER × factor_actividad
```

| Nivel | Factor |
|-------|--------|
| Gran Danés / Galgo activo | 1.6 |
| Terrier | 1.4 |
| Muy activo | 1.3 |
| Activo | 1.2 |
| Adulto joven | 1.0 |
| Inactivo / senior | 0.8 |
| Canil, muy inactivo | 0.7 |
| Sedentario | 0.6 |

### 1.6 Redondeo

Todos los valores se redondean a 2 decimales.

### Ejemplos completos

**Perro adulto, 21 kg, sedentario:**
1. Peso efectivo: 21 kg
2. Peso metabólico: 21^0.75 = 9.81
3. MER base: 130 × 9.81 = 1,275.3
4. Adulto: sin ajuste
5. Ingesta: 1,275.3 × 0.6 = 765.2 kcal/día

**Gato adulto, 4 kg, activo:**
1. Peso efectivo: 4 kg
2. Peso metabólico: 4^0.67 = 2.52
3. MER base: 100 × 2.52 = 251.5
4. Adulto: sin ajuste
5. Ingesta: 251.5 × 1.2 = 301.8 kcal/día

**Perra embarazada, 15 kg, adulto joven:**
1. Peso efectivo: 15 kg
2. Peso metabólico: 15^0.75 = 7.62
3. MER base: 130 × 7.62 = 990.6
4. Embarazo: 990.6 + (26 × 15) = 1,380.6
5. Ingesta: 1,380.6 × 1.0 = 1,380.6 kcal/día

---

## Parte 2: Requerimientos Nutricionales (Targets)

Determina el objetivo diario para cada nutriente según NRC (2006).

### Fórmula general

```
target_diario = RA_por_kg_BW × peso_metabólico
```

Donde:
- `RA_por_kg_BW` = Recommended Allowance por kg de BW^exp (de las tablas NRC)
- `peso_metabólico` = peso_efectivo ^ exponente (0.75 perros, 0.67 gatos)

### Tablas NRC utilizadas

| Especie | Etapa de vida | Tabla NRC | Notas |
|---------|---------------|-----------|-------|
| Perro | Cachorro (14+ semanas) | 15-3 | Proteínas/aminoácidos de 14+ sem; grasas/minerales/vitaminas post-destete |
| Perro | Adulto | 15-5 | Mantenimiento |
| Perro | Gestación / Lactancia | 15-8 | Valores unificados |
| Gato | Cachorro (kitten) | 15-10 | Crecimiento |
| Gato | Adulto | 15-12 | Mantenimiento |
| Gato | Gestación | 15-14 | Sección "Gestating" (proteínas/aminoácidos) + grasas/minerales/vitaminas de 15-14 |
| Gato | Lactancia | 15-14 | Sección "Lactating" + grasas/minerales/vitaminas de 15-14 |

### Unidades de medida

Las tablas NRC reportan valores en diferentes unidades. El sistema almacena todo en la unidad del nutriente:

| Unidad | Nutrientes |
|--------|------------|
| g | Proteína, aminoácidos, ácidos grasos, lípidos, calcio, fósforo, potasio |
| mg | Minerales (hierro, zinc, cobre, etc.), vitaminas hidrosolubles, taurina (perros) |
| µg | Selenio, yodo, vitaminas liposolubles (A, D, E, K), B12, folato |

Conversión Vitamin K: NRC usa mg (menadione). Sistema usa µg. Factor: ×1000.

### Nutrientes con target NRC

| Categoría | Nutrientes | Unidad |
|-----------|-----------|--------|
| Proteínas y Aminoácidos | Proteína, Arginina, Histidina, Isoleucina, Leucina, Lisina, Metionina, Met+Cis, Fenilalanina, Phe+Tyr, Treonina, Triptófano, Valina | g |
| Lípidos y Ácidos Grasos | Lípidos totales, Ácido linoleico, Ácido α-linoleico, Ácido araquidónico (gatos), EPA+DHA Total | g |
| Minerales | Calcio, Fósforo, Magnesio, Sodio, Potasio, Cloruro, Hierro, Cobre, Zinc, Manganeso, Selenio, Yodo | mg/µg |
| Vitaminas | Vitamina A (retinol), Vitamina D3, Vitamina E, Vitamina K, Tiamina, Riboflavina, Vitamina B6, Niacina, Ácido pantoténico, Vitamina B12, Folato, Colina | mg/µg |

### Nutrientes con target adicional (varía por contexto)

| Nutriente | Perro adulto | Perro cachorro | Perro gestación | Gato adulto | Gato cachorro | Gato gestación/lactancia |
|-----------|-------------|----------------|-----------------|-------------|---------------|--------------------------|
| Taurina | null (sin RA) | 21 mg/kgBW^0.75 | 18 mg/kgBW^0.75 | 9.9 mg/kgBW^0.67 | 21 mg/kgBW^0.67 | 18 mg/kgBW^0.67 |
| Ácido araquidónico | null (sin RA) | 0.022 g/kgBW^0.75 | null | 0.0015 g/kgBW^0.67 | 0.001 g/kgBW^0.67 | 0.011 g/kgBW^0.67 |

### Nutrientes SIN target NRC (null)

| Nutriente | Razón |
|-----------|-------|
| Carbohidratos, Fibra | NRC no define requerimiento mínimo |
| Vitamina C, Betaina | Sintetizados por el organismo |
| EPA individual, DHA individual | NRC solo define EPA+DHA combinados |
| Omega 6 sin especificar, Omega 3 sin especificar | NRC define ácidos grasos específicos |
| 20:2 n-6, 20:3 n-6 | Metabolitos intermedios |
| Ca:P, Zn:Cu, Omega 6:3 | Ratios calculados de componentes |
| Tamaño porción, Agua, Materia seca, Ceniza | Valores calculados, no de tablas |

### Límites Seguros Superiores (SUL)

Las tablas NRC definen SUL para algunos nutrientes. Si el total supera el SUL, el nutriente se marca como EXCESO (rojo).

**Nutrientes con SUL definidos por tabla:**

| Tabla | Nutrientes con SUL |
|-------|-------------------|
| Perro cachorro (15-3) | Lisina, Lípidos totales, Linoleico, EPA+DHA, Calcio, Vitamina A, Vitamina D3 |
| Perro adulto (15-5) | Lípidos totales, Linoleico, EPA+DHA, Vitamina A, Vitamina D3 |
| Perro gestación (15-8) | Lípidos totales, Linoleico, EPA+DHA, Vitamina A, Vitamina D3 |
| Gato cachorro (15-10) | Arginina, Histidina, Isoleucina, Leucina, Lisina, Metionina, Phe, Phe+Tyr, Treonina, Triptófano, Valina, Taurina, Lípidos, Linoleico, Vitamina A, Vitamina D3 |
| Gato adulto (15-12) | Lípidos totales, Linoleico, Araquidónico, Zinc, Vitamina A, Vitamina D3 |
| Gato gestación/lactancia (15-14) | Lípidos totales, Linoleico, Vitamina A, Vitamina D3 |

**Nutrientes SIN SUL (el NRC intencionalmente no los define):**

| Nutriente | Razón |
|-----------|-------|
| Proteína | Los perros/gatos toleran altos niveles de proteína dietaria |
| La mayoría de aminoácidos | No se establecieron límites por falta de evidencia de toxicidad |
| Calcio (perro adulto/gestación) | No se definió SUL para estas etapas |
| Fósforo, Magnesio, Sodio, Potasio, Cloruro | Minerales sin evidencia de toxicidad a nivel dietario |
| Hierro, Cobre, Manganeso, Selenio, Yodo | Minerales esenciales sin SUL definido en la mayoría de tablas |
| Vitamina E, K, Tiamina, Riboflavina, B6, Niacina, Pantoténico, B12, Folato, Colina | Vitaminas hidrosolubles sin SUL definido |

**Consecuencia visual:** nutrientes sin SUL nunca se muestran en rojo (EXCESS), incluso si el total supera ampliamente el target. Se muestran en verde (ADEQUATE) siempre que el total ≥ target.

Ejemplos de SUL (perro adulto, RA por kg BW^0.75):

| Nutriente | SUL | RA | Ratio SUL/RA |
|-----------|-----|-----|--------------|
| Lípidos totales | 10.8 g | 1.8 g | 6.0× |
| Linoleico | 2.1 g | 0.36 g | 5.8× |
| EPA+DHA | 0.37 g | 0.03 g | 12.3× |
| Vitamina A | 2,099 µg | 50 µg | 42.0× |
| Vitamina D3 | 2.6 µg | 0.45 µg | 5.8× |

### Límites Seguros — ejemplos por especie/etapa

**Vitamina A (µg/kg BW^exp):**

| Tabla | RA | SUL |
|-------|-----|-----|
| Perro cachorro | 105 | 1,044 |
| Perro adulto | 50 | 2,099 |
| Perro gestación | 186 | 1,846 |
| Gato cachorro | 52 | 4,180 |
| Gato adulto | 24.7 | 2,469 |
| Gato gestación/lactancia | 107 | 5,333 |

**Calcio (mg/kg BW^exp):**

| Tabla | RA |
|-------|-----|
| Perro cachorro | 680 |
| Perro adulto | 130 |
| Perro gestación | 820 |
| Gato cachorro | 410 |
| Gato adulto | 71 |
| Gato gestación/lactancia | 565 |

---

## Parte 3: Codificación por Colores

Cada nutriente se evalúa contra su target y SUL:

| Estado | Condición | Color | Significado |
|--------|-----------|-------|-------------|
| INSUFFICIENT | total < target | Negro | Por debajo del mínimo recomendado |
| ADEQUATE | target ≤ total ≤ SUL, o total ≥ target sin SUL definido | Verde | Dentro del rango seguro, o sin límite superior definido |
| EXCESS | total > SUL | Rojo | Excede el límite seguro |

Si un nutriente no tiene target (null), no se evalúa (sin color).

**Nota importante:** nutrientes sin SUL nunca serán EXCESS. Esto no es un error — es una decisión del NRC (2006) basada en la falta de evidencia de toxicidad para esos nutrientes a nivel dietario. Ver tabla anterior en "Límites Seguros Superiores (SUL)".

---

## Parte 4: Implementación en Código

### Archivos principales

| Archivo | Función |
|---------|---------|
| `EnergyCalculator.java` | Calcula ingesta calórica (Parte 1) |
| `NRCRequirementData.java` | Datos hardcodeados de las 6 tablas NRC |
| `NRCRequirementService.java` | Lookup + cálculo de targets diarios (Parte 2) |
| `DietService.java` | Orquesta cálculos, genera NutrientSummary |
| `NutrientSummary` (record) | `(total, target, sul, pctTarget, level)` |

### Flujo de cálculo en el sistema

```
AnimalProfile
    ↓
EnergyCalculator.calculateRecommendedIntake(profile) → kcal/día
    ↓
NRCRequirementService.calculateDailyTarget(profile, nutrient) → target diario
    ↓
DietService.calculateNutrientSummary(diet) → Map<Nutrient, NutrientSummary>
    ↓
Vista: tabla agrupada por categoría con colores
```

### Selector de tabla NRC

```
Species(DOG)  × LifeStage(PUPPY)  → Table 15-3  (14+ weeks)
Species(DOG)  × LifeStage(ADULT)  → Table 15-5
Species(DOG)  × LifeStage(*)      → Table 15-8  (gestación/lactancia)
Species(CAT)  × LifeStage(PUPPY)  → Table 15-10
Species(CAT)  × LifeStage(ADULT)  → Table 15-12
Species(CAT)  × LifeStage(PREGNANT) → Table 15-14 (gestating)
Species(CAT)  × LifeStage(LACTATING) → Table 15-14 (lactating)
```

---

## Fuentes

NRC (2006). *Nutrient Requirements of Dogs and Cats*. National Research Council.

| Tabla | Contenido |
|-------|-----------|
| 3-4 | MER de perros |
| 3-6 | MER de gatos |
| 15-2 | Energía para crecimiento de cachorros |
| 15-3 | Requerimientos nutricionales — cachorros |
| 15-4 | MER de perros adultos |
| 15-5 | Requerimientos nutricionales — perros adultos |
| 15-6 | Energía para gestación en perras |
| 15-7 | Energía para lactancia en perras |
| 15-8 | Requerimientos nutricionales — perras gestación/lactancia |
| 15-10 | Requerimientos nutricionales — gatitos |
| 15-11 | Energía para gatos adultos |
| 15-12 | Requerimientos nutricionales — gatos adultos |
| 15-13 | Energía para lactancia en gatas |
| 15-14 | Requerimientos nutricionales — gatas gestación/lactancia |

---

## Glosario

| Abreviatura | Inglés | Español |
|-------------|--------|---------|
| BW | Body Weight | Peso corporal |
| ME | Metabolizable Energy | Energía metabolizable |
| MER | Maintenance Energy Requirement | Requerimiento energético de mantenimiento |
| RA | Recommended Allowance | Recomendación (valor más conservador) |
| MR | Minimal Requirement | Requerimiento mínimo |
| AI | Adequate Intake | Ingesta adecuada |
| SUL | Safe Upper Limit | Límite seguro superior |
| DM | Dry Matter | Materia seca |
| kcal | Kilocalorie | Kilocaloría |
| g | Gram | Gramo |
| mg | Milligram | Miligramo |
| µg | Microgram | Microgramo |
| RE | Retinol Equivalent | Equivalente de retinol |
