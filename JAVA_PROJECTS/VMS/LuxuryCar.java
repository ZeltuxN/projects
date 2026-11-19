package vehiclemanagementsystem;

public class LuxuryCar extends Vehicle {
    public LuxuryCar(String registrationNumber, String brand, String model, int creationYear, int seats) throws InvalidSeatsException {
        super(registrationNumber, brand, model, creationYear, seats, 120.0);
        if (seats < 4 || seats > 5) {
            throw new InvalidSeatsException("Seats must be either 4 or 5!");
        }
        setMinSeats(4);
        setMaxSeats(5);
    }
}