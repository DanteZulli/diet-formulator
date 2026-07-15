# Diet Formulator MVP Notes (Class 1)

## Profile Creator (Animal data for diet formulation)
### Animal data
Depending on the category and selection, combos, inputs, or variants are unlocked, enabled, or added.
- Name
- Animal type [Adult Dog, Adult Cat, Puppy, Kitten, Pregnant Dog, Pregnant Cat, Lactating Dog, Lactating Cat]
- Weight (kg/g)
- Age (years/months)
- Breed
- Body Condition (Numeric, 1 to 5, for reference)
- Has black fur
- If puppy, age in months. If adult, age in years. (Always compute in months)
- For puppies, an additional combobox is unlocked with the adult weight.
- In cats (puppies or adults, a checkbox is added for overweight status) -> I don't know why only for cats
- If pregnant, nothing changes, but if lactating, the number of puppies can be added (free input) and the number of weeks of lactation (1 to 4)
### Energy requirements
These do not change or add options based on the animal type. The same inputs are always shown.
The only exception is the caloric intake field (which can be set manually or automated based on the animal's defined activity level, and is calculated from the "Animal data" information)
- Activity level (descending order):
    - Great Dane/Greyhound active
    - Terrier
    - Very active
    - Active
    - Young adult
    - Inactive, senior
    - Kennel dog, very inactive
    - Sedentary
Note: usually they fall between young adult and inactive, senior. I don't have a clear definition, it should be in the book. I don't know if the dog-named categories vary based on animal type (if I select cats, is Great Dane still the highest activity category? or does it change to one that represents the same but with a cat breed?)
- Ideal weight (free, kg/g)
- Is at ideal weight (checkbox)
- Caloric intake (calculated based on all the above, not editable)
- Use recommended caloric intake (checkbox. If yes, it will use the upper value to formulate the diet; if no, it will use the custom value [you might want to set more than recommended for weight gain, or less for weight loss])
- If recommended caloric intake is disabled, a new input should be available to enter a custom caloric intake.
- If ideal weight is disabled, entering the ideal weight becomes mandatory, and the diet should automatically reduce the caloric intake in its calculation.
This menu has those two recommended/ideal checkboxes to allow the veterinarian to manually generate the patient profile values.
### Macronutrient Targets
Set of sliders from 0 to 100, including:
- Protein
- Fat
- Carbohydrates
- Fiber (%DM)
### Reason for consultation
Free-form extra notes.
### Images
- Face for profile and preview
- Top view
- Side view

## Recipe Creator (The actual diets)
A previously generated profile will be selected, and some of the entered data will be previewed (Name, age, weight, and macronutrient targets).
From this menu, foods can be added to the diet. The only way to change the recipe is by modifying these foods.
From this menu, the recipe can be previewed while adding foods, and can be exported to PDF.

The food menu is a preselection of several predefined foods, with the option to add more.
The displayed text is a composite field of 3 things:
- The food itself (e.g., Ground beef)
- The percentage as quantity (e.g., 3%)
- The cooking type (e.g., Pan-fried / Slow-cooked)
When adding it to the diet, the quantity must be specified (in grams, milligrams, capsules, etc. The entity must have a unit or quantity and type).

The diet itself will present a column and section structure.
It will have 5 columns [Nutrient/Value, Target, Total, %DM, %Target]
And will be divided into sections [General, Protein g, Total Lipids g, Carbohydrates, Minerals, Vitamins]
Nutrient/Value will have the name of what is being measured in that row.
Target will have the target value for the diet (Calculated based on the total percentage selected in the profile slider)
%DM will be the dry matter percentage.
%Target indicates how well the target is met (Black means insufficient, Green means adequate [below the safe lower limit, but close to it], and red means excess [exceeded the safe lower limit]. This value is not a 0/100, it can represent values beyond this (percentages) and still be green (e.g., 443% in thiamine mg is correct, green). Safe limits are unique for each field type and must also be persisted.

Markdown table with all possible Nutrient/Value entries and an example diet (Verify in the book since it was OCR'd, things may be missing. What we're missing are the safe lower limits):
This is for a diet with the following "Create Profile" values (15 years, 21 kg, Adult Dog, German Shepherd Mix, Body Condition 1, Sedentary Dog, ideal weight, protein 40%, fat 20%, carbohydrates 40%, fiber %DM 5%)
As you can see, 785 is the resulting recommended caloric intake from the previous calculations (or imposed by the veterinarian in the combo). The general values represented in the first section will be calculated based on the chosen percentages and the total entered. In other words, the only proposed value is the total caloric intake; the rest of the diet will be formulated by the veterinarian adding or removing foods that contain or don't contain the nutrients in the column, thus impacting the diet, and also evaluating the safe lower limit, which I still don't know where it comes from, but it should be in the book)

|  | Target | Total | % DM | % Target |
| :--- | :--- | :--- | :--- | :--- |
| **Energy kcal** | 785 | 790.80 | | 101% |
| Portion size g | | 518.00 | | |
| Water g | | 374.36 | | |
| Dry matter g | | 148.08 | | |
| Ash g | | 5.17 | 3.49% | |
| | | | | |
| **Protein g** | 32.18 | 63.48 | 42.87% | 197% |
| Tryptophan g | 0.45 | 0.51 | 0.34% | 113% |
| Threonine g | 1.37 | 2.47 | 1.67% | 180% |
| Isoleucine g | 1.18 | 2.60 | 1.75% | 221% |
| Leucine g | 2.16 | 4.57 | 3.09% | 212% |
| Lysine g | 1.08 | 4.46 | 3.01% | 413% |
| Methionine g | 1.08 | 1.45 | 0.98% | 134% |
| Methionine and Cystine g | 2.06 | 2.15 | 1.45% | 104% |
| Phenylalanine g | 1.47 | 2.49 | 1.68% | 170% |
| Phenylalanine and Tyrosine g | 1.91 | 4.39 | 2.97% | 230% |
| Valine g | 1.57 | 3.09 | 2.08% | 197% |
| Arginine g | 1.08 | 3.73 | 2.52% | 346% |
| Histidine g | 0.61 | 1.66 | 1.12% | 274% |
| Taurine mg | 0.00 | 0.00 | 0.00% | |
| | | | | |
| **Total Lipids g** | 17.66 | 44.41 | 29.99% | 251% |
| Linoleic acid g | 3.53 | 2.93 | 1.98% | 83% |
| alpha-Linolenic acid g | 0.14 | 0.33 | 0.22% | 237% |
| 20:2 n-6 c,c g | | 0.01 | 0.01% | |
| 20:3 n-6 g | | 0.01 | 0.01% | |
| Arachidonic acid g | 0.00 | 0.41 | 0.28% | |
| 20:5 n-3 (EPA) g | 0.16 | 0.56 | 0.38% | 347% |
| 22:6 n-3 (DHA) g | 0.13 | 0.67 | 0.45% | 503% |
| EPA + DHA undifferentiated g | | 0.00 | 0.00% | |
| EPA + DHA Total g | 0.29 | 1.23 | 0.83% | 417% |
| Omega 6 unspecified g | | 0.00 | 0.00% | |
| Omega 3 unspecified g | | 0.00 | 0.00% | |
| Omega 6 : Omega 3 | | 2.16 | 2.16% | 2.16 |
| | | | | |
| **Carbohydrates g** | 79.08 | 30.14 | 20.35% | 38% |
| Fiber, total g | 7.40 | 4.87 | 3.29% | 66% |
| | | | | |
| **Minerals** | | | | |
| Calcium, Ca mg | 1275.29 | 1285.85 | 0.87% | 101% |
| Iron, Fe mg | 9.81 | 12.93 | 0.01% | 132% |
| Magnesium, Mg mg | 193.25 | 236.65 | 0.16% | 122% |
| Phosphorus, P mg | 980.99 | 813.85 | 0.55% | 83% |
| Potassium, K mg | 1373.39 | 1311.60 | 0.89% | 96% |
| Sodium, Na mg | 257.02 | 453.40 | 0.31% | 176% |
| Zinc, Zn mg | 19.62 | 29.43 | 0.02% | 150% |
| Copper, Cu mg | 1.96 | 2.49 | 0.00% | 127% |
| Manganese, Mn mg | 1.57 | 1.90 | 0.00% | 121% |
| Selenium, Se ug | 115.76 | 158.73 | 0.00% | 137% |
| Chloride, Cl mg | 392.40 | 0.00 | 0.00% | 0% |
| Iodine, I ug | 290.37 | 450.00 | 0.00% | 155% |
| Ca:P | 1.30 | 1.58 | 1.58% | 1.58 |
| Zn:Cu | 10.00 | 11.82 | 11.82% | 11.82 |
| | | | | |
| **Vitamins** | | | | |
| Vitamin C mg | | 32.44 | 0.02% | |
| Thiamine mg | 0.73 | 3.21 | 0.00% | 443% |
| Riboflavin mg | 1.68 | 4.61 | 0.00% | 275% |
| Niacin mg | 5.59 | 35.36 | 0.02% | 632% |
| Pantothenic acid mg | 4.81 | 15.19 | 0.01% | 316% |
| Vitamin B-6 mg | 0.48 | 3.86 | 0.00% | 803% |
| Folate, total ug | 87.31 | 356.15 | 0.00% | 408% |
| Choline, total mg | 549.35 | 311.22 | 0.21% | 57% |
| Betaine mg | | 14.33 | 0.01% | |
| Vitamin B-12 ug | 11.28 | 56.43 | 0.00% | 500% |
| Retinol ug | 490.49 | 1233.70 | 0.00% | 252% |
| Vitamin E mg | 14.41 | 70.22 | 0.05% | 487% |
| Vitamin D3 ug | 4.41 | 11.29 | 0.00% | 256% |
| Vitamin K (phylloquinone) ug | 529.73 | 8.59 | 0.00% | 2% |

PDF export should not show all of this, only the selected ingredients and percentage values.
These values will be divided into two main categories with subcategories:
- Calories
    - Protein
    - Fat
    - Carbohydrates
- Dry Matter
    - Protein
    - Fat
    - Carbohydrates
    - Fiber
    - Ash
all obviously calculated based on the diet values.

## Food Management
### Adding Foods
The ADD FOODS option in the formulator does not allow you to freely add any food; instead, it retrieves from the USDA (U.S. Department of Agriculture) FoodData Central database.
ALL foundation foods are in JSON or CSV format and are updated twice a year (April and October). We could set up a cron job to pull this CSV and serve it from a local database.
The JSON has approximately 365 data points per food.
### Creating Foods
It should also be possible to manually create a food, entering:
- Name
- Type [animal, supplement, cooked starch, other plants, recipe]
- Portion size
- Portion size unit [grams, capsules, tablets, teaspoons, tablespoons]
And all available nutritional information for this supplement (build the model based on the table above)

# Data Model Notes
A profile can have many diet recipes, while a diet recipe can only belong to one profile.
All entities with modification and creation dates.
