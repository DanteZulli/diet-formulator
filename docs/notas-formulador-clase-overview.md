# Notas Formulador MVP de Dietas (Clase 1)

## Creador de Perfiles (Datos del animal para formular las dietas)
### Datos del animal
Dependiendo la categoría, y lo seleccionado, los combos, inputs o variantes se desbloquean, liberan o agregan opciones.
- Nombre
- El animal es [Perro Adulto, Gato Adulto, Perro Cachorro, Gato Cachorro, Perra Preñada, Gata Preñada, Perra Lactancia, Gata Lactancia]
- Peso (kg/g)
- Edad (años/meses)
- Raza
- Condición Corporal (Numérico, del 1 al 5, a modo de referencia)
- Tiene el pelo negro
- Si es cachorro, edad en meses. Si es adulto, edad en años. (Computar siempre en meses)
- Para los cachorros, además se desbloquea un nuevo combobox con el peso de adulto.
- En gatos (cachorros o adultos, se agrega un checkbox para si tiene o no sobrepeso) -> Desconozco por qué sólo con gatos
- Si están preñadas, nada cambia, pero si están en lactancia, se puede agregar el número de cachorros (libre) y la cantidad de semanas de lactancia (de 1 a 4)
### Requerimientos energéticos
Estos no cambian ni agregan opciones según el animal que sea. Siempre muestran los mismos inputs.
La única excepción el campo de la ingesta calórica (que puede ponerse manual o automatizarse en base al nivel de actividad del animal definido, y es calculado en base a la información de "Datos del animal")
- Nivel de actividad (orden decreciente):
    - Gran Danes/Galgo activo
    - Terrier
    - Muy activo
    - Activo
    - Adulto jóven
    - Inactivo, senior
    - Perro canil, muy inactivo
    - Sedentario
Nota: por lo general, están entre adulto joven e inactivo, senior. La definición no la tengo clara, deberá estar en el libro. Desconozco si las categorías con nombres de perros varían según el tipo de animal (si elijo gatos, gran danes sigue siendo la categoría de actividad más alta? o cambia a una que represente lo mismo pero con una raza gatuna?)
- Peso ideal (libre, kg/g)
- Esta en su peso ideal (checkbox)
- Ingesta calórica (dato calculado en función de todo lo antes mencionado, no modificable)
- Usar ingesta calórica recomendada (checkbox. si es sí, usará el valor superior para formular la dieta, si es no, usará el valor customizado [podría desearse poner más que lo recomendado para que suba de peso, o menos para que baje])
- Si la ingesta calórica recomendada está desactivada, deberemos dejarle un nuevo input con la posibilidad de agregar una nueva ingesta calórica.
- Si el peso ideal está desactivado, será obligatorio cargar el peso ideal, y la dieta debería de bajar la ingesta calórica automáticamente en su cálculo.
Este menú tiene esos dos checkbox de recomendado/ideal para permitirle al veterinario generar manualmente los valores del perfil de su paciente.
### Objetivos de Macronutrientes
Conjunto de sliders de 0 a 100, contando con:
- Proteina
- Grasa
- Carbohidratos
- Fibra (%MS)
### Motivo de la consulta
Notas extras totalmente libres.
### Imagenes
- De la cara para el perfil y la previsualizacion
- De la vista superior
- De la vista lateral

## Creador de Recetas (Dietas propiamente dichas)
Se seleccionará uno de los perfiles generados anteriormente, y se previsualizarán algunos de los datos ingresados (Nombre, edad, peso y objetivos de macronutrientes).
Desde este menú se podrá agregar alimentos para la dieta. La única forma de cambiar la receta será modificando estos alimentos.
Desde este menú, se podrá previsualizar la receta mientras se van agregando alimentos, y se podrá exportar a PDF. 

El menú de alimentos es un preseleccionado de varios alimentos ya definidos, con la posibilidad de agregar más. 
El texto a presentar es un campo compuesto por 3 cosas:
- El alimento propiamente dicho (ej. Carne molida)
- El porcentaje a modo de cantidad (ej. 3%)
- El tipo de cocción (ej. Dorada en sartén / Cocido a fuego lento)
Al momento de agregarlo a la dieta, se deberá indicar la cantidad (en gramos, miligramos, cápsulas, etc. La entidad tendrá que contar con unidad o cantidad y tipo).

