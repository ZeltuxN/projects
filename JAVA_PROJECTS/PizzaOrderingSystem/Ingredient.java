package LAB4;

public class Ingredient {

    private String name;
    private double price;
    private int availableQuantity;
    private boolean isAnimalBased;

    public Ingredient(String name, double price, int availableQuantity, boolean isAnimalBased) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.isAnimalBased = isAnimalBased;
    }

    public Ingredient(String name, double price, boolean isAnimalBased) {
        this(name, price, 0, isAnimalBased);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public boolean isAnimalBased() {
        return isAnimalBased;
    }

    public void setAnimalBased(boolean animalBased) {
        this.isAnimalBased = animalBased;
    }

    public void increaseQuantity(int amount) {
        this.availableQuantity += amount;
    }

    public boolean decreaseQuantity(int amount) {
        if (amount > this.availableQuantity) {
            System.out.println("Not enough " + name + " in stock!");
            return false;
        }
        this.availableQuantity -= amount;
        return true;
    }

    @Override
    public String toString() {
        return name + " (€" + String.format("%.2f", price) + ")";
    }
}