package vehiclemanagementsystem;

public class Convertible extends Vehicle {
    public Convertible(String registrationNumber, String brand, String model, int creationYear, int seats) throws InvalidSeatsException {
        super(registrationNumber, brand, model, creationYear, seats, 90.0);
        if (seats < 2 || seats > 4) {
            throw new InvalidSeatsException("Seats must be 2 3 or 4!");
        }
        setMinSeats(2);
        setMaxSeats(4);
    }
}