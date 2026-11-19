package vehiclemanagementsystem;

public class SUV extends Vehicle {
    public SUV(String registrationNumber, String brand, String model, int creationYear, int seats) throws InvalidSeatsException {
        super(registrationNumber, brand, model, creationYear, seats, 70.0);
        if (seats > 7 || seats < 5) {
            throw new InvalidSeatsException("Seats must be (5 6 or 7)");
        }
        setMinSeats(5);
        setMaxSeats(7);
    }
}