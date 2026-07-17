# Fórmulas de Cálculo de Energía

Referencia rápida de las fórmulas en `EnergyCalculator.java`.

## Flujo general

El perfil del animal se usa para calcular la **ingesta calórica recomendada** en kcal/día. El proceso es:

1. Determinar el **peso efectivo** (ideal o actual)
2. Calcular el **peso metabólico** (BW elevado a un exponente)
3. Obtener el **MER base** (coeficiente x peso metabólico)
4. Aplicar **ajuste por etapa de vida** (cachorro, embarazo, lactancia)
5. Aplicar **factor de actividad**

El resultado final es la **Ingesta Calórica Recomendada**.

---

## 1. Peso Efectivo

Si el animal **no está en su peso ideal** y se proporcionó un peso ideal, se usa ese peso ideal para el cálculo. Si está en su peso ideal (o no se proporcionó uno), se usa el peso actual.

Esto es porque las ecuaciones deben calcular la energía para el peso que el animal debería tener, no el que tiene actualmente.

---

## 2. Peso Metabólico

Se calcula elevando el peso efectivo a un exponente que depende de la especie.

| Especie | Exponente | Referencia |
|---------|-----------|------------|
| Perro   | 0.75      | NRC (2006), estándar para mamíferos |
| Gato    | 0.67      | NRC (2006), ajustado para metabolismo felino |

Ejemplo: perro de 21 kg → 21 elevado a 0.75 = 9.63

---

## 3. MER Base

El MER (Maintenance Energy Requirement) base es el producto del coeficiente de la especie por el peso metabólico.

| Especie | Coeficiente | Referencia |
|---------|-------------|------------|
| Perro   | 130.0       | NRC (2006), tabla 3-4 |
| Gato    | 100.0       | NRC (2006), tabla 3-6 |

Ejemplo: perro de 21 kg → 130 x 9.63 = 1,251.9 kcal/día

---

## 4. Ajustes por Etapa de Vida

### Adulto
Sin ajuste. El MER base se usa tal cual.

### Cachorro
Se multiplica el MER base por un factor según la edad en meses:

| Edad (meses) | Factor |
|--------------|--------|
| Hasta 4      | 2.5    |
| 5 a 8        | 2.0    |
| Más de 8     | 1.6    |

Estos factores incluyen la energía necesaria para el crecimiento. Los cachorros muy jóvenes necesitan hasta 2.5 veces la energía de mantenimiento.

### Embarazada
Se suma energía extra por el desarrollo fetal:

Energía extra = 26 x peso en kg del animal

Ejemplo: perra de 21 kg → MER base 1,251.9 + (26 x 21) = 1,251.9 + 546 = 1,797.9 kcal/día

### Lactancia
Es el estado de mayor demanda energética. El cálculo tiene tres partes:

**Producción de leche por cachorro** (según tamaño de la camada):

| Cachorros en la camada | Leche por cachorro (% del peso materno/día) |
|------------------------|---------------------------------------------|
| 1 a 4                  | 1.0%                                        |
| 5 a 8                  | 0.5%                                        |
| Más de 8               | 0.3%                                        |

**Factor de semana de lactancia**:

| Semana | Factor |
|--------|--------|
| 1      | 0.75   |
| 2      | 0.95   |
| 3      | 1.10   |
| 4 o más| 1.20   |

**Cálculo de la energía de lactancia**:

La energía de la leche es 1.45 kcal por gramo de leche producida.

Litro de leche diario = peso del animal (g) x porcentaje de leche por cachorro x número de cachorros

Energía de lactancia = litro de leche diario x 1.45 x factor de semana

MER final = MER base + energía de lactancia

Ejemplo: perra de 21 kg, 4 cachorros, semana 3:
- Leche por cachorro: 1.0% (hasta 4 cachorros)
- Factor semana 3: 1.10
- Leche diaria: 21,000 g x 0.01 x 4 = 840 g
- Energía de lactancia: 840 x 1.45 x 1.10 = 1,337.7 kcal
- MER = 1,251.9 + 1,337.7 = 2,589.6 kcal/día

---

## 5. Factor de Actividad

La ingesta calórica recomendada final es el MER (ya ajustado por etapa de vida) multiplicado por el factor de actividad.

| Nivel de Actividad | Factor |
|--------------------|--------|
| Gran Danés / Galgo activo | 1.6 |
| Terrier | 1.4 |
| Muy activo | 1.3 |
| Activo | 1.2 |
| Adulto joven | 1.0 |
| Inactivo / senior | 0.8 |
| Canil, muy inactivo | 0.7 |
| Sedentario | 0.6 |

---

## Ejemplos completos

### Perro adulto, 21 kg, peso ideal, sedentario

1. Peso efectivo: 21 kg
2. Peso metabólico: 21^0.75 = 9.63
3. MER base: 130 x 9.63 = 1,251.9
4. Ajuste adulto: sin cambio
5. Ingesta: 1,251.9 x 0.6 = 751.1 kcal/día

### Gato adulto, 4 kg, activo

1. Peso efectivo: 4 kg
2. Peso metabólico: 4^0.67 = 2.52
3. MER base: 100 x 2.52 = 251.5
4. Ajuste adulto: sin cambio
5. Ingesta: 251.5 x 1.2 = 301.8 kcal/día

### Perra embarazada, 15 kg, adulto joven

1. Peso efectivo: 15 kg
2. Peso metabólico: 15^0.75 = 7.62
3. MER base: 130 x 7.62 = 990.6
4. Ajuste embarazo: 990.6 + (26 x 15) = 990.6 + 390 = 1,380.6
5. Ingesta: 1,380.6 x 1.0 = 1,380.6 kcal/día

---

## Redondeo

Todos los valores se redondean a 2 decimales.

---

## Fuentes

- NRC (2006). Nutrient Requirements of Dogs and Cats. National Research Council.
  - Tabla 3-4: Maintenance Energy Requirements of Dogs
  - Tabla 3-6: Daily Maintenance Energy Requirements of Cats
  - Tabla 15-4: Daily ME Requirements for Adult Dogs at Maintenance
  - Tabla 15-7: Daily ME Requirements for Lactating Bitches
  - Tabla 15-11: Daily ME Requirements for Adult Cats at Maintenance

---

## Glosario de abreviaturas

| Abreviatura | Inglés | Español |
|-------------|--------|---------|
| BW | Body Weight | Peso corporal |
| ME | Metabolizable Energy | Energía metabolizable |
| MER | Maintenance Energy Requirement | Requerimiento energético de mantenimiento |
| DM | Dry Matter | Materia seca |
| kcal | Kilocalorie | Kilocaloría |
| g | Gram | Gramo |
| mg | Milligram | Miligramo |
| ug | Microgram | Microgramo |
