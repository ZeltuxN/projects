package vehiclemanagementsystem;

public class AdditionalSafety extends AdditionalServices {
    public AdditionalSafety() {
        super("Additional Safety", 30.0);
    }

    @Override
    public double calculateCost(int rentalDays) {
        if (rentalDays % 5 == 0) {
            return (rentalDays / 5) * 30.0;
        } else {
            return ((rentalDays / 5) + 1) * 30.0;
        }
    }
}