La dieta propiamente dicha presentará una estructura de columnas y secciones.
Tendrá 5 columnas [Texto/Valor, Objetivo, Total, %MS, %Objetivo]
Y estará dividida en secciones [General, Proteína g, Lípidos totales g, Carbohidratos, Minerales, Vitaminas]
Texto/Valor tendrá el nombre de lo que se esté midiendo en esa línea.
Objetivo tendrá el valor objetivo para la dieta (Calculado en función del porcentaje total seleccionado en el slider del perfil)
%MS será el porcentaje de material seca.
%Objetivo que tanto está acorde del objetivo (Negro significa insuficiente, Verde suficiente [debajo del límite inferior seguro, pero cercano a], y rojo exceso [excedido del límite inferior seguro]. Este valor no es un 0/100, pudiendo representar valores más allá de esto (porcentuales) y seguir siendo verdes (ejemplo 443% en tiamina mg cómo correcto, verde). Los límites seguros son únicos para cada tipo de campo, deberán persistirse también.

Tabla de Markdown con todos los posibles valores en Texto/Valor y una dieta de ejemplo (Verificar en el libro ya que se armó con un OCR, pueden faltar cosas. Lo que nos faltan son los límites seguros inferiores):
Esto es para una dieta del perfil de una perra con los siguientes valores en "Crear Perfil" (15 años, 21 kilos, Perro Adulto, Mestizo Pastor Alemán, Condición Corporal 1, Perro Sedentario, peso ideal, proteínas 40%, grasas 20%, carbohidratos 40%, fibra %MS 5%)
Cómo podrás ver 785 es la ingesta calórica resultante recomendada de los cálculos anteriores (o impuesta por el médico veterinario en el combo). Los valores generales representados en la primer sección serán calculados según los porcentajes elegidos y conforme al total ingresado. Es decir, el único valor propuesto es la ingesta calórica total, el resto de la dieta la formulará el veterinario agregando o eliminando alimentos que contengan o no en sus haberes los nutrientes de la columna, y luego impactando así la dieta, además de evaluar el límite inferior seguro, que sigo sin saber de dónde sale, pero en el libro tiene que estar)

