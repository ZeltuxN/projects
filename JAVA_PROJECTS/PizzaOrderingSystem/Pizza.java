package LAB4;

import java.util.ArrayList;

public abstract class Pizza {

    public static final String SMALL  = "Small";
    public static final String MEDIUM = "Medium";
    public static final String LARGE  = "Large";
    public static final String XL     = "XL";

    protected String size;
    protected String doughType;
    protected ArrayList<Ingredient> ingredients;

    public Pizza(String size, String doughType) {
        this.size = size;
        this.doughType = doughType;
        this.ingredients = new ArrayList<>();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDoughType() {
        return doughType;
    }

    public void setDoughType(String doughType) {
        this.doughType = doughType;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public boolean removeIngredient(String ingredientName) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equalsIgnoreCase(ingredientName)) {
                ingredients.remove(i);
                return true;
            }
        }
        System.out.println("Ingredient \"" + ingredientName + "\" not found on this pizza.");
        return false;
    }

    protected int getSizeMultiplier() {
        switch (size) {
            case SMALL:  return 2;
            case MEDIUM: return 3;
            case LARGE:  return 4;
            case XL:     return 5;
            default:     return 2;
        }
    }

    public abstract double calculatePrice();

    @Override
    public String toString() {
        String info = getClass().getSimpleName() + " | Size: " + size
                + " | Dough: " + doughType + " | Ingredients: ";
        if (ingredients.isEmpty()) {
            info += "(none)";
        } else {
            ArrayList<String> names = new ArrayList<>();
            for (Ingredient ing : ingredients) {
                names.add(ing.getName());
            }
            info += String.join(", ", names);
        }
        info += " | Price: €" + String.format("%.2f", calculatePrice());
        return info;
    }
}