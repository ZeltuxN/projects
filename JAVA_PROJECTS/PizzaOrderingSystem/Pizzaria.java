package LAB4;
import java.util.ArrayList;
import java.util.Scanner;

public class Pizzaria {

    private ArrayList<Pizza> order;
    private ArrayList<Ingredient> availableIngredients;

    public Pizzaria() {
        order = new ArrayList<>();
        availableIngredients = new ArrayList<>();
        loadDefaultIngredients();
    }

    private void loadDefaultIngredients() {
        availableIngredients.add(new Ingredient("Tomato Sauce", 1.0, 50, false));
        availableIngredients.add(new Ingredient("Mushrooms",    1.0, 40, false));
        availableIngredients.add(new Ingredient("Peppers",      1.0, 40, false));
        availableIngredients.add(new Ingredient("Olives",       1.0, 30, false));
        availableIngredients.add(new Ingredient("Onion",        1.0, 35, false));
        availableIngredients.add(new Ingredient("Arugula",      1.0, 25, false));
        availableIngredients.add(new Ingredient("Pineapple",    1.0, 20, false));

        availableIngredients.add(new Ingredient("Mozzarella",   1.5, 50, true));
        availableIngredients.add(new Ingredient("Pepperoni",    1.5, 30, true));
        availableIngredients.add(new Ingredient("Sausage",      1.5, 25, true));
        availableIngredients.add(new Ingredient("Bacon",        1.5, 25, true));
        availableIngredients.add(new Ingredient("Prosciutto",   1.5, 20, true));
        availableIngredients.add(new Ingredient("Parmesan",     1.5, 30, true));
        availableIngredients.add(new Ingredient("Chicken",      1.5, 20, true));
    }

    public void showAvailableIngredients() {
        System.out.println("\n--- Available Ingredients ---");
        for (int i = 0; i < availableIngredients.size(); i++) {
            Ingredient ing = availableIngredients.get(i);
            String type = ing.isAnimalBased() ? "Animal" : "Plant";
            System.out.println((i + 1) + ". " + ing.getName()
                    + " [" + type + "] - Stock: " + ing.getAvailableQuantity());
        }
    }

    public void startOrder(Scanner scanner) {
        boolean ordering = true;

        while (ordering) {
            System.out.println("\n========== PIZZA MENU ==========");
            System.out.println("1. Vegetarian Pizza  (base 6 euro)");
            System.out.println("2. Meat Lovers Pizza (base 7 euro)");
            System.out.println("3. Special Pizza     (base 8 euro)");
            System.out.println("4. Custom Pizza      (you pick the ingredients!)");
            System.out.println("================================");
            System.out.print("Choose a pizza type (1-4): ");
            int typeChoice = readInt(scanner, 1, 4);

            System.out.println("\nSizes:");
            System.out.println("1. Small   (x2)");
            System.out.println("2. Medium  (x3)");
            System.out.println("3. Large   (x4)");
            System.out.println("4. XL      (x5)");
            System.out.print("Choose a size (1-4): ");
            int sizeChoice = readInt(scanner, 1, 4);
            String size = sizeFromChoice(sizeChoice);

            System.out.println("\nDough types:");
            System.out.println("1. Thin");
            System.out.println("2. Regular");
            System.out.println("3. Cheese-Stuffed");
            System.out.print("Choose dough (1-3): ");
            int doughChoice = readInt(scanner, 1, 3);
            String dough = doughFromChoice(doughChoice);

            Pizza pizza = null;

            switch (typeChoice) {
                case 1:
                    pizza = new Vegeterianpizza(size, dough);
                    System.out.println("\nVegetarian Pizza created with default ingredients.");
                    offerIngredientChanges(scanner, pizza);
                    break;
                case 2:
                    pizza = new Meatloverspizza(size, dough);
                    System.out.println("\nMeat Lovers Pizza created with default ingredients.");
                    offerIngredientChanges(scanner, pizza);
                    break;
                case 3:
                    pizza = new Specialpizza(size, dough);
                    System.out.println("\nSpecial Pizza created with default ingredients.");
                    offerIngredientChanges(scanner, pizza);
                    break;
                case 4:
                    pizza = buildCustomPizza(scanner, size, dough);
                    break;
            }

            if (pizza != null) {
                order.add(pizza);
                System.out.println("\nPizza added to order! Price: "
                        + String.format("%.2f", pizza.calculatePrice()) + " euro");
            }

            System.out.print("\nWould you like to order another pizza? (yes/no): ");
            String answer = scanner.next().trim().toLowerCase();
            ordering = answer.equals("yes") || answer.equals("y");
        }

        printReceipt();
    }

