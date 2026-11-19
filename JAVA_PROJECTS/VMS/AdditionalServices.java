package vehiclemanagementsystem;

public abstract class AdditionalServices {
    protected String name;
    protected double cost;

    public AdditionalServices(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public abstract double calculateCost(int rentalDays);

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}