|  | Objetivo | Total | % MS | % Objetivo |
| :--- | :--- | :--- | :--- | :--- |
| **Energía kcal** | 785 | 790.80 | | 101% |
| Tamaño porción g | | 518.00 | | |
| Agua g | | 374.36 | | |
| Materia seca g | | 148.08 | | |
| Ceniza g | | 5.17 | 3.49% | |
| | | | | |
| **Proteína g** | 32.18 | 63.48 | 42.87% | 197% |
| Triptófano g | 0.45 | 0.51 | 0.34% | 113% |
| Treonina g | 1.37 | 2.47 | 1.67% | 180% |
| Isoleucina g | 1.18 | 2.60 | 1.75% | 221% |
| Leucina g | 2.16 | 4.57 | 3.09% | 212% |
| Lisina g | 1.08 | 4.46 | 3.01% | 413% |
| Metionina g | 1.08 | 1.45 | 0.98% | 134% |
| Metionina y Cistina g | 2.06 | 2.15 | 1.45% | 104% |
| Fenilalanina g | 1.47 | 2.49 | 1.68% | 170% |
| Fenilalanina y Tirosina g | 1.91 | 4.39 | 2.97% | 230% |
| Valina g | 1.57 | 3.09 | 2.08% | 197% |
| Arginina g | 1.08 | 3.73 | 2.52% | 346% |
| Histidina g | 0.61 | 1.66 | 1.12% | 274% |
| Taurina mg | 0.00 | 0.00 | 0.00% | |
| | | | | |
| **Lípidos totales g** | 17.66 | 44.41 | 29.99% | 251% |
| Ácido linoleico g | 3.53 | 2.93 | 1.98% | 83% |
| Ácido α-linoleico g | 0.14 | 0.33 | 0.22% | 237% |
| 20:2 n-6 c,c g | | 0.01 | 0.01% | |
| 20:3 n-6 g | | 0.01 | 0.01% | |
| Ácido araquidónico g | 0.00 | 0.41 | 0.28% | |
| 20:5 n-3 (EPA) g | 0.16 | 0.56 | 0.38% | 347% |
| 22:6 n-3 (DHA) g | 0.13 | 0.67 | 0.45% | 503% |
| EPA + DHA sin diferenciar g | | 0.00 | 0.00% | |
| EPA + DHA Total g | 0.29 | 1.23 | 0.83% | 417% |
| Omega 6 sin especificar g | | 0.00 | 0.00% | |
| Omega 3 sin especificar g | | 0.00 | 0.00% | |
| Omega 6 : Omega 3 | | 2.16 | 2.16% | 2.16 |
| | | | | |
| **Carbohidratos g** | 79.08 | 30.14 | 20.35% | 38% |
| Fibra, total g | 7.40 | 4.87 | 3.29% | 66% |
| | | | | |
| **Minerales** | | | | |
| Calcio, Ca mg | 1275.29 | 1285.85 | 0.87% | 101% |
| Hierro, Fe mg | 9.81 | 12.93 | 0.01% | 132% |
| Magnesio, Mg mg | 193.25 | 236.65 | 0.16% | 122% |
| Fósforo, P mg | 980.99 | 813.85 | 0.55% | 83% |
| Potasio, K mg | 1373.39 | 1311.60 | 0.89% | 96% |
| Sodio, Na mg | 257.02 | 453.40 | 0.31% | 176% |
| Zinc, Zn mg | 19.62 | 29.43 | 0.02% | 150% |
| Cobre, Cu mg | 1.96 | 2.49 | 0.00% | 127% |
| Manganeso, Mn mg | 1.57 | 1.90 | 0.00% | 121% |
| Selenio, Se µg | 115.76 | 158.73 | 0.00% | 137% |
| Cloruro, Cl mg | 392.40 | 0.00 | 0.00% | 0% |
| Yodo, I µg | 290.37 | 450.00 | 0.00% | 155% |
| Ca:P | 1.30 | 1.58 | 1.58% | 1.58 |
| Zn:Cu | 10.00 | 11.82 | 11.82% | 11.82 |
| | | | | |
| **Vitaminas** | | | | |
| Vitamina C mg | | 32.44 | 0.02% | |
| Tiamina mg | 0.73 | 3.21 | 0.00% | 443% |
| Riboflavina mg | 1.68 | 4.61 | 0.00% | 275% |
| Niacina mg | 5.59 | 35.36 | 0.02% | 632% |
| Ácido pantoténico mg | 4.81 | 15.19 | 0.01% | 316% |
| Vitamina B-6 mg | 0.48 | 3.86 | 0.00% | 803% |
| Folato, total µg | 87.31 | 356.15 | 0.00% | 408% |
| Colina, total mg | 549.35 | 311.22 | 0.21% | 57% |
| Betaina mg | | 14.33 | 0.01% | |
| Vitamina B-12 µg | 11.28 | 56.43 | 0.00% | 500% |
| Retinol µg | 490.49 | 1233.70 | 0.00% | 252% |
| Vitamina E mg | 14.41 | 70.22 | 0.05% | 487% |
| Vitamina D3 µg | 4.41 | 11.29 | 0.00% | 256% |
| Vitamina K (filoquinona) µg | 529.73 | 8.59 | 0.00% | 2% |

El exportar a PDF no deberá mostrar todo esto, sino sólo los ingredientes elegidos y valores porcentuales.
Estos valores se dividirán en dos grandes categorías con sub-categorías: 
- Calorías
    - Proteína
    - Grasa
    - Carbohidratos
- Materia Seca
    - Proteína
    - Grasa
    - Carbohidratos
    - Fibra
    - Ceniza
todos obviamente calculados en base a los valores de la dieta.

## Gestión de Alimentos
### Agregar Alimentos
La opción de AGREGAR ALIMENTOS al formulador no te permite agregar libremente un alimento cualquiera, sino que lo recupera de la base de datos de la USDA (U.S. Department of Agriculture) FoodData Central. 
TODAS las foundation foods están en formato JSON o CSV y se actualizan dos veces cada año (Abril y Octubre), podríamos poner un cron job que pullee este CSV y lo sirva desde alguna base local.
El json tiene 365 datos aprox por alimento.
### Crear Alimentos
También se debera de poder crear un alimento a mano, poniendo:
- Nombre
- Tipo [animal, suplemento, almidon cocido, otras plantas, receta]
- Tamaño porción
- Unidad tamaño porción [gramos, cápsulas, tabletas, cucharadas de té, cucharadas]
Y toda la información nutricional disponible para este suplemento (armar el modelo en función a la tabla superior)

# Notas el modelo de datos
Un perfil podrá tener muchas recetas de dietas, mientras que una receta de dieta sólo le podrá pertenecer a un perfil.
Todas las entidades con fecha de modificación y creación.


