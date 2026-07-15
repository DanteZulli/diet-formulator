# Diet Formulator

Formulador de dietas nutricionales veterinarias para perros y gatos.

## Descripción

Aplicación web para veterinarios que permiten formular planes nutricionales personalizados para pacientes caninos y felinos. Calcula requerimientos nutricionales basados en el peso metabólico del paciente según los estándares del NRC (National Research Council).

El sistema fue inspirado por el trabajo de la médica veterinaria Trinidad Achterberg Barros y su Formulador MVP. Este proyecto es una reimplementación independiente y de código abierto, sin relación comercial con el producto original.

### Objetivo

Proveer a la comunidad veterinaria una herramienta gratuita, profesional y basada en evidencia científica para:

- Crear perfiles de pacientes (perros y gatos, todas las etapas de vida)
- Calcular requerimientos energéticos y nutricionales
- Formular dietas caseras balanceadas (crudas, cocidas, mixtas)
- Visualizar deficiencias y excesos de nutrientes en tiempo real
- Exportar planes nutricionales en formato PDF

## Aviso Importante - Disclaimer

**El autor de este proyecto NO es veterinario.** Esta aplicación es una herramienta de apoyo desarrollada por un programador con fines educativos y de aprendizaje. **NO sustituye el criterio profesional de un médico veterinario.**

- Ningún dato, cálculo o recomendación generada por este software debe ser utilizado como plan nutricional real para un animal.
- Los valores calculados son **especulativos e informativos**, no profesionales.
- Cualquier dieta para una mascota debe ser supervisada y validada por un veterinario matriculado.
- **El autor no se responsabiliza** por el uso que se le dé a esta herramienta ni por las consecuencias que pueda generar su aplicación directa o indirecta sobre la salud de un animal.

### Sobre la naturaleza del proyecto

Este proyecto es una reimplementación independiente, inspirada en el concepto general de formuladores de dietas veterinarias. **No es una copia ni derivado de ningún producto comercial existente.**

- **Licencia**: GPL-3.0 (GNU General Public License v3.0)
- **Código**: Propiedad del autor original, licenciado bajo GPL-3.0
- **Base de datos nutricional**: USDA FoodData Central (dominio público del gobierno de EE.UU.)
- **Referencia científica**: *Nutrient Requirements of Dogs and Cats* (NRC, 2006) - National Academies Press

### Sobre el libro de referencia

El libro *Nutrient Requirements of Dogs and Cats* (National Research Council, 2006) es una publicación con copyright de la National Academy of Sciences. **Este proyecto no distribuye, reproduce ni incluye dicho libro.** Se utiliza únicamente como fuente bibliográfica para la implementación de los requerimientos nutricionales. Los valores y tablas de requerimientos fueron implementados de forma independiente basándose en la literatura científica pública.

### Acknowledgments

- **Trinidad Achterberg Barros** - Médica veterinaria, autora del Formulador MVP original cuyo trabajo inspiró este proyecto
- **National Research Council** - *Nutrient Requirements of Dogs and Cats* (2006). National Academies Press. Washington, D.C.
- **USDA FoodData Central** - Base de datos de composición nutricional de alimentos

## Características Planificadas (MVP)

### Creador de Perfiles

- Registro de datos del paciente (nombre, especie, edad, peso, raza, condición corporal)
- Soporte para: perros y gatos adultos, cachorros, perras/gatas preñadas y en lactancia
- Cálculo automático de requerimientos energéticos según nivel de actividad
- Configuración de objetivos de macronutrientes (proteínas, grasas, carbohidratos, fibra)
- Gestión de peso ideal y peso actual
- Registro fotográfico (cara, vista superior, vista lateral)

### Creador de Dietas

- Selección de perfil de paciente
- Agregado de alimentos con cantidad y tipo de cálculo
- Visualización en tiempo real de la dieta completa
- Tabla de nutrientes con 5 columnas: Texto/Valor, Objetivo, Total, %MS, %Objetivo
- Secciones: General, Proteína, Lípidos, Carbohidratos, Minerales, Vitaminas
- Indicadores de color: negro (insuficiente), verde (suficiente), rojo (exceso)
- Exportación a PDF con resumen de calorías y materia seca

### Gestión de Alimentos

- Base de datos de +450 alimentos y suplementos
- Importación desde USDA FoodData Central (CSV/JSON, actualizado Abril/Octubre)
- Creación manual de alimentos con información nutricional completa
- Categorías: animal, suplemento, almidón cocido, otras plantas, receta

## Stack Tecnológico

- **Backend**: Spring Boot 4.1 + Gradle
- **Template Engine**: Thymeleaf (a definir)
- **Lenguaje**: Java 25
- **Base de datos**: A definir
- **Base de datos nutricional**: USDA FoodData Central (foundation foods)

## Estructura del Proyecto

```
diet-formulator/
├── docs/
│   ├── notas-formulador-clase-overview.md    # Notas de análisis funcional
│   ├── formulador-original-descripcion.txt   # Descripción del formulador original
│   ├── dieta-ejemplo.pdf                     # Dieta de ejemplo (PDF original)
│   └── dieta-ejemplo.md                      # Dieta de ejemplo (tabla en markdown)
├── src/
│   ├── main/
│   └── test/
├── build.gradle
├── settings.gradle
└── README.md
```

## Fuentes y Referencias

### Científicas

- National Research Council (NRC). 2006. *Nutrient Requirements of Dogs and Cats*. Washington, D.C.: National Academies Press. ISBN: 978-0-309-08628-8
- USDA FoodData Central - https://fdc.nal.usda.gov/

### Datos Nutricionales

La base de datos de alimentos se alimenta desde USDA FoodData Central, que contiene información nutricional de más de 300,000 alimentos. Los datos se actualizan dos veces al año (Abril y Octubre).

## Licencia

Este proyecto está licenciado bajo la GNU General Public License v3.0. Consulta el archivo [LICENSE](LICENSE) para los términos completos.