    private Pizza buildCustomPizza(Scanner scanner, String size, String dough) {
        CustomPizza pizza = new CustomPizza(size, dough);

        System.out.println("\nBuild your Custom Pizza! (minimum 5 ingredients)");
        showAvailableIngredients();

        boolean addingIngredients = true;

        while (addingIngredients) {
            System.out.print("Enter ingredient number to add (0 to finish): ");
            int choice = readInt(scanner, 0, availableIngredients.size());

            if (choice == 0) {
                if (pizza.hasEnoughIngredients()) {
                    addingIngredients = false;
                } else {
                    System.out.println("You need at least " + pizza.getMinIngredients()
                            + " ingredients. Currently you have "
                            + pizza.getIngredients().size() + ".");
                }
            } else {
                Ingredient chosen = availableIngredients.get(choice - 1);
                pizza.addIngredient(chosen);
                System.out.println("  Added: " + chosen.getName());
            }
        }

        return pizza;
    }

    private void offerIngredientChanges(Scanner scanner, Pizza pizza) {
        boolean changing = true;

        while (changing) {
            System.out.println("Current ingredients:");
            ArrayList<Ingredient> ings = pizza.getIngredients();
            for (int i = 0; i < ings.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + ings.get(i).getName());
            }

            System.out.print("Would you like to swap an ingredient? (yes/no): ");
            String answer = scanner.next().trim().toLowerCase();

            if (answer.equals("yes") || answer.equals("y")) {
                System.out.print("Enter the number of the ingredient to REMOVE (1-"
                        + ings.size() + "): ");
                int removeIndex = readInt(scanner, 1, ings.size());
                String removedName = ings.get(removeIndex - 1).getName();

                System.out.println("\nChoose a replacement:");
                showAvailableIngredients();
                System.out.print("Enter ingredient number: ");
                int addIndex = readInt(scanner, 1, availableIngredients.size());
                Ingredient replacement = availableIngredients.get(addIndex - 1);

                if (pizza instanceof Vegeterianpizza) {
                    ((Vegeterianpizza) pizza).changeIngredient(removedName, replacement);
                } else if (pizza instanceof Meatloverspizza) {
                    ((Meatloverspizza) pizza).changeIngredient(removedName, replacement);
                } else if (pizza instanceof Specialpizza) {
                    ((Specialpizza) pizza).changeIngredient(removedName, replacement);
                }
            } else {
                changing = false;
            }
        }
    }

    public void printReceipt() {
        System.out.println("\n============ RECEIPT ============");

        double total = 0;

        for (int i = 0; i < order.size(); i++) {
            Pizza p = order.get(i);
            double price = p.calculatePrice();
            total += price;
            System.out.println("Pizza " + (i + 1) + ": " + p);
        }

        System.out.println("---------------------------------");
        System.out.println("TOTAL: " + String.format("%.2f", total) + " euro");
        System.out.println("=================================");
    }

    private int readInt(Scanner scanner, int min, int max) {
        int value;
        while (true) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.next();
            }
            System.out.print("Please enter a number between " + min + " and " + max + ": ");
        }
    }

    private String sizeFromChoice(int choice) {
        switch (choice) {
            case 1: return Pizza.SMALL;
            case 2: return Pizza.MEDIUM;
            case 3: return Pizza.LARGE;
            case 4: return Pizza.XL;
            default: return Pizza.SMALL;
        }
    }

    private String doughFromChoice(int choice) {
        switch (choice) {
            case 1: return "Thin";
            case 2: return "Regular";
            case 3: return "Cheese-Stuffed";
            default: return "Regular";
        }
    }

    public ArrayList<Pizza> getOrder() {
        return order;
    }

    public ArrayList<Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }
}