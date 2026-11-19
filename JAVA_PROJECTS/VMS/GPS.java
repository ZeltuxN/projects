package vehiclemanagementsystem;

public class GPS extends AdditionalServices {
    public GPS() {
        super("GPS/navigation", 5.0);
    }

    @Override
    public double calculateCost(int rentalDays) {
        return rentalDays * 5.0;
    }
}