package LAB4;

public class Vegeterianpizza extends Pizza {

    private static final double BASE_PRICE = 6.0;
    private static final double EXTRA_INGREDIENT_COST = 1.50;

    private int changesMade;

    public Vegeterianpizza(String size, String doughType) {
        super(size, doughType);
        this.changesMade = 0;

        addIngredient(new Ingredient("Tomato Sauce", 1.0, false));
        addIngredient(new Ingredient("Mozzarella",   1.5, true));
        addIngredient(new Ingredient("Mushrooms",    1.0, false));
        addIngredient(new Ingredient("Peppers",      1.0, false));
        addIngredient(new Ingredient("Olives",       1.0, false));
    }

    public void changeIngredient(String oldIngredientName, Ingredient newIngredient) {
        boolean removed = removeIngredient(oldIngredientName);
        if (removed) {
            addIngredient(newIngredient);
            changesMade++;
            if (changesMade <= 1) {
                System.out.println("Free change used! Swapped " + oldIngredientName
                        + " for " + newIngredient.getName() + ".");
            } else {
                System.out.println("Extra change (+€" + String.format("%.2f", EXTRA_INGREDIENT_COST)
                        + "): Swapped " + oldIngredientName + " for " + newIngredient.getName() + ".");
            }
        }
    }

    @Override
    public double calculatePrice() {
        int paidChanges = Math.max(0, changesMade - 1);
        return (BASE_PRICE * getSizeMultiplier()) + (paidChanges * EXTRA_INGREDIENT_COST);
    }

    public double getBasePrice() {
        return BASE_PRICE;
    }
}
