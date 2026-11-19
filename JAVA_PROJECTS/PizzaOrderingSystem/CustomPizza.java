package LAB4;

public class CustomPizza extends Pizza {

    private static final double PLANT_INGREDIENT_PRICE  = 1.0;
    private static final double ANIMAL_INGREDIENT_PRICE = 1.5;
    private static final int    MIN_INGREDIENTS         = 5;

    public CustomPizza(String size, String doughType) {
        super(size, doughType);
    }

    public boolean hasEnoughIngredients() {
        return ingredients.size() >= MIN_INGREDIENTS;
    }

    @Override
    public double calculatePrice() {
        double ingredientTotal = 0;

        for (Ingredient ing : ingredients) {
            if (ing.isAnimalBased()) {
                ingredientTotal += ANIMAL_INGREDIENT_PRICE;
            } else {
                ingredientTotal += PLANT_INGREDIENT_PRICE;
            }
        }

        return ingredientTotal * getSizeMultiplier();
    }

    public int getMinIngredients() {
        return MIN_INGREDIENTS;
    }
